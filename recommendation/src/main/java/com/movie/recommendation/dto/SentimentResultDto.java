package com.movie.recommendation.dto;

/**
 * 감성분석 결과 DTO
 *
 * Python AI 서버 또는 감성분석 서비스에서
 * 결과를 받아올 때 사용하는 객체
 */
public class SentimentResultDto {

    // 감정 결과
    // 예: POSITIVE / NEGATIVE
    private String sentiment;

    // 감정 신뢰도
    // 예: 0.95
    private double confidence;

    // 예측 평점
    // 예: 4.5
    private double score;

    /**
     * 기본 생성자
     */
    public SentimentResultDto() {
    }

    /**
     * 전체 생성자
     */
    public SentimentResultDto(String sentiment,
                              double confidence,
                              double score) {

        this.sentiment = sentiment;
        this.confidence = confidence;
        this.score = score;
    }

    // ================= Getter / Setter =================

    public String getSentiment() {
        return sentiment;
    }

    public void setSentiment(String sentiment) {
        this.sentiment = sentiment;
    }

    public double getConfidence() {
        return confidence;
    }

    public void setConfidence(double confidence) {
        this.confidence = confidence;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}