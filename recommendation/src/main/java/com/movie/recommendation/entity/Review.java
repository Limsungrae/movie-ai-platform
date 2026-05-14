package com.movie.recommendation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * 리뷰 엔티티
 *
 * 사용자가 영화에 작성한 리뷰 정보를 저장
 */
@Entity
@Getter
@Setter
public class Review {

    /**
     * 리뷰 PK
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 어떤 영화의 리뷰인지
     * 여러 리뷰 : 하나의 영화
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;

    /**
     * 어떤 사용자가 작성했는지
     * 여러 리뷰 : 한 명의 사용자
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    /**
     * 리뷰 내용
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * 감성 분석 결과
     * 예:
     * POSITIVE
     * NEGATIVE
     */
    private String sentiment;

    /**
     * AI 예측 평점
     * 예:
     * 4.5
     * 3.2
     */
    private Double predictedRating;

    /**
     * 리뷰 작성 시간
     */
    private LocalDateTime createDate;

    /**
     * DB 저장 직전에 자동 실행
     * 작성 시간 자동 저장
     */
    @PrePersist
    public void prePersist() {

        this.createDate = LocalDateTime.now();
    }
}