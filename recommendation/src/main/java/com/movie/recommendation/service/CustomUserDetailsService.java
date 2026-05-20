package com.movie.recommendation.service;

import com.movie.recommendation.entity.User;
import com.movie.recommendation.repository.UserRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import org.springframework.security.core.userdetails.*;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    // DB 접근
    private final UserRepository userRepository;

    // 생성자
    public CustomUserDetailsService(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    /**
     * Spring Security가 로그인 시
     * 자동 호출하는 메서드
     */
    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        /**
         * 이메일로 사용자 찾기
         */
        User user = userRepository
                .findByEmail(email)

                .orElseThrow(() ->

                        new UsernameNotFoundException(
                                "사용자를 찾을 수 없습니다."
                        )
                );

        /**
         * Spring Security에게
         * 사용자 정보 전달
         */
        return new org.springframework.security.core.userdetails.User(

                // 로그인 아이디
                user.getEmail(),

                // 암호화된 비밀번호
                user.getPassword(),

                // 권한 목록
                List.of(
                        new SimpleGrantedAuthority(
                                "ROLE_" + user.getRole()
                        )
                )
        );
    }
}