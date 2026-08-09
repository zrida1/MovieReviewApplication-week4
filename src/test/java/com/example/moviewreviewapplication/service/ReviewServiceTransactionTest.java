package com.example.moviewreviewapplication.service;

import com.example.moviewreviewapplication.dto.ReviewRequestDTO;
import com.example.moviewreviewapplication.entity.Movie;
import com.example.moviewreviewapplication.entity.User;
import com.example.moviewreviewapplication.repository.MovieRepository;
import com.example.moviewreviewapplication.repository.ReviewRepository;
import com.example.moviewreviewapplication.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ReviewServiceTransactionTest {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    private Movie movie;
    private User user;

    @BeforeEach
    void setUp() {

        reviewRepository.deleteAll();
        userRepository.deleteAll();
        movieRepository.deleteAll();

        movie = new Movie();
        movie.setTitle("Transaction Test");
        movie.setDescription("Test Description");
        movie.setGenre("Action");
        movie.setReleaseYear(2025);
        movie.setImdbRating(7.0);

        movie = movieRepository.save(movie);

        user = new User();
        user.setName("Test User");
        user.setEmail("transaction@test.com");
        user.setPassword("123456");

        user = userRepository.save(user);
    }

    @Test
    void shouldRollbackTransaction() {

        long reviewCount = reviewRepository.count();

        ReviewRequestDTO dto = new ReviewRequestDTO();
        dto.setRating(4.0);
        dto.setComment("Rollback");
        dto.setMovieId(movie.getId());
        dto.setUserId(user.getId());

        assertThrows(RuntimeException.class,
                () -> reviewService.createReviewWithTransaction(dto));

        assertEquals(reviewCount, reviewRepository.count());
    }

    @Test
    void shouldCommitTransaction() {

        long reviewCount = reviewRepository.count();

        ReviewRequestDTO dto = new ReviewRequestDTO();
        dto.setRating(8.5);
        dto.setComment("Success");
        dto.setMovieId(movie.getId());
        dto.setUserId(user.getId());

        reviewService.createReviewWithTransaction(dto);

        assertEquals(reviewCount + 1, reviewRepository.count());
    }
}

