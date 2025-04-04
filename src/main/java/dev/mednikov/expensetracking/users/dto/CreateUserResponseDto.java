package dev.mednikov.expensetracking.users.dto;

public class CreateUserResponseDto {

    private Long id;

    public CreateUserResponseDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
