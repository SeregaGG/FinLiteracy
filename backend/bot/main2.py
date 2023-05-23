import json
from my_secrets import ACCESS_PASS, BOT_TOKEN
import xlsxwriter

import requests
from aiogram import Bot, Dispatcher, executor, types
from aiogram.contrib.fsm_storage.memory import MemoryStorage
from aiogram.types import InlineKeyboardButton, InlineKeyboardMarkup, InputFile

HOST = "81.200.149.240"
PORT = "8000"

bot = Bot(token=BOT_TOKEN)
storage = MemoryStorage()
dp = Dispatcher(bot, storage=storage)
access_ids = set()
user_data = dict()


def is_allowd_id(user_id: int) -> bool:
    with open("ids.txt") as file:
        lines = {line.rstrip().split("|")[0]: line.rstrip().split("|")[1] for line in file}

    return str(user_id) in list(lines.keys())


def get_phone_by_id(user_id: int):
    with open("ids.txt") as file:
        lines = {line.rstrip().split("|")[0]: line.rstrip().split("|")[1] for line in file}

    return lines.get(str(user_id))


@dp.message_handler(commands=['start'])
async def url(message: types.Message):
    if message.text.lower() != ACCESS_PASS.lower() and message.from_user.id not in access_ids:
        await bot.send_message(
            message.from_user.id,
            'Введите пароль, который вам предоставили',
        )
        return 0
    markup = types.ReplyKeyboardMarkup(resize_keyboard=True, one_time_keyboard=True)
    markup.add(types.KeyboardButton(text='Send phone', request_contact=True))
    await bot.send_message(
        message.from_user.id,
        'Разрешите доступ к вашим личным данным',
        reply_markup=markup,
    )


@dp.message_handler(commands=['result'])
async def get_result(message: types.Message):
    if not is_allowd_id(message.from_user.id):
        await bot.send_message(
            message.from_user.id,
            'Вы еще не создавали токенов'
        )

    current_user_data = user_data.get(message.from_user.id)

    result = requests.get(f"http://{HOST}:{PORT}/bot/results?phone=%2B{get_phone_by_id(message.from_user.id)[1:]}")
    result = json.loads(result.content)

    teacher_classes = set([x.get("class_name") for x in result])
    workbook = xlsxwriter.Workbook(f'{get_phone_by_id(message.from_user.id)}_results.xlsx')

    format_green = workbook.add_format({'bg_color': 'green'})
    format_red = workbook.add_format({'bg_color': 'red'})

    worksheets = [workbook.add_worksheet(x) for x in teacher_classes]
    for worksheet in worksheets:
        row = 0
        worksheet.write_row(row, 0, ("Имя", "Фамилия", "Правильно ли ответил ребенок", "Класс", "Вопрос", "Правильный ответ", "Токен"))#result[0].keys())
        row += 1
        for item in result:
            if item.get("class_name") == worksheet.name:
                item["was_answer_right"] = str(item["was_answer_right"])
                worksheet.write_row(row, 0, item.values())
                row += 1
        row += 1
        worksheet.write_blank(row, 0, " ")
        worksheet.conditional_format('A2:C100', {'type': 'text',
                                                 'criteria': 'containing',
                                                 'value': "True",
                                                 'format': format_green})

        worksheet.conditional_format('A2:C100', {'type': 'text',
                                                 'criteria': 'containing',
                                                 'value': "False",
                                                 'format': format_red})

    workbook.close()
    file = InputFile(path_or_bytesio=f'{get_phone_by_id(message.from_user.id)}_results.xlsx')
    await bot.send_document(
        message.from_user.id,
        file
    )


@dp.message_handler(content_types=['contact'])
async def contact(message):
    if message.contact is not None:
        print(message.contact, message.from_user.id)
        current_user_data = user_data.get(message.from_user.id)

        current_user_data['phone'] = message.contact.phone_number

        if not is_allowd_id(message.from_user.id):
            with open("ids.txt", "a") as myfile:
                myfile.write(f"{str(message.from_user.id)}|{message.contact.phone_number}\n")

        user_data.update(current_user_data)

    await bot.send_message(
        message.from_user.id,
        'Обратите внимание, что город и школу можно выбрать только из предложенных\n'
        'Если вы попытаетесь ввести их вручную, то заполнение начнется с начала',
    )

    cities = requests.get(f"http://{HOST}:{PORT}/cities")
    cities = json.loads(cities.content)
    cities = [InlineKeyboardButton(x.get("city"), callback_data=f'city {x.get("city")} {x.get("id")}') for x in cities]
    inline_kb = InlineKeyboardMarkup(row_width=1).add(*cities)
    await bot.send_message(
        message.from_user.id,
        'Выберите ваш город',
        reply_markup=inline_kb,
    )


# @dp.callback_query_handler(text=['Екатеринбург', 'Челябинск', 'Березовский'])
async def set_city(callback: types.CallbackQuery):
    mark, city, city_id = callback.data.split()
    current_user_data = user_data.get(callback.from_user.id)
    current_user_data['city'] = city
    user_data.update(current_user_data)

    schools = requests.get(f"http://{HOST}:{PORT}/schools?city_id={city_id}")
    schools = json.loads(schools.content)
    schools = [InlineKeyboardButton(x.get("school"), callback_data=f'school|{x.get("school")}|{x.get("id")}') for x in
               schools]
    inline_school = InlineKeyboardMarkup(row_width=1).add(*schools)

    await bot.send_message(
        callback.message.chat.id,
        'Выберите вашу школу',
        reply_markup=inline_school,
    )
    await callback.answer()


