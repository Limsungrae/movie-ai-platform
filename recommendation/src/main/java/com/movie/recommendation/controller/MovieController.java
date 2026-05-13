package com.movie.recommendation.controller;

import com.movie.recommendation.entity.Movie;
import com.movie.recommendation.service.MovieService;

import org.springframework.data.domain.Page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MovieController {

    // MovieService 연결
    private final MovieService movieService;

    // 생성자 주입
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    /*
     영화 목록 페이지

     예시 URL
     /movie/list?page=0
     /movie/list?page=1
    */
    @GetMapping("/movie/list")
    public String movieList(

            // 현재 페이지 번호
            // 기본값은 0 (첫 페이지)
            @RequestParam(value = "page",
                    defaultValue = "0")
            int page,

            Model model) {

        // 서비스에서 페이지별 영화 데이터 가져오기
        Page<Movie> paging = movieService.getMovieList(page);

        // HTML로 데이터 전달
        model.addAttribute("paging", paging);

        // movie/list.html 로 이동
        return "movie/list";
    }
}