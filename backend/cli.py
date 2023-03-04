import uvicorn

if __name__ == '__main__':
    uvicorn.run('main:app', reload=True, host='127.0.0.1')
