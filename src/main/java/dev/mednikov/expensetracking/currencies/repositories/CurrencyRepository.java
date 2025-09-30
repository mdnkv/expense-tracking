package dev.mednikov.expensetracking.currencies.repositories;

import dev.mednikov.expensetracking.currencies.models.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    List<Currency> findAllByUserId (Long userId);

}
