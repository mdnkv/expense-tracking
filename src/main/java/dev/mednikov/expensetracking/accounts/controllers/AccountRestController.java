package dev.mednikov.expensetracking.accounts.controllers;

import dev.mednikov.expensetracking.accounts.dto.AccountDto;
import dev.mednikov.expensetracking.accounts.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/accounts")
public class AccountRestController {

    private final AccountService accountService;

    public AccountRestController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody AccountDto createAccount(@RequestBody AccountDto body) {
        return this.accountService.createAccount(body);
    }

    @PutMapping("/update")
    public @ResponseBody AccountDto updateAccount(@RequestBody AccountDto body) {
        return this.accountService.updateAccount(body);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAccount(@PathVariable Long id) {
        this.accountService.deleteAccount(id);
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        Optional<AccountDto> result = this.accountService.findAccountById(id);
        return ResponseEntity.of(result);
    }

    @GetMapping("/user/{userId}")
    public @ResponseBody List<AccountDto> getAllAccountsForUser (@PathVariable Long userId){
        return this.accountService.findAllAccountsForUser(userId);
    }

}
