package com.movie.recommendation.repository;

import com.movie.recommendation.entity.Movie;
import com.movie.recommendation.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMovie(Movie movie);
}