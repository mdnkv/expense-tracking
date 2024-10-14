package dev.mednikov.expensetracking.users.services;

import dev.mednikov.expensetracking.users.dto.ChangePasswordRequestDto;
import dev.mednikov.expensetracking.users.dto.CreateUserRequestDto;
import dev.mednikov.expensetracking.users.dto.CreateUserResponseDto;
import dev.mednikov.expensetracking.users.dto.UserDto;

import java.util.Optional;

public interface UserService {

    CreateUserResponseDto createUser (CreateUserRequestDto request);

    Optional<UserDto> getUser (Long userId);

    UserDto updateUser (UserDto request);

    void deleteUser (Long userId);

    void changePassword (ChangePasswordRequestDto request);

}
