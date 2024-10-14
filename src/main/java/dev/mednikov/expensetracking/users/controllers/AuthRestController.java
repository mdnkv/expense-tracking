package dev.mednikov.expensetracking.users.controllers;

import dev.mednikov.expensetracking.users.dto.LoginRequestDto;
import dev.mednikov.expensetracking.users.dto.LoginResponseDto;
import dev.mednikov.expensetracking.users.services.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthRestController {

    private final AuthService authService;

    public AuthRestController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public @ResponseBody LoginResponseDto login (@RequestBody LoginRequestDto body){
        return this.authService.login(body);
    }

}
