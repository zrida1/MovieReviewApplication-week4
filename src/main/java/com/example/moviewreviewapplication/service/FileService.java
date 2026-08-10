package com.example.moviewreviewapplication.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    String uploadMoviePoster(Long movieId, MultipartFile file) throws IOException;
    byte[] downloadMoviePoster(Long movieId) throws IOException;
}
