package com.movie.recommendation.controller;

import com.movie.recommendation.entity.Recommendation;
import com.movie.recommendation.entity.User;
import com.movie.recommendation.service.RecommendationService;
import jakarta.servlet.http.HttpSession;
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

    public RecommendationController(
            RecommendationService recommendationService) {

        this.recommendationService = recommendationService;
    }

    /**
     * 추천 페이지
     */
    @GetMapping("/recommend")
    public String recommendPage(HttpSession session,
                                Model model) {

        // 로그인 사용자
        User user =
                (User) session.getAttribute("user");

        // 로그인 안한 경우
        if (user == null) {

            return "redirect:/login";
        }

        // 추천 목록 조회
        List<Recommendation> recommendations =
                recommendationService.getRecommendations(user);

        // html 전달
        model.addAttribute(
                "recommendations",
                recommendations
        );

        return "recommend/list";
    }
}