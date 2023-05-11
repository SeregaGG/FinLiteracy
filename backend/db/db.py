from sqlmodel import SQLModel
import os
from sqlalchemy.ext.asyncio import AsyncSession, create_async_engine
from sqlalchemy.orm import sessionmaker
DATABASE_URL = f"postgresql+asyncpg://" \
               f"{os.getenv('POSTGRES_USER')}:" \
               f"{os.getenv('POSTGRES_PASSWORD')}@" \
               f"{os.getenv('POSTGRES_HOST')}:" \
               f"{os.getenv('POSTGRES_PORT')}/" \
               f"{os.getenv('POSTGRES_DB')}"

engine = create_async_engine(DATABASE_URL, echo=True, future=True)


async def init_db() -> None:
    async with engine.begin() as conn:
        # await conn.run_sync(SQLModel.metadata.drop_all)
        await conn.run_sync(SQLModel.metadata.create_all)


async def get_session() -> AsyncSession:
    async_session = sessionmaker(
        engine, class_=AsyncSession, expire_on_commit=False
    )
    async with async_session() as session:
        yield session
