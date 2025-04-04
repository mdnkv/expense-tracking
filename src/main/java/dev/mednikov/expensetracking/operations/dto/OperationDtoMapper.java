package dev.mednikov.expensetracking.operations.dto;

import dev.mednikov.expensetracking.accounts.dto.AccountDto;
import dev.mednikov.expensetracking.accounts.dto.AccountDtoMapper;
import dev.mednikov.expensetracking.categories.dto.CategoryDto;
import dev.mednikov.expensetracking.categories.dto.CategoryDtoMapper;
import dev.mednikov.expensetracking.operations.models.Operation;

import java.util.Optional;
import java.util.function.Function;

public final class OperationDtoMapper implements Function<Operation, OperationDto> {

    private final static AccountDtoMapper accountDtoMapper = new AccountDtoMapper();
    private final static CategoryDtoMapper categoryDtoMapper = new CategoryDtoMapper();

    @Override
    public OperationDto apply(Operation operation) {
        OperationDto result = new OperationDto();
        AccountDto account = accountDtoMapper.apply(operation.getAccount());
        Optional<CategoryDto> category = operation.getCategory().map(categoryDtoMapper);
        Long userId = operation.getUser().getId();
        if (category.isPresent()) {
            CategoryDto categoryDto = category.get();
            result.setCategoryId(categoryDto.getId());
            result.setCategory(categoryDto);
        } else {
            result.setCategoryId(null);
            result.setCategory(null);
        }
        result.setAccount(account);
        result.setAmount(operation.getAmount());
        result.setCurrency(operation.getCurrency());
        result.setDescription(operation.getDescription());
        result.setId(operation.getId());
        result.setDate(operation.getOperationDate());
        result.setOperationType(operation.getType());
        result.setUserId(userId);
        return result;
    }
}
