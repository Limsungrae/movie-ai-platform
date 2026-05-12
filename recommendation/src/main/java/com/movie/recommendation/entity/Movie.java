package com.movie.recommendation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 영화 제목
    private String title;

    // 장르
    private String genre;

    // 감독
    private String director;

    // 배우
    @Column(length = 1000)
    private String actors;

    // 개봉일
    private String releaseDate;

    // 포스터 이미지
    @Column(length = 1000)
    private String posterUrl;

    // 줄거리
    @Column(length = 5000)
    private String description;

    // 평균 평점
    private Double rating;
}