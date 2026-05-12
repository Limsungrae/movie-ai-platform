package com.movie.recommendation.service;

import com.movie.recommendation.entity.Movie;
import com.movie.recommendation.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // 영화 저장
    public void save(Movie movie) {
        movieRepository.save(movie);
    }

    // 영화 전체 조회
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }
}
