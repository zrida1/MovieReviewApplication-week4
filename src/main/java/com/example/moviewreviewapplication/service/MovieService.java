package com.example.moviewreviewapplication.service;

import com.example.moviewreviewapplication.dto.MovieRequestDTO;
import com.example.moviewreviewapplication.dto.MovieResponseDTO;
import com.example.moviewreviewapplication.dto.ReviewRequestDTO;
import org.springframework.data.domain.Page;

import java.util.List;


public interface MovieService {


    MovieResponseDTO getMovieById(Long id);

    Page<MovieResponseDTO> getAllMoviesWithCategories(int page, int size, String sortBy);
    MovieResponseDTO updateMovie(Long id, MovieRequestDTO dto);

    void deleteMovie(Long id);
    MovieResponseDTO createMovie(MovieRequestDTO dto);
    List<MovieResponseDTO> getMoviesByGenre(String genre);
    List<MovieResponseDTO> getMoviesByRating(Double rating);
    List<MovieResponseDTO> searchByTitle(String title);
    List<MovieResponseDTO> getTopMovies(Double rating,Integer year);
    List<MovieResponseDTO> getMoviesByCategory(String category);

    List<MovieResponseDTO> getBestMovies();
    List<MovieResponseDTO> filterMovies(String genre, Double rating, Integer year, String title);

}
