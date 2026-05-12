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

        review.setUser(user);
        review.setMovie(movie);
        review.setContent(content);

        // 임시 감정분석
        review.setSentiment("POSITIVE");
        review.setScore(0.95);

        review.setCreatedAt(LocalDateTime.now());

        reviewRepository.save(review);
    }

    // 영화 리뷰 조회
    public List<Review> getReviews(Movie movie) {
        return reviewRepository.findByMovie(movie);
    }
}