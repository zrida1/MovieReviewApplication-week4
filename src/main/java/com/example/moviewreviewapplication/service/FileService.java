package com.example.moviewreviewapplication.service;

import com.example.moviewreviewapplication.dto.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {

    String uploadMoviePoster(Long movieId, MultipartFile file) throws IOException;
    FileResponse downloadMoviePoster(Long movieId) throws IOException;
}
