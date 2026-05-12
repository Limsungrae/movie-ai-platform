package com.movie.recommendation.controller;

import com.movie.recommendation.entity.User;
import com.movie.recommendation.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // 홈 (로그인 체크)
    @GetMapping("/") public String home() { return "index"; }

    // 회원가입 페이지
    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(User user) {
        userService.register(user);
        return "redirect:/login";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(User user, HttpSession session, Model model) {

        User loginUser = userService.login(user.getUsername(), user.getPassword());

        if (loginUser != null) {
            session.setAttribute("user", loginUser);
            return "redirect:/";
        }

        model.addAttribute("error", "로그인 실패");
        return "login";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}