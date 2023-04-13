import random
import string

import requests
import telebot
from telebot import types
from backend.models import serializers
from aiogram import executor
from aiogram import Dispatcher, types
from aiogram import Bot, Dispatcher
from aiogram.contrib.fsm_storage.memory import MemoryStorage
from aiogram.dispatcher.filters import Text

bot_token = '5752954362:AAE0_BaG6xe8Vc_4OFIYLsTZpUzQjgiB0DI'

bot = Bot(token=bot_token)
storage = MemoryStorage()
dp = Dispatcher(bot, storage=storage)
access_ids = set()
user_data = dict()
ACCESS_PASS = 'старт'

cities = ['Екатеринбург', 'Челябинск', 'Березовский']
schools = ['МОУ СОШ №110', 'МОУ СОШ №111', 'МОУ СОШ №112']

@dp.message_handler(commands=['start'])
async def url(message: types.Message):
    if message.text.lower() != ACCESS_PASS.lower() and not message.from_user.id in access_ids:
        await bot.send_message(message.from_user.id, "Введите пароль, который вам предоставили")
        return 0

    markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
    markup.add(types.KeyboardButton(text='Send phone', request_contact=True))
    await bot.send_message(message.from_user.id, 'Разрешите доступ к вашим личным данным', reply_markup=markup)


@dp.message_handler(content_types=['contact'])
async def contact(message):
    if message.contact is not None:
        print(message.contact, message.from_user.id)
        current_user_data = user_data.get(message.from_user.id)

        current_user_data['phone'] = message.contact.phone_number
        user_data.update(current_user_data)

    markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
    for city in cities:
        markup.add(types.KeyboardButton(text=f'{city}'))

    await bot.send_message(message.from_user.id, "Обратите внимание, что город и школу можно выбрать только из предложенных\n"
                                           "Если вы попытаетесь ввести их вручную, то заполнение начнется с начала")
    await bot.send_message(message.from_user.id, "Выберите ваш город", reply_markup=markup)


# @dp.message_handler(commands=['set_city'])
async def set_city(message: types.Message):
    if not message.from_user.id in cities:
        markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
        for city in cities:
            markup.add(types.KeyboardButton(text=f'{city}'))
        await bot.send_message(message.from_user.id, "Выберите ваш город из предложенного списка", reply_markup=markup)
    if not message.from_user.id in access_ids:
        await bot.send_message(message.from_user.id, "Введите пароль, который вам предоставили")
        return 0
    if message.contact is not None:
        print(message.contact)
    current_user_data = user_data.get(message.from_user.id)
    current_user_data['city'] = message.text.split(' ')[1]
    user_data.update(current_user_data)

    markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
    for school in schools:
        markup.add(f'{school}')
    await bot.send_message(message.from_user.id, "Выберите вашу школу", reply_markup=markup)


# @dp.message_handler(commands=['set_school'])
async def set_school(message: types.Message):
    if not message.from_user.id in access_ids:
        await bot.send_message(message.from_user.id, "Введите пароль, который вам предоставили")
        return 0

    current_user_data = user_data.get(message.from_user.id)
    current_user_data['school'] = ' '.join(message.text.split(' ')[1:])
    user_data.update(current_user_data)

    markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
    for number in range(1, 5):
        markup.add(f'/set_number {number}')
    await bot.send_message(message.from_user.id, "Выберите номер вашего класса", reply_markup=markup)


@dp.message_handler(commands=['set_number'])
async def url(message):
    if not message.from_user.id in access_ids:
        await bot.send_message(message.from_user.id, "Введите пароль, который вам предоставили")
        return 0

    current_user_data = user_data.get(message.from_user.id)
    current_user_data['number'] = message.text.split(' ')[1]
    user_data.update(current_user_data)

    markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
    for liter in ['А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж']:
        markup.add(f'/set_liter {liter}')
    await bot.send_message(message.from_user.id, "Выберите букву вашего класса", reply_markup=markup)


@dp.message_handler(commands=['set_liter'])
async def url(message):
    if not message.from_user.id in access_ids:
        await bot.send_message(message.from_user.id, "Введите пароль, который вам предоставили")
        return 0
    markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
    markup.add('/start')

    current_user_data = user_data.get(message.from_user.id)
    current_user_data['liter'] = message.text.split(' ')[1]
    user_data.update(current_user_data)
    random_str = ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(5)).upper()
    await bot.send_message(message.from_user.id, f'Код доступа для учеников - {random_str}')
    await bot.send_message(message.from_user.id,
                     f"{current_user_data['city']}, {current_user_data['school']}, {current_user_data['number']}{current_user_data['liter']}",
                     reply_markup=markup)

    current_token = serializers.Token(phone=current_user_data['phone'], token=random_str, city=current_user_data['city'], school=current_user_data['school'], class_name=current_user_data['number']+current_user_data['liter'])

    print(current_token.dict())
    requests.post('http://127.0.0.1:8000/token', json=current_token.dict())



@dp.message_handler(content_types=['text'])
async def get_text_messages(message):
    if message.text.lower() != ACCESS_PASS.lower() and not message.from_user.id in access_ids:
        await bot.send_message(message.from_user.id, "Введите пароль, который вам предоставили")
        return 0
    access_ids.add(message.from_user.id)
    user_data.update({message.from_user.id: {}})

    markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
    markup.add(types.KeyboardButton(text='Send phone', request_contact=True))
    await bot.send_message(message.from_user.id, 'Разрешите доступ к вашим личным данным', reply_markup=markup)


dp.register_message_handler(set_city, Text(equals=["да💫", 'Погнали!▶️']))

if __name__ == '__main__':
    executor.start_polling(dp,
                           skip_updates=True)