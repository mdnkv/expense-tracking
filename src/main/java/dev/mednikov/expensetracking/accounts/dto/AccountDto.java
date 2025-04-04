package dev.mednikov.expensetracking.accounts.dto;

import dev.mednikov.expensetracking.accounts.models.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AccountDto{

    private Long id;
    private @NotNull Long userId;
    private @NotNull @NotBlank String name;
    private @NotNull AccountType type;

    public AccountDto(){}

    public AccountDto(Long id, Long userId, String name, AccountType type) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.type = type;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }
}
