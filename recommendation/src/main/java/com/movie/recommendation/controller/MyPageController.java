package com.movie.recommendation.controller;

import com.movie.recommendation.entity.Review;
import com.movie.recommendation.entity.User;
import com.movie.recommendation.service.ReviewService;
import com.movie.recommendation.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MyPageController {

    private final ReviewService reviewService;

    private final UserService userService;

    public MyPageController(
            ReviewService reviewService,
            UserService userService
    ) {

        this.reviewService = reviewService;
        this.userService = userService;
    }

    @GetMapping("/mypage")
    public String mypage(Authentication auth,
                         Model model) {

        // =========================
        // 로그인 체크
        // =========================

        if (auth == null) {

            return "redirect:/login";
        }

        // =========================
        // 현재 로그인 이메일
        // =========================

        String email = auth.getName();

        // =========================
        // DB에서 유저 조회
        // =========================

        User user =
                userService.getUserByEmail(email);

        // =========================
        // 내가 작성한 리뷰
        // =========================

        List<Review> reviews =
                reviewService.getMyReviews(user);

        // =========================
        // 긍정 리뷰 개수
        // =========================

        long positiveCount =
                reviews.stream()
                        .filter(r ->
                                "POSITIVE".equals(
                                        r.getSentiment()
                                )
                        )
                        .count();

        // =========================
        // 부정 리뷰 개수
        // =========================

        long negativeCount =
                reviews.stream()
                        .filter(r ->
                                "NEGATIVE".equals(
                                        r.getSentiment()
                                )
                        )
                        .count();

        // =========================
        // HTML 전달
        // =========================

        model.addAttribute("user", user);

        model.addAttribute("reviews", reviews);

        model.addAttribute(
                "positiveCount",
                positiveCount
        );

        model.addAttribute(
                "negativeCount",
                negativeCount
        );

        return "mypage";
    }
}