package com.example.moviewreviewapplication.controller;

import com.example.moviewreviewapplication.dto.FileResponse;
import com.example.moviewreviewapplication.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/movies")
@Tag(name = "Movie Posters", description = "Movie poster upload and download APIs")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @Operation(summary = "Upload movie poster")
    @PostMapping("/{movieId}/poster")
    public ResponseEntity<String> uploadPoster(
            @PathVariable Long movieId,
            @RequestParam("file") MultipartFile file) throws IOException {

        String fileName = fileService.uploadMoviePoster(movieId, file);

        return ResponseEntity.ok(
                "Poster uploaded successfully: " + fileName
        );
    }

    @Operation(summary = "Get movie poster")
    @GetMapping("/{movieId}/poster")
    public ResponseEntity<byte[]> getPoster(
            @PathVariable Long movieId) throws IOException {

        FileResponse fileResponse =
                fileService.downloadMoviePoster(movieId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                .contentType(
                        MediaType.parseMediaType(fileResponse.getContentType())
                )
                .body(fileResponse.getData());
    }
}