//// 더미 데이터로  테스트하는 코드
//package com.movie.recommendation.config;
//
//import com.movie.recommendation.entity.Movie;
//import com.movie.recommendation.repository.MovieRepository;
//import jakarta.annotation.PostConstruct;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataInit {
//
//    private final MovieRepository movieRepository;
//
//    public DataInit(MovieRepository movieRepository) {
//        this.movieRepository = movieRepository;
//    }
//
//    @PostConstruct
//    public void init() {
//
//        if (movieRepository.count() > 2) {
//            return;
//        }
//
//        Movie movie1 = new Movie();
//        movie1.setTitle("인터스텔라");
//        movie1.setGenre("SF");
//        movie1.setDirector("크리스토퍼 놀란");
//        movie1.setPosterUrl("https://image.tmdb.org/t/p/w500/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg");
//        movie1.setDescription("우주를 배경으로 한 감동적인 SF 영화");
//        movie1.setRating(4.8);
//
//        Movie movie2 = new Movie();
//        movie2.setTitle("기생충");
//        movie2.setGenre("드라마");
//        movie2.setDirector("봉준호");
//        movie2.setPosterUrl("https://image.tmdb.org/t/p/w500/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg");
//        movie2.setDescription("빈부격차를 다룬 한국 영화");
//        movie2.setRating(4.9);
//
//        movieRepository.save(movie1);
//        movieRepository.save(movie2);
//    }
//}