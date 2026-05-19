package com.movie.recommendation.repository;

import com.movie.recommendation.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface MovieRepository
        extends JpaRepository<Movie, Long> {

    boolean existsByTitle(String title);

    Optional<Movie> findByTitle(String title);

    List<Movie> findTop6ByOrderByRatingDesc();

    Page<Movie> findByTitleContaining(String keyword,
                                      Pageable pageable);
}