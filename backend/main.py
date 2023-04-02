import pymongo
import os
from fastapi import FastAPI, Depends
from fastapi.responses import JSONResponse, Response
from pydantic import BaseModel
from secrets import db_user, db_pass
import motor.motor_asyncio
import string
import random
import json
from models import data_base, serializers

data_base.Base.metadata.create_all(bind=data_base.engine)


class TokenClass(BaseModel):
    school: str
    city: str
    class_name: str


class User(BaseModel):
    name: str
    lastname: str
    patronymic: str
    token: str
    email: str


class Question(BaseModel):
    question: str
    cost: int
    variants: list[str]
    lvl: int
    answer: int


class Results(BaseModel):
    user: User
    score: int
    questions: dict[str, dict[str, str]]


app = FastAPI()

tokens = dict()


@app.post('/token')
async def create_token(token_data: serializers.Token):
    db = data_base.get_db()
    random_str = ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(5)).upper()
    new_token = data_base.Token(**token_data.dict())
    db.add(new_token)
    db.commit()
    # tokens.update({random_str: token_data.dict()})
    return JSONResponse(status_code=200, content={"token": random_str})

@app.get('/token')
async def get_token(r_token: str):
    db = data_base.get_db()
    current_token = db.query(data_base.Token).filter(data_base.Token.token == r_token).first()
    if current_token is None:
        return Response(status_code=304)
    return JSONResponse(status_code=200, content={"token": "Token exists"})


@app.post('/results')
async def set_results(results: Results):
    client = motor.motor_asyncio.AsyncIOMotorClient(
        f"mongodb+srv://{db_user}:{db_pass}@rating.xxrncp1.mongodb.net/?retryWrites=true&w=majority")
    db = client["schools_rating"]
    collection = db["results"]
    await collection.insert_one(results.dict())
    return JSONResponse(status_code=200, content={})


@app.post('/user')
async def user_add(user: User):
    if user.token not in tokens:
        return JSONResponse(status_code=403, content={"message": "Forbidden, bad token"})
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
    db = client["schools_rating"]
    collection = db["users"]
    user: User = User(**(await collection.find_one({"username": name})))

    return user


@app.get('/questions', response_model=list[Question])
def get_question():
    with open(os.path.dirname(__file__) + '\questions.json', encoding="utf8") as f:
        questions = json.load(f)
    result_questions = [Question(**question) for question in questions]

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
    db = client["schools_rating"]
    collection = db[scheme]
    await collection.drop()

    return 0
