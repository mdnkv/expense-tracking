package dev.mednikov.expensetracking.accounts.dto;

import dev.mednikov.expensetracking.accounts.models.AccountType;

public record AccountDto(
        Long id,
        Long userId,
        String name,
        AccountType type
) {
}
