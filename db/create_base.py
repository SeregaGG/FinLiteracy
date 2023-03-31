import sqlalchemy as sqla
from sqlalchemy import create_engine
from sqlalchemy import Column, Integer, String, Text, Table, ForeignKey, ARRAY
from create_connection import get_session

session = get_session()

metadata = sqla.MetaData()
products = sqla.Table('Products', metadata,
                      sqla.Column('product_id', sqla.Integer, primary_key=True),
                      sqla.Column('product_id2', sqla.Text),
                      sqla.Column('product_id3', sqla.Integer)
                      )


tokens = Table('tokens', metadata,
    Column('token_id', Integer(), primary_key=True),
    Column('city', String(200), nullable=False),
    Column('school', String(200),  nullable=False),
    Column('class', Text(),  nullable=False),
    Column('teacher_phone',Text() ),
)

students = Table('students', metadata,
    Column('student_id', Integer(), primary_key=True),
    Column('city', String(200), nullable=False),
    Column('school', String(200), nullable=False),
    Column('class', String(200), nullable=False),
    Column('teacher_phone', String(200), nullable=False),
)

questions = Table('questions', metadata,
    Column('question_id', Integer(), primary_key=True),
    Column('wrong_answers', ARRAY(Text(), dimensions=5), nullable=False),
    Column('right_answer', String(200), nullable=False),
    Column('quest_name', String(200), nullable=False),
    Column('coins', Integer(), nullable=False),
)

statistics = Table('statistics', metadata,
    Column('statistic_id', Integer(), primary_key=True),
    Column('student_id', ForeignKey('students.student_id')),
    Column('question_id', ForeignKey('questions.question_id')),
    Column('was_answer_right', String(200), nullable=False),
    Column('total_coins', Integer(), nullable=False),
)

metadata.create_all(session)