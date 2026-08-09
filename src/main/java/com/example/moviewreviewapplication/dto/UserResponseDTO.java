package com.example.moviewreviewapplication.dto;

import com.example.moviewreviewapplication.entity.Review;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;
@JsonPropertyOrder({
        "id",
        "name",
        "email"
})
@Data
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
}
