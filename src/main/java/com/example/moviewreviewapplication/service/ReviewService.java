package com.example.moviewreviewapplication.service;

import com.example.moviewreviewapplication.dto.ReviewRequestDTO;
import com.example.moviewreviewapplication.dto.ReviewResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ReviewService {
    Page<ReviewResponseDTO> getAllReviews(Integer page, Integer size, String sortBy);

    ReviewResponseDTO getReview(Long id);

    ReviewResponseDTO createReview(ReviewRequestDTO dto);

    ReviewResponseDTO updateReview(Long id, ReviewRequestDTO dto);

    void deleteReview(Long id);
    ReviewResponseDTO createReviewWithTransaction(ReviewRequestDTO dto);
}
