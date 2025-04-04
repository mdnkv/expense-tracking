package dev.mednikov.expensetracking.operations.services;

import com.github.javafaker.Faker;

import dev.mednikov.expensetracking.accounts.models.Account;
import dev.mednikov.expensetracking.accounts.models.AccountType;
import dev.mednikov.expensetracking.accounts.repositories.AccountRepository;
import dev.mednikov.expensetracking.categories.models.Category;
import dev.mednikov.expensetracking.categories.repositories.CategoryRepository;
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

    private final static Faker faker = new Faker();

    @Mock private UserRepository userRepository;
    @Mock private OperationRepository operationRepository;
    @Mock private AccountRepository accountRepository;
    @Mock private CategoryRepository categoryRepository;

    @InjectMocks private OperationServiceImpl operationService;

    @Test
    void createOperation_withAccountTest(){
        Long userId = faker.number().randomNumber();
        Long accountId = faker.number().randomNumber();
        Long operationId = faker.number().randomNumber();

        User user = new User.UserBuilder().withId(userId).build();
        Account account = new Account.AccountBuilder()
                .withName(faker.lorem().fixedString(50))
                .withId(accountId)
                .withUser(user)
                .withType(AccountType.CASH)
                .build();
        Operation operation = new Operation.OperationBuilder()
                .withId(operationId)
                .withUser(user)
                .withAccount(account)
                .withAmount(BigDecimal.valueOf(100))
                .withCurrency("EUR")
                .withOperationDate(LocalDate.now())
                .withType(OperationType.EXPENSE)
                .withDescription(faker.lorem().fixedString(200))
                .build();

        Mockito.when(userRepository.getReferenceById(userId)).thenReturn(user);
        Mockito.when(accountRepository.getReferenceById(accountId)).thenReturn(account);
        Mockito.when(operationRepository.save(Mockito.any(Operation.class))).thenReturn(operation);

//        OperationRequestDto request = new OperationRequestDto.OperationRequestDtoBuilder()
//                .withAccountId(accountId)
//                .withUserId(userId)
//                .withAmount(BigDecimal.valueOf(100))
//                .withCurrency("EUR")
//                .withOperationDate(LocalDate.now())
//                .withType(OperationType.EXPENSE)
//                .withDescription(faker.lorem().fixedString(200))
//                .build();
//
        OperationDto request = new OperationDto();
        request.setAmount(BigDecimal.valueOf(100));
        request.setCurrency("EUR");
        request.setOperationType(OperationType.EXPENSE);
        request.setDescription(faker.lorem().fixedString(200));
        request.setAccountId(accountId);
        request.setUserId(userId);
        request.setDate(LocalDate.now());

        OperationDto result = operationService.createOperation(request);
        Assertions
                .assertThat(result)
                .hasFieldOrPropertyWithValue("id", operationId)
                .hasFieldOrProperty("account");
    }

    @Test
    void createOperation_withAccountAndCategoryTest(){
        Long userId = faker.number().randomNumber();
        Long accountId = faker.number().randomNumber();
        Long operationId = faker.number().randomNumber();
        Long categoryId = faker.number().randomNumber();

        User user = new User.UserBuilder().withId(userId).build();
        Account account = new Account.AccountBuilder()
                .withName(faker.lorem().fixedString(50))
                .withId(accountId)
                .withUser(user)
                .withType(AccountType.CASH)
                .build();

        Category category = new Category.CategoryBuilder()
                .withId(categoryId)
                .withName(faker.lorem().fixedString(15))
                .withUser(user)
                .build();

        Operation operation = new Operation.OperationBuilder()
                .withId(operationId)
                .withUser(user)
                .withAccount(account)
                .withAmount(BigDecimal.valueOf(100))
                .withCurrency("EUR")
                .withOperationDate(LocalDate.now())
                .withType(OperationType.EXPENSE)
                .withDescription(faker.lorem().fixedString(200))
                .withCategory(category)
                .build();
        OperationDto request = new OperationDto();
        request.setCategoryId(categoryId);
        request.setAmount(BigDecimal.valueOf(100));
        request.setCurrency("EUR");
        request.setOperationType(OperationType.EXPENSE);
        request.setDescription(faker.lorem().fixedString(200));
        request.setAccountId(accountId);
        request.setUserId(userId);
        request.setDate(LocalDate.now());

        Mockito.when(userRepository.getReferenceById(userId)).thenReturn(user);
        Mockito.when(accountRepository.getReferenceById(accountId)).thenReturn(account);
        Mockito.when(categoryRepository.getReferenceById(categoryId)).thenReturn(category);
        Mockito.when(operationRepository.save(Mockito.any(Operation.class))).thenReturn(operation);

//        OperationRequestDto request = new OperationRequestDto.OperationRequestDtoBuilder()
//                .withAccountId(accountId)
//                .withUserId(userId)
//                .withAmount(BigDecimal.valueOf(100))
//                .withCurrency("EUR")
//                .withOperationDate(LocalDate.now())
//                .withType(OperationType.EXPENSE)
//                .withCategoryId(categoryId)
//                .withDescription(faker.lorem().fixedString(200))
//                .build();

        OperationDto result = operationService.createOperation(request);
        Assertions
                .assertThat(result)
                .hasFieldOrPropertyWithValue("id", operationId)
                .hasFieldOrProperty("account")
                .hasFieldOrProperty("category");
    }

    @Test
    void updateOperation_notFoundTest(){
        Long userId = faker.number().randomNumber();
        Long accountId = faker.number().randomNumber();
        Long operationId = faker.number().randomNumber();
        Long categoryId = faker.number().randomNumber();
//        OperationRequestDto request = new OperationRequestDto.OperationRequestDtoBuilder()
//                .withId(operationId)
//                .withAccountId(accountId)
//                .withUserId(userId)
//                .withAmount(BigDecimal.valueOf(100))
//                .withCurrency("EUR")
//                .withOperationDate(LocalDate.now())
//                .withType(OperationType.EXPENSE)
//                .withCategoryId(categoryId)
//                .withDescription(faker.lorem().fixedString(200))
//                .build();
        OperationDto request = new OperationDto();
        request.setId(operationId);
        request.setAmount(BigDecimal.valueOf(100));
        request.setCurrency("EUR");
        request.setOperationType(OperationType.EXPENSE);
        request.setDescription(faker.lorem().fixedString(200));
        request.setAccountId(accountId);
        request.setUserId(userId);
        request.setDate(LocalDate.now());

        Mockito.when(operationRepository.findById(operationId)).thenReturn(Optional.empty());

        Assertions
                .assertThatThrownBy(() -> operationService.updateOperation(request))
                .isInstanceOf(OperationNotFoundException.class);
    }

    @Test
    void updateOperation_withAccountTest(){
        Long userId = faker.number().randomNumber();
        Long accountId = faker.number().randomNumber();
        Long operationId = faker.number().randomNumber();

        User user = new User.UserBuilder().withId(userId).build();
        Account account = new Account.AccountBuilder()
                .withName(faker.lorem().fixedString(50))
                .withId(accountId)
                .withUser(user)
                .withType(AccountType.CASH)
                .build();

        Operation operation = new Operation.OperationBuilder()
                .withId(operationId)
                .withUser(user)
                .withAccount(account)
                .withAmount(BigDecimal.valueOf(100))
                .withCurrency("EUR")
                .withOperationDate(LocalDate.now())
                .withType(OperationType.EXPENSE)
                .withDescription(faker.lorem().fixedString(200))
                .build();

        Mockito.when(operationRepository.findById(operationId)).thenReturn(Optional.of(operation));
        Mockito.when(accountRepository.getReferenceById(accountId)).thenReturn(account);
        Mockito.when(operationRepository.save(Mockito.any(Operation.class))).thenReturn(operation);

//        OperationRequestDto request = new OperationRequestDto.OperationRequestDtoBuilder()
//                .withId(operationId)
//                .withAccountId(accountId)
//                .withUserId(userId)
//                .withAmount(BigDecimal.valueOf(250))
//                .withCurrency("EUR")
//                .withOperationDate(LocalDate.now())
//                .withType(OperationType.EXPENSE)
//                .withDescription(faker.lorem().fixedString(200))
//                .build();
        OperationDto request = new OperationDto();
        request.setId(operationId);
        request.setAmount(BigDecimal.valueOf(100));
        request.setCurrency("EUR");
        request.setOperationType(OperationType.EXPENSE);
        request.setDescription(faker.lorem().fixedString(200));
        request.setAccountId(accountId);
        request.setUserId(userId);
        request.setDate(LocalDate.now());

        OperationDto result = operationService.updateOperation(request);
        Assertions.assertThat(result)
                .hasFieldOrPropertyWithValue("id", operationId)
                .hasFieldOrProperty("account");
    }

    @Test
    void updateOperation_withAccountAndCategoryTest(){
        Long userId = faker.number().randomNumber();
        Long accountId = faker.number().randomNumber();
        Long operationId = faker.number().randomNumber();
        Long categoryId = faker.number().randomNumber();

        User user = new User.UserBuilder().withId(userId).build();
        Account account = new Account.AccountBuilder()
                .withName(faker.lorem().fixedString(50))
                .withId(accountId)
                .withUser(user)
                .withType(AccountType.CASH)
                .build();

        Category category = new Category.CategoryBuilder()
                .withId(categoryId)
                .withName(faker.lorem().fixedString(15))
                .withUser(user)
                .build();

        Operation operation = new Operation.OperationBuilder()
                .withId(operationId)
                .withUser(user)
                .withAccount(account)
                .withAmount(BigDecimal.valueOf(100))
                .withCurrency("EUR")
                .withOperationDate(LocalDate.now())
                .withType(OperationType.EXPENSE)
                .withCategory(category)
                .withDescription(faker.lorem().fixedString(200))
                .build();

        Mockito.when(operationRepository.findById(operationId)).thenReturn(Optional.of(operation));
        Mockito.when(accountRepository.getReferenceById(accountId)).thenReturn(account);
        Mockito.when(categoryRepository.getReferenceById(categoryId)).thenReturn(category);
        Mockito.when(operationRepository.save(Mockito.any(Operation.class))).thenReturn(operation);

//        OperationRequestDto request = new OperationRequestDto.OperationRequestDtoBuilder()
//                .withId(operationId)
//                .withAccountId(accountId)
//                .withCategoryId(categoryId)
//                .withUserId(userId)
//                .withAmount(BigDecimal.valueOf(300))
//                .withCurrency("EUR")
//                .withOperationDate(LocalDate.now())
//                .withType(OperationType.EXPENSE)
//                .withDescription(faker.lorem().fixedString(200))
//                .build();
        OperationDto request = new OperationDto();
        request.setId(operationId);
        request.setAmount(BigDecimal.valueOf(100));
        request.setCurrency("EUR");
        request.setOperationType(OperationType.EXPENSE);
        request.setDescription(faker.lorem().fixedString(200));
        request.setAccountId(accountId);
        request.setUserId(userId);
        request.setDate(LocalDate.now());
        request.setCategoryId(categoryId);

        OperationDto result = operationService.updateOperation(request);
        Assertions.assertThat(result)
                .hasFieldOrPropertyWithValue("id", operationId)
                .hasFieldOrProperty("account");
    }

    @Test
    void findOperationById_existsTest(){
        Long userId = faker.number().randomNumber();
        Long accountId = faker.number().randomNumber();
        Long operationId = faker.number().randomNumber();
        Long categoryId = faker.number().randomNumber();

        User user = new User.UserBuilder().withId(userId).build();
        Account account = new Account.AccountBuilder()
                .withName(faker.lorem().fixedString(50))
                .withId(accountId)
                .withUser(user)
                .withType(AccountType.CASH)
                .build();

        Category category = new Category.CategoryBuilder()
                .withId(categoryId)
                .withName(faker.lorem().fixedString(15))
                .withUser(user)
                .build();

        Operation operation = new Operation.OperationBuilder()
                .withId(operationId)
                .withUser(user)
                .withAccount(account)
                .withAmount(BigDecimal.valueOf(100))
                .withCurrency("EUR")
                .withOperationDate(LocalDate.now())
                .withType(OperationType.EXPENSE)
                .withCategory(category)
                .withDescription(faker.lorem().fixedString(200))
                .build();

        Mockito.when(operationRepository.findById(operationId)).thenReturn(Optional.of(operation));
        Optional<OperationDto> result = operationService.findOperationById(operationId);
        Assertions.assertThat(result).isPresent();
    }

    @Test
    void findOperationById_doesNotExistTest(){
        Long id = faker.number().randomNumber();
        Mockito.when(operationRepository.findById(id)).thenReturn(Optional.empty());
        Optional<OperationDto> result = operationService.findOperationById(id);
        Assertions.assertThat(result).isEmpty();
    }



}
