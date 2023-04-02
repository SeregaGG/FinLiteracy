from pydantic import BaseModel


class Token(BaseModel):
    phone: str
    token: str
    school: str
    city: str
    class_name: str
