package dev.mednikov.expensetracking.categories.dto;

public record CategoryDto(
        Long id,
        Long userId,
        String name
) {
}
