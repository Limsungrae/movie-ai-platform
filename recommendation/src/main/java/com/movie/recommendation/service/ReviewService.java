package com.movie.recommendation.service;

import com.movie.recommendation.entity.Movie;
import com.movie.recommendation.entity.Review;
import com.movie.recommendation.entity.User;
import com.movie.recommendation.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    // 리뷰 저장
    public void saveReview(User user, Movie movie, String content) {

        Review review = new Review();

        // 사용자 정보 저장
        review.setUser(user);

        // 영화 정보 저장
        review.setMovie(movie);

        // 리뷰 내용 저장
        review.setContent(content);

        // -----------------------------
        // 임시 AI 감정분석 결과
        // 나중에 Python AI 서버 연결 예정
        // -----------------------------

        // 감정 분석 결과
        review.setSentiment("POSITIVE");

        // AI 예측 평점
        review.setPredictedRating(4.8);

        // 리뷰 작성 시간
        review.setCreateDate(LocalDateTime.now());

        // DB 저장
        reviewRepository.save(review);
    }

    // 특정 영화 리뷰 조회
    public List<Review> getReviews(Movie movie) {

        return reviewRepository.findByMovie(movie);
    }
}