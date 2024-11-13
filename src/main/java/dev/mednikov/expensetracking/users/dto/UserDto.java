package dev.mednikov.expensetracking.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDto(
        @NotNull Long id,
        String email,
        @NotNull @NotBlank String firstName,
        @NotNull @NotBlank String lastName
) {
}
