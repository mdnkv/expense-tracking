package dev.mednikov.expensetracking.users.dto;

public record CreateUserRequestDto(
        String email,
        String password,
        String firstName,
        String lastName
) {
}
