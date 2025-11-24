package dev.mednikov.expensetracking.users.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ChangePasswordRequestDto{

    private @NotNull String id;
    private @NotNull @NotBlank String password;

    public ChangePasswordRequestDto(){}

    public ChangePasswordRequestDto(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
