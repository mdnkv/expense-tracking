package dev.mednikov.expensetracking.currencies.controllers;

import dev.mednikov.expensetracking.currencies.dto.CurrencyDto;
import dev.mednikov.expensetracking.currencies.services.CurrencyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/currencies")
public class CurrencyRestController {

    private final CurrencyService currencyService;

    public CurrencyRestController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/user/{userId}")
    public @ResponseBody List<CurrencyDto> getCurrenciesForUser (@PathVariable Long userId) {
        return this.currencyService.getCurrenciesForUser(userId);
    }

}
