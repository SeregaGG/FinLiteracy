import random
import string

import requests
import telebot
from telebot import types

from backend.models import serializers
from secrets import BOT_TOKEN, ACCESS_PASS
import backend.models.serializers

bot = telebot.TeleBot(BOT_TOKEN)

access_ids = set()
user_data = dict()

cities = ['Екатеринбург', 'Челябинск', 'Березовский']
schools = ['МОУ СОШ №110', 'МОУ СОШ №111', 'МОУ СОШ №112']


@bot.message_handler(commands=['start'])
def url(message):
    if message.text.lower() != ACCESS_PASS.lower() and not message.from_user.id in access_ids:
        bot.send_message(message.from_user.id, "Введите пароль, который вам предоставили")
        return 0

    markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
    markup.add(types.KeyboardButton(text='Send phone', request_contact=True))
    bot.send_message(message.from_user.id, 'Разрешите доступ к вашим личным данным', reply_markup=markup)


@bot.message_handler(content_types=['contact'])
def contact(message):
    if message.contact is not None:
        print(message.contact, message.from_user.id)
        current_user_data = user_data.get(message.from_user.id)

        current_user_data['phone'] = message.contact.phone_number
        user_data.update(current_user_data)

    markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
    for city in cities:
        markup.add(types.KeyboardButton(text=f'/set_city {city}'))

    bot.send_message(message.from_user.id, "Обратите внимание, что город и школу можно выбрать только из предложенных\n"
                                           "Если вы попытаетесь ввести их вручную, то заполнение начнется с начала")
    bot.send_message(message.from_user.id, "Выберите ваш город", reply_markup=markup)


@bot.message_handler(commands=['set_city'])
def url(message):
    if not message.from_user.id in access_ids:
        bot.send_message(message.from_user.id, "Введите пароль, который вам предоставили")
        return 0
    if message.contact is not None:
        print(message.contact)
    current_user_data = user_data.get(message.from_user.id)
    current_user_data['city'] = message.text.split(' ')[1]
    user_data.update(current_user_data)

    markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
    for school in schools:
        markup.add(f'/set_school {school}')
    bot.send_message(message.from_user.id, "Выберите вашу школу", reply_markup=markup)


@bot.message_handler(commands=['set_school'])
def url(message):
    if not message.from_user.id in access_ids:
        bot.send_message(message.from_user.id, "Введите пароль, который вам предоставили")
        return 0

    current_user_data = user_data.get(message.from_user.id)
    current_user_data['school'] = ' '.join(message.text.split(' ')[1:])
    user_data.update(current_user_data)

    markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
    for number in range(1, 5):
        markup.add(f'/set_number {number}')
    bot.send_message(message.from_user.id, "Выберите номер вашего класса", reply_markup=markup)


@bot.message_handler(commands=['set_number'])
def url(message):
    if not message.from_user.id in access_ids:
        bot.send_message(message.from_user.id, "Введите пароль, который вам предоставили")
        return 0

    current_user_data = user_data.get(message.from_user.id)
    current_user_data['number'] = message.text.split(' ')[1]
    user_data.update(current_user_data)

    markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
    for liter in ['А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ж']:
        markup.add(f'/set_liter {liter}')
    bot.send_message(message.from_user.id, "Выберите букву вашего класса", reply_markup=markup)


@bot.message_handler(commands=['set_liter'])
def url(message):
    if not message.from_user.id in access_ids:
        bot.send_message(message.from_user.id, "Введите пароль, который вам предоставили")
        return 0
    markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
    markup.add('/start')

    current_user_data = user_data.get(message.from_user.id)
    current_user_data['liter'] = message.text.split(' ')[1]
    user_data.update(current_user_data)
    random_str = ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(5)).upper()
    bot.send_message(message.from_user.id, f'Код доступа для учеников - {random_str}')
    bot.send_message(message.from_user.id,
                     f"{current_user_data['city']}, {current_user_data['school']}, {current_user_data['number']}{current_user_data['liter']}",
                     reply_markup=markup)
    current_token = serializers.Token(phone=current_user_data['phone'], token=random_str, city=current_user_data['city'], school=current_user_data['school'], class_name=current_user_data['number']+current_user_data['liter'])

    print(current_token.dict())
    requests.post('http://127.0.0.1:8000/token', json=current_token.dict())



@bot.message_handler(content_types=['text'])
def get_text_messages(message):
    if message.text.lower() != ACCESS_PASS.lower() and not message.from_user.id in access_ids:
        bot.send_message(message.from_user.id, "Введите пароль, который вам предоставили")
        return 0
    access_ids.add(message.from_user.id)
    user_data.update({message.from_user.id: {}})

    markup = types.ReplyKeyboardMarkup(resize_keyboard=True)
    markup.add(types.KeyboardButton(text='Send phone', request_contact=True))
    bot.send_message(message.from_user.id, 'Разрешите доступ к вашим личным данным', reply_markup=markup)


bot.polling()
