package com.movie.recommendation.controller;

import com.movie.recommendation.entity.Movie;
import com.movie.recommendation.entity.Review;
import com.movie.recommendation.service.MovieService;
import com.movie.recommendation.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MovieController {

    private final MovieService movieService;
    private final ReviewService reviewService;

    public MovieController(MovieService movieService,
                           ReviewService reviewService) {

        this.movieService = movieService;
        this.reviewService = reviewService;
    }

    // 영화 목록 페이지
    @GetMapping("/movie/list")
    public String movieList(Model model,

                            // 한 페이지에 6개씩 출력
                            @PageableDefault(size = 6) Pageable pageable) {

        // 페이지 형태로 영화 조회
        Page<Movie> moviePage =
                movieService.getMovieList(pageable);

        // html 로 데이터 전달
        model.addAttribute("paging", moviePage);

        return "movie/list";
    }

    // 영화 상세 페이지
    @GetMapping("/movie/detail/{id}")
    public String movieDetail(@PathVariable Long id,
                              Model model) {

        // 영화 1개 조회
        Movie movie = movieService.getMovie(id);

        // 해당 영화 리뷰 목록 조회
        List<Review> reviews =
                reviewService.getReviews(movie);

        // html 로 전달
        model.addAttribute("movie", movie);
        model.addAttribute("reviews", reviews);

        return "movie/detail";
    }
}