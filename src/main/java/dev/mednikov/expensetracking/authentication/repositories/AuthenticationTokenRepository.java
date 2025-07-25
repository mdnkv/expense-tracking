package dev.mednikov.expensetracking.authentication.repositories;

import dev.mednikov.expensetracking.authentication.models.AuthenticationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthenticationTokenRepository extends JpaRepository<AuthenticationToken, Long> {

    Optional<AuthenticationToken> findByToken(String token);

    List<AuthenticationToken> findAllByUserId (Long userId);

}
