import io

import PIL.Image
from fastapi import FastAPI, Depends, File, UploadFile, Request
from fastapi.responses import JSONResponse, Response, FileResponse, StreamingResponse, RedirectResponse
import string
import random

from sqlalchemy import text

from db.db import init_db, get_session, engine
from db.models import Tokens, Statistics, Questions, Students, QuestionsCreate, TokenCreate, StudentsCreate, TokenView, \
    QuestionsView, StudentView, StatisticsCreate, StatisticsView, Results, School, City, SchoolView, CityView
from sqlalchemy.future import select
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy.orm import Query
import pandas as pd
from sqlalchemy.sql import func
from sqladmin import Admin, ModelView
from sqladmin.authentication import AuthenticationBackend
from typing import Optional

from PIL import Image, ImageDraw, ImageFont
from datetime import datetime


class AdminAuth(AuthenticationBackend):
    async def login(self, request: Request) -> bool:
        form = await request.form()
        username, password = form["username"], form["password"]
        request.session.update({"token": "..."})
        if password == "admin" and username == "admin":
            return True
        return False

    async def logout(self, request: Request) -> bool:
        request.session.clear()
        return True

    async def authenticate(self, request: Request) -> Optional[RedirectResponse]:
        token = request.session.get("token")

        if not token:
            return RedirectResponse(request.url_for("admin:login"), status_code=302)


app = FastAPI()
authentication_backend = AdminAuth(secret_key="123")
admin = Admin(app, engine=engine, authentication_backend=authentication_backend)
admin.add_view(TokenView)
admin.add_view(QuestionsView)
admin.add_view(StudentView)
admin.add_view(StatisticsView)
admin.add_view(SchoolView)
admin.add_view(CityView)
tokens = dict()


@app.on_event('startup')
async def on_startup():
    await init_db()


@app.post('/token')
async def create_token(token_data: TokenCreate, students_count: int, session: AsyncSession = Depends(get_session)):
    result = []
    while students_count > 0:
        try:
            random_str = ''.join(random.choice("АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЫЬЭЮЯ" + string.digits) for _ in range(5)).upper()
            token = Tokens(**token_data.dict(), id=random_str)
            session.add(token)
            await session.commit()
            await session.refresh(token)
            students_count -= 1
            result.append(random_str)
        except Exception:
            await session.rollback()

    return JSONResponse(status_code=200, content={"tokens": result})


@app.get('/token/info')
async def get_token(token: str, session: AsyncSession = Depends(get_session)):
    token = token.upper()
    raw_tokens = await session.execute(
        select(Tokens.teacher_phone, City.city, School.school, Tokens.class_name, Tokens.id)
        .where(Tokens.id == token, School.id == Tokens.school_id, City.id == School.city_id)
    )

    current_token = raw_tokens.one()
    if current_token is None:
        return JSONResponse(content={"message": "Forbidden"}, status_code=403)

    current_token = {key: value for key, value in zip(current_token.keys(), current_token)}

    raw_students = await session.execute(select(Students).where(Students.token_id == token))
    current_student: Students = raw_students.scalar_one_or_none()

    in_use: bool = not (current_student is None)

    can_play: bool = True

    if in_use:
        raw_statistic = await session.execute(
            select(Statistics).where(Statistics.student_id == current_student.student_id))
        can_play = len(raw_statistic.all()) == 0

    current_token.update({"can_play": can_play, "in_use": in_use})

    return current_token


