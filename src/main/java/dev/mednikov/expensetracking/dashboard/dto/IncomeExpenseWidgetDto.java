package dev.mednikov.expensetracking.dashboard.dto;

import dev.mednikov.expensetracking.currencies.dto.CurrencyDto;

import java.math.BigDecimal;

public class IncomeExpenseWidgetDto {
    private CurrencyDto currency;
    private BigDecimal income;
    private BigDecimal expense;

    public CurrencyDto getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDto currency) {
        this.currency = currency;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public BigDecimal getExpense() {
        return expense;
    }

    public void setExpense(BigDecimal expense) {
        this.expense = expense;
    }
}
