package dev.mednikov.expensetracking.dashboard.dto;

import java.math.BigDecimal;

public record IncomeExpenseWidgetDto(
        BigDecimal incomeAmount,
        BigDecimal expenseAmount,
        String income,
        String expense
) {
}
