package dev.mednikov.expensetracking.categories.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryDto(
        Long id,
        @NotNull Long userId,
        @NotNull @NotBlank String name
) {
}
