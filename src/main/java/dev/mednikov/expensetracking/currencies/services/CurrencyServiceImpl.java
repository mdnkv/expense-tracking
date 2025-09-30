package dev.mednikov.expensetracking.currencies.services;

import dev.mednikov.expensetracking.currencies.dto.CurrencyDto;
import dev.mednikov.expensetracking.currencies.dto.CurrencyDtoMapper;
import dev.mednikov.expensetracking.currencies.models.Currency;
import dev.mednikov.expensetracking.currencies.repositories.CurrencyRepository;
import dev.mednikov.expensetracking.users.events.NewUserCreatedEvent;
import dev.mednikov.expensetracking.users.models.User;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public List<CurrencyDto> getCurrenciesForUser(Long userId) {
        CurrencyDtoMapper mapper = new CurrencyDtoMapper();
        return this.currencyRepository.findAllByUserId(userId)
                .stream()
                .map(mapper)
                .toList();
    }

    @EventListener(NewUserCreatedEvent.class)
    public void onNewUserCreatedEventListener (NewUserCreatedEvent event) {
        User user = event.getUser();
        // Create currency
        Currency currency = new Currency();
        currency.setUser(user);
        currency.setCode("EUR");
        currency.setName("Euro - EUR");
        currencyRepository.save(currency);
    }

}
