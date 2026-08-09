package com.example.moviewreviewapplication.service;

import com.example.moviewreviewapplication.dto.ReviewRequestDTO;
import com.example.moviewreviewapplication.dto.ReviewResponseDTO;
import com.example.moviewreviewapplication.entity.Movie;
import com.example.moviewreviewapplication.entity.Review;
import com.example.moviewreviewapplication.entity.User;
import com.example.moviewreviewapplication.exception.ResourceNotFoundException;
import com.example.moviewreviewapplication.mapper.ReviewMapper;
import com.example.moviewreviewapplication.repository.MovieRepository;
import com.example.moviewreviewapplication.repository.ReviewRepository;
import com.example.moviewreviewapplication.repository.UserRepository;
import com.example.moviewreviewapplication.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    void createReview_ShouldReturnReviewResponseDTO() {

        ReviewRequestDTO request = new ReviewRequestDTO();
        request.setRating(9.5);
        request.setComment("Excellent");
        request.setMovieId(1L);
        request.setUserId(1L);

        Movie movie = new Movie();
        movie.setId(1L);

        User user = new User();
        user.setId(1L);

        Review review = new Review();

        Review savedReview = new Review();
        savedReview.setId(1L);

        ReviewResponseDTO response = new ReviewResponseDTO();
        response.setId(1L);
        response.setComment("Excellent");

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(reviewMapper.toEntity(request)).thenReturn(review);
        when(reviewRepository.save(review)).thenReturn(savedReview);
        when(reviewMapper.toResponseDTO(savedReview)).thenReturn(response);

        ReviewResponseDTO result = reviewService.createReview(request);

        assertEquals(1L, result.getId());
        assertEquals("Excellent", result.getComment());

        verify(reviewRepository).save(review);
    }

    @Test
    void getReview_ShouldReturnReview() {

        Review review = new Review();
        review.setId(1L);

        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setId(1L);

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(reviewMapper.toResponseDTO(review)).thenReturn(dto);

        ReviewResponseDTO result = reviewService.getReview(1L);

        assertEquals(1L, result.getId());
    }

    @Test
    void updateReview_ShouldUpdateReview() {

        Review review = new Review();
        review.setId(1L);

        Movie movie = new Movie();
        movie.setId(1L);

        User user = new User();
        user.setId(1L);

        ReviewRequestDTO request = new ReviewRequestDTO();
        request.setRating(8.5);
        request.setComment("Updated");
        request.setMovieId(1L);
        request.setUserId(1L);

        ReviewResponseDTO dto = new ReviewResponseDTO();
        dto.setComment("Updated");

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(reviewRepository.save(review)).thenReturn(review);
        when(reviewMapper.toResponseDTO(review)).thenReturn(dto);

        ReviewResponseDTO result = reviewService.updateReview(1L, request);

        assertEquals("Updated", result.getComment());

        verify(reviewRepository).save(review);
    }

    @Test
    void deleteReview_ShouldDeleteReview() {

        Review review = new Review();
        review.setId(1L);

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        reviewService.deleteReview(1L);

        verify(reviewRepository).delete(review);
    }

    @Test
    void getReview_ShouldThrowException() {

        when(reviewRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> reviewService.getReview(10L));
    }
}