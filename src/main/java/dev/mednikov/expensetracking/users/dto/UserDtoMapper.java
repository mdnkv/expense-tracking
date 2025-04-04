package dev.mednikov.expensetracking.users.dto;

import dev.mednikov.expensetracking.users.models.User;

import java.util.function.Function;

public final class UserDtoMapper implements Function<User, UserDto> {

    @Override
    public UserDto apply(User user) {
        UserDto result = new UserDto();
        result.setId(user.getId());
        result.setEmail(user.getEmail());
        result.setFirstName(user.getFirstName());
        result.setLastName(user.getLastName());
        return result;
    }
}
