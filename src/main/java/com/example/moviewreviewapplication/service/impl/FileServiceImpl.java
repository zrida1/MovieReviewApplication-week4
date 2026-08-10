package com.example.moviewreviewapplication.service.impl;

import com.example.moviewreviewapplication.dto.FileResponse;
import com.example.moviewreviewapplication.entity.Movie;
import com.example.moviewreviewapplication.exception.ResourceNotFoundException;
import com.example.moviewreviewapplication.repository.MovieRepository;
import com.example.moviewreviewapplication.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.UUID;

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
    public String uploadMoviePoster(Long movieId, MultipartFile file)throws IOException {

        Movie movie = movieRepository.findById(movieId).orElseThrow(() ->new ResourceNotFoundException("Movie not found with id: " + movieId));

        if (file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException( "File size cannot exceed 5 MB");
        }

        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException("Only JPEG and PNG files are allowed");
        }

        Files.createDirectories(uploadDirectory);
        String extension = getExtension(file.getOriginalFilename());
        String fileName = UUID.randomUUID() + extension;
        Path filePath = uploadDirectory.resolve(fileName);
        Files.copy(file.getInputStream(),filePath,StandardCopyOption.REPLACE_EXISTING);

        movie.setPosterFileName(fileName);
        movieRepository.save(movie);

        return fileName;
    }
    @Override
    public FileResponse downloadMoviePoster(Long movieId)
            throws IOException {

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Movie not found with id: " + movieId));

        if (movie.getPosterFileName() == null) {
            throw new ResourceNotFoundException(
                    "Poster not found for movie");
        }

        Path filePath =
                uploadDirectory.resolve(movie.getPosterFileName());

        if (!Files.exists(filePath)) {
            throw new ResourceNotFoundException(
                    "Poster file not found");
        }

        byte[] data = Files.readAllBytes(filePath);

        String contentType = Files.probeContentType(filePath);

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return new FileResponse(data, contentType);
    }

    private String getExtension(String filename) {

        if (filename == null || !filename.contains(".")) {
            return "";
        }

        return filename.substring(filename.lastIndexOf(".")).toLowerCase();

    }


}
