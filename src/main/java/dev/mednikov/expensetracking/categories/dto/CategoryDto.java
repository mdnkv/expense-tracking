package dev.mednikov.expensetracking.categories.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CategoryDto{

    private Long id;
    private @NotNull Long userId;
    private @NotNull @NotBlank String name;

    public CategoryDto(){}

    public CategoryDto(Long id, Long userId, String name) {
        this.id = id;
        this.userId = userId;
        this.name = name;
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
}
