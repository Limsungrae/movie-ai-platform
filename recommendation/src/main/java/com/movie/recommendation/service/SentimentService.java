package com.movie.recommendation.service;

import com.movie.recommendation.dto.AnalysisResultDto;
import com.movie.recommendation.dto.PythonResponseDto;
import com.movie.recommendation.dto.RecommendationDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.movie.recommendation.entity.Movie;
import com.movie.recommendation.service.MovieService;
import java.util.*;

@Service
public class SentimentService {

    private final RestTemplate restTemplate;
    private final MovieService movieService;
    public SentimentService(RestTemplate restTemplate,
                            MovieService movieService) {

        this.restTemplate = restTemplate;
        this.movieService = movieService;
    }

    public AnalysisResultDto analyze(String movieTitle,
                                     String reviewContent) {

        // =========================
        // Python 서버 요청 데이터
        // =========================

        Map<String, String> request = new HashMap<>();

        request.put("title", movieTitle);
        request.put("review", reviewContent);

        // =========================
        // HTTP 헤더
        // =========================

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity =
                new HttpEntity<>(request, headers);

        // =========================
        // Python 서버 호출
        // =========================

        ResponseEntity<PythonResponseDto> response =
                restTemplate.exchange(
                        "http://127.0.0.1:8000/predict",
                        HttpMethod.POST,
                        entity,
                        PythonResponseDto.class
                );

        PythonResponseDto pythonResult =
                response.getBody();

        // =========================
        // Spring DTO 변환
        // =========================

        AnalysisResultDto result =
                new AnalysisResultDto();

        // 감성 결과
        String sentiment =
                pythonResult.getSentiment().get감정();

        if (sentiment.equals("긍정")) {

            result.setSentiment("POSITIVE");

        } else {

            result.setSentiment("NEGATIVE");
        }
        double positive =
                Math.round(
                        pythonResult.getSentiment().get긍정확률() * 1000
                ) / 10.0;

        double negative =
                Math.round(
                        (100 -
                                (pythonResult.getSentiment().get긍정확률() * 100)
                        ) * 10
                ) / 10.0;

        result.setPositivePercent(positive);

        result.setNegativePercent(negative);

        result.setAiComment(
                "AI가 리뷰를 기반으로 영화를 추천했어요."
        );

        // =========================
        // 추천 영화 변환
        // =========================

        List<RecommendationDto> recommendations =
                new ArrayList<>();

        for (PythonResponseDto.MovieDto movie :
                pythonResult.getRecommend().get추천영화목록()) {

            RecommendationDto dto =
                    new RecommendationDto();

            dto.setTitle(movie.get영화제목());

            dto.setGenre(movie.get장르());

            dto.setMatchScore(movie.get매칭률());

            // 임시값
            Movie foundMovie =
                    movieService.findByTitle(movie.get영화제목());

            if (foundMovie != null) {

                dto.setMovieId(foundMovie.getId());

                dto.setPosterUrl(foundMovie.getPosterUrl());

                dto.setRating(foundMovie.getRating());
            }
            recommendations.add(dto);
        }

        result.setRecommendations(recommendations);

        return result;
    }
}