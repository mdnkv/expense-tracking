package dev.mednikov.expensetracking.operations.repositories;

import dev.mednikov.expensetracking.operations.models.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Query("""
    SELECT op FROM Operation op where op.user.id=:userId ORDER BY op.operationDate DESC
    """)
    List<Operation> findAllByUserId (Long userId);

    @Query("""
    select o from Operation o where o.user.id = :userId and o.operationDate > :startDate and
    o.operationDate <= :endDate
    """)
    List<Operation> findAllInPeriod (Long userId, LocalDate startDate, LocalDate endDate);

}
