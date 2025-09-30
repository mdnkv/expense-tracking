package dev.mednikov.expensetracking.operations.dto;

import dev.mednikov.expensetracking.accounts.dto.AccountDto;
import dev.mednikov.expensetracking.categories.dto.CategoryDto;
import dev.mednikov.expensetracking.currencies.dto.CurrencyDto;
import dev.mednikov.expensetracking.operations.models.OperationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OperationDto {

    private Long id;
    @NotNull private Long userId;
    @NotNull private Long accountId;
    private Long categoryId;
    @NotNull private Long currencyId;
    @NotNull @NotBlank private String description;
    @NotNull private BigDecimal amount;
    @NotNull private LocalDate date;
    @NotNull private OperationType operationType;
    private CategoryDto category;
    private AccountDto account;
    private CurrencyDto currency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory(CategoryDto category) {
        this.category = category;
    }

    public AccountDto getAccount() {
        return account;
    }

    public void setAccount(AccountDto account) {
        this.account = account;
    }

    public CurrencyDto getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyDto currency) {
        this.currency = currency;
    }
}
