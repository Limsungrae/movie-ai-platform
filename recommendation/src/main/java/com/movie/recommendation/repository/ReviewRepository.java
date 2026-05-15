package com.movie.recommendation.repository;

import com.movie.recommendation.entity.Movie;
import com.movie.recommendation.entity.Review;
import com.movie.recommendation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository
        extends JpaRepository<Review, Long> {

    // 특정 영화 리뷰 조회
    List<Review> findByMovie(Movie movie);

    // 특정 유저 리뷰 조회
    List<Review> findByUser(User user);
}