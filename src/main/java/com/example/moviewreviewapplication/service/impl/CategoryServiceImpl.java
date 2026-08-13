package com.example.moviewreviewapplication.service.impl;

import com.example.moviewreviewapplication.dto.CategoryRequestDTO;
import com.example.moviewreviewapplication.dto.CategoryResponseDTO;
import com.example.moviewreviewapplication.entity.Category;
import com.example.moviewreviewapplication.exception.ResourceNotFoundException;
import com.example.moviewreviewapplication.mapper.CategoryMapper;
import com.example.moviewreviewapplication.repository.CategoryRepository;
import com.example.moviewreviewapplication.service.CategoryService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }
    @Cacheable(value = "allCategories", key = "#page + '-' + #size + '-' + #sortBy")
    @Override
    public Page<CategoryResponseDTO> getAllCategories(int page, int size, String sortBy) {

        Pageable pageable =
                PageRequest.of(page, size, Sort.by(sortBy).ascending());

        return categoryRepository.findAll(pageable)
                .map(categoryMapper::toResponseDTO);
    }

    @Cacheable(value = "categories", key = "#id")
    @Override
    public CategoryResponseDTO getCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + id));

        return categoryMapper.toResponseDTO(category);
    }

    @Override
    @CacheEvict(value = {"categories", "allCategories", "movies", "allMovies", "filteredMovies"}, allEntries = true)
    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) {

        return categoryMapper.toResponseDTO(
                categoryRepository.save(categoryMapper.toEntity(dto)));
    }

    @Override
    @CacheEvict(value = {"categories", "allCategories", "movies", "allMovies", "filteredMovies"}, allEntries = true)
    public CategoryResponseDTO updateCategory(Long id,
                                              CategoryRequestDTO dto) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + id));

        category.setName(dto.getName());

        return categoryMapper.toResponseDTO(
                categoryRepository.save(category));
    }

    @Override
    @CacheEvict(value = {"categories", "allCategories", "movies", "allMovies", "filteredMovies"}, allEntries = true)
    public void deleteCategory(Long id) {

        Category category = categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: " + id));

        categoryRepository.delete(category);
    }

}
