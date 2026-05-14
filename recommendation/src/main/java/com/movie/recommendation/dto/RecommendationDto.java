package com.movie.recommendation.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 추천 영화 DTO
 * 감성 분석 결과 페이지에서 사용
 */
@Getter
@Setter
public class RecommendationDto {

    /**
     * 영화 ID
     */
    private Long movieId;

    /**
     * 영화 제목
     */
    private String title;

    /**
     * 영화 포스터 URL
     */
    private String posterUrl;

    /**
     * 영화 장르
     */
    private String genre;

    /**
     * 영화 평균 평점
     */
    private Double rating;

    /**
     * 추천 매칭률
     * 예: 92%
     */
    private Double matchScore;
}