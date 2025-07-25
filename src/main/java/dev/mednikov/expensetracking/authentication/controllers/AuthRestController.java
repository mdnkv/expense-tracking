package dev.mednikov.expensetracking.authentication.controllers;

import dev.mednikov.expensetracking.authentication.dto.LoginRequestDto;
import dev.mednikov.expensetracking.authentication.dto.LoginResponseDto;
import dev.mednikov.expensetracking.authentication.models.AuthenticatedUser;
import dev.mednikov.expensetracking.authentication.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthRestController {

    private final AuthService authService;

    public AuthRestController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public @ResponseBody LoginResponseDto login (@RequestBody @Valid LoginRequestDto body){
        return this.authService.login(body);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout(Authentication authentication) {
        AuthenticatedUser principal = (AuthenticatedUser) authentication.getPrincipal();
        this.authService.logout(principal);
    }

}
