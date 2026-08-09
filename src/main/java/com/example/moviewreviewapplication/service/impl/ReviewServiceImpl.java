package com.example.moviewreviewapplication.service.impl;

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
import com.example.moviewreviewapplication.service.ReviewService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final ReviewMapper reviewMapper;
    ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository, MovieRepository movieRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
        this.reviewMapper = reviewMapper;
    }

    public Page<ReviewResponseDTO> getAllReviews(Integer page, Integer size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return reviewRepository.findAll(pageable).map(reviewMapper::toResponseDTO);
    }

    public ReviewResponseDTO getReview(Long id){
        return reviewMapper.toResponseDTO(reviewRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Review not found with id: " + id)));
    }

    public ReviewResponseDTO createReview(ReviewRequestDTO dto){
        Movie movie = movieRepository.findById(dto.getMovieId()).orElseThrow(()->new ResourceNotFoundException("Movie not found with id: " + dto.getMovieId()));
        User user = userRepository.findById(dto.getUserId()).orElseThrow(()->new ResourceNotFoundException("User not found with id: " + dto.getUserId()));
        Review review = reviewMapper.toEntity(dto);
        review.setMovie(movie);
        review.setUser(user);
        return   reviewMapper.toResponseDTO(reviewRepository.save(review));
    }

    public ReviewResponseDTO updateReview(Long id, ReviewRequestDTO dto){
        Review review = reviewRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Review not found with id: " + id));
        Movie movie = movieRepository.findById(dto.getMovieId()).orElseThrow(()->new ResourceNotFoundException("Movie not found with id: " + dto.getMovieId()));
        User user = userRepository.findById(dto.getUserId()).orElseThrow(()->new ResourceNotFoundException("User not found with id: " + dto.getUserId()));
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setMovie(movie);
        review.setUser(user);
        return   reviewMapper.toResponseDTO(reviewRepository.save(review));
    }

    public void deleteReview(Long id){
        Review review = reviewRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Review not found with id: " + id));

        reviewRepository.delete(review);
    }
    @Override
    @Transactional
    public ReviewResponseDTO createReviewWithTransaction(ReviewRequestDTO dto) {

        Movie movie = movieRepository.findById(dto.getMovieId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Movie not found"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Review review = reviewMapper.toEntity(dto);
        review.setMovie(movie);
        review.setUser(user);

        reviewRepository.save(review);

        movie.setImdbRating(dto.getRating());
        movieRepository.save(movie);

        if (dto.getRating() < 5) {
            throw new RuntimeException("Transaction rolled back");
        }

        return reviewMapper.toResponseDTO(review);
    }


}
