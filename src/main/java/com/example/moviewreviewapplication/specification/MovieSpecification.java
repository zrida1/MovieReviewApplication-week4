package com.example.moviewreviewapplication.specification;

import com.example.moviewreviewapplication.entity.Movie;
import org.springframework.data.jpa.domain.Specification;

public class MovieSpecification {
    public static Specification<Movie> hasGenre(String genre) {
        return (root, query, cb) ->
                genre == null ? null :
                        cb.equal(root.get("genre"), genre);
    }

    public static Specification<Movie> hasMinimumRating(Double rating) {
        return (root, query, cb) ->
                rating == null ? null :
                        cb.greaterThanOrEqualTo(root.get("imdbRating"), rating);
    }

    public static Specification<Movie> hasReleaseYear(Integer year) {
        return (root, query, cb) ->
                year == null ? null :
                        cb.equal(root.get("releaseYear"), year);
    }

    public static Specification<Movie> titleContains(String title) {
        return (root, query, cb) ->
                title == null ? null :
                        cb.like(
                                cb.lower(root.get("title")),
                                "%" + title.toLowerCase() + "%"
                        );
    }

}
