package com.example.moviewreviewapplication.mapper;

import com.example.moviewreviewapplication.dto.CategoryRequestDTO;
import com.example.moviewreviewapplication.dto.CategoryResponseDTO;
import com.example.moviewreviewapplication.entity.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequestDTO dto);

    CategoryResponseDTO toResponseDTO(Category category);

    List<CategoryResponseDTO> toResponseDTOList(List<Category> categories);
}
