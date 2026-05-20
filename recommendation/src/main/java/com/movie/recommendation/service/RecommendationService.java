package com.movie.recommendation.service;

import com.movie.recommendation.entity.Recommendation;
import com.movie.recommendation.entity.User;
import com.movie.recommendation.repository.RecommendationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    public RecommendationService(RecommendationRepository recommendationRepository) {
        this.recommendationRepository = recommendationRepository;
    }

    public void save(Recommendation recommendation) {
        recommendationRepository.save(recommendation);
    }

    /**
     * 사용자 추천 목록 조회 (지연 로딩 방지 버전으로 변경)
     */
    public List<Recommendation> getRecommendations(User user) {
        if (user == null) {
            return List.of();
        }
        // 🌟 새로 만든 패치 조인 메서드로 호출을 변경합니다.
        return recommendationRepository.findByUserWithMovie(user);
    }
}