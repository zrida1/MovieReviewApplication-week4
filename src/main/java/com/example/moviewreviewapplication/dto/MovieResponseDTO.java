package com.example.moviewreviewapplication.dto;
import com.example.moviewreviewapplication.entity.Category;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.List;

@Data
@JsonPropertyOrder({
        "id",
        "title",
        "genre",
        "releaseYear",
        "imdbRating",
        "categories",
        "description"
})
public class MovieResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String genre;
    private Integer releaseYear;
    private Double imdbRating;
    private List<String> categories;

}
