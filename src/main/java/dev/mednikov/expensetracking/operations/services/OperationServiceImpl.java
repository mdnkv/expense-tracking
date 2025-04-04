package dev.mednikov.expensetracking.operations.services;

import dev.mednikov.expensetracking.accounts.models.Account;
import dev.mednikov.expensetracking.accounts.repositories.AccountRepository;
import dev.mednikov.expensetracking.categories.models.Category;
import dev.mednikov.expensetracking.categories.repositories.CategoryRepository;
import dev.mednikov.expensetracking.operations.dto.*;
import dev.mednikov.expensetracking.operations.exceptions.OperationNotFoundException;
import dev.mednikov.expensetracking.operations.models.Operation;
import dev.mednikov.expensetracking.operations.repositories.OperationRepository;
import dev.mednikov.expensetracking.users.models.User;
import dev.mednikov.expensetracking.users.repositories.UserRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OperationServiceImpl implements OperationService {

    private final static OperationDtoMapper mapper = new OperationDtoMapper();

    private final OperationRepository operationRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;

    public OperationServiceImpl(OperationRepository operationRepository, UserRepository userRepository, AccountRepository accountRepository, CategoryRepository categoryRepository) {
        this.operationRepository = operationRepository;
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public OperationDto createOperation(OperationDto request) {
        User user = this.userRepository.getReferenceById(request.getUserId());
        Account account = this.accountRepository.getReferenceById(request.getAccountId());

        Operation operation = new Operation.OperationBuilder()
                .withUser(user)
                .withAccount(account)
                .withAmount(request.getAmount())
                .withDescription(request.getDescription())
                .withCurrency(request.getCurrency())
                .withType(request.getOperationType())
                .withOperationDate(request.getDate())
                .build();

        if (request.getCategoryId() != null){
            Category category = this.categoryRepository.getReferenceById(request.getCategoryId());
            operation.setCategory(category);
        }

        Operation result = this.operationRepository.save(operation);
        return mapper.apply(result);

    }

    @Override
    public OperationDto updateOperation(OperationDto request) {
        Operation operation = this.operationRepository
                .findById(request.getId())
                .orElseThrow(OperationNotFoundException::new);

        operation.setAmount(request.getAmount());
        operation.setDescription(request.getDescription());
        operation.setCurrency(request.getCurrency());
        operation.setType(request.getOperationType());
        operation.setOperationDate(request.getDate());

        Account account = this.accountRepository.getReferenceById(request.getAccountId());
        operation.setAccount(account);

        if (request.getCategoryId() != null){
            Category category = this.categoryRepository.getReferenceById(request.getCategoryId());
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
