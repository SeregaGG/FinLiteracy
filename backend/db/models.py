from sqlmodel import SQLModel, Field, Column, Integer, String, ARRAY, ForeignKey, Boolean, Relationship
from sqladmin import ModelView
from typing import Optional
from pydantic import BaseModel, Field as PdField


class TokensBase(SQLModel):
    city: str = Field(sa_column=Column(String(50), nullable=False))
    school: str = Field(sa_column=Column(String(200), nullable=False))
    class_name: str = Field(sa_column=Column(String(200), nullable=False))
    teacher_phone: str = Field(sa_column=Column(String(200), nullable=False))

    class Config:
        arbitrary_types_allowed = True


class Tokens(TokensBase, table=True):
    __tablename__ = 'tokens'
    students: Optional["Students"] = Relationship(back_populates="stoken")
    # current_token: str = Field(sa_column=Column(String(50), unique=True))
    id: str = Field(sa_column=Column(String, primary_key=True, unique=True))


class TokenCreate(TokensBase):
    pass


class TokenView(ModelView, model=Tokens):
    column_list = [Tokens.id, Tokens.city]
    form_include_pk = True


class StudentsBase(SQLModel):
    first_name: str = Field(sa_column=Column(String(200), nullable=False))
    second_name: str = Field(sa_column=Column(String(200), nullable=False))


class Students(StudentsBase, table=True):
    __tablename__ = 'students'
    student_id = Field(sa_column=Column(Integer, primary_key=True, autoincrement=True))
    token_id: str = Field(sa_column=Column(String, ForeignKey('tokens.id', ondelete="CASCADE"), nullable=False))
    stoken: Optional[Tokens] = Relationship(back_populates="students")

    # current_token_id: str = Field(sa_column=Column(String, ForeignKey('tokens.current_token'), nullable=False))

    class Config:
        arbitrary_types_allowed = True


class StudentsCreate(StudentsBase):
    pass


class StudentView(ModelView, model=Students):
    column_list = [Students.first_name, Students.second_name, Students.token_id]
    column_searchable_list = [Students.token_id]
    form_ajax_refs = {
        'stoken': {
            'fields': ('id',)
        }
    }


class QuestionsBase(SQLModel):
    question: str = Field(sa_column=Column(String, nullable=False, unique=True))
    wrong_answers: list[str] = Field(sa_column=Column(ARRAY(String), nullable=False))
    right_answer: str = Field(sa_column=Column(String, nullable=False))
    quest_name: str = Field(sa_column=Column(String, nullable=False))
    coins: int = Field(sa_column=Column(Integer, nullable=False))
    location: str = Field(sa_column=Column(String, nullable=False))
    question_id: int = Field(sa_column=Column(Integer, primary_key=True, autoincrement=True))

    class Config:
        arbitrary_types_allowed = True


class Questions(QuestionsBase, table=True):
    __tablename__ = 'questions'


class QuestionsCreate(QuestionsBase):
    pass


class QuestionsView(ModelView, model=Questions):
    column_list = [Questions.question, Questions.quest_name]


class StatisticsBase(SQLModel):
    question_id: int = Field(
        sa_column=Column(Integer, ForeignKey('questions.question_id', ondelete="CASCADE"), nullable=False))
    was_answer_right: bool = Field(sa_column=Column(Boolean, nullable=False))

    class Config:
        arbitrary_types_allowed = True


class Statistics(StatisticsBase, table=True):
    __tablename__ = 'statistics'
    statistic_id: int = Field(sa_column=Column(Integer, primary_key=True, autoincrement=True))
    student_id: int = Field(
        sa_column=Column(Integer, ForeignKey('students.student_id', ondelete="CASCADE"), nullable=False))


class StatisticsCreate(StatisticsBase):
    pass


class StatisticsView(ModelView, model=Statistics):
    column_list = [Statistics.student_id, Statistics.statistic_id]


class Results(SQLModel):
    first_name: str = Field(sa_column=Column(String, nullable=False))
    second_name: str = Field(sa_column=Column(String, nullable=False))
    score: int = Field(sa_column=Column(Integer, nullable=False))
    city: str = Field(sa_column=Column(String, nullable=False))
    school: str = Field(sa_column=Column(String, nullable=False))
    class_name: str = Field(sa_column=Column(String, nullable=False))

    class Config:
        arbitrary_types_allowed = True
