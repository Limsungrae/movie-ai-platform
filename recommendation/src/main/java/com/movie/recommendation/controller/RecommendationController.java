package com.movie.recommendation.controller;

import com.movie.recommendation.entity.Recommendation;
import com.movie.recommendation.entity.User;
import com.movie.recommendation.service.RecommendationService;
import com.movie.recommendation.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * 추천 컨트롤러
 */
@Controller
public class RecommendationController {

    private final RecommendationService recommendationService;

    private final UserService userService;

    public RecommendationController(
            RecommendationService recommendationService,
            UserService userService
    ) {

        this.recommendationService = recommendationService;
        this.userService = userService;
    }

    /**
     * 추천 페이지
     */
    @GetMapping("/recommend")
    public String recommendPage(Authentication auth,
                                Model model) {

        // =========================
        // 로그인 체크
        // =========================

        if (auth == null) {

            return "redirect:/login";
        }

        // =========================
        // 로그인 이메일
        // =========================

        String email = auth.getName();

        // =========================
        // DB 유저 조회
        // =========================

        User user =
                userService.getUserByEmail(email);

        // =========================
        // 추천 목록 조회
        // =========================

        List<Recommendation> recommendations =
                recommendationService
                        .getRecommendations(user);

        // =========================
        // HTML 전달
        // =========================

        model.addAttribute(
                "recommendations",
                recommendations
        );

        return "recommend/list";
    }
}