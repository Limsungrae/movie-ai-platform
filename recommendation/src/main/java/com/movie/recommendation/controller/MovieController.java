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

                            @RequestParam(
                                    value = "keyword",
                                    required = false
                            ) String keyword,

                            @PageableDefault(size = 10)
                            Pageable pageable) {

        Page<Movie> moviePage;

        // 검색어 있으면 검색
        if (keyword != null &&
                !keyword.trim().isEmpty()) {

            moviePage =
                    movieService.searchMovies(
                            keyword,
                            pageable
                    );

        } else {

            moviePage =
                    movieService.getMovieList(
                            pageable
                    );
        }

        int currentPage = moviePage.getNumber();

        int totalPages = moviePage.getTotalPages();

        int startPage =
                Math.max(currentPage - 2, 0);

        int endPage =
                Math.min(currentPage + 2,
                        totalPages - 1);

        model.addAttribute("paging", moviePage);

        model.addAttribute("startPage", startPage);

        model.addAttribute("endPage", endPage);

        model.addAttribute("keyword", keyword);

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
    @GetMapping("/")
    public String index(Model model) {

        List<Movie> movies =
                movieService.getTopMovies();

        model.addAttribute("movies", movies);

        return "index";
    }
}