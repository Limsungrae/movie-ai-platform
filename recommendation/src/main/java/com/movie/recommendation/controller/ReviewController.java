package com.movie.recommendation.controller;

import com.movie.recommendation.dto.AnalysisResultDto;
import com.movie.recommendation.entity.Movie;
import com.movie.recommendation.entity.Review;
import com.movie.recommendation.entity.User;
import com.movie.recommendation.service.MovieService;
import com.movie.recommendation.service.ReviewService;
import com.movie.recommendation.service.SentimentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReviewController {

    private final MovieService movieService;
    private final ReviewService reviewService;
    private final SentimentService sentimentService;

    public ReviewController(MovieService movieService,
                            ReviewService reviewService,
                            SentimentService sentimentService) {

        this.movieService = movieService;
        this.reviewService = reviewService;
        this.sentimentService = sentimentService;
    }

    /**
     * 리뷰 작성 페이지 이동
     */
    @GetMapping("/movie/review/{id}")
    public String reviewWritePage(@PathVariable Long id,
                                  Model model,
                                  HttpSession session) {

        // =========================
        // 로그인 체크
        // =========================

        User user = (User) session.getAttribute("user");

        // 로그인 안한 경우
        if (user == null) {

            return "redirect:/login";
        }

        // =========================
        // 영화 조회
        // =========================

        Movie movie = movieService.getMovie(id);

        // html 전달
        model.addAttribute("movie", movie);

        return "movie/review-write";
    }
    /**
     * 리뷰 저장 + 감성 분석
     */
    @PostMapping("/movie/review/{id}")
    public String reviewSubmit(@PathVariable Long id,
                               @RequestParam String content,
                               HttpSession session,
                               Model model) {

        // 로그인 유저 가져오기
        User user = (User) session.getAttribute("user");

        // 로그인 안한 경우
        if (user == null) {
            return "redirect:/login";
        }

        // 영화 조회
        Movie movie = movieService.getMovie(id);

        // ====================================
        // AI 감성 분석 실행
        // ====================================

        AnalysisResultDto result =
                sentimentService.analyze(
                        movie.getTitle(),
                        content
                );

        // ====================================
        // 리뷰 객체 생성
        // ====================================

        Review review = new Review();

        review.setUser(user);

        review.setMovie(movie);

        review.setContent(content);

        // 감정 결과 저장
        review.setSentiment(result.getSentiment());

        // 예측 평점 저장
        review.setPredictedRating(
                result.getPositivePercent() / 20.0
        );

        // 리뷰 저장
        reviewService.save(review);

        // ====================================
        // html 전달
        // ====================================

        model.addAttribute("movie", movie);

        model.addAttribute("review", review);

        model.addAttribute("result", result);

        // 결과 페이지 이동
        return "analysis/result";
    }
}