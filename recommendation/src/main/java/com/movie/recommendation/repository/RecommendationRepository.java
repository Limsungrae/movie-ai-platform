package com.movie.recommendation.repository;

import com.movie.recommendation.entity.Recommendation;
import com.movie.recommendation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 추천 Repository
 */
public interface RecommendationRepository
        extends JpaRepository<Recommendation, Long> {

    /**
     * 특정 사용자의 추천 목록 조회
     */
    List<Recommendation> findByUser(User user);
}