package dev.mednikov.expensetracking.operations.dto;

import dev.mednikov.expensetracking.accounts.dto.AccountDto;
import dev.mednikov.expensetracking.accounts.dto.AccountDtoMapper;
import dev.mednikov.expensetracking.categories.dto.CategoryDto;
import dev.mednikov.expensetracking.categories.dto.CategoryDtoMapper;
import dev.mednikov.expensetracking.operations.models.Operation;

import java.util.function.Function;

public final class OperationResponseDtoMapper implements Function<Operation, OperationResponseDto> {

    private final static AccountDtoMapper accountDtoMapper = new AccountDtoMapper();
    private final static CategoryDtoMapper categoryDtoMapper = new CategoryDtoMapper();

    @Override
    public OperationResponseDto apply(Operation operation) {
        AccountDto account = accountDtoMapper.apply(operation.getAccount());
        CategoryDto category = (operation.getCategory() != null) ? categoryDtoMapper.apply(operation.getCategory()) : null;
        return new OperationResponseDto(
                operation.getId(),
                account,
                category,
                operation.getDescription(),
                operation.getCurrency(),
                operation.getAmount(),
                operation.getType(),
                operation.getOperationDate()
        );
    }
}
