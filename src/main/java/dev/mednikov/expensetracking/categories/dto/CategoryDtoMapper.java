package dev.mednikov.expensetracking.categories.dto;

import dev.mednikov.expensetracking.categories.models.Category;

import java.util.function.Function;

public final class CategoryDtoMapper implements Function<Category, CategoryDto> {

    @Override
    public CategoryDto apply(Category category) {
        CategoryDto result = new CategoryDto();
        result.setId(category.getId().toString());
        result.setName(category.getName());
        result.setUserId(category.getUser().getId().toString());
        return result;
    }
}
