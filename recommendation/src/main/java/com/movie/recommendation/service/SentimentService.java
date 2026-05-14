package com.movie.recommendation.service;

import com.movie.recommendation.dto.AnalysisResultDto;
import com.movie.recommendation.dto.RecommendationDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 감성 분석 서비스
 *
 * 현재:
 * 임시(Mock) 데이터 반환
 *
 * 추후:
 * Python AI 서버 연동 예정
 */
@Service
public class SentimentService {

    /**
     * 리뷰 감성 분석
     */
    public AnalysisResultDto analyze(String reviewContent) {

        // =============================
        // 감성 분석 결과 DTO 생성
        // =============================

        AnalysisResultDto result =
                new AnalysisResultDto();

        // -----------------------------
        // 임시 감성 분석 결과
        // -----------------------------

        result.setSentiment("POSITIVE");

        result.setPositivePercent(87.0);

        result.setNegativePercent(13.0);

        // AI 분석 코멘트
        result.setAiComment(
                "사용자는 몰입감 있는 SF 영화를 선호하는 경향이 있습니다."
        );

        // =============================
        // 추천 영화 리스트 생성
        // =============================

        List<RecommendationDto> recommendations =
                new ArrayList<>();

        // -----------------------------
        // 추천 영화 1
        // -----------------------------

        RecommendationDto movie1 =
                new RecommendationDto();

        movie1.setMovieId(1L);

        movie1.setTitle("인셉션");

        movie1.setPosterUrl(
                "https://image.tmdb.org/t/p/w500/qmDpIHrmpJINaRKAfWQfftjCdyi.jpg"
        );

        movie1.setGenre("SF");

        movie1.setRating(4.7);

        movie1.setMatchScore(92.0);

        recommendations.add(movie1);

        // -----------------------------
        // 추천 영화 2
        // -----------------------------

        RecommendationDto movie2 =
                new RecommendationDto();

        movie2.setMovieId(2L);

        movie2.setTitle("그래비티");

        movie2.setPosterUrl(
                "https://image.tmdb.org/t/p/w500/kZ2nZw8D681aphje8NJi8EfbL1U.jpg"
        );

        movie2.setGenre("SF");

        movie2.setRating(4.6);

        movie2.setMatchScore(89.0);

        recommendations.add(movie2);

        // -----------------------------
        // 추천 영화 3
        // -----------------------------

        RecommendationDto movie3 =
                new RecommendationDto();

        movie3.setMovieId(3L);

        movie3.setTitle("메이즈 러너");

        movie3.setPosterUrl(
                "https://image.tmdb.org/t/p/w500/ode14q7WtDugFDp78fo9lCsmay9.jpg"
        );

        movie3.setGenre("SF");

        movie3.setRating(4.5);

        movie3.setMatchScore(86.0);

        recommendations.add(movie3);

        // -----------------------------
        // 추천 영화 4
        // -----------------------------

        RecommendationDto movie4 =
                new RecommendationDto();

        movie4.setMovieId(4L);

        movie4.setTitle("블레이드 러너 2049");

        movie4.setPosterUrl(
                "https://image.tmdb.org/t/p/w500/gajva2L0rPYkEWjzgFlBXCAVBE5.jpg"
        );

        movie4.setGenre("SF");

        movie4.setRating(4.4);

        movie4.setMatchScore(84.0);

        recommendations.add(movie4);

        // 추천 영화 리스트 저장
        result.setRecommendations(recommendations);

        // 최종 결과 반환
        return result;
    }
}