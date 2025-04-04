package dev.mednikov.expensetracking.operations.services;

import dev.mednikov.expensetracking.operations.dto.OperationDto;

import java.util.List;
import java.util.Optional;

public interface OperationService {

    OperationDto createOperation (OperationDto request);

    OperationDto updateOperation (OperationDto request);

    void deleteOperation (Long id);

    Optional<OperationDto> findOperationById (Long id);

    List<OperationDto> findAllOperationsForUser (Long userId);

}
