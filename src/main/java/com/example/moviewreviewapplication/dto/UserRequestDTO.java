package com.example.moviewreviewapplication.dto;

import com.example.moviewreviewapplication.entity.Review;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class UserRequestDTO {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @Email(message = "Invalid email")
    @NotBlank(message = "Email cannot be empty")
    private String email;
    @Size(min = 6, message = "Password must contain at least 6 characters")
    private String password;
}
