package dev.mednikov.expensetracking.accounts.services;

import dev.mednikov.expensetracking.accounts.dto.AccountDto;
import dev.mednikov.expensetracking.accounts.dto.AccountDtoMapper;
import dev.mednikov.expensetracking.accounts.exceptions.AccountNotFoundException;
import dev.mednikov.expensetracking.accounts.models.Account;
import dev.mednikov.expensetracking.accounts.repositories.AccountRepository;
import dev.mednikov.expensetracking.users.models.User;
import dev.mednikov.expensetracking.users.repositories.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final static AccountDtoMapper mapper = new AccountDtoMapper();

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto request) {
        User user = this.userRepository.getReferenceById(request.userId());
        Account account = new Account.AccountBuilder()
                .withUser(user)
                .withName(request.name())
                .withType(request.type())
                .build();
        Account result  = this.accountRepository.save(account);
        return mapper.apply(result);
    }

    @Override
    public AccountDto updateAccount(AccountDto request) {
        Account account = this.accountRepository
                .findById(request.id())
                .orElseThrow(AccountNotFoundException::new);

        account.setName(request.name());
        account.setType(request.type());

        Account result = this.accountRepository.save(account);
        return mapper.apply(result);
    }

    @Override
    public void deleteAccount(Long id) {
        this.accountRepository.deleteById(id);
    }

    @Override
    public Optional<AccountDto> findAccountById(Long id) {
        return this.accountRepository.findById(id).map(mapper);
    }

    @Override
    public List<AccountDto> findAllAccountsForUser(Long userId) {
        return this.accountRepository.findAllByUserId(userId)
                .stream().sorted().map(mapper).toList();
    }
}
