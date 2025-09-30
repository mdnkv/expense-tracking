package dev.mednikov.expensetracking.currencies.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public final class CurrencyNotFoundException extends RuntimeException {
}
