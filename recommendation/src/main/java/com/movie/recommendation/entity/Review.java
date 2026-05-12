package com.movie.recommendation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 리뷰 작성자
    @ManyToOne
    private User user;

    // 리뷰 대상 영화
    @ManyToOne
    private Movie movie;

    // 리뷰 내용
    @Column(columnDefinition = "TEXT")
    private String content;

    // 감정분석 결과
    private String sentiment;

    // 긍정 점수
    private double score;

    // 작성 시간
    private LocalDateTime createdAt;
}