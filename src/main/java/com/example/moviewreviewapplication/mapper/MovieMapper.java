package com.example.moviewreviewapplication.mapper;

import com.example.moviewreviewapplication.dto.MovieRequestDTO;
import com.example.moviewreviewapplication.dto.MovieResponseDTO;
import com.example.moviewreviewapplication.entity.Movie;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import com.example.moviewreviewapplication.entity.Category;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieRequestDTO toRequestDTO(Movie movie);
    @Mapping(
            target = "categories",
            expression = "java(movie.getCategories().stream().map(com.example.moviewreviewapplication.entity.Category::getName).toList())"
    )
    MovieResponseDTO toResponseDTO(Movie movie);
    List<MovieResponseDTO> toResponseDTOList(List<Movie> movies);

    @Mapping(target = "categories", ignore = true)
    Movie toEntity(MovieRequestDTO dto);

    @Mapping(target = "categories", ignore = true)
    Movie toEntity(MovieResponseDTO dto);
}