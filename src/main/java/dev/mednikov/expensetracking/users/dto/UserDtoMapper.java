package dev.mednikov.expensetracking.users.dto;

import dev.mednikov.expensetracking.users.models.User;

import java.util.function.Function;

public final class UserDtoMapper implements Function<User, UserDto> {

    @Override
    public UserDto apply(User user) {
        Long userId = user.getId();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        return new UserDto(userId, email, firstName, lastName);
    }
}
