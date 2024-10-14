package dev.mednikov.expensetracking.users.dto;

public record UserDto(
        Long id,
        String email,
        String firstName,
        String lastName
) {
}
