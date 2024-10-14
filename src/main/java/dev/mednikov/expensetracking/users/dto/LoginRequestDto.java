package dev.mednikov.expensetracking.users.dto;

public record LoginRequestDto(
        String email,
        String password
) {
}
