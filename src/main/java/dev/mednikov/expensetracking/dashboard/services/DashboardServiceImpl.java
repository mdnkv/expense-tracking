package dev.mednikov.expensetracking.dashboard.services;

import dev.mednikov.expensetracking.dashboard.dto.IncomeExpenseWidgetDto;
import dev.mednikov.expensetracking.operations.models.Operation;
import dev.mednikov.expensetracking.operations.models.OperationType;
import dev.mednikov.expensetracking.operations.repositories.OperationRepository;

import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final static CurrencyUnit defaultCurrency = CurrencyUnit.EUR;

    private final OperationRepository operationRepository;

    public DashboardServiceImpl(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public IncomeExpenseWidgetDto getIncomeExpenseWidget(Long userId) {
        // get query range dates
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(30);

        // get operations for user and in range
        List<Operation> operations = this.operationRepository.findAllInPeriod(userId, startDate, endDate);
        if (operations.isEmpty()){
            // return empty widget
            return new IncomeExpenseWidgetDto(
                    BigDecimal.ZERO,
                    BigDecimal.ZERO,
                    Money.zero(defaultCurrency).toString(),
                    Money.zero(defaultCurrency).toString()
            );
        }

        // calculate income
        Money income = operations.stream()
                .filter(o -> o.getType() == OperationType.INCOME)
                .map(Operation::getMonetaryAmount)
                .reduce(Money.zero(defaultCurrency), Money::plus);

        // calculate expense
        Money expense = operations.stream()
                .filter(o -> o.getType() == OperationType.EXPENSE)
                .map(Operation::getMonetaryAmount)
                .reduce(Money.zero(defaultCurrency), Money::plus);

        // create widget dto and return
        return new IncomeExpenseWidgetDto(
                income.getAmount(),
                expense.getAmount(),
                income.toString(),
                expense.toString()
        );
    }
}
