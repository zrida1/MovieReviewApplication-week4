package com.example.moviewreviewapplication.service;

import com.example.moviewreviewapplication.dto.MovieRequestDTO;
import com.example.moviewreviewapplication.dto.MovieResponseDTO;
import com.example.moviewreviewapplication.entity.Movie;
import com.example.moviewreviewapplication.exception.ResourceNotFoundException;
import com.example.moviewreviewapplication.mapper.MovieMapper;
import com.example.moviewreviewapplication.repository.MovieRepository;
import com.example.moviewreviewapplication.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private MovieMapper movieMapper;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    void createMovie_ShouldReturnMovieResponseDTO() {

        MovieRequestDTO request = new MovieRequestDTO();
        request.setTitle("Inception");
        request.setGenre("Sci-Fi");
        request.setDescription("Movie");
        request.setReleaseYear(2010);
        request.setImdbRating(8.8);

        Movie movie = new Movie();
        movie.setTitle("Inception");

        Movie savedMovie = new Movie();
        savedMovie.setId(1L);
        savedMovie.setTitle("Inception");

        MovieResponseDTO response = new MovieResponseDTO();
        response.setId(1L);
        response.setTitle("Inception");

        when(movieMapper.toEntity(request)).thenReturn(movie);
        when(movieRepository.save(movie)).thenReturn(savedMovie);
        when(movieMapper.toResponseDTO(savedMovie)).thenReturn(response);

        MovieResponseDTO result = movieService.createMovie(request);

        assertEquals(1L, result.getId());
        assertEquals("Inception", result.getTitle());

        verify(movieRepository).save(movie);
    }

    @Test
    void getMovieById_ShouldReturnMovie() {

        Movie movie = new Movie();
        movie.setId(1L);
        movie.setTitle("Inception");

        MovieResponseDTO dto = new MovieResponseDTO();
        dto.setId(1L);
        dto.setTitle("Inception");

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(movieMapper.toResponseDTO(movie)).thenReturn(dto);

        MovieResponseDTO result = movieService.getMovieById(1L);

        assertEquals("Inception", result.getTitle());
    }

    @Test
    void deleteMovie_ShouldDeleteMovie() {

        Movie movie = new Movie();
        movie.setId(1L);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));

        movieService.deleteMovie(1L);

        verify(movieRepository).delete(movie);
    }

    @Test
    void updateMovie_ShouldUpdateMovie() {

        Movie movie = new Movie();
        movie.setId(1L);

        MovieRequestDTO request = new MovieRequestDTO();
        request.setTitle("Batman");
        request.setDescription("Action movie");
        request.setGenre("Action");
        request.setReleaseYear(2008);
        request.setImdbRating(9.0);

        MovieResponseDTO dto = new MovieResponseDTO();
        dto.setTitle("Batman");
        dto.setDescription("Action movie");
        dto.setGenre("Action");
        dto.setReleaseYear(2008);
        dto.setImdbRating(9.0);

        when(movieRepository.findById(1L)).thenReturn(Optional.of(movie));
        when(movieRepository.save(movie)).thenReturn(movie);
        when(movieMapper.toResponseDTO(movie)).thenReturn(dto);

        MovieResponseDTO result = movieService.updateMovie(1L, request);

        assertEquals("Batman", result.getTitle());
        verify(movieRepository).save(movie);
    }

    @Test
    void getMovieById_ShouldThrowException() {

        when(movieRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> movieService.getMovieById(5L));
    }
}
