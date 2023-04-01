from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy_utils import database_exists, create_database
from local_settings import postgresql as settings

import logging

log = logging.getLogger(__name__)

def get_database():
    """
    Соединение с базой
    Возвращает:
        engine
    """
    try:
        engine = get_engine_from_settings()
        log.info("Connected to PostgreSQL database!")
    except IOError:
        log.exception("Failed to get database connection!")
        return None, 'fail'
    return engine


def get_engine_from_settings():
    """
    Настраивает соединение с базой исходя из переданных настроек.
    Вход:
        Словарь, содержащий pghost, pguser, pgpassword, pgdatabase and pgport.
    Возвращает:
        Вызывает функцию get_database возвращающую engine
    """
    keys = ['pguser','pgpasswd','pghost','pgport','pgdb']
    if not all(key in keys for key in settings.keys()):
        raise Exception('Bad config file')

    return get_engine(settings['pguser'],
                      settings['pgpasswd'],
                      settings['pghost'],
                      settings['pgport'],
                      settings['pgdb'])


def get_engine(user, passwd, host, port, db):
    """
    Get SQLalchemy engine using credentials.
    Input:
        db: database name
        user: Username
        host: Hostname of the database server
        port: Port number
        passwd: Password for the database
    Returns:
        Database engine
    """

    url = 'postgresql://{user}:{passwd}@{host}:{port}/{db}'.format(
        user=user, passwd=passwd, host=host, port=port, db=db)
    if not database_exists(url):
        create_database(url)
    engine = create_engine(url, pool_size=50, echo=False)
    return engine


def get_session():
    """
    Возвращает SQLAlchemy session
    p.s пока хз нужно ли нам оно, посмотрим
    Input:
        engine: an SQLAlchemy engine
    """
    engine = get_database()
    session = sessionmaker(bind=engine)()
    #session = Session()
    return session


db = get_database()
session = get_session()
Base = declarative_base()