package dev.mednikov.expensetracking.operations.dto;

import dev.mednikov.expensetracking.accounts.dto.AccountDto;
import dev.mednikov.expensetracking.categories.dto.CategoryDto;
import dev.mednikov.expensetracking.operations.models.OperationType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OperationResponseDto(
        Long id,
        AccountDto account,
        CategoryDto category,
        String description,
        String currency,
        BigDecimal amount,
        OperationType type,
        LocalDate operationDate
) {
}
