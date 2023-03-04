import pymongo
from fastapi import FastAPI
from fastapi.responses import JSONResponse
from pydantic import BaseModel
from secrets import db_user, db_pass
import motor.motor_asyncio


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


@app.post('/token')
async def create_token(token_data: TokenClass):
    client = motor.motor_asyncio.AsyncIOMotorClient(
        f"mongodb+srv://{db_user}:{db_pass}@rating.xxrncp1.mongodb.net/?retryWrites=true&w=majority")
    # client = pymongo.MongoClient(
    #     f"mongodb+srv://{db_user}:{db_pass}@rating.xxrncp1.mongodb.net/?retryWrites=true&w=majority")
    db = client["schools_rating"]
    collection = db["tokens"]
    await collection.insert_one({"token": token_data.__str__()})
    return JSONResponse(status_code=200, content={})


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
    client = motor.motor_asyncio.AsyncIOMotorClient(
        f"mongodb+srv://{db_user}:{db_pass}@rating.xxrncp1.mongodb.net/?retryWrites=true&w=majority")
    # client = pymongo.MongoClient(
    #     f"mongodb+srv://{db_user}:{db_pass}@rating.xxrncp1.mongodb.net/?retryWrites=true&w=majority")

    db = client["schools_rating"]
    tokens_coll = db["tokens"]
    token = tokens_coll.find_one({"token": user.token})

    if token is None:
        return JSONResponse(status_code=403, content={"message": "Forbidden"})

    collection = db["users"]
    await collection.insert_one(user.dict())

    return JSONResponse(status_code=200, content={
        "city": "test",
        "name": user.username,
        "school": "test",
        "class": "test"
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
def get_question(name: str):
    client = pymongo.MongoClient(
        f"mongodb+srv://{db_user}:{db_pass}@rating.xxrncp1.mongodb.net/?retryWrites=true&w=majority")
    db = client["schools_rating"]
    collection = db["questions"]
    user: User = User(**collection.find_one({"username": name}))

    return user


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
