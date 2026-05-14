package com.movie.recommendation.service;

import com.movie.recommendation.entity.User;
import com.movie.recommendation.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(User user) {
        userRepository.save(user);
    }

    public User login(String email, String password) {

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user.get();
        }

        return null;
    }
    /**
     * 이메일 중복 체크
     */
    public boolean existsByEmail(String email) {

        return userRepository.findByEmail(email)
                .isPresent();
    }
}