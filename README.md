# 🎬 CineFeel - AI 영화 리뷰 감성 분석 & 추천 시스템

AI 기반 영화 리뷰 감성 분석 및 개인화 영화 추천 플랫폼입니다.
사용자 리뷰를 분석하여 감정을 예측하고, 선호 장르 및 리뷰 데이터를 기반으로 영화를 추천합니다.

---

# 📌 프로젝트 소개

CineFeel은 사용자의 영화 리뷰를 AI가 분석하여
긍정/부정 감성을 판별하고 예측 평점을 생성하는 웹 플랫폼입니다.

또한 사용자 선호 장르 및 리뷰 데이터를 기반으로
개인화 영화 추천 기능을 제공합니다.

---

# 🛠 기술 스택

## Backend

* Java 21
* Spring Boot 3
* Spring Security
* Spring Data JPA
* REST API

## AI Server

* Python
* FastAPI
* Transformers
* KoBERT / HuggingFace

## Database

* MySQL

## Frontend

* Thymeleaf
* HTML / CSS / JavaScript

## DevOps

* AWS EC2
* Nginx
* GitHub

---

# ✨ 주요 기능

## 🔐 회원 기능

* 회원가입
* 로그인 / 로그아웃
* Spring Security 인증 처리

## 🎥 영화 기능

* 영화 목록 조회
* 영화 상세 페이지
* 영화 리뷰 작성

## 🤖 AI 감성 분석

* 리뷰 감성 분석
* 긍정 / 부정 판별
* AI 예측 평점 생성

## ⭐ 영화 추천 기능

* 사용자 선호 장르 기반 추천
* 리뷰 기반 추천 결과 제공
* 추천 점수 및 추천 사유 저장

---

# 🧠 시스템 아키텍처

```text
[ Client ]
    ↓
[ Spring Boot Server ]
    ↓ REST API
[ FastAPI AI Server ]
    ↓
[ MySQL Database ]
```

---

# 🗂 데이터베이스 구조

주요 테이블:

* users
* movie
* review
* recommendation
* user_genres

ERD 기반 관계형 데이터베이스로 설계하였으며,
AI 추천 및 감성 분석 결과 저장이 가능하도록 구성하였습니다.

---

# 🚀 배포 환경

| 항목        | 내용             |
| --------- | -------------- |
| 서버        | AWS EC2 Ubuntu |
| 웹 서버      | Nginx          |
| DB        | MySQL          |
| AI 서버     | FastAPI        |
| 애플리케이션 서버 | Spring Boot    |

---


# 👨‍💻 담당 역할

* Spring Boot 백엔드 개발
* FastAPI AI 서버 구축
* MySQL 데이터베이스 설계
* AWS EC2 서버 배포
* 영화 추천 시스템 구현
* 감성 분석 API 연동

---

# 📈 프로젝트 성과

* AI 감성 분석 기능 구현
* REST API 기반 서버 연동 성공
* AWS EC2 배포 완료
* 사용자 맞춤형 영화 추천 기능 구현

---


---
