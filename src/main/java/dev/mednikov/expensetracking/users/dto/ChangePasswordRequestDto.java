package dev.mednikov.expensetracking.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangePasswordRequestDto(
        @NotNull Long id,
        @NotNull @NotBlank String password)
{
}