@app.post('/auth')
async def auth(token: str, student: StudentsCreate = None, session: AsyncSession = Depends(get_session)):
    token = token.upper()
    raw_tokens = await session.execute(select(Tokens).where(Tokens.id == token))
    current_token: Tokens = raw_tokens.scalar_one_or_none()
    if current_token is None:
        return JSONResponse(content={"message": "Forbidden"}, status_code=403)

    raw_students = await session.execute(select(Students).where(Students.token_id == current_token.id))
    current_student: Students = raw_students.scalar_one_or_none()

    if current_student is None and student is None:
        return JSONResponse(content={"message": "Body should be not empty to create"}, status_code=400)

    if current_student is None:
        new_student = Students(token_id=current_token.id, **student.dict())
        session.add(new_student)
        await session.commit()
        return JSONResponse(status_code=201, content={"token": "Student created"})

    response_student = StudentsCreate(**current_student.dict())

    raw_statistic = await session.execute(select(Statistics).where(Statistics.student_id == current_student.student_id))
    can_play: bool = len(raw_statistic.all()) == 0

    raw_school = await session.execute(
        select(City.city, School.school)
        .where(Tokens.id == token, School.id == Tokens.school_id, City.id == School.city_id)
    )
    school_info = raw_school.one()

    return JSONResponse(status_code=200, content={
        **response_student.dict(),
        "city": school_info[0],
        "school": school_info[1],
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
    token = token.upper()
    if len(results) != 5:
        return JSONResponse(content={"message": "Bad questions count. Should be 5"}, status_code=400)

    questions_ids = [x.question_id for x in results]
    if len(questions_ids) != len(set(questions_ids)):
        return JSONResponse(content={"message": "Bad content, only uniq ids"}, status_code=400)

    raw_students = await session.execute(select(Students).where(Students.token_id == token))
    current_student: Students = raw_students.scalar_one_or_none()

    raw_statistic = await session.execute(select(Statistics).where(Statistics.student_id == current_student.student_id))
    can_play: bool = len(raw_statistic.all()) == 0

    if not can_play:
        return JSONResponse(content={"message": "Only one attempt"}, status_code=403)

    raw_tokens = await session.execute(select(Tokens).where(Tokens.id == token))
    current_token: Tokens = raw_tokens.scalar_one_or_none()
    if current_token is None:
        return JSONResponse(content={"message": "Forbidden"}, status_code=403)

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
    token = token.upper()
    raw_tokens = await session.execute(select(Tokens).where(Tokens.id == token))
    current_token: Tokens = raw_tokens.scalar_one_or_none()
    if current_token is None:
        return JSONResponse(content={"message": "Forbidden"}, status_code=403)

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
    # TODO выдавать школу/город/класс
    all_students_res = await session.execute(text(
        "SELECT students.first_name, students.second_name, sum_1, cities.city, schools.school, tokens.class_name "
        "FROM (SELECT statistics.student_id as cs_id, sum(questions.coins) AS sum_1 "
        "FROM questions JOIN statistics ON statistics.question_id = questions.question_id "
        "WHERE statistics.was_answer_right = true GROUP BY statistics.student_id) as TempTable "
        "INNER JOIN students ON students.student_id = TempTable.cs_id "
        "INNER JOIN tokens ON students.token_id = tokens.id "
        "INNER JOIN schools ON tokens.school_id = schools.id "
        "INNER JOIN cities ON cities.id = schools.city_id"
    ))
    result: list = [
        {"first_name": x[0], "second_name": x[1], "score": x[2], "city": x[3], "school": x[4], "class_name": x[5]} for x
        in
        all_students_res.all()]
    result = sorted(result, key=lambda x: x.get('score'), reverse=True)
    return JSONResponse(status_code=200, content=result)


@app.get('/cities', response_model=list[City])
async def get_cities(session: AsyncSession = Depends(get_session)):
    raw_cities = await session.execute(select(City))
    current_cities: list[City] = raw_cities.scalars().all()
    return current_cities


@app.get('/schools', response_model=list[School])
async def get_schools(city_id: int, session: AsyncSession = Depends(get_session)):
    raw_schools = await session.execute(select(School).where(School.city_id == city_id))
    current_schools: list[City] = raw_schools.scalars().all()
    return current_schools


@app.get('/bot/results')
async def get_bot_results(phone: str, session: AsyncSession = Depends(get_session)):
    raw_tokens = await session.execute(select(Tokens).where(Tokens.teacher_phone == phone))
    current_tokens: list[Tokens] = raw_tokens.scalars().all()
    if len(current_tokens) == 0:
        return JSONResponse(content={"message": "Empty"}, status_code=404)

    raw_result = await session.execute(text(
        "SELECT first_name, second_name, was_answer_right, class_name, "
        "questions.question, questions.right_answer, token FROM "
        "(SELECT students.first_name as first_name, students.second_name as second_name, "
        "statistics.question_id as qid, statistics.was_answer_right as was_answer_right, "
        "tokens.class_name as class_name, tokens.id as token "
        "FROM students, statistics, tokens "
        f"WHERE tokens.teacher_phone = '{phone}' "
        "AND tokens.id = students.token_id "
        "AND statistics.student_id = students.student_id) "
        "as TempTable INNER JOIN questions ON questions.question_id = TempTable.qid"
    ))

    current_result: list = raw_result.all()

    return current_result


@app.get('/bot/classes')
async def get_teacher_classes(phone: str, session: AsyncSession = Depends(get_session)):
    raw_tokens = await session.execute(select(Tokens).where(Tokens.teacher_phone == phone))
    current_tokens: list[Tokens] = raw_tokens.scalars().all()
    teacher_classes = set([x.class_name for x in current_tokens])
    return teacher_classes


@app.get('/certificate', response_class=FileResponse)
async def get_certificate(token: str, session: AsyncSession = Depends(get_session)):
    token = token.upper()
    raw_tokens = await session.execute(select(Tokens).where(Tokens.id == token))
    current_token: Tokens = raw_tokens.scalar_one_or_none()
    if current_token is None:
        return JSONResponse(content={"message": "Forbidden"}, status_code=403)

    raw_students = await session.execute(select(Students).where(Students.token_id == current_token.id))
    current_student: Students = raw_students.scalar_one_or_none()
    if current_student is None:
        return Response(status_code=404)

    raw_school = await session.execute(select(School).where(School.id == current_token.school_id))
    current_school: School = raw_school.scalar_one_or_none()

    student_info = [current_token.class_name[0], current_token.class_name[1], current_school.school,
                    f"{current_student.first_name} {current_student.second_name}", datetime.now().strftime('%d.%m.%Y')]

    my_coords = [(0, 1075, 940, 58), (1, 1160, 940, 58),
                 (3, 1260, 1170, 120), (4, 1225, 1560, 58)]

    image = Image.open('Certificate.png')
    draw = ImageDraw.Draw(image)
    font = ImageFont.truetype('MyriadPro.ttf', 58)
    draw.text((1430, 918), student_info[2], font=font, fill=(12, 12, 12))

    for numText, coordinates_x, coordinates_y, sizeFont in my_coords:
        container_width = 1
        container_height = 1
        # Определяем координаты верхнего левого угла контейнера
        container_x = coordinates_x
        container_y = coordinates_y
        # Определяем размеры текста
        text_bbox = draw.textbbox((0, 0), student_info[numText], font=font)
        text_width = text_bbox[2] - text_bbox[0]
        text_height = text_bbox[3] - text_bbox[1]
        # Определяем координаты верхнего левого угла текста
        text_x = container_x + (container_width - text_width) / 2
        text_y = container_y + (container_height - text_height) / 2
        # Рисуем текст
        draw.text((text_x, text_y), student_info[numText], font=font,
                  fill=(12, 12, 12))

    roi_img = image.crop()

    img_byte_arr = io.BytesIO()
    roi_img.save(img_byte_arr, format='PNG')
    img_byte_arr = img_byte_arr.getvalue()
    return Response(img_byte_arr, media_type="image/png")
