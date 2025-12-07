package dev.mednikov.expensetracking.dashboard.services;

import dev.mednikov.expensetracking.currencies.dto.CurrencyDto;
import dev.mednikov.expensetracking.currencies.dto.CurrencyDtoMapper;
import dev.mednikov.expensetracking.currencies.models.Currency;
import dev.mednikov.expensetracking.currencies.repositories.CurrencyRepository;
import dev.mednikov.expensetracking.dashboard.dto.IncomeExpenseWidgetDto;
import dev.mednikov.expensetracking.operations.models.Operation;
import dev.mednikov.expensetracking.operations.models.OperationType;
import dev.mednikov.expensetracking.operations.repositories.OperationRepository;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService {
    private final static CurrencyDtoMapper currencyDtoMapper = new CurrencyDtoMapper();

    private final CurrencyRepository currencyRepository;
    private final OperationRepository operationRepository;

    public DashboardServiceImpl(OperationRepository operationRepository, CurrencyRepository currencyRepository) {
        this.operationRepository = operationRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public IncomeExpenseWidgetDto getIncomeExpenseWidget(Long userId) {
        // get query range dates
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(30);

        // get operations for user and in range
        List<Operation> operations = this.operationRepository.findAllInPeriod(userId, startDate, endDate);
        Currency currency = this.currencyRepository.findAllByUserId(userId).getFirst();
        CurrencyDto currencyDto = currencyDtoMapper.apply(currency);
        if (operations.isEmpty()){
            // return empty widget
            IncomeExpenseWidgetDto incomeExpenseWidgetDto = new IncomeExpenseWidgetDto();
            incomeExpenseWidgetDto.setCurrency(currencyDto);
            incomeExpenseWidgetDto.setIncome(BigDecimal.ZERO);
            incomeExpenseWidgetDto.setExpense(BigDecimal.ZERO);
            return incomeExpenseWidgetDto;
        }
        BigDecimal income = operations.stream().filter(o -> o.getType() == OperationType.INCOME)
                .map(Operation::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal expense = operations.stream().filter(o -> o.getType() == OperationType.EXPENSE)
                .map(Operation::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        IncomeExpenseWidgetDto incomeExpenseWidgetDto = new IncomeExpenseWidgetDto();
        incomeExpenseWidgetDto.setCurrency(currencyDto);
        incomeExpenseWidgetDto.setIncome(income);
        incomeExpenseWidgetDto.setExpense(expense);
        return incomeExpenseWidgetDto;
        // calculate income
//        Money income = operations.stream()
//                .filter(o -> o.getType() == OperationType.INCOME)
//                .map(Operation::getMonetaryAmount)
//                .reduce(Money.zero(defaultCurrency), Money::plus);
//
//        // calculate expense
//        Money expense = operations.stream()
//                .filter(o -> o.getType() == OperationType.EXPENSE)
//                .map(Operation::getMonetaryAmount)
//                .reduce(Money.zero(defaultCurrency), Money::plus);

        // create widget dto and return

    }
}
