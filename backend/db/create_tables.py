from sqlalchemy import Boolean, Column, \
    ForeignKey, Integer, \
    String, ARRAY, Text
from utils.psql import Base, db, engine


class Tokens(Base):
    __tablename__ = 'tokens'
    token_id = Column(Integer, primary_key=True, autoincrement=True)
    city = Column(String(50), nullable=False)
    school = Column(String(200), nullable=False)
    classs = Column(String(200), nullable=False)
    teacher_phone = Column(String(200), nullable=False)


class Students(Base):
    __tablename__ = 'students'
    student_id = Column(Integer, primary_key=True, autoincrement=True)
    city = Column(String(50), nullable=False)
    school = Column(String(200), nullable=False)
    classs = Column(String(200), nullable=False)
    teacher_phone = Column(String(200), nullable=False)


class Questions(Base):
    __tablename__ = 'questions'
    question_id = Column(Integer, primary_key=True, autoincrement=True)
    question = Column(String, nullable=False)
    wrong_answers = Column(ARRAY(String), nullable=False)
    right_answer = Column(String, nullable=False)
    quest_name = Column(String, nullable=False)
    coins = Column(Integer, nullable=False)


class Statistics(Base):
    __tablename__ = 'statistics'
    statistic_id = Column(Integer, primary_key=True, autoincrement=True)
    student_id = Column(Integer, ForeignKey('students.student_id',
                                            onupdate="CASCADE",
                                            ondelete="CASCADE"), nullable=False)
    question_id = Column(Integer, ForeignKey('questions.question_id',
                                                 onupdate="CASCADE",
                                                 ondelete="CASCADE"), nullable=False)
    was_answer_right = Column(Boolean, nullable=False)
    total_coins = Column(Integer, nullable=False)


def create():
    Base.metadata.create_all(db)

Statistics.__table__.drop(engine)
Questions.__table__.drop(engine)
Students.__table__.drop(engine)
Tokens.__table__.drop(engine)
create()
