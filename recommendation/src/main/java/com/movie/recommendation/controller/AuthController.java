package com.movie.recommendation.controller;

import com.movie.recommendation.entity.User;
import com.movie.recommendation.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signup(User user,
                         @RequestParam String passwordCheck) {

        // 이메일 중복
        if (userService.existsByEmail(user.getEmail())) {
            return "redirect:/signup?error=email";
        }

        // username 중복
        if (userService.existsByUsername(user.getUsername())) {
            return "redirect:/signup?error=username";
        }

        // 비밀번호 확인
        if (!user.getPassword().equals(passwordCheck)) {
            return "redirect:/signup?error=password";
        }

        userService.register(user);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}