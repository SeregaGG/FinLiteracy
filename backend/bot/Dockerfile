FROM python:3.11

RUN mkdir -p /usr/src/backend/
WORKDIR /usr/src/backend/

COPY . /usr/src/backend/
RUN pip install --no-cache-dir -r requirements.txt
WORKDIR /usr/src/backend/bot

CMD ["python", "main2.py"]