package com.example.moviewreviewapplication.service;

import com.example.moviewreviewapplication.dto.UserRequestDTO;
import com.example.moviewreviewapplication.dto.UserResponseDTO;
import com.example.moviewreviewapplication.entity.User;
import com.example.moviewreviewapplication.exception.ResourceNotFoundException;
import com.example.moviewreviewapplication.mapper.UserMapper;
import com.example.moviewreviewapplication.repository.UserRepository;
import com.example.moviewreviewapplication.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_ShouldReturnUserResponseDTO() {

        UserRequestDTO request = new UserRequestDTO();
        request.setName("Zoya");
        request.setEmail("zoya@gmail.com");
        request.setPassword("123456");

        User user = new User();
        user.setName("Zoya");
        user.setEmail("zoya@gmail.com");
        user.setPassword("123456");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setName("Zoya");
        savedUser.setEmail("zoya@gmail.com");

        UserResponseDTO response = new UserResponseDTO();
        response.setId(1L);
        response.setName("Zoya");
        response.setEmail("zoya@gmail.com");

        when(userMapper.toEntity(request)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(savedUser);
        when(userMapper.toResponseDTO(savedUser)).thenReturn(response);

        UserResponseDTO result = userService.createUser(request);

        assertEquals(1L, result.getId());
        assertEquals("Zoya", result.getName());

        verify(userRepository).save(user);
    }

    @Test
    void getUser_ShouldReturnUser() {

        User user = new User();
        user.setId(1L);
        user.setName("Zoya");

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(1L);
        dto.setName("Zoya");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toResponseDTO(user)).thenReturn(dto);

        UserResponseDTO result = userService.getUser(1L);

        assertEquals("Zoya", result.getName());
    }

    @Test
    void updateUser_ShouldUpdateUser() {

        User user = new User();
        user.setId(1L);

        UserRequestDTO request = new UserRequestDTO();
        request.setName("Ali");
        request.setEmail("ali@gmail.com");
        request.setPassword("654321");

        UserResponseDTO dto = new UserResponseDTO();
        dto.setName("Ali");
        dto.setEmail("ali@gmail.com");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponseDTO(user)).thenReturn(dto);

        UserResponseDTO result = userService.updateUser(1L, request);

        assertEquals("Ali", result.getName());
        assertEquals("ali@gmail.com", result.getEmail());

        verify(userRepository).save(user);
    }

    @Test
    void deleteUser_ShouldDeleteUser() {

        User user = new User();
        user.setId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).delete(user);
    }

    @Test
    void getUser_ShouldThrowException() {

        when(userRepository.findById(5L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.getUser(5L));
    }
}