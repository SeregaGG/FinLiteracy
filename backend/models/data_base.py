from sqlalchemy.orm import sessionmaker
from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import Column, Integer, String

SQLALCHEMY_DATABASE_URL = "sqlite:///../sql_app.db"

engine = create_engine(
    SQLALCHEMY_DATABASE_URL, connect_args={"check_same_thread": False}
)


def get_db():
    session_local = sessionmaker(autoflush=False, bind=engine)
    db = session_local()
    return db


Base = declarative_base()


class Token(Base):
    __tablename__ = "tokens"

    id = Column(Integer, primary_key=True, index=True)
    phone = Column(String)
    token = Column(String)
    school = Column(String)
    city = Column(String)
    class_name = Column(String)


class User(Base):
    __tablename__ = "users"

    id = Column(Integer, primary_key=True, index=True)
    name = Column(String)
    lastname = Column(String)
    patronymic = Column(String)
    token = Column(String)
    email = Column(String)


# Base.metadata.create_all(bind=engine)
