package dev.mednikov.expensetracking.operations.repositories;

import dev.mednikov.expensetracking.operations.models.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    List<Operation> findAllByUserId (Long userId);

}
