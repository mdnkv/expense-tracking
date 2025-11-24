package dev.mednikov.expensetracking.accounts.dto;

import dev.mednikov.expensetracking.accounts.models.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AccountDto{

    private String id;
    private @NotNull String userId;
    private @NotNull @NotBlank String name;
    private @NotNull AccountType type;

    public AccountDto(){}

    public AccountDto(String id, String userId, String name, AccountType type) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.type = type;
    }

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
