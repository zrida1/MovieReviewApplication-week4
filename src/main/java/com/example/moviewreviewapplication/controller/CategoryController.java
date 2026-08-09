package com.example.moviewreviewapplication.controller;


import com.example.moviewreviewapplication.dto.CategoryRequestDTO;
import com.example.moviewreviewapplication.dto.CategoryResponseDTO;
import com.example.moviewreviewapplication.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Categories", description = "Category Management APIs")
@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get all categories")
    @GetMapping
    public Page<CategoryResponseDTO> getAllCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "name") String sortBy) {

        return categoryService.getAllCategories(page, size, sortBy);
    }

    @Operation(summary = "Get category by id")
    @GetMapping("/{id}")
    public CategoryResponseDTO getCategory(@PathVariable Long id) {
        return categoryService.getCategory(id);
    }

    @Operation(summary = "Create category")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponseDTO createCategory(
            @Valid @RequestBody CategoryRequestDTO dto) {

        return categoryService.createCategory(dto);
    }

    @Operation(summary = "Update category")
    @PutMapping("/{id}")
    public CategoryResponseDTO updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequestDTO dto) {

        return categoryService.updateCategory(id, dto);
    }

    @Operation(summary = "Delete category")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
