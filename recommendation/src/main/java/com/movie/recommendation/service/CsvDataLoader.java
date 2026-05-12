package com.movie.recommendation.service;

import com.movie.recommendation.entity.Movie;
import com.movie.recommendation.repository.MovieRepository;
import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

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

        // 이미 데이터 있으면 종료
        if (movieRepository.count() > 5) {
            return;
        }

        try {

            CSVReader reader = new CSVReader(
                    new InputStreamReader(
                            getClass().getResourceAsStream("/csv/movies.csv"),
                            "UTF-8"
                    )
            );

            List<String[]> rows = reader.readAll();

            // 첫 줄 헤더 제외
            for (int i = 1; i < rows.size(); i++) {

                String[] row = rows.get(i);

                Movie movie = new Movie();

                movie.setTitle(row[0]);
                movie.setGenre(row[1]);
                movie.setDirector(row[2]);
                movie.setActors(row[3]);
                movie.setReleaseDate(row[4]);
                movie.setPosterUrl(row[5]);
                movie.setDescription(row[6]);

                movie.setRating(4.5);

                movieRepository.save(movie);
            }

            System.out.println("CSV 데이터 저장 완료!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}