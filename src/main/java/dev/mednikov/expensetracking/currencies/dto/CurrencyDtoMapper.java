package dev.mednikov.expensetracking.currencies.dto;

import dev.mednikov.expensetracking.currencies.models.Currency;

import java.util.function.Function;

public final class CurrencyDtoMapper implements Function<Currency, CurrencyDto> {

    @Override
    public CurrencyDto apply(Currency currency) {
        CurrencyDto currencyDto = new CurrencyDto();
        currencyDto.setName(currency.getName());
        currencyDto.setCode(currency.getCode());
        currencyDto.setId(currency.getId());
        currencyDto.setUserId(currency.getUser().getId());
        return currencyDto;
    }
}
