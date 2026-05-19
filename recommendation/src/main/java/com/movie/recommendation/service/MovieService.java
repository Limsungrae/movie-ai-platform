package com.movie.recommendation.service;

import com.movie.recommendation.entity.Movie;
import com.movie.recommendation.repository.MovieRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
@Service
public class MovieService {

    // MovieRepository 연결
    private final MovieRepository movieRepository;

    // 생성자 주입
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    // 영화 목록 조회 + 페이징 처리
    public Page<Movie> getMovieList(int page){

        /*
         page      : 현재 페이지 번호
         6         : 한 페이지에 보여줄 영화 개수

         예시
         page=0 → 1페이지
         page=1 → 2페이지
        */
        Pageable pageable = PageRequest.of(page, 6);

        // DB에서 영화 데이터를 페이지 단위로 가져옴
        return movieRepository.findAll(pageable);
    }
    // 영화 상세 조회
    public Movie getMovie(Long id){

    /*
     findById(id)
     → Optional<Movie> 반환

     없으면 예외 발생
    */
        return movieRepository.findById(id)
                .orElseThrow();
    }
    // 영화 목록 조회 (페이징)
    public Page<Movie> getMovieList(Pageable pageable) {

        return movieRepository.findAll(pageable);
    }
    public Movie findByTitle(String title) {

        return movieRepository.findByTitle(title)
                .orElse(null);
    }
    public List<Movie> getTopMovies() {

        return movieRepository.findTop6ByOrderByRatingDesc();
    }
    public Page<Movie> searchMovies(String keyword,
                                    Pageable pageable) {

        return movieRepository.findByTitleContaining(
                keyword,
                pageable
        );
    }
}