import uvicorn
import os

if __name__ == '__main__':
    uvicorn.run('main:app', reload=True, host=os.getenv("HOST"))
