package com.movie.recommendation.service;

import com.movie.recommendation.entity.User;
import com.movie.recommendation.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 회원가입
     */
    public void register(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }


        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        // 기본 권한
        user.setRole("USER");

        // 저장
        userRepository.save(user);
    }

    /**
     * 이메일 중복 체크
     */
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }
    public User getUserByEmail(String email) {

        return userRepository
                .findByEmail(email)
                .orElse(null);
    }
}