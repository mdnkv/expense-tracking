package dev.mednikov.expensetracking.currencies.services;

import dev.mednikov.expensetracking.currencies.dto.CurrencyDto;

import java.util.List;

public interface CurrencyService {

    List<CurrencyDto> getCurrenciesForUser (Long userId);

}
