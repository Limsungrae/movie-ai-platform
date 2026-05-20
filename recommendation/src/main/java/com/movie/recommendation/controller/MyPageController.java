package com.movie.recommendation.controller;

import com.movie.recommendation.entity.Review;
import com.movie.recommendation.entity.User;
import com.movie.recommendation.entity.Recommendation;
import com.movie.recommendation.service.ReviewService;
import com.movie.recommendation.service.RecommendationService;
import com.movie.recommendation.repository.UserRepository; // 🌟 추가
import org.springframework.security.core.Authentication; // 🌟 추가
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MyPageController {

    private final ReviewService reviewService;
    private final RecommendationService recommendationService;
    private final UserRepository userRepository; // 🌟 유저 조회를 위해 추가

    public MyPageController(
            ReviewService reviewService,
            RecommendationService recommendationService,
            UserRepository userRepository // 🌟 주입 추가
    ) {
        this.reviewService = reviewService;
        this.recommendationService = recommendationService;
        this.userRepository = userRepository;
    }

    @GetMapping("/mypage")
    public String mypage(Authentication authentication, // 🌟 세션 대신 시큐리티 Authentication 사용
                         Model model) {

        // 로그인 안했으면 로그인 페이지로 리다이렉트
        if (authentication == null) {
            return "redirect:/login";
        }

        // 시큐리티인증 정보에서 로그인한 유저의 이메일(혹은 ID) 추출
        String email = authentication.getName();

        // DB에서 유저 객체 조회
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }

        // 내가 작성한 리뷰 조회
        List<Review> reviews = reviewService.getMyReviews(user);

        // 추천 받은 영화 데이터 가져오기
        List<Recommendation> recommendations = recommendationService.getRecommendations(user);

        // 긍정 리뷰 개수
        long positiveCount = reviews.stream()
                .filter(r -> "POSITIVE".equals(r.getSentiment()))
                .count();

        // 부정 리뷰 개수
        long negativeCount = reviews.stream()
                .filter(r -> "NEGATIVE".equals(r.getSentiment()))
                .count();

        model.addAttribute("user", user);
        model.addAttribute("reviews", reviews);
        model.addAttribute("recommendations", recommendations);
        model.addAttribute("positiveCount", positiveCount);
        model.addAttribute("negativeCount", negativeCount);

        return "mypage";
    }
}