package dev.mednikov.expensetracking.accounts.dto;

import dev.mednikov.expensetracking.accounts.models.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AccountDto(
        Long id,
        @NotNull Long userId,
        @NotNull @NotBlank String name,
        @NotNull AccountType type
) {
}
