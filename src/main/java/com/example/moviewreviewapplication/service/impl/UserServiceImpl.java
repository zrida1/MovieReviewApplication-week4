package com.example.moviewreviewapplication.service.impl;

import com.example.moviewreviewapplication.dto.UserRequestDTO;
import com.example.moviewreviewapplication.dto.UserResponseDTO;
import com.example.moviewreviewapplication.entity.User;
import com.example.moviewreviewapplication.exception.ResourceNotFoundException;
import com.example.moviewreviewapplication.mapper.UserMapper;
import com.example.moviewreviewapplication.repository.UserRepository;
import com.example.moviewreviewapplication.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.encoder = encoder;
    }

    public Page<UserResponseDTO> getAllUsers(Integer page, Integer size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return userRepository.findAll(pageable).map(userMapper::toResponseDTO);
    }
    public UserResponseDTO getUser(Long id) {
        return userMapper.toResponseDTO(userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with id: " + id)));
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){
        userRequestDTO.setPassword(encoder.encode(userRequestDTO.getPassword()));
        return userMapper.toResponseDTO(userRepository.save(userMapper.toEntity(userRequestDTO)));
    }
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO){
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User not found with id: " + id));
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(encoder.encode(userRequestDTO.getPassword()));
        return userMapper.toResponseDTO(userRepository.save(user));
    }
    public void deleteUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with id: " + id));

        userRepository.delete(user);
    }
}
