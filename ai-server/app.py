from fastapi import FastAPI
from pydantic import BaseModel

from main import analyze

app = FastAPI()

# =========================
# 요청 DTO
# =========================

class ReviewRequest(BaseModel):
    title: str
    review: str

# =========================
# 감성 분석 API
# =========================

@app.post("/predict")
def predict(data: ReviewRequest):

    result = analyze(
        data.title,
        data.review
    )

    return result