package dev.mednikov.expensetracking.dashboard.controllers;

import dev.mednikov.expensetracking.dashboard.dto.IncomeExpenseWidgetDto;
import dev.mednikov.expensetracking.dashboard.services.DashboardService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardRestController {

    private final DashboardService dashboardService;

    public DashboardRestController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/income-expense/{userId}")
    public @ResponseBody IncomeExpenseWidgetDto getIncomeExpenseWidget(@PathVariable Long userId) {
        return this.dashboardService.getIncomeExpenseWidget(userId);
    }

}
