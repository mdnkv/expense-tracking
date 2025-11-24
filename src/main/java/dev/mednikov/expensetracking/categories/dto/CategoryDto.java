package dev.mednikov.expensetracking.categories.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CategoryDto{

    private String id;
    private @NotNull String userId;
    private @NotNull @NotBlank String name;

    public CategoryDto(){}

    public CategoryDto(String id, String userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
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
}
