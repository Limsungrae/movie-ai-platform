package com.movie.recommendation.service;

import com.movie.recommendation.entity.Recommendation;
import com.movie.recommendation.entity.User;
import com.movie.recommendation.repository.RecommendationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 추천 서비스
 */
@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    public RecommendationService(
            RecommendationRepository recommendationRepository) {

        this.recommendationRepository = recommendationRepository;
    }

    /**
     * 추천 저장
     */
    public void save(Recommendation recommendation) {

        recommendationRepository.save(recommendation);
    }

    /**
     * 사용자 추천 목록 조회
     */
    public List<Recommendation> getRecommendations(User user) {

        return recommendationRepository.findByUser(user);
    }
}