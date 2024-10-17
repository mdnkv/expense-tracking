package dev.mednikov.expensetracking.dashboard.services;

import dev.mednikov.expensetracking.dashboard.dto.IncomeExpenseWidgetDto;

public interface DashboardService {

    IncomeExpenseWidgetDto getIncomeExpenseWidget(Long userId);

}
