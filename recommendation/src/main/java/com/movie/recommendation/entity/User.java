package com.movie.recommendation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    // 사용자 PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 로그인 아이디
    @Column(unique = true, nullable = false)
    private String username;

    // 비밀번호
    @Column(nullable = false)
    private String password;

    // 사용자 이메일
    @Column(unique = true)
    private String email;

    // 사용자 권한
    private String role;

    // 회원가입 날짜
    private LocalDateTime createDate;

    // =========================
    // 선호 장르
    // =========================
    private String favoriteGenre;

    // =========================
    // 작성 리뷰 목록
    // =========================
    @OneToMany(mappedBy = "user")
    private List<Review> reviews;

}