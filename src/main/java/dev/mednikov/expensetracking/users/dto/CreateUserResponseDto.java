package dev.mednikov.expensetracking.users.dto;

public class CreateUserResponseDto {

    private String id;

    public CreateUserResponseDto(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
