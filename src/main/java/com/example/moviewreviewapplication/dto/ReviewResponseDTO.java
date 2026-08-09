package com.example.moviewreviewapplication.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonPropertyOrder({
        "id",
        "movieTitle",
        "userName",
        "rating",
        "comment"
})
@Data
public class ReviewResponseDTO {
    private Long id;
    private Double rating;
    private String comment;
    private String movieTitle;
    private String userName;
}
