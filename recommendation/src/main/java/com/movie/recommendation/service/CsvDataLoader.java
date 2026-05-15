package com.movie.recommendation.service;

import com.movie.recommendation.entity.Movie;
import com.movie.recommendation.repository.MovieRepository;
import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Service
public class CsvDataLoader {

    private final MovieRepository movieRepository;

    public CsvDataLoader(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @PostConstruct
    public void loadCsvData() {

        try {

            // =========================
            // CSV 파일 읽기
            // =========================

            InputStream inputStream =
                    getClass().getResourceAsStream("/csv/movies.csv");

            // 파일 없으면 종료
            if (inputStream == null) {

                System.out.println("movies.csv 파일을 찾을 수 없습니다.");
                return;
            }

            CSVReader reader = new CSVReader(
                    new InputStreamReader(inputStream, "UTF-8")
            );

            List<String[]> rows = reader.readAll();

            // =========================
            // 첫 줄(header) 제외
            // =========================

            for (int i = 1; i < rows.size(); i++) {

                String[] row = rows.get(i);

                // 영화 제목
                String title = row[0];

                // =========================
                // 중복 체크
                // =========================

                boolean exists =
                        movieRepository.existsByTitle(title);

                if (exists) {
                    continue;
                }

                // =========================
                // 영화 저장
                // =========================

                Movie movie = new Movie();

                movie.setTitle(row[0]);
                movie.setGenre(row[1]);
                movie.setDirector(row[2]);
                movie.setActors(row[3]);
                movie.setReleaseDate(row[4]);
                movie.setPosterUrl(row[5]);
                movie.setDescription(row[6]);

                // 임시 평점
                movie.setRating(4.5);

                movieRepository.save(movie);
            }

            System.out.println("CSV 영화 추가 완료!");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
}