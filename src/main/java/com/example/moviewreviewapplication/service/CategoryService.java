package com.example.moviewreviewapplication.service;

import com.example.moviewreviewapplication.dto.CategoryRequestDTO;
import com.example.moviewreviewapplication.dto.CategoryResponseDTO;
import org.springframework.data.domain.Page;

public interface CategoryService {
    Page<CategoryResponseDTO> getAllCategories(int page, int size, String sortBy);

    CategoryResponseDTO getCategory(Long id);

    CategoryResponseDTO createCategory(CategoryRequestDTO dto);

    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO dto);

    void deleteCategory(Long id);
}
