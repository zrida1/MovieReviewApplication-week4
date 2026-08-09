package com.example.moviewreviewapplication.repository;

import com.example.moviewreviewapplication.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    @Override
    @EntityGraph(attributePaths = {"categories"})
    Page<Movie> findAll(Pageable pageable);
    List<Movie> findByGenre(String genre);
    List<Movie> findByImdbRatingGreaterThanEqual(Double rating);
    List<Movie> findByTitleContainingIgnoreCase(String title);
    @Query("""
        SELECT m
        FROM Movie m
        WHERE m.imdbRating >= :rating
        AND m.releaseYear >= :year
    """)
    List<Movie> findTopMovies(@Param("rating") Double rating, @Param("year") Integer year);

    @Query("""
        SELECT m
        FROM Movie m
        JOIN m.categories c
        WHERE c.name = :categoryName
    """)
    List<Movie> findMoviesByCategory(@Param("categoryName") String categoryName);

    @Query(value="""
        SELECT *
        FROM movies
        WHERE imdb_rating>= 8
    """,nativeQuery = true)
    List<Movie>findBestMovies();

}
