package dev.mednikov.expensetracking.categories.dto;

import dev.mednikov.expensetracking.categories.models.Category;

import java.util.function.Function;

public final class CategoryDtoMapper implements Function<Category, CategoryDto> {

    @Override
    public CategoryDto apply(Category category) {
        CategoryDto result = new CategoryDto();
        result.setId(category.getId());
        result.setName(category.getName());
        result.setUserId(category.getUser().getId());
        return result;
    }
}
