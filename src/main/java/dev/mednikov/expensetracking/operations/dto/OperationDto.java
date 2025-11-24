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

    private String id;
    @NotNull private String userId;
    @NotNull private String accountId;
    private String categoryId;
    @NotNull private String currencyId;
    @NotNull @NotBlank private String description;
    @NotNull private BigDecimal amount;
    @NotNull private LocalDate date;
    @NotNull private OperationType operationType;
    private CategoryDto category;
    private AccountDto account;
    private CurrencyDto currency;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
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
