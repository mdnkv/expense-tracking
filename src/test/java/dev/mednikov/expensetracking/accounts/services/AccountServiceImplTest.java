package dev.mednikov.expensetracking.accounts.services;

import dev.mednikov.expensetracking.accounts.dto.AccountDto;
import dev.mednikov.expensetracking.accounts.exceptions.AccountNotFoundException;
import dev.mednikov.expensetracking.accounts.models.Account;
import dev.mednikov.expensetracking.accounts.models.AccountType;
import dev.mednikov.expensetracking.accounts.repositories.AccountRepository;
import dev.mednikov.expensetracking.users.models.User;
import dev.mednikov.expensetracking.users.repositories.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock private AccountRepository accountRepository;
    @Mock private UserRepository userRepository;
    @InjectMocks private AccountServiceImpl accountService;

    @Test
    void createAccountTest(){
        Long userId = 1L;
        User user = new User.UserBuilder().withId(userId).build();

        Long accountId = 1L;
        String accountName = "Cash account";
        Account account = new Account.AccountBuilder()
                .withUser(user)
                .withId(accountId)
                .withType(AccountType.CASH)
                .withName(accountName)
                .build();

        Mockito.when(userRepository.getReferenceById(userId)).thenReturn(user);
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);

        AccountDto request = new AccountDto();
        request.setUserId(userId);
        request.setName(accountName);
        request.setType(AccountType.CASH);

        AccountDto result = accountService.createAccount(request);

        Assertions
                .assertThat(result)
                .hasFieldOrPropertyWithValue("id", accountId)
                .hasFieldOrPropertyWithValue("name", accountName)
                .hasFieldOrPropertyWithValue("type", AccountType.CASH);
    }

    @Test
    void updateAccount_notFoundTest(){
        Long userId = 1L;
        Long accountId = 1L;

        Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.empty());

        AccountDto request = new AccountDto();
        request.setId(accountId);
        request.setUserId(userId);
        request.setName("My card account");
        request.setType(AccountType.CREDIT_CARD);
        Assertions
                .assertThatThrownBy(() -> accountService.updateAccount(request))
                .isInstanceOf(AccountNotFoundException.class);
    }

    @Test
    void updateAccount_successTest(){
        Long userId = 1L;
        User user = new User.UserBuilder().withId(userId).build();
        Long accountId = 1L;
        String accountName = "My credit card account";
        Account account = new Account.AccountBuilder()
                .withUser(user)
                .withId(accountId)
                .withType(AccountType.CREDIT_CARD)
                .withName(accountName)
                .build();

        Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));
        Mockito.when(accountRepository.save(Mockito.any(Account.class))).thenReturn(account);

        AccountDto request = new AccountDto();
        request.setId(accountId);
        request.setUserId(userId);
        request.setName(accountName);
        request.setType(AccountType.CREDIT_CARD);

        AccountDto result = accountService.updateAccount(request);

        Assertions
                .assertThat(result)
                .hasFieldOrPropertyWithValue("id", accountId)
                .hasFieldOrPropertyWithValue("name", accountName)
                .hasFieldOrPropertyWithValue("type", AccountType.CREDIT_CARD);
    }

    @Test
    void findAccountById_existsTest(){
        Long userId = 1L;
        User user = new User.UserBuilder().withId(userId).build();
        Long accountId = 1L;
        String accountName = "My cash account";
        Account account = new Account.AccountBuilder()
                .withUser(user)
                .withId(accountId)
                .withType(AccountType.CASH)
                .withName(accountName)
                .build();

        Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.of(account));

        Optional<AccountDto> result = accountService.findAccountById(accountId);
        Assertions.assertThat(result).isPresent();
    }

    @Test
    void findAccountById_doesNotExistTest(){
        Long accountId = 1L;
        Mockito.when(accountRepository.findById(accountId)).thenReturn(Optional.empty());
        Optional<AccountDto> result = accountService.findAccountById(accountId);
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    void findAllAccountsForUserTest(){
        Long userId = 1L;
        User user = new User.UserBuilder().withId(userId).build();

        List<Account> accounts = List.of(
                new Account.AccountBuilder()
                        .withUser(user)
                        .withId(1L)
                        .withType(AccountType.CASH)
                        .withName("Cash account")
                        .build(),
                new Account.AccountBuilder()
                        .withUser(user)
                        .withId(2L)
                        .withType(AccountType.CREDIT_CARD)
                        .withName("Credit card account")
                        .build(),
                new Account.AccountBuilder()
                        .withUser(user)
                        .withId(3L)
                        .withType(AccountType.BANK_ACCOUNT)
                        .withName("My bank account")
                        .build()
        );

        Mockito.when(accountRepository.findAllByUserId(userId)).thenReturn(accounts);

        List<AccountDto> result = accountService.findAllAccountsForUser(userId);
        Assertions.assertThat(result).hasSize(3);
    }

}
