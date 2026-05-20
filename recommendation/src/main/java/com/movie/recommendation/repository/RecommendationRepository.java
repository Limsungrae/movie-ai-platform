package com.movie.recommendation.repository;

import com.movie.recommendation.entity.Recommendation;
import com.movie.recommendation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {

    // 🌟 @Query와 join fetch를 사용하여 무비 데이터를 한 번에 조회 (Lazy 로딩 에러 원천 차단)
    @Query("select r from Recommendation r join fetch r.movie where r.user = :user")
    List<Recommendation> findByUserWithMovie(@Param("user") User user);
}