import enum
import numpy as np
import pandas as pd

from sqlalchemy import BigInteger, Boolean, Column, \
    Date, DateTime, Enum, Float, ForeignKey, Integer, \
    String, UniqueConstraint, and_, func, ARRAY, Text
from sqlalchemy.orm import relationship
from psql import Base, db, session


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
    wrong_answers = Column(ARRAY(Text, dimensions=5), nullable=False)
    right_answer = Column(String(200), nullable=False)
    quest_name = Column(String(200), nullable=False)
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


create()
