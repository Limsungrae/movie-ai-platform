package com.movie.recommendation.controller;

import com.movie.recommendation.dto.AnalysisResultDto;
import com.movie.recommendation.entity.Movie;
import com.movie.recommendation.entity.Review;
import com.movie.recommendation.entity.User;
import com.movie.recommendation.service.MovieService;
import com.movie.recommendation.service.ReviewService;
import com.movie.recommendation.service.SentimentService;
import com.movie.recommendation.repository.UserRepository;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReviewController {

    private final MovieService movieService;
    private final ReviewService reviewService;
    private final SentimentService sentimentService;
    private final UserRepository userRepository;

    public ReviewController(
            MovieService movieService,
            ReviewService reviewService,
            SentimentService sentimentService,
            UserRepository userRepository
    ) {

        this.movieService = movieService;
        this.reviewService = reviewService;
        this.sentimentService = sentimentService;
        this.userRepository = userRepository;
    }

    /**
     * 리뷰 작성 페이지
     */
    @GetMapping("/movie/review/{id}")
    public String reviewWritePage(
            @PathVariable Long id,
            Model model,
            Authentication authentication
    ) {

        // 로그인 안한 경우
        if (authentication == null) {

            return "redirect:/login";
        }

        // 현재 로그인 이메일
        String email = authentication.getName();

        // DB 사용자 조회
        User user = userRepository
                .findByEmail(email)
                .orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        // 영화 조회
        Movie movie = movieService.getMovie(id);

        model.addAttribute("movie", movie);

        return "movie/review-write";
    }

    /**
     * 리뷰 저장 + 감성 분석
     */
    @PostMapping("/movie/review/{id}")
    public String reviewSubmit(
            @PathVariable Long id,
            @RequestParam String content,
            Authentication authentication,
            Model model
    ) {

        // 로그인 안한 경우
        if (authentication == null) {

            return "redirect:/login";
        }

        // 로그인 이메일
        String email = authentication.getName();

        // DB 사용자 조회
        User user = userRepository
                .findByEmail(email)
                .orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        // 영화 조회
        Movie movie = movieService.getMovie(id);

        // 감성 분석
        AnalysisResultDto result =
                sentimentService.analyze(
                        movie.getTitle(),
                        content
                );

        // 리뷰 생성
        Review review = new Review();

        review.setUser(user);

        review.setMovie(movie);

        review.setContent(content);

        review.setSentiment(result.getSentiment());

        review.setPredictedRating(
                result.getPositivePercent() / 20.0
        );

        // 저장
        reviewService.save(review);

        // 화면 전달
        model.addAttribute("movie", movie);

        model.addAttribute("review", review);

        model.addAttribute("result", result);

        return "analysis/result";
    }
}