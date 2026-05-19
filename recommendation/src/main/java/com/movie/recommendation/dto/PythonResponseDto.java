package com.movie.recommendation.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PythonResponseDto {

    private SentimentDto sentiment;

    private RecommendDto recommend;

    private int positive_count;

    private int negative_count;

    // =========================
    // 감성 분석 결과
    // =========================

    @Getter
    @Setter
    public static class SentimentDto {

        private String 리뷰;

        private String 감정;

        private double 긍정확률;

        private int 예측평점;

        private List<String> 키워드;
    }

    // =========================
    // 추천 결과
    // =========================

    @Getter
    @Setter
    public static class RecommendDto {

        private String 리뷰작성영화;

        private List<MovieDto> 추천영화목록;
    }

    // =========================
    // 추천 영화 DTO
    // =========================

    @Getter
    @Setter
    public static class MovieDto {

        private String 영화제목;

        private String 장르;

        private double 매칭률;
    }
}