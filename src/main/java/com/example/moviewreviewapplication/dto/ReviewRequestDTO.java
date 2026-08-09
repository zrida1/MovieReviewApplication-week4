package com.example.moviewreviewapplication.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewRequestDTO {
    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 10, message = "Rating must be at most 10")
    private Double rating;
    @NotBlank(message = "Comment cannot be empty")
    private String comment;
    @NotNull(message = "Movie id is required")
    private Long movieId;
    @NotNull(message = "User id is required")
    private Long userId;
}
