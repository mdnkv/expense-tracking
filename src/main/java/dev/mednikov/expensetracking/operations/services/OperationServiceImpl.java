package dev.mednikov.expensetracking.operations.services;

import dev.mednikov.expensetracking.accounts.models.Account;
import dev.mednikov.expensetracking.accounts.repositories.AccountRepository;
import dev.mednikov.expensetracking.categories.models.Category;
import dev.mednikov.expensetracking.categories.repositories.CategoryRepository;
import dev.mednikov.expensetracking.operations.dto.OperationRequestDto;
import dev.mednikov.expensetracking.operations.dto.OperationResponseDto;
import dev.mednikov.expensetracking.operations.dto.OperationResponseDtoMapper;
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

    private final static OperationResponseDtoMapper responseDtoMapper = new OperationResponseDtoMapper();

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
    public OperationResponseDto createOperation(OperationRequestDto request) {
        User user = this.userRepository.getReferenceById(request.userId());
        Account account = this.accountRepository.getReferenceById(request.accountId());

        Operation operation = new Operation.OperationBuilder()
                .withUser(user)
                .withAccount(account)
                .withAmount(request.amount())
                .withDescription(request.description())
                .withCurrency(request.currency())
                .withType(request.type())
                .withOperationDate(request.operationDate())
                .build();

        if (request.categoryId() != null){
            Category category = this.categoryRepository.getReferenceById(request.categoryId());
            operation.setCategory(category);
        }

        Operation result = this.operationRepository.save(operation);
        return responseDtoMapper.apply(result);

    }

    @Override
    public OperationResponseDto updateOperation(OperationRequestDto request) {
        Operation operation = this.operationRepository
                .findById(request.id())
                .orElseThrow(OperationNotFoundException::new);

        operation.setAmount(request.amount());
        operation.setDescription(request.description());
        operation.setCurrency(request.currency());
        operation.setType(request.type());
        operation.setOperationDate(request.operationDate());

        if (request.categoryId() != null){
            Category category = this.categoryRepository.getReferenceById(request.categoryId());
            operation.setCategory(category);
        }

        if (request.accountId() != null){
            Account account = this.accountRepository.getReferenceById(request.accountId());
            operation.setAccount(account);
        }

        Operation result = this.operationRepository.save(operation);
        return responseDtoMapper.apply(result);
    }

    @Override
    public void deleteOperation(Long id) {
        operationRepository.deleteById(id);
    }

    @Override
    public Optional<OperationResponseDto> findOperationById(Long id) {
        return this.operationRepository.findById(id).map(responseDtoMapper);
    }

    @Override
    public List<OperationResponseDto> findAllOperationsForUser(Long userId) {
        return this.operationRepository
                .findAllByUserId(userId)
                .stream().map(responseDtoMapper).toList();
    }
}
