package com.movie.recommendation.controller;

import com.movie.recommendation.entity.Review;
import com.movie.recommendation.entity.User;
import com.movie.recommendation.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MyPageController {

    private final ReviewService reviewService;

    public MyPageController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/mypage")
    public String mypage(HttpSession session,
                         Model model) {

        // 로그인 유저
        User user =
                (User) session.getAttribute("user");

        // 로그인 안했으면 로그인 페이지
        if (user == null) {
            return "redirect:/login";
        }

        // 내가 작성한 리뷰
        List<Review> reviews =
                reviewService.getMyReviews(user);

        // 긍정 리뷰 개수
        long positiveCount =
                reviews.stream()
                        .filter(r -> "POSITIVE".equals(r.getSentiment()))
                        .count();

        // 부정 리뷰 개수
        long negativeCount =
                reviews.stream()
                        .filter(r -> "NEGATIVE".equals(r.getSentiment()))
                        .count();

        model.addAttribute("user", user);

        model.addAttribute("reviews", reviews);

        model.addAttribute("positiveCount", positiveCount);

        model.addAttribute("negativeCount", negativeCount);

        return "mypage";
    }
}