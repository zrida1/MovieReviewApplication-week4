package com.example.moviewreviewapplication.service.impl;

import com.example.moviewreviewapplication.dto.LoginRequest;
import com.example.moviewreviewapplication.dto.LoginResponse;
import com.example.moviewreviewapplication.dto.UserRequestDTO;
import com.example.moviewreviewapplication.dto.UserResponseDTO;
import com.example.moviewreviewapplication.entity.Role;
import com.example.moviewreviewapplication.entity.User;
import com.example.moviewreviewapplication.exception.EmailAlreadyExistsException;
import com.example.moviewreviewapplication.mapper.UserMapper;
import com.example.moviewreviewapplication.repository.UserRepository;
import com.example.moviewreviewapplication.service.AuthService;
import com.example.moviewreviewapplication.service.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtService jwtService;

    private final PasswordEncoder encoder;

    public AuthServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           JwtService jwtService,
                           PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
        this.encoder = encoder;
    }

    @Override
    public UserResponseDTO register(UserRequestDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException("Email is already in use.");
        }

        User user = userMapper.toEntity(dto);
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRole(Role.USER);


        return userMapper.toResponseDTO(userRepository.save(user));
    }

    @Override
    public LoginResponse login(LoginRequest dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!encoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(token);
    }
}