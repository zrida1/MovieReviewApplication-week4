package com.example.moviewreviewapplication.controller;

import com.example.moviewreviewapplication.dto.MovieRequestDTO;
import com.example.moviewreviewapplication.dto.MovieResponseDTO;
import com.example.moviewreviewapplication.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.data.domain.Page;

@Tag(name = "Movies", description = "Movie Management APIs")
@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Operation(summary = "Get all movies")
    @GetMapping
    public Page<MovieResponseDTO> getMovies(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "title") String sortBy) {
        return movieService.getAllMoviesWithCategories(page, size, sortBy);
    }

    @Operation(summary = "Get movie by id")
    @GetMapping("/{id}")
    public MovieResponseDTO getMovie(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }


    @Operation(summary = "Update movie")
    @PutMapping("/{id}")
    public MovieResponseDTO updateMovie(@PathVariable Long id, @Valid @RequestBody MovieRequestDTO movie) {
        return movieService.updateMovie(id, movie);
    }

    @Operation(summary = "Delete movie")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);

    }

    @Operation(summary = "Create new movie")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MovieResponseDTO createNewMovie(@Valid @RequestBody MovieRequestDTO movie) {
        return movieService.createMovie(movie);

    }

    @Operation(summary = "Get movies by genre")
    @GetMapping("/genre/{genre}")
    public List<MovieResponseDTO> getByGenre(@PathVariable String genre) {
        return movieService.getMoviesByGenre(genre);
    }

    @Operation(summary = "Get movies by minimum IMDb rating")
    @GetMapping("/rating/{rating}")
    public List<MovieResponseDTO> getByRating(@PathVariable Double rating) {
        return movieService.getMoviesByRating(rating);
    }

    @Operation(summary = "Search movies by title")
    @GetMapping("/search")
    public List<MovieResponseDTO> search(@RequestParam String title) {
        return movieService.searchByTitle(title);
    }

    @Operation(summary = "Filter movies by rating and release year")
    @GetMapping("/top")
    public List<MovieResponseDTO> topMovies(
            @RequestParam Double rating,
            @RequestParam Integer year) {
        return movieService.getTopMovies(rating, year);
    }

    @Operation(summary = "Get movies by category")
    @GetMapping("/category/{category}")
    public List<MovieResponseDTO> byCategory(@PathVariable String category) {
        return movieService.getMoviesByCategory(category);
    }

    @Operation(summary = "Get movies with IMDb rating greater than or equal to 8")
    @GetMapping("/best")
    public List<MovieResponseDTO> bestMovies() {

        return movieService.getBestMovies();
    }

    @Operation(summary = "Filter movies dynamically")
    @GetMapping("/filter")
    public List<MovieResponseDTO> filterMovies(
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) Double rating,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) String title) {

        return movieService.filterMovies(
                genre,
                rating,
                year,
                title
        );
    }


}
