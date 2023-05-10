from fastapi import FastAPI, Depends, File, UploadFile
from fastapi.responses import JSONResponse, Response
import string
import random

from sqlalchemy import text

from db.db import init_db, get_session, engine
from db.models import Tokens, Statistics, Questions, Students, QuestionsCreate, TokenCreate, StudentsCreate, TokenView, \
    QuestionsView, StudentView, StatisticsCreate, StatisticsView, Results
from sqlalchemy.future import select
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy.orm import Query
import pandas as pd
from sqlalchemy.sql import func
from sqladmin import Admin, ModelView

app = FastAPI()
admin = Admin(app, engine=engine)
admin.add_view(TokenView)
admin.add_view(QuestionsView)
admin.add_view(StudentView)
admin.add_view(StatisticsView)
tokens = dict()


@app.on_event('startup')
async def on_startup():
    await init_db()


@app.post('/token')
async def create_token(token_data: TokenCreate, students_count: int, session: AsyncSession = Depends(get_session)):
    result = []
    while students_count > 0:
        try:
            random_str = ''.join(random.choice(string.ascii_uppercase + string.digits) for _ in range(5)).upper()
            token = Tokens(**token_data.dict(), current_token="random_str", id=random_str)
            session.add(token)
            await session.commit()
            await session.refresh(token)
            students_count -= 1
            result.append(random_str)
        except Exception:
            await session.rollback()

    return JSONResponse(status_code=200, content={"tokens": result})


@app.post('/auth')
async def get_token(token: str, student: StudentsCreate = None, session: AsyncSession = Depends(get_session)):
    raw_tokens = await session.execute(select(Tokens).where(Tokens.id == token))
    current_token: Tokens = raw_tokens.scalar_one_or_none()
    if current_token is None:
        return Response(status_code=304)

    raw_students = await session.execute(select(Students).where(Students.token_id == current_token.id))
    current_student: Students = raw_students.scalar_one_or_none()
    if current_student is None:
        new_student = Students(token_id=current_token.id, **student.dict())
        session.add(new_student)
        await session.commit()
        return JSONResponse(status_code=201, content={"token": "Student created"})

    response_student = StudentsCreate(**current_student.dict())

    raw_statistic = await session.execute(select(Statistics).where(Statistics.student_id == current_student.student_id))
    can_play: bool = len(raw_statistic.all()) == 0
    return JSONResponse(status_code=200, content={
        **response_student.dict(),
        "city": current_token.city,
        "school": current_token.school,
        "class_name": current_token.class_name,
        "can_play": can_play
    })


@app.get('/questions', response_model=list[QuestionsCreate])
async def get_question(session: AsyncSession = Depends(get_session)):
    # results = [
    #     await session.execute(select(Questions).where(Questions.location == x))  # .order_by(func.random()).limit(1))
    #     for x in ['bank', 'shop', 'fin_org', 'entertainment_center', 'school']
    # ]
    # # result = await session.execute(select(Questions).where(Questions.location == 'bank').order_by(func.random()).limit(1))
    # questions: list[QuestionsCreate] = list(chain.from_iterable([x.scalars().all() for x in results]))

    result = await session.execute(select(Questions))
    questions: list[QuestionsCreate] = result.scalars().all()
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

    return JSONResponse(status_code=201, content={"Message": "Questions added"})


@app.post('/results')
async def post_results(token: str, results: list[StatisticsCreate], session: AsyncSession = Depends(get_session)):
    raw_tokens = await session.execute(select(Tokens).where(Tokens.id == token))
    current_token: Tokens = raw_tokens.scalar_one_or_none()
    if current_token is None:
        return Response(status_code=304)

    raw_students = await session.execute(select(Students).where(Students.token_id == current_token.id))
    current_student: Students = raw_students.scalar_one_or_none()
    if current_student is None:
        return Response(status_code=404)

    for result in results:
        statistic = Statistics(student_id=current_student.student_id, **result.dict())
        session.add(statistic)

    await session.commit()
    return JSONResponse(status_code=201, content={"Message": "Results saved"})


@app.get('/results')
async def get_results(token: str, session: AsyncSession = Depends(get_session)):
    raw_tokens = await session.execute(select(Tokens).where(Tokens.id == token))
    current_token: Tokens = raw_tokens.scalar_one_or_none()
    if current_token is None:
        return Response(status_code=304)

    raw_students = await session.execute(select(Students).where(Students.token_id == current_token.id))
    current_student: Students = raw_students.scalar_one_or_none()
    if current_student is None:
        return Response(status_code=404)

    coins = await session.execute(
        select(func.sum(Questions.coins)).select_from(Questions, Statistics, Students)
        .join_from(Questions, Statistics, Statistics.question_id == Questions.question_id)
        .filter(Statistics.student_id == current_student.student_id)
        .filter(Statistics.was_answer_right == True)
        .group_by(Students.student_id))
    return JSONResponse(status_code=200, content={"Results": coins.scalar()})


@app.get('/results/all', response_model=list[Results])
async def get_all_results(session: AsyncSession = Depends(get_session)):
    # all_students_res = await session.execute(
    #         select(Statistics.student_id, func.sum(Questions.coins)).select_from(Questions, Statistics)
    #         .join_from(Questions, Statistics, Statistics.question_id == Questions.question_id)
    #         .where(Statistics.was_answer_right == True)
    #         .group_by(Statistics.student_id))
    #TODO выдавать школу/город/класс
    all_students_res = await session.execute(text(
        "SELECT students.first_name, students.second_name, sum_1, tokens.city, tokens.school, tokens.class_name "
        "FROM (SELECT statistics.student_id as cs_id, sum(questions.coins) AS sum_1 "
        "FROM questions JOIN statistics ON statistics.question_id = questions.question_id "
        "WHERE statistics.was_answer_right = true GROUP BY statistics.student_id) as TempTable "
        "INNER JOIN students ON students.student_id = TempTable.cs_id "
        "INNER JOIN tokens ON students.token_id = tokens.id"
    ))
    result: list = [Results(first_name=x[0], second_name=x[1], score=x[2], city=x[3], school=x[4], class_name=x[5]).dict() for x in all_students_res.all()]
    result = sorted(result, key=lambda x: x.get('score'), reverse=True)
    return JSONResponse(status_code=200, content=result)
