package dev.mednikov.expensetracking.dashboard.services;

import com.github.javafaker.Faker;
import dev.mednikov.expensetracking.accounts.models.Account;
import dev.mednikov.expensetracking.accounts.models.AccountType;
import dev.mednikov.expensetracking.dashboard.dto.IncomeExpenseWidgetDto;
import dev.mednikov.expensetracking.operations.models.Operation;
import dev.mednikov.expensetracking.operations.models.OperationType;
import dev.mednikov.expensetracking.operations.repositories.OperationRepository;
import dev.mednikov.expensetracking.users.models.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class DashboardServiceImplTest {

    private final static Faker faker = new Faker();

    @Mock private OperationRepository operationRepository;
    @InjectMocks private DashboardServiceImpl dashboardService;

    @Test
    void getIncomeExpenseWidget_noOperationsTest(){
        Long userId = faker.number().randomNumber();
        List<Operation> operations = List.of();

        Mockito
                .when(operationRepository.findAllInPeriod(Mockito.eq(userId), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
                .thenReturn(operations);

        IncomeExpenseWidgetDto result = dashboardService.getIncomeExpenseWidget(userId);
        Assertions.assertThat(result)
                .hasFieldOrProperty("incomeAmount")
                .hasFieldOrProperty("expenseAmount")
                .hasFieldOrPropertyWithValue("income", "EUR 0.00")
                .hasFieldOrPropertyWithValue("expense", "EUR 0.00");

    }

    @Test
    void getIncomeExpenseWidgetTest(){
        Long userId = faker.number().randomNumber();
        Long accountId = faker.number().randomNumber();
        User user = new User.UserBuilder().withId(userId).build();
        Account account = new Account.AccountBuilder()
                .withName(faker.lorem().fixedString(50))
                .withId(accountId)
                .withUser(user)
                .withType(AccountType.CASH)
                .build();

        List<Operation> operations = List.of(
                new Operation.OperationBuilder()
                        .withId(faker.number().randomNumber())
                        .withUser(user)
                        .withAccount(account)
                        .withAmount(BigDecimal.valueOf(100))
                        .withCurrency("EUR")
                        .withOperationDate(LocalDate.now())
                        .withType(OperationType.EXPENSE)
                        .withDescription(faker.lorem().fixedString(200))
                        .build(),
                new Operation.OperationBuilder()
                        .withId(faker.number().randomNumber())
                        .withUser(user)
                        .withAccount(account)
                        .withAmount(BigDecimal.valueOf(100))
                        .withCurrency("EUR")
                        .withOperationDate(LocalDate.now())
                        .withType(OperationType.EXPENSE)
                        .withDescription(faker.lorem().fixedString(200))
                        .build(),
                new Operation.OperationBuilder()
                        .withId(faker.number().randomNumber())
                        .withUser(user)
                        .withAccount(account)
                        .withAmount(BigDecimal.valueOf(150))
                        .withCurrency("EUR")
                        .withOperationDate(LocalDate.now())
                        .withType(OperationType.INCOME)
                        .withDescription(faker.lorem().fixedString(200))
                        .build(),
                new Operation.OperationBuilder()
                        .withId(faker.number().randomNumber())
                        .withUser(user)
                        .withAccount(account)
                        .withAmount(BigDecimal.valueOf(150))
                        .withCurrency("EUR")
                        .withOperationDate(LocalDate.now())
                        .withType(OperationType.INCOME)
                        .withDescription(faker.lorem().fixedString(200))
                        .build()
        );

        Mockito
                .when(operationRepository.findAllInPeriod(Mockito.eq(userId), Mockito.any(LocalDate.class), Mockito.any(LocalDate.class)))
                .thenReturn(operations);

        IncomeExpenseWidgetDto result = dashboardService.getIncomeExpenseWidget(userId);
        Assertions.assertThat(result)
                .hasFieldOrProperty("incomeAmount")
                .hasFieldOrProperty("expenseAmount")
                .hasFieldOrPropertyWithValue("income", "EUR 300.00")
                .hasFieldOrPropertyWithValue("expense", "EUR 200.00");
    }

}
