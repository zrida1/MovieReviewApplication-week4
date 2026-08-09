package com.example.moviewreviewapplication.service.impl;

import com.example.moviewreviewapplication.dto.MovieRequestDTO;
import com.example.moviewreviewapplication.dto.MovieResponseDTO;
import com.example.moviewreviewapplication.dto.ReviewRequestDTO;
import com.example.moviewreviewapplication.entity.Category;
import com.example.moviewreviewapplication.entity.Movie;
import com.example.moviewreviewapplication.exception.ResourceNotFoundException;
import com.example.moviewreviewapplication.mapper.MovieMapper;
import com.example.moviewreviewapplication.repository.CategoryRepository;
import com.example.moviewreviewapplication.repository.MovieRepository;
import com.example.moviewreviewapplication.service.MovieService;
import com.example.moviewreviewapplication.specification.MovieSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;
    private final CategoryRepository categoryRepository;
    public MovieServiceImpl(MovieRepository movieRepository, MovieMapper movieMapper,  CategoryRepository categoryRepository) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
        this.categoryRepository = categoryRepository;
    }

    public MovieResponseDTO getMovieById(Long id) {
        return movieMapper.toResponseDTO(movieRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Movie not found with id: " + id)));
    }

    @Override
    public Page<MovieResponseDTO> getAllMoviesWithCategories(int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return movieRepository.findAll(pageable).map(movieMapper::toResponseDTO);
    }
    public MovieResponseDTO updateMovie(Long id, MovieRequestDTO dto) {
        Movie movie = movieRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Movie not found with id: " + id));
        List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
        movie.setTitle(dto.getTitle());
        movie.setDescription(dto.getDescription());
        movie.setGenre(dto.getGenre());
        movie.setReleaseYear(dto.getReleaseYear());
        movie.setImdbRating(dto.getImdbRating());
        movie.setCategories(categories);
        return movieMapper.toResponseDTO(movieRepository.save(movie));

    }

    public void deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Movie not found with id: " + id));

        movieRepository.delete(movie);
    }

    @Transactional
    public MovieResponseDTO createMovie(MovieRequestDTO dto){
        List<Category> categories = categoryRepository.findAllById(dto.getCategoryIds());
        if (categories.size() != dto.getCategoryIds().size()) {
            throw new ResourceNotFoundException("One or more categories not found.");
        }
        Movie movie = movieMapper.toEntity(dto);
        movie.setCategories(categories);
        return movieMapper.toResponseDTO(movieRepository.save(movie));

    }
    @Override
    public List<MovieResponseDTO> getMoviesByGenre(String genre){
        return movieRepository.findByGenre(genre)
                .stream()
                .map(movieMapper::toResponseDTO)
                .toList();
    }
    @Override
    public List<MovieResponseDTO> getMoviesByRating(Double rating){
        return movieRepository.findByImdbRatingGreaterThanEqual(rating)
                .stream()
                .map(movieMapper::toResponseDTO)
                .toList();
    }
    @Override
    public List<MovieResponseDTO> searchByTitle(String title){
        return movieRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(movieMapper::toResponseDTO)
                .toList();
    }
    @Override
    public List<MovieResponseDTO> getTopMovies(Double rating,Integer year){
        return movieRepository.findTopMovies(rating, year)
                .stream()
                .map(movieMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<MovieResponseDTO> getMoviesByCategory(String category){
        return movieRepository.findMoviesByCategory(category)
                .stream()
                .map(movieMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<MovieResponseDTO> getBestMovies(){
        return movieRepository.findBestMovies()
                .stream()
                .map(movieMapper::toResponseDTO)
                .toList();
    }
    @Override
    public List<MovieResponseDTO> filterMovies(
            String genre,
            Double rating,
            Integer year,
            String title) {

        Specification<Movie> specification =
                Specification.where(MovieSpecification.hasGenre(genre))
                        .and(MovieSpecification.hasMinimumRating(rating))
                        .and(MovieSpecification.hasReleaseYear(year))
                        .and(MovieSpecification.titleContains(title));

        return movieRepository.findAll(specification)
                .stream()
                .map(movieMapper::toResponseDTO)
                .toList();
    }

}
