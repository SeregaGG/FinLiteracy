import asyncpg
from fastapi import FastAPI, Depends, File, UploadFile
from fastapi.responses import JSONResponse, Response
import string
import random
from db.db import init_db, get_session, engine
from db.models import Tokens, Statistics, Questions, Students, QuestionsCreate, TokenCreate, StudentsCreate, TokenView, QuestionsView
from sqlalchemy.future import select
from sqlalchemy.ext.asyncio import AsyncSession
import pandas as pd
from sqlalchemy.sql import func
from itertools import chain
from sqladmin import Admin, ModelView

app = FastAPI()
admin = Admin(app, engine=engine)
admin.add_view(TokenView)
admin.add_view(QuestionsView)
tokens = dict()


@app.on_event('startup')
async def on_startup():
    await init_db()


@app.post('/token')
async def create_token(token_data: TokenCreate, session: AsyncSession = Depends(get_session)):
    random_str = ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(5)).upper()
    token = Tokens(**token_data.dict(), token_id=random_str)
    session.add(token)
    await session.commit()
    await session.refresh(token)

    return JSONResponse(status_code=200, content={"token": random_str})


@app.post('/auth')
async def get_token(token: str, student: StudentsCreate, session: AsyncSession = Depends(get_session)):
    result = await session.execute(select(Tokens).where(Tokens.token_id == token))
    current_token: Tokens = result.scalar_one_or_none()

    if current_token is None:
        return JSONResponse(status_code=304, content={"token": "Forbidden"})

    new_student = Students(city=current_token.city, school=current_token.school, class_name=current_token.class_name,
                           teacher_phone=current_token.teacher_phone, **student.dict())
    session.add(new_student)
    await session.commit()
    return JSONResponse(status_code=200, content={"token": "Token exists"})


@app.get('/questions', response_model=list[QuestionsCreate])
async def get_question(session: AsyncSession = Depends(get_session)):
    results = [
        await session.execute(select(Questions).where(Questions.location == x)) #.order_by(func.random()).limit(1))
        for x in ['bank', 'shop', 'fin_org', 'entertainment_center', 'school']
    ]
    # result = await session.execute(select(Questions).where(Questions.location == 'bank').order_by(func.random()).limit(1))
    questions: list[QuestionsCreate] = list(chain.from_iterable([x.scalars().all() for x in results]))
    return questions


@app.post('/questions')
async def get_question(questions: UploadFile = File(...), session: AsyncSession = Depends(get_session)):
    df = pd.read_excel(await questions.read())
    df_grouped = df.groupby(['question', 'right_answer', 'quest_name', 'coins', 'location'], as_index=False) \
        .agg({'wrong_answer': tuple})

    for i, r in df_grouped.iterrows():
        print(r['wrong_answer'])
        questionss = Questions(
            question=r['question'],
            wrong_answers=r['wrong_answer'],
            right_answer=r['right_answer'],
            quest_name=r['quest_name'],
            coins=r['coins'],
            location=r['location']
        )
        try:
            session.add(questionss)
            await session.commit()
        except Exception:
            await session.rollback()
            continue

    return 0
