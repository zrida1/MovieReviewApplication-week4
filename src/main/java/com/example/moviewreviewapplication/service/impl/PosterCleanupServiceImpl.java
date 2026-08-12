package com.example.moviewreviewapplication.service.impl;

import com.example.moviewreviewapplication.entity.Movie;
import com.example.moviewreviewapplication.repository.MovieRepository;
import com.example.moviewreviewapplication.service.PosterCleanupService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PosterCleanupServiceImpl implements PosterCleanupService {

    private final MovieRepository movieRepository;

    private final Path uploadDirectory =
            Paths.get("uploads/posters");

    public PosterCleanupServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    @Scheduled(fixedRate = 30000)
    public void cleanUnusedPosters() throws IOException {

        if (!Files.exists(uploadDirectory)) {
            return;
        }

        List<Movie> movies = movieRepository.findAll();

        Set<String> usedPosters = new HashSet<>();

        for (Movie movie : movies) {
            if (movie.getPosterFileName() != null) {
                usedPosters.add(movie.getPosterFileName());
            }
        }

        try (DirectoryStream<Path> files =
                     Files.newDirectoryStream(uploadDirectory)) {

            for (Path file : files) {

                if (Files.isRegularFile(file)) {

                    String fileName =
                            file.getFileName().toString();

                    if (!usedPosters.contains(fileName)) {
                        Files.delete(file);

                        System.out.println(
                                "Deleted unused poster: " + fileName);
                    }
                }
            }
        }
    }
}
