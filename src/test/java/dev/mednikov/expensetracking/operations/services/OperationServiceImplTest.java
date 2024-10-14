package dev.mednikov.expensetracking.operations.services;

import com.github.javafaker.Faker;
import dev.mednikov.expensetracking.accounts.models.Account;
import dev.mednikov.expensetracking.accounts.models.AccountType;
import dev.mednikov.expensetracking.accounts.repositories.AccountRepository;
import dev.mednikov.expensetracking.categories.models.Category;
import dev.mednikov.expensetracking.categories.repositories.CategoryRepository;
import dev.mednikov.expensetracking.operations.dto.OperationRequestDto;
import dev.mednikov.expensetracking.operations.dto.OperationResponseDto;
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
    void createAccount_withAccountTest(){
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

        OperationRequestDto request = new OperationRequestDto.OperationRequestDtoBuilder()
                .withAccountId(accountId)
                .withUserId(userId)
                .withAmount(BigDecimal.valueOf(100))
                .withCurrency("EUR")
                .withOperationDate(LocalDate.now())
                .withType(OperationType.EXPENSE)
                .withDescription(faker.lorem().fixedString(200))
                .build();

        OperationResponseDto result = operationService.createOperation(request);
        Assertions
                .assertThat(result)
                .hasFieldOrPropertyWithValue("id", operationId)
                .hasFieldOrProperty("account");


    }

    @Test
    void createAccount_withAccountAndCategoryTest(){
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

        Mockito.when(userRepository.getReferenceById(userId)).thenReturn(user);
        Mockito.when(accountRepository.getReferenceById(accountId)).thenReturn(account);
        Mockito.when(categoryRepository.getReferenceById(categoryId)).thenReturn(category);
        Mockito.when(operationRepository.save(Mockito.any(Operation.class))).thenReturn(operation);

        OperationRequestDto request = new OperationRequestDto.OperationRequestDtoBuilder()
                .withAccountId(accountId)
                .withUserId(userId)
                .withAmount(BigDecimal.valueOf(100))
                .withCurrency("EUR")
                .withOperationDate(LocalDate.now())
                .withType(OperationType.EXPENSE)
                .withCategoryId(categoryId)
                .withDescription(faker.lorem().fixedString(200))
                .build();

        OperationResponseDto result = operationService.createOperation(request);
        Assertions
                .assertThat(result)
                .hasFieldOrPropertyWithValue("id", operationId)
                .hasFieldOrProperty("account")
                .hasFieldOrProperty("category");
    }

    @Test
    void updateAccount_notFoundTest(){
        Long userId = faker.number().randomNumber();
        Long accountId = faker.number().randomNumber();
        Long operationId = faker.number().randomNumber();
        Long categoryId = faker.number().randomNumber();
        OperationRequestDto request = new OperationRequestDto.OperationRequestDtoBuilder()
                .withId(operationId)
                .withAccountId(accountId)
                .withUserId(userId)
                .withAmount(BigDecimal.valueOf(100))
                .withCurrency("EUR")
                .withOperationDate(LocalDate.now())
                .withType(OperationType.EXPENSE)
                .withCategoryId(categoryId)
                .withDescription(faker.lorem().fixedString(200))
                .build();

        Mockito.when(operationRepository.findById(operationId)).thenReturn(Optional.empty());

        Assertions
                .assertThatThrownBy(() -> operationService.updateOperation(request))
                .isInstanceOf(OperationNotFoundException.class);
    }

    void updateAccount_withAccountTest(){}

    void updateAccount_withAccountAndCategoryTest(){}

    void findAccountById_existsTest(){}

    void findAccountById_doesNotExistTest(){}



}
