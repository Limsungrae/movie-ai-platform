package com.movie.recommendation.controller;

import com.movie.recommendation.dto.AnalysisResultDto;
import com.movie.recommendation.dto.PythonResponseDto;
import com.movie.recommendation.entity.Movie;
import com.movie.recommendation.entity.Review;
import com.movie.recommendation.entity.User;
import com.movie.recommendation.entity.Recommendation;
import com.movie.recommendation.service.MovieService;
import com.movie.recommendation.service.ReviewService;
import com.movie.recommendation.service.SentimentService;
import com.movie.recommendation.service.RecommendationService;
import com.movie.recommendation.repository.UserRepository;
import com.movie.recommendation.repository.MovieRepository;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
public class ReviewController {

    private final MovieService movieService;
    private final ReviewService reviewService;
    private final SentimentService sentimentService;
    private final RecommendationService recommendationService;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    // 모든 의존성 주입을 처리하는 생성자
    public ReviewController(
            MovieService movieService,
            ReviewService reviewService,
            SentimentService sentimentService,
            RecommendationService recommendationService,
            UserRepository userRepository,
            MovieRepository movieRepository
    ) {
        this.movieService = movieService;
        this.reviewService = reviewService;
        this.sentimentService = sentimentService;
        this.recommendationService = recommendationService;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    /**
     * 리뷰 작성 페이지 진입 (스프링 시큐리티 인증 반영)
     */
    @GetMapping("/movie/review/{id}")
    public String reviewWritePage(
            @PathVariable Long id,
            Model model,
            Authentication authentication
    ) {
        // 1. 비로그인 상태면 로그인 페이지로 리다이렉트
        if (authentication == null) {
            return "redirect:/login";
        }

        // 2. 시큐리티에서 인증된 이메일 주소 추출
        String email = authentication.getName();

        // 3. 우리 DB에서 실제 User 엔티티 객체 조회
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        // 4. 리뷰를 작성할 영화 조회 후 화면 전달
        Movie movie = movieService.getMovie(id);
        model.addAttribute("movie", movie);

        return "movie/review-write";
    }

    /**
     * 리뷰 등록 + 파이썬 AI 서버 연동 (감성 분석 및 연관 영화 추천 자동 저장)
     */
    @PostMapping("/movie/review/{id}")
    public String reviewSubmit(
            @PathVariable Long id,
            @RequestParam String content,
            Authentication authentication,
            Model model
    ) {
        // 1. 비로그인 회원 차단 안전장치
        if (authentication == null) {
            return "redirect:/login";
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        // 2. 현재 리뷰 대상 영화 정보 가져오기
        Movie movie = movieService.getMovie(id);

        // 3. SentimentService를 통해 파이썬 AI 서버 통신 수행
        // (내부에서 PythonResponseDto를 파싱하여 결합해오는 기존 비즈니스 로직 호출)
        AnalysisResultDto result = sentimentService.analyze(movie.getTitle(), content);

        // 긍정 확률을 기반으로 가상 별점(5점 만점) 계산 세팅
        result.setPredictedRating(result.getPositivePercent() / 20.0);

        // 4. 사용자가 작성한 리뷰 데이터(Review 테이블) 생성 및 최종 저장
        Review review = new Review();
        review.setUser(user);
        review.setMovie(movie);
        review.setContent(content);
        review.setSentiment(result.getSentiment()); // 파이썬이 판별한 "POSITIVE" 또는 "NEGATIVE"
        review.setPredictedRating(result.getPositivePercent() / 20.0);
        reviewService.save(review);

        // ====================================================================
        // 🌟 [AI 추천 파이프라인 데이터 다중 저장 로직]
        // 사용자의 리뷰가 긍정(POSITIVE)이면서, 파이썬이 던져준 추천 리스트가 존재할 때만 작동
        // ====================================================================
        if ("POSITIVE".equals(review.getSentiment()) && result.getRecommendations() != null) {

            // AnalysisResultDto 내부에 담겨 복사되어 들어온 추천 리스트를 순회합니다.
            result.getRecommendations().forEach(recDto -> {

                // 파이썬이 넘겨준 추천 영화 제목을 기반으로 Spring DB(movie 테이블) 조회
                movieRepository.findByTitle(recDto.getTitle())
                        .stream()
                        .findFirst()
                        .ifPresent(recommendedMovie -> {

                            // 우리 DB에 영화가 실존할 때만 유저-추천영화 매핑 데이터 생성
                            Recommendation recommendation = new Recommendation();
                            recommendation.setUser(user);
                            recommendation.setMovie(recommendedMovie); // 🌟 내가 쓴 영화가 아닌 '알고리즘 추천 영화' 매핑

                            // 파이썬 기반 매칭률 세팅 (0.0 ~ 1.0 사이의 비율로 저장, 예: 92% -> 0.92)
                            recommendation.setScore(recDto.getMatchScore());

                            // 마이페이지에 보일 상세 사유 텍스트 포맷팅
                            recommendation.setReason(movie.getTitle() + " 기반 AI 추천");
                            recommendation.setCreateDate(LocalDateTime.now());

                            // recommendation 테이블에 인서트 실행
                            recommendationService.save(recommendation);
                        });
            });
        }
        // ====================================================================

        // 5. 결과 페이지(analysis/result.html) 뷰 타임리프로 데이터 바인딩
        model.addAttribute("movie", movie);
        model.addAttribute("review", review);
        model.addAttribute("result", result);

        return "analysis/result";
    }
}