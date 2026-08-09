package com.example.moviewreviewapplication.mapper;

import com.example.moviewreviewapplication.dto.UserRequestDTO;
import com.example.moviewreviewapplication.dto.UserResponseDTO;
import com.example.moviewreviewapplication.entity.User;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRequestDTO toRequestDTO(User user);

    UserResponseDTO toResponseDTO(User user);
    List<UserResponseDTO> toResponseDTO(List<User> user);

    User toEntity(UserRequestDTO dto);

    User toEntity(UserResponseDTO dto);
}