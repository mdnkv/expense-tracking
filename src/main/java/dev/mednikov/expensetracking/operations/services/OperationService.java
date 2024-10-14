package dev.mednikov.expensetracking.operations.services;

import dev.mednikov.expensetracking.operations.dto.OperationRequestDto;
import dev.mednikov.expensetracking.operations.dto.OperationResponseDto;

import java.util.List;
import java.util.Optional;

public interface OperationService {

    OperationResponseDto createOperation (OperationRequestDto request);

    OperationResponseDto updateOperation (OperationRequestDto request);

    void deleteOperation (Long id);

    Optional<OperationResponseDto> findOperationById (Long id);

    List<OperationResponseDto> findAllOperationsForUser (Long userId);

}
