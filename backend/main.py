import pymongo
from fastapi import FastAPI
from fastapi.responses import JSONResponse
from pydantic import BaseModel
from secrets import db_user, db_pass, SECRET_KEY, ALGORITHM
import motor.motor_asyncio
from jose import jwt
import string
import random


class TokenClass(BaseModel):
    school: str
    city: str
    class_name: str


class User(BaseModel):
    name: str
    lastname: str
    patronymic: str
    token: str


class Question(BaseModel):
    question: str
    cost: int
    variants: list[str]
    lvl: int
    answer: str


class Results(BaseModel):
    user: User
    score: int
    questions: dict[str, dict[str, str]]


app = FastAPI()

tokens = dict()


@app.post('/token')
async def create_token(token_data: TokenClass):
    # encode_data = token_data.dict()
    # encode_jwt = jwt.encode(encode_data, SECRET_KEY, ALGORITHM)
    # tokens.append(encode_jwt)
    random_str = ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(10)).upper()
    tokens.update({random_str: token_data.dict()})
    # encode_data = ' '.join([token_data.school, token_data.class_name, token_data.city])
    # encode_jwt = [ord(symbol) for symbol in encode_data]
    # client = motor.motor_asyncio.AsyncIOMotorClient(
    #     f"mongodb+srv://{db_user}:{db_pass}@rating.xxrncp1.mongodb.net/?retryWrites=true&w=majority")
    # # client = pymongo.MongoClient(
    # #     f"mongodb+srv://{db_user}:{db_pass}@rating.xxrncp1.mongodb.net/?retryWrites=true&w=majority")
    # db = client["schools_rating"]
    # collection = db["tokens"]
    # await collection.insert_one({"token": token_data.__str__()})
    return JSONResponse(status_code=200, content={"token": random_str})


@app.post('/results')
async def set_results(results: Results):
    client = motor.motor_asyncio.AsyncIOMotorClient(
        f"mongodb+srv://{db_user}:{db_pass}@rating.xxrncp1.mongodb.net/?retryWrites=true&w=majority")
    # client = pymongo.MongoClient(
    #     f"mongodb+srv://{db_user}:{db_pass}@rating.xxrncp1.mongodb.net/?retryWrites=true&w=majority")
    db = client["schools_rating"]
    collection = db["results"]
    await collection.insert_one(results.dict())
    return JSONResponse(status_code=200, content={})


@app.post('/user')
async def user_add(user: User):
    # client = motor.motor_asyncio.AsyncIOMotorClient(
    #     f"mongodb+srv://{db_user}:{db_pass}@rating.xxrncp1.mongodb.net/?retryWrites=true&w=majority")
    # # client = pymongo.MongoClient(
    # #     f"mongodb+srv://{db_user}:{db_pass}@rating.xxrncp1.mongodb.net/?retryWrites=true&w=majority")
    #
    # db = client["schools_rating"]
    # tokens_coll = db["tokens"]
    # token = tokens_coll.find_one({"token": user.token})
    #
    # if token is None:
    #     return JSONResponse(status_code=403, content={"message": "Forbidden"})

    if user.token not in tokens:
        return JSONResponse(status_code=403, content={"message": "Forbidden, bad token"})
    #
    # collection = db["users"]
    # await collection.insert_one(user.dict())
    token_data = tokens.get(user.token)
    return JSONResponse(status_code=200, content={
        "city": token_data.get("city"),
        "name": user.name,
        "lastname": user.lastname,
        "patronymic": user.patronymic,
        "school": token_data.get("school"),
        "class": token_data.get("class_name")
    })


@app.get('/user')
async def get_user(name: str):
    client = motor.motor_asyncio.AsyncIOMotorClient(
        f"mongodb+srv://{db_user}:{db_pass}@rating.xxrncp1.mongodb.net/?retryWrites=true&w=majority")
    # client = pymongo.MongoClient(
    #     f"mongodb+srv://{db_user}:{db_pass}@rating.xxrncp1.mongodb.net/?retryWrites=true&w=majority")
    db = client["schools_rating"]
    collection = db["users"]
    user: User = User(**(await collection.find_one({"username": name})))

    return user


@app.get('/questions', response_model=list[Question])
def get_question():
    questions_dict = {
        "question1": {
            "cost": 400,
            "variants": ["qwe", "ewq", "q"],
            "answer": "q",
            "lvl": 3
        },
        "question2": {
            "cost": 300,
            "variants": ["qwe", "ewq", "q"],
            "answer": "ewq",
            "lvl": 2
        },
        "question3": {
            "cost": 200,
            "variants": ["qwe", "ewq", "q"],
            "answer": "qwe",
            "lvl": 1
        }
    }
    result_questions = [Question(question=question, **values) for question, values in questions_dict.items()]

    return result_questions


@app.post('/questions')
def get_question(questions: list[Question]):
    client = pymongo.MongoClient(
        f"mongodb+srv://{db_user}:{db_pass}@rating.xxrncp1.mongodb.net/?retryWrites=true&w=majority")
    db = client["schools_rating"]
    collection = db["questions"]
    prepared_questions = [question.dict() for question in questions]
    collection.insert_many(prepared_questions)

    return 0


@app.delete('/scheme')
async def clean_scheme_by_name(scheme: str):
    client = motor.motor_asyncio.AsyncIOMotorClient(
        f"mongodb+srv://{db_user}:{db_pass}@rating.xxrncp1.mongodb.net/?retryWrites=true&w=majority")
    # client = pymongo.MongoClient(
    #     f"mongodb+srv://{db_user}:{db_pass}@rating.xxrncp1.mongodb.net/?retryWrites=true&w=majority")
    db = client["schools_rating"]
    collection = db[scheme]
    await collection.drop()

    return 0
