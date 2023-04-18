from sqlmodel import SQLModel, Field, Column, Integer, String, ARRAY, ForeignKey, Boolean


class TokensBase(SQLModel):
    city: str = Field(sa_column=Column(String(50), nullable=False))
    school: str = Field(sa_column=Column(String(200), nullable=False))
    class_name: str = Field(sa_column=Column(String(200), nullable=False))
    teacher_phone: str = Field(sa_column=Column(String(200), nullable=False))

    class Config:
        arbitrary_types_allowed = True


class Tokens(TokensBase, table=True):
    __tablename__ = 'tokens'
    token_id: str = Field(sa_column=Column(String(50), primary_key=True, autoincrement=False, unique=True))


class TokenCreate(TokensBase):
    pass


class StudentsBase(SQLModel):
    first_name: str = Field(sa_column=Column(String(200), nullable=False))
    second_name: str = Field(sa_column=Column(String(200), nullable=False))


class Students(StudentsBase, table=True):
    __tablename__ = 'students'
    student_id = Field(sa_column=Column(Integer, primary_key=True, autoincrement=True))
    city: str = Field(sa_column=Column(String(50), nullable=False))
    school: str = Field(sa_column=Column(String(200), nullable=False))
    class_name: str = Field(sa_column=Column(String(200), nullable=False))
    teacher_phone: str = Field(sa_column=Column(String(200), nullable=False))

    class Config:
        arbitrary_types_allowed = True


class StudentsCreate(StudentsBase):
    pass


class QuestionsBase(SQLModel):
    question: str = Field(sa_column=Column(String, nullable=False))
    wrong_answers: list[str] = Field(sa_column=Column(ARRAY(String), nullable=False))
    right_answer: str = Field(sa_column=Column(String, nullable=False))
    quest_name: str = Field(sa_column=Column(String, nullable=False))
    coins: int = Field(sa_column=Column(Integer, nullable=False))
    location: str = Field(sa_column=Column(String, nullable=False))

    class Config:
        arbitrary_types_allowed = True


class Questions(QuestionsBase, table=True):
    __tablename__ = 'questions'
    question_id = Field(sa_column=Column(Integer, primary_key=True, autoincrement=True))


class QuestionsCreate(QuestionsBase):
    pass


class Statistics(SQLModel, table=True):
    __tablename__ = 'statistics'
    statistic_id = Field(sa_column=Column(Integer, primary_key=True, autoincrement=True))
    student_id = Field(sa_column=Column(Integer, ForeignKey('students.student_id',
                                                            onupdate="CASCADE",
                                                            ondelete="CASCADE"), nullable=False))
    question_id = Field(sa_column=Column(Integer, ForeignKey('questions.question_id',
                                                             onupdate="CASCADE",
                                                             ondelete="CASCADE"), nullable=False))
    was_answer_right = Field(sa_column=Column(Boolean, nullable=False))
    total_coins = Field(sa_column=Column(Integer, nullable=False))

    class Config:
        arbitrary_types_allowed = True
