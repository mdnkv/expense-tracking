package dev.mednikov.expensetracking.operations.services;

import cn.hutool.core.lang.generator.SnowflakeGenerator;
import dev.mednikov.expensetracking.accounts.models.Account;
import dev.mednikov.expensetracking.accounts.repositories.AccountRepository;
import dev.mednikov.expensetracking.categories.models.Category;
import dev.mednikov.expensetracking.categories.repositories.CategoryRepository;
import dev.mednikov.expensetracking.currencies.exceptions.CurrencyNotFoundException;
import dev.mednikov.expensetracking.currencies.models.Currency;
import dev.mednikov.expensetracking.currencies.repositories.CurrencyRepository;
import dev.mednikov.expensetracking.operations.dto.*;
import dev.mednikov.expensetracking.operations.exceptions.OperationNotFoundException;
import dev.mednikov.expensetracking.operations.models.Operation;
import dev.mednikov.expensetracking.operations.repositories.OperationRepository;
import dev.mednikov.expensetracking.users.models.User;
import dev.mednikov.expensetracking.users.repositories.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class OperationServiceImpl implements OperationService {

    private final static SnowflakeGenerator snowflakeGenerator = new SnowflakeGenerator();
    private final static OperationDtoMapper mapper = new OperationDtoMapper();

    private final OperationRepository operationRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final CurrencyRepository currencyRepository;

    public OperationServiceImpl(
            OperationRepository operationRepository,
            UserRepository userRepository,
            AccountRepository accountRepository,
            CategoryRepository categoryRepository,
            CurrencyRepository currencyRepository) {
        this.operationRepository = operationRepository;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
        this.currencyRepository = currencyRepository;
    }

    @Override
    public OperationDto createOperation(OperationDto request) {
        Long userId = Long.parseLong(request.getUserId());
        User user = this.userRepository.getReferenceById(userId);

        Long accountId = Long.parseLong(request.getAccountId());
        Account account = this.accountRepository.getReferenceById(accountId);

        Long currencyId = Long.parseLong(request.getCurrencyId());
        Currency currency = this.currencyRepository.findById(currencyId)
                .orElseThrow(CurrencyNotFoundException::new);

        Operation operation = new Operation.OperationBuilder()
                .withId(snowflakeGenerator.next())
                .withUser(user)
                .withAccount(account)
                .withAmount(request.getAmount())
                .withDescription(request.getDescription())
                .withCurrency(currency)
                .withType(request.getOperationType())
                .withOperationDate(request.getDate())
                .build();

        if (request.getCategoryId() != null){
            Long categoryId = Long.parseLong(request.getCategoryId());
            Category category = this.categoryRepository.getReferenceById(categoryId);
            operation.setCategory(category);
        }

        Operation result = this.operationRepository.save(operation);
        return mapper.apply(result);

    }

    @Override
    public OperationDto updateOperation(OperationDto request) {
        Objects.requireNonNull(request.getId());
        Long id = Long.parseLong(request.getId());
        Operation operation = this.operationRepository
                .findById(id)
                .orElseThrow(OperationNotFoundException::new);

        operation.setAmount(request.getAmount());
        operation.setDescription(request.getDescription());
        operation.setType(request.getOperationType());
        operation.setOperationDate(request.getDate());

        Long accountId = Long.parseLong(request.getAccountId());
        Account account = this.accountRepository.getReferenceById(accountId);
        operation.setAccount(account);

        if (request.getCategoryId() != null){
            Long categoryId = Long.parseLong(request.getCategoryId());
            Category category = this.categoryRepository.getReferenceById(categoryId);
            operation.setCategory(category);
        }



        Operation result = this.operationRepository.save(operation);
        return mapper.apply(result);
    }

    @Override
    public void deleteOperation(Long id) {
        operationRepository.deleteById(id);
    }

    @Override
    public Optional<OperationDto> findOperationById(Long id) {
        return this.operationRepository.findById(id).map(mapper);
    }

    @Override
    public List<OperationDto> findAllOperationsForUser(Long userId) {
        return this.operationRepository
                .findAllByUserId(userId)
                .stream()
                .map(mapper).toList();
    }
}
