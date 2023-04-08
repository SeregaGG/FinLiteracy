from fastapi import FastAPI, Depends, File, UploadFile
from fastapi.responses import JSONResponse, Response
import string
import random
from db.db import init_db, get_session
from db.models import Tokens, Statistics, Questions, Students, QuestionsCreate, TokenCreate
from sqlalchemy.future import select
from sqlalchemy.ext.asyncio import AsyncSession
import pandas as pd

app = FastAPI()

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


@app.get('/token')
async def get_token(session: AsyncSession = Depends(get_session)):
    result = await session.execute(select(Tokens))
    current_token = result.scalar_one_or_none()

    if current_token is None:
        return Response(status_code=304)

    return JSONResponse(status_code=200, content={"token": "Token exists"})


@app.get('/questions', response_model=list[QuestionsCreate])
async def get_question(session: AsyncSession = Depends(get_session)):
    result = await session.execute(select(Questions))
    questions = result.scalars().all()
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
        session.add(questionss)
        await session.commit()
    return 0
