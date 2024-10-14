package dev.mednikov.expensetracking.users.services;

import dev.mednikov.expensetracking.users.dto.LoginRequestDto;
import dev.mednikov.expensetracking.users.dto.LoginResponseDto;

public interface AuthService {

    LoginResponseDto login (LoginRequestDto request);

}
