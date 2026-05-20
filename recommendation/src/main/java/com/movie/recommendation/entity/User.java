package com.movie.recommendation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    // PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 로그인 아이디
    @Column(unique = true, nullable = false)
    private String username;

    // 비밀번호
    @Column(nullable = false)
    private String password;

    // 이메일
    @Column(unique = true, nullable = false)
    private String email;

    // 권한
    @Column(nullable = false)
    private String role = "USER";

    // 가입일
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createDate;

    // 선호 장르
    @ElementCollection
    private List<String> genres;

    // 리뷰
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Review> reviews;
}