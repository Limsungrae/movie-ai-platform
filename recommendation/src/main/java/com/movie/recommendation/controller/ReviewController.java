package com.movie.recommendation.controller;

import com.movie.recommendation.entity.Movie;
import com.movie.recommendation.entity.User;
import com.movie.recommendation.service.MovieService;
import com.movie.recommendation.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final MovieService movieService;

    public ReviewController(ReviewService reviewService,
                            MovieService movieService) {

        this.reviewService = reviewService;
        this.movieService = movieService;
    }

    // 리뷰 작성
    @PostMapping("/write/{movieId}")
    public String writeReview(@PathVariable Long movieId,
                              @RequestParam String content,
                              HttpSession session,
                              Model model) {

        // 로그인 사용자 가져오기
        User user = (User) session.getAttribute("user");

        // 로그인 안한 경우
        if(user == null) {

            return "redirect:/login";
        }

        // 영화 조회
        Movie movie = movieService.getMovie(movieId);

        // 리뷰 저장
        reviewService.saveReview(user, movie, content);

        // 영화 상세 페이지로 이동
        return "redirect:/movie/detail/" + movieId;
    }
}