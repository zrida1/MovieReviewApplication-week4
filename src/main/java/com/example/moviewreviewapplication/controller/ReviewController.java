package com.example.moviewreviewapplication.controller;

import com.example.moviewreviewapplication.dto.ReviewRequestDTO;
import com.example.moviewreviewapplication.dto.ReviewResponseDTO;
import com.example.moviewreviewapplication.entity.Review;
import com.example.moviewreviewapplication.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Reviews", description = "Review Management APIs")
@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @Operation(summary = "Get all reviews")
    @GetMapping
    public Page<ReviewResponseDTO> getAllReviews(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "5") Integer size, @RequestParam(defaultValue = "rating") String sortBy) {
        return reviewService.getAllReviews(page, size, sortBy);
    }

    @Operation(summary = "Get review by id")
    @GetMapping("/{id}")
    public ReviewResponseDTO getReview(@PathVariable Long id) {
        return reviewService.getReview(id);
    }

    @Operation(summary = "Create new review")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponseDTO createReview(@Valid @RequestBody ReviewRequestDTO dto) {
        return reviewService.createReview(dto);
    }

    @Operation(summary = "Update review")
    @PutMapping("/{id}")
    public ReviewResponseDTO updateReview(@PathVariable Long id, @Valid @RequestBody ReviewRequestDTO dto) {
        return reviewService.updateReview(id, dto);
    }

    @Operation(summary = "Delete review")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
    }

    @Operation(summary = "Create review with transaction")
    @PostMapping("/transaction")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponseDTO createReviewTransaction(
            @Valid @RequestBody ReviewRequestDTO dto) {

        return reviewService.createReviewWithTransaction(dto);
    }

}