# @dp.callback_query_handler(text=['МОУ СОШ №110', 'МОУ СОШ №111', 'МОУ СОШ №112'])
async def set_school(callback: types.CallbackQuery):
    mark, school, school_id = callback.data.split("|")
    if callback.from_user.id not in access_ids:
        await bot.send_message(
            callback.message.chat.id,
            'Введите пароль, который вам предоставили',
        )
        return 0

    current_user_data = user_data.get(callback.from_user.id)
    current_user_data['school'] = (school, school_id)
    user_data.update(current_user_data)

    current_numbers = [InlineKeyboardButton(str(x), callback_data=f'number {x}') for x in range(1, 6)]
    class_number_inline = InlineKeyboardMarkup(row_width=1).add(*current_numbers)

    await bot.send_message(
        callback.message.chat.id,
        'Выберите номер вашего класса',
        reply_markup=class_number_inline,
    )
    await callback.answer()


# @dp.callback_query_handler(text=['1', '2', '3', '4', '5'])
async def set_number(callback: types.CallbackQuery):
    mark, number = callback.data.split()
    current_user_data = user_data.get(callback.from_user.id)
    current_user_data['number'] = number
    user_data.update(current_user_data)
    liters = [InlineKeyboardButton(x, callback_data=f'liter {x}') for x in ['А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж']]

    inline_class_liter = InlineKeyboardMarkup(row_width=1).add(*liters)
    await bot.send_message(
        callback.message.chat.id,
        'Выберите букву вашего класса',
        reply_markup=inline_class_liter,
    )
    await callback.answer()


# @dp.callback_query_handler(text=['МОУ СОШ №110', 'МОУ СОШ №111', 'МОУ СОШ №112'])
async def set_liter(callback: types.CallbackQuery):
    mark, liter = callback.data.split()

    markup = types.ReplyKeyboardMarkup(resize_keyboard=True, one_time_keyboard=True)
    markup.add('/start')

    current_user_data = user_data.get(callback.from_user.id)
    current_user_data['liter'] = liter
    user_data.update(current_user_data)

    tokens = requests.post(f"http://{HOST}:{PORT}/token?students_count={int(current_user_data['count'])}",
                           data=json.dumps({
                               "school_id": int(current_user_data['school'][1]),
                               "class_name": f"{current_user_data['number']}{current_user_data['liter']}",
                               "teacher_phone": current_user_data['phone']
                           }))

    new_list = json.loads(tokens.content).get('tokens')

    results_file = f'{current_user_data["phone"]}_tokens_{current_user_data["number"]}{current_user_data["liter"]}.xlsx'
    with xlsxwriter.Workbook(results_file) as workbook:
        worksheet = workbook.add_worksheet()
        for row_num, data in enumerate(new_list):
            worksheet.write(row_num, 0, data)

    file = InputFile(path_or_bytesio=results_file)
    await bot.send_document(
        callback.from_user.id,
        file
    )
    await bot.send_message(
        callback.from_user.id,
        'Для получения результатов кликните по команде /result\n Для получения дополнительных токенов вручную ВВЕДИТЕ нужное кол-во, а затем кликните по команде /start',
    )
    current_user_data['count'] = 0
    await callback.answer()


@dp.message_handler(content_types=['text'])
async def get_text_messages(message):
    if message.text.lower() != ACCESS_PASS.lower() and message.from_user.id not in access_ids:
        await bot.send_message(
            message.from_user.id,
            'Введите пароль, который вам предоставили',
        )
        return 0

    if message.from_user.id not in access_ids:
        access_ids.add(message.from_user.id)
        user_data.update({message.from_user.id: {}})
        await bot.send_message(
            message.from_user.id,
            'Введите количествое учеников',
        )
        return 0

    current_user_data = user_data.get(message.from_user.id)
    try:
        if current_user_data['count'] == 0:
            current_user_data['count'] = int(message.text.lower())
            await bot.send_message(
                message.from_user.id,
                f'Кол-во учеников = {int(message.text.lower())}',
            )
            return 0
    except KeyError:
        pass

    try:
        current_user_data['count'] = int(message.text.lower())
    except ValueError:
        await bot.send_message(
            message.from_user.id,
            'Введите количествое учеников',
        )
        return 0

    markup = types.ReplyKeyboardMarkup(resize_keyboard=True, one_time_keyboard=True)
    markup.add(types.KeyboardButton(text='Send phone', request_contact=True))
    await bot.send_message(
        message.from_user.id,
        f'Кол-во учеников - {int(message.text.lower())}.\nРазрешите доступ к вашим личным данным',
        reply_markup=markup,
    )


dp.register_message_handler(url, commands=['start'])
dp.register_callback_query_handler(
    set_city,
    state='*',
    text_startswith="city"
)
dp.register_callback_query_handler(
    set_school,
    state='*',
    text_startswith="school"
)
dp.register_callback_query_handler(
    set_number,
    state='*',
    text_startswith="number"
)
dp.register_callback_query_handler(
    set_liter,
    state='*',
    text_startswith="liter"
)

if __name__ == '__main__':
    executor.start_polling(dp, skip_updates=True)
