package com.movie.recommendation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 추천 영화 엔티티
 *
 * 어떤 사용자에게
 * 어떤 영화를 추천했는지 저장
 */
@Entity
@Getter
@Setter
public class Recommendation {

    /**
     * 추천 PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 추천 대상 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /**
     * 추천 영화
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    /**
     * AI 추천 점수
     * 예:
     * 4.8
     * 93%
     */
    private Double score;

    /**
     * 추천 이유
     *
     * 예:
     * "액션 장르 선호"
     * "긍정 리뷰 기반 추천"
     */
    @Column(length = 500)
    private String reason;

    /**
     * 추천 생성 시간
     */
    private LocalDateTime createDate;

    /**
     * DB 저장 직전 자동 실행
     */
    @PrePersist
    public void prePersist() {

        this.createDate = LocalDateTime.now();
    }
}