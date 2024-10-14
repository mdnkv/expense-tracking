package dev.mednikov.expensetracking.categories.services;

import dev.mednikov.expensetracking.categories.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    CategoryDto createCategory (CategoryDto request);

    CategoryDto updateCategory (CategoryDto request);

    void deleteCategory (Long id);

    Optional<CategoryDto> findCategoryById(Long id);

    List<CategoryDto> findAllCategoriesForUser (Long userId);

}
