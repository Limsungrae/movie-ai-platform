package com.movie.recommendation.dto;

import java.util.List;

/**
 * 감성분석 결과 DTO
 */
public class SentimentResultDto {

    // 감정 결과
    private String sentiment;

    // 긍정 확률
    private double confidence;

    // AI 예측 평점
    private double score;

    // 핵심 키워드
    private List<String> keywords;

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
                              double score,
                              List<String> keywords) {

        this.sentiment = sentiment;
        this.confidence = confidence;
        this.score = score;
        this.keywords = keywords;
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

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }
}