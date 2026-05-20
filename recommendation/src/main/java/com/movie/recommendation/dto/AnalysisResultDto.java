package com.movie.recommendation.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 감성 분석 결과 DTO
 */
@Getter
@Setter
public class AnalysisResultDto {

    /**
     * 감정 결과
     * POSITIVE / NEGATIVE
     */
    private String sentiment;

    /**
     * 긍정 확률
     */
    private Double positivePercent;

    /**
     * 부정 확률
     */
    private Double negativePercent;

    /**
     * AI 분석 코멘트
     */
    private String aiComment;

    /**
     * AI 예상 평점
     */
    private Double predictedRating;

    /**
     * 추천 영화 목록
     */
    private List<RecommendationDto> recommendations;
}