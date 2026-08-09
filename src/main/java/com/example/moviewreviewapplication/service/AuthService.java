package com.example.moviewreviewapplication.service;

import com.example.moviewreviewapplication.dto.LoginRequest;
import com.example.moviewreviewapplication.dto.LoginResponse;
import com.example.moviewreviewapplication.dto.UserRequestDTO;
import com.example.moviewreviewapplication.dto.UserResponseDTO;

public interface AuthService {
    UserResponseDTO register(UserRequestDTO dto);

    LoginResponse login(LoginRequest dto);
}
