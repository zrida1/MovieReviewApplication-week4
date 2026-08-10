package com.example.moviewreviewapplication.service.impl;

import com.example.moviewreviewapplication.repository.MovieRepository;
import com.example.moviewreviewapplication.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

@Service
public class FileServiceImpl implements FileService {
    private final MovieRepository movieRepository;

    private final Path uploadDirectory =
            Paths.get("uploads/posters");

    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    private static final Set<String> ALLOWED_TYPES = Set.of(
            "image/jpeg",
            "image/png"
    );

    public FileServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public String uploadMoviePoster(Long movieId, MultipartFile file){
        return null;
    }
    @Override
    public byte[] downloadMoviePoster(Long movieId){
        return null;
    }


}
