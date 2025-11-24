package dev.mednikov.expensetracking.operations.services;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import dev.mednikov.expensetracking.accounts.models.Account;
import dev.mednikov.expensetracking.accounts.models.AccountType;
import dev.mednikov.expensetracking.accounts.repositories.AccountRepository;
import dev.mednikov.expensetracking.categories.models.Category;
import dev.mednikov.expensetracking.categories.repositories.CategoryRepository;
import dev.mednikov.expensetracking.currencies.models.Currency;
import dev.mednikov.expensetracking.currencies.repositories.CurrencyRepository;
import dev.mednikov.expensetracking.operations.dto.OperationDto;
import dev.mednikov.expensetracking.operations.exceptions.OperationNotFoundException;
import dev.mednikov.expensetracking.operations.models.Operation;
import dev.mednikov.expensetracking.operations.models.OperationType;
import dev.mednikov.expensetracking.operations.repositories.OperationRepository;
import dev.mednikov.expensetracking.users.models.User;
import dev.mednikov.expensetracking.users.repositories.UserRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OperationServiceImplTest {

    private final static SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator();
    
    @Mock private UserRepository userRepository;
    @Mock private OperationRepository operationRepository;
    @Mock private AccountRepository accountRepository;
    @Mock private CategoryRepository categoryRepository;
    @Mock private CurrencyRepository currencyRepository;

    @InjectMocks private OperationServiceImpl operationService;

    @Test
    void createOperation_withAccountTest(){
        Long userId = snowflakeGenerator.next();
        Long accountId = snowflakeGenerator.next();
        Long operationId = snowflakeGenerator.next();
        Long currencyId = snowflakeGenerator.next();

        User user = new User.UserBuilder().withId(userId).build();

        Currency currency = new Currency();
        currency.setId(snowflakeGenerator.next());
        currency.setName("Euro");
        currency.setCode("EUR");
        currency.setUser(user);

        String description = "Sed lectus nibh, mattis quis tempor";
        Account account = new Account.AccountBuilder()
                .withName("Cash account")
                .withId(accountId)
                .withUser(user)
                .withType(AccountType.CASH)
                .build();
        Operation operation = new Operation.OperationBuilder()
                .withId(operationId)
                .withUser(user)
                .withAccount(account)
                .withAmount(BigDecimal.valueOf(100))
                .withCurrency(currency)
                .withOperationDate(LocalDate.now())
                .withType(OperationType.EXPENSE)
                .withDescription(description)
                .build();

        Mockito.when(currencyRepository.findById(currencyId)).thenReturn(Optional.of(currency));
        Mockito.when(userRepository.getReferenceById(userId)).thenReturn(user);
        Mockito.when(accountRepository.getReferenceById(accountId)).thenReturn(account);
        Mockito.when(operationRepository.save(Mockito.any(Operation.class))).thenReturn(operation);

        OperationDto request = new OperationDto();
        request.setAmount(BigDecimal.valueOf(100));
        request.setCurrencyId(currencyId.toString());
        request.setOperationType(OperationType.EXPENSE);
        request.setDescription(description);
        request.setAccountId(accountId.toString());
        request.setUserId(userId.toString());
        request.setDate(LocalDate.now());

        OperationDto result = operationService.createOperation(request);
        Assertions.assertThat(result).isNotNull();
    }

    @Test
    void createOperation_withAccountAndCategoryTest(){
        Long userId = snowflakeGenerator.next();
        Long accountId = snowflakeGenerator.next();
        Long operationId = snowflakeGenerator.next();
        Long categoryId = snowflakeGenerator.next();
        Long currencyId = snowflakeGenerator.next();

        User user = new User.UserBuilder().withId(userId).build();

        Currency currency = new Currency();
        currency.setId(currencyId);
        currency.setName("Euro");
        currency.setCode("EUR");
        currency.setUser(user);

        Account account = new Account.AccountBuilder()
                .withName("Cash account")
                .withId(accountId)
                .withUser(user)
                .withType(AccountType.CASH)
                .build();

        Category category = new Category.CategoryBuilder()
                .withId(categoryId)
                .withName("Expenses")
                .withUser(user)
                .build();

        String description = "Sed lectus nibh, mattis quis tempor";
        Operation operation = new Operation.OperationBuilder()
                .withId(operationId)
                .withUser(user)
                .withAccount(account)
                .withAmount(BigDecimal.valueOf(100))
                .withCurrency(currency)
                .withOperationDate(LocalDate.now())
                .withType(OperationType.EXPENSE)
                .withDescription(description)
                .withCategory(category)
                .build();
        OperationDto request = new OperationDto();
        request.setCategoryId(categoryId.toString());
        request.setAmount(BigDecimal.valueOf(100));
        request.setCurrencyId(currencyId.toString());
        request.setOperationType(OperationType.EXPENSE);
        request.setDescription(description);
        request.setAccountId(accountId.toString());
        request.setUserId(userId.toString());
        request.setDate(LocalDate.now());

        Mockito.when(currencyRepository.findById(currencyId)).thenReturn(Optional.of(currency));
        Mockito.when(userRepository.getReferenceById(userId)).thenReturn(user);
        Mockito.when(accountRepository.getReferenceById(accountId)).thenReturn(account);
        Mockito.when(categoryRepository.getReferenceById(categoryId)).thenReturn(category);
        Mockito.when(operationRepository.save(Mockito.any(Operation.class))).thenReturn(operation);

        OperationDto result = operationService.createOperation(request);
        Assertions
                .assertThat(result)
                .hasFieldOrPropertyWithValue("id", operationId.toString())
                .hasFieldOrProperty("account")
                .hasFieldOrProperty("category");
    }

    @Test
    void updateOperation_notFoundTest(){
        Long userId = snowflakeGenerator.next();
        Long accountId = snowflakeGenerator.next();
        Long operationId = snowflakeGenerator.next();
        Long currencyId = snowflakeGenerator.next();

        String description = "Sed lectus nibh, mattis quis tempor";
        OperationDto request = new OperationDto();
        request.setId(operationId.toString());
        request.setAmount(BigDecimal.valueOf(100));
        request.setCurrencyId(currencyId.toString());
        request.setOperationType(OperationType.EXPENSE);
        request.setDescription(description);
        request.setAccountId(accountId.toString());
        request.setUserId(userId.toString());
        request.setDate(LocalDate.now());

        Mockito.when(operationRepository.findById(operationId)).thenReturn(Optional.empty());

        Assertions
                .assertThatThrownBy(() -> operationService.updateOperation(request))
                .isInstanceOf(OperationNotFoundException.class);
    }

    @Test
    void updateOperation_withAccountTest(){
        Long userId = snowflakeGenerator.next();
        Long accountId = snowflakeGenerator.next();
        Long operationId = snowflakeGenerator.next();
        Long currencyId = snowflakeGenerator.next();

        User user = new User.UserBuilder().withId(userId).build();
        Account account = new Account.AccountBuilder()
                .withName("My credit card")
                .withId(accountId)
                .withUser(user)
                .withType(AccountType.CREDIT_CARD)
                .build();

        Currency currency = new Currency();
        currency.setId(snowflakeGenerator.next());
        currency.setName("Euro");
        currency.setCode("EUR");
        currency.setUser(user);

        String description = "Sed lectus nibh, mattis quis tempor";
        Operation operation = new Operation.OperationBuilder()
                .withId(operationId)
                .withUser(user)
                .withAccount(account)
                .withAmount(BigDecimal.valueOf(100))
                .withCurrency(currency)
                .withOperationDate(LocalDate.now())
                .withType(OperationType.EXPENSE)
                .withDescription(description)
                .build();

        Mockito.when(operationRepository.findById(operationId)).thenReturn(Optional.of(operation));
        Mockito.when(accountRepository.getReferenceById(accountId)).thenReturn(account);
        Mockito.when(operationRepository.save(Mockito.any(Operation.class))).thenReturn(operation);
        OperationDto request = new OperationDto();
        request.setId(operationId.toString());
        request.setAmount(BigDecimal.valueOf(100));
        request.setCurrencyId(currencyId.toString());
        request.setOperationType(OperationType.EXPENSE);
        request.setDescription(description);
        request.setAccountId(accountId.toString());
        request.setUserId(userId.toString());
        request.setDate(LocalDate.now());

        OperationDto result = operationService.updateOperation(request);
        Assertions.assertThat(result)
                .hasFieldOrPropertyWithValue("id", operationId.toString())
                .hasFieldOrProperty("account");
    }

    @Test
    void updateOperation_withAccountAndCategoryTest(){
        Long userId = snowflakeGenerator.next();
        Long accountId = snowflakeGenerator.next();
        Long operationId = snowflakeGenerator.next();
        Long categoryId = snowflakeGenerator.next();
        Long currencyId = snowflakeGenerator.next();

        User user = new User.UserBuilder().withId(userId).build();
        Account account = new Account.AccountBuilder()
                .withName("My cash account")
                .withId(accountId)
                .withUser(user)
                .withType(AccountType.CASH)
                .build();

        Currency currency = new Currency();
        currency.setId(snowflakeGenerator.next());
        currency.setName("Euro");
        currency.setCode("EUR");
        currency.setUser(user);

        Category category = new Category.CategoryBuilder()
                .withId(categoryId)
                .withName("Expenses")
                .withUser(user)
                .build();

        String description = "Sed lectus nibh, mattis quis tempor";
        Operation operation = new Operation.OperationBuilder()
                .withId(operationId)
                .withUser(user)
                .withAccount(account)
                .withAmount(BigDecimal.valueOf(100))
                .withCurrency(currency)
                .withOperationDate(LocalDate.now())
                .withType(OperationType.EXPENSE)
                .withCategory(category)
                .withDescription(description)
                .build();

        Mockito.when(operationRepository.findById(operationId)).thenReturn(Optional.of(operation));
        Mockito.when(accountRepository.getReferenceById(accountId)).thenReturn(account);
        Mockito.when(categoryRepository.getReferenceById(categoryId)).thenReturn(category);
        Mockito.when(operationRepository.save(Mockito.any(Operation.class))).thenReturn(operation);

        OperationDto request = new OperationDto();
        request.setId(operationId.toString());
        request.setAmount(BigDecimal.valueOf(100));
        request.setCurrencyId(currencyId.toString());
        request.setOperationType(OperationType.EXPENSE);
        request.setDescription(description);
        request.setAccountId(accountId.toString());
        request.setUserId(userId.toString());
        request.setDate(LocalDate.now());
        request.setCategoryId(categoryId.toString());

        OperationDto result = operationService.updateOperation(request);
        Assertions.assertThat(result)
                .hasFieldOrPropertyWithValue("id", operationId.toString())
                .hasFieldOrProperty("account");
    }

    @Test
    void findOperationById_existsTest(){
        Long userId = snowflakeGenerator.next();
        Long accountId = snowflakeGenerator.next();
        Long operationId = snowflakeGenerator.next();
        Long categoryId = snowflakeGenerator.next();

        User user = new User.UserBuilder().withId(userId).build();
        Account account = new Account.AccountBuilder()
                .withName("My bank account")
                .withId(accountId)
                .withUser(user)
                .withType(AccountType.BANK_ACCOUNT)
                .build();

        Currency currency = new Currency();
        currency.setId(snowflakeGenerator.next());
        currency.setName("Euro");
        currency.setCode("EUR");
        currency.setUser(user);

        Category category = new Category.CategoryBuilder()
                .withId(categoryId)
                .withName("Expenses")
                .withUser(user)
                .build();

        Operation operation = new Operation.OperationBuilder()
                .withId(operationId)
                .withUser(user)
                .withAccount(account)
                .withAmount(BigDecimal.valueOf(100))
                .withCurrency(currency)
                .withOperationDate(LocalDate.now())
                .withType(OperationType.EXPENSE)
                .withCategory(category)
                .withDescription("Nam orci purus, scelerisque quis eleifend vitae")
                .build();

        Mockito.when(operationRepository.findById(operationId)).thenReturn(Optional.of(operation));
        Optional<OperationDto> result = operationService.findOperationById(operationId);
        Assertions.assertThat(result).isPresent();
    }

    @Test
    void findOperationById_doesNotExistTest(){
        Long id = snowflakeGenerator.next();
        Mockito.when(operationRepository.findById(id)).thenReturn(Optional.empty());
        Optional<OperationDto> result = operationService.findOperationById(id);
        Assertions.assertThat(result).isEmpty();
    }



}
