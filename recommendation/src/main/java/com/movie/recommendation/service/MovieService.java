package com.movie.recommendation.service;

import com.movie.recommendation.entity.Movie;
import com.movie.recommendation.repository.MovieRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

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
}