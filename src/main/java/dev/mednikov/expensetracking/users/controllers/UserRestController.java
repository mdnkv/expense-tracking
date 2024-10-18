package dev.mednikov.expensetracking.users.controllers;

import dev.mednikov.expensetracking.users.dto.ChangePasswordRequestDto;
import dev.mednikov.expensetracking.users.dto.CreateUserRequestDto;
import dev.mednikov.expensetracking.users.dto.CreateUserResponseDto;
import dev.mednikov.expensetracking.users.dto.UserDto;
import dev.mednikov.expensetracking.users.services.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public @ResponseBody CreateUserResponseDto createUser (@RequestBody CreateUserRequestDto body){
        return this.userService.createUser(body);
    }

    @PutMapping("/update/user")
    public @ResponseBody UserDto updateUser (@RequestBody UserDto body){
        return this.userService.updateUser(body);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser (@PathVariable Long id){
        this.userService.deleteUser(id);
    }

    @PostMapping("/update/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword (@RequestBody ChangePasswordRequestDto body){
        this.userService.changePassword(body);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserDto> getUser (@PathVariable Long id){
        Optional<UserDto> user = this.userService.getUser(id);
        return ResponseEntity.of(user);
    }

}
