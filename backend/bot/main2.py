import random
import string
from secrets import ACCESS_PASS, BOT_TOKEN

import requests
from aiogram import Bot, Dispatcher, executor, types
from aiogram.contrib.fsm_storage.memory import MemoryStorage
from aiogram.types import (InlineKeyboardButton,
                           InlineKeyboardMarkup,
                           KeyboardButton,
                           ReplyKeyboardMarkup,
                           ReplyKeyboardRemove,
                           )

from backend.models import serializers

# Клавиатуры
ekat_city = InlineKeyboardButton('Екатеринбург', callback_data='Екатеринбург')
chel_city = InlineKeyboardButton('Челябинск', callback_data='Челябинск')
berezov_city = InlineKeyboardButton('Березовский', callback_data='Березовский')
inline_kb = InlineKeyboardMarkup(row_width=1).add(ekat_city, chel_city, berezov_city)
mbosoh110 = InlineKeyboardButton('МОУ СОШ №110', callback_data='МОУ СОШ №110')
mbosoh111 = InlineKeyboardButton('МОУ СОШ №111', callback_data='МОУ СОШ №111')
mbosoh112 = InlineKeyboardButton('МОУ СОШ №112', callback_data='МОУ СОШ №112')
inline_school = InlineKeyboardMarkup(row_width=1).add(mbosoh110, mbosoh111, mbosoh112)
a_class = InlineKeyboardButton('А', callback_data='А')
b_class = InlineKeyboardButton('Б', callback_data='Б')
v_class = InlineKeyboardButton('В', callback_data='В')
g_class = InlineKeyboardButton('Г', callback_data='Г')
d_class = InlineKeyboardButton('Д', callback_data='Д')
e_class = InlineKeyboardButton('Е', callback_data='Е')
h_class = InlineKeyboardButton('Ж', callback_data='Ж')
inline_class_liter = InlineKeyboardMarkup(row_width=1).add(a_class, b_class, v_class,
                                                      g_class, d_class, e_class,
                                                      h_class)
class1 = InlineKeyboardButton('1', callback_data='1')
class2 = InlineKeyboardButton('2', callback_data='2')
class3 = InlineKeyboardButton('3', callback_data='3')
class4 = InlineKeyboardButton('4', callback_data='4')
class5 = InlineKeyboardButton('5', callback_data='5')
class_number_inline = InlineKeyboardMarkup(row_width=1).add(class1, class2, class3,
                                                      class4, class5)


bot = Bot(token=BOT_TOKEN)
storage = MemoryStorage()
dp = Dispatcher(bot, storage=storage)
access_ids = set()
user_data = dict()

cities = ['Екатеринбург', 'Челябинск', 'Березовский']
schools = ['МОУ СОШ №110', 'МОУ СОШ №111', 'МОУ СОШ №112']

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


@dp.message_handler(content_types=['contact'])
async def contact(message):
    if message.contact is not None:
        print(message.contact, message.from_user.id)
        current_user_data = user_data.get(message.from_user.id)

        current_user_data['phone'] = message.contact.phone_number
        user_data.update(current_user_data)


    await bot.send_message(
        message.from_user.id,
        'Обратите внимание, что город и школу можно выбрать только из предложенных\n'
        'Если вы попытаетесь ввести их вручную, то заполнение начнется с начала',
    )
    await bot.send_message(
        message.from_user.id,
        'Выберите ваш город',
        reply_markup=inline_kb,
    )



# @dp.callback_query_handler(text=['Екатеринбург', 'Челябинск', 'Березовский'])
async def set_city(callback: types.CallbackQuery):
    if callback.from_user.id not in access_ids:
        await bot.send_message(
            callback.message.chat.id,
            'Введите пароль, который вам предоставили',
        )
        return 0
    if callback.message.contact is not None:
        print(callback.message.contact)
    if callback.data not in cities:
        await bot.send_message(callback.message.chat.id, 'Выберите ваш город из предложенного списка', reply_markup=inline_kb)
    current_user_data = user_data.get(callback.from_user.id)
    current_user_data['city'] = callback.data
    user_data.update(current_user_data)
    await bot.send_message(
        callback.message.chat.id,
        'Выберите вашу школу',
        reply_markup=inline_school,
    )
    await callback.answer()


