package dev.mednikov.expensetracking.accounts.dto;

import dev.mednikov.expensetracking.accounts.models.Account;

import java.util.function.Function;

public final class AccountDtoMapper implements Function<Account, AccountDto> {

    @Override
    public AccountDto apply(Account account) {
        return new AccountDto(
                account.getId(),
                account.getUser().getId(),
                account.getName(),
                account.getType()
        );
    }
}
