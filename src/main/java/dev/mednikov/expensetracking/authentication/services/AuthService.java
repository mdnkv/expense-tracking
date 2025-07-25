package dev.mednikov.expensetracking.authentication.services;

import dev.mednikov.expensetracking.authentication.dto.LoginRequestDto;
import dev.mednikov.expensetracking.authentication.dto.LoginResponseDto;
import dev.mednikov.expensetracking.authentication.models.AuthenticatedUser;
import org.springframework.security.core.AuthenticatedPrincipal;

import java.util.Optional;

public interface AuthService {

    LoginResponseDto login (LoginRequestDto request);

    Optional<AuthenticatedUser> authorize (String credentials);

    void logout (AuthenticatedUser principal);

}
