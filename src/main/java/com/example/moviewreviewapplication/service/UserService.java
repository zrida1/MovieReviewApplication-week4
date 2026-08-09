package com.example.moviewreviewapplication.service;

import com.example.moviewreviewapplication.dto.UserRequestDTO;
import com.example.moviewreviewapplication.dto.UserResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    Page<UserResponseDTO> getAllUsers(Integer page, Integer size, String sortBy);
    UserResponseDTO getUser(Long id);
    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
    UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO);
    void deleteUser(Long id);
}
