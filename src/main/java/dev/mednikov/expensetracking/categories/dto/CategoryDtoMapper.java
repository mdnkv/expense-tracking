package dev.mednikov.expensetracking.categories.dto;

import dev.mednikov.expensetracking.categories.models.Category;

import java.util.function.Function;

public final class CategoryDtoMapper implements Function<Category, CategoryDto> {

    @Override
    public CategoryDto apply(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getUser().getId(),
                category.getName()
        );
    }
}