# @dp.callback_query_handler(text=['МОУ СОШ №110', 'МОУ СОШ №111', 'МОУ СОШ №112'])
async def set_school(callback: types.CallbackQuery):
    if callback.from_user.id not in access_ids:
        await bot.send_message(
            callback.message.chat.id,
            'Введите пароль, который вам предоставили',
        )
        return 0

    current_user_data = user_data.get(callback.from_user.id)
    current_user_data['school'] = callback.data
    user_data.update(current_user_data)
    await bot.send_message(
        callback.message.chat.id,
        'Выберите номер вашего класса',
        reply_markup=class_number_inline,
    )
    await callback.answer()


# @dp.callback_query_handler(text=['1', '2', '3', '4', '5'])
async def set_number(callback: types.CallbackQuery):
    if callback.from_user.id not in access_ids:
        await bot.send_message(
            callback.message.chat.id,
            'Введите пароль, который вам предоставили',
        )
        return 0

    current_user_data = user_data.get(callback.from_user.id)
    current_user_data['number'] = callback.data
    user_data.update(current_user_data)
    await bot.send_message(
        callback.message.chat.id,
        'Выберите букву вашего класса',
        reply_markup=inline_class_liter,
    )
    await callback.answer()


# @dp.callback_query_handler(text=['МОУ СОШ №110', 'МОУ СОШ №111', 'МОУ СОШ №112'])
async def set_liter(callback: types.CallbackQuery):
    if callback.from_user.id not in access_ids:
        await bot.send_message(
            callback.message.chat.id,
            'Введите пароль, который вам предоставили',
        )
        return 0
    markup = types.ReplyKeyboardMarkup(resize_keyboard=True, one_time_keyboard=True)
    markup.add('/start')

    current_user_data = user_data.get(callback.from_user.id)
    current_user_data['liter'] = callback.data
    user_data.update(current_user_data)
    random_str = ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(5)).upper()
    await bot.send_message(
        callback.from_user.id,
        f'Код доступа для учеников - {random_str}',
    )
    await bot.send_message(
        callback.from_user.id,
        f"{current_user_data['city']}, "
        f"{current_user_data['school']}, "
        f"{current_user_data['number']}{current_user_data['liter']}",
        reply_markup=markup,
    )

    current_token = serializers.Token(
        phone=current_user_data['phone'],
        token=random_str,
        city=current_user_data['city'],
        school=current_user_data['school'],
        class_name=current_user_data['number'] + current_user_data['liter'],
    )
    await callback.answer()
    print(current_token.dict())
    requests.post('http://127.0.0.1:8000/token', json=current_token.dict())


@dp.message_handler(content_types=['text'])
async def get_text_messages(message):
    if message.text.lower() != ACCESS_PASS.lower() and message.from_user.id not in access_ids:
        await bot.send_message(
            message.from_user.id,
            'Введите пароль, который вам предоставили',
        )
        return 0
    access_ids.add(message.from_user.id)
    user_data.update({message.from_user.id: {}})

    markup = types.ReplyKeyboardMarkup(resize_keyboard=True, one_time_keyboard=True )
    markup.add(types.KeyboardButton(text='Send phone', request_contact=True))
    await bot.send_message(
        message.from_user.id,
        'Разрешите доступ к вашим личным данным',
        reply_markup=markup,
    )

dp.register_message_handler(url, commands=['start'])
dp.register_callback_query_handler(
    set_city,
    state='*',
    text=['Екатеринбург', 'Челябинск', 'Березовский'],
)
dp.register_callback_query_handler(
    set_school,
    state='*',
    text=['МОУ СОШ №110', 'МОУ СОШ №111', 'МОУ СОШ №112'],
)
dp.register_callback_query_handler(
    set_number,
    state='*',
    text=['1', '2', '3', '4', '5'],
)
dp.register_callback_query_handler(
    set_liter,
    state='*',
    text=['А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж'],
)


if __name__ == '__main__':
    executor.start_polling(dp, skip_updates=True)
