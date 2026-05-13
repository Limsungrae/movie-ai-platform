package com.movie.recommendation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Review {

    // 리뷰 PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 어떤 영화 리뷰인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    // 어떤 사용자인지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // 리뷰 내용
    @Column(columnDefinition = "TEXT")
    private String content;

    // 감정 분석 결과
    // ex) POSITIVE / NEGATIVE
    private String sentiment;

    // AI가 예측한 평점
    // ex) 4.7
    private Double predictedRating;

    // 리뷰 작성일
    private LocalDateTime createDate;

}