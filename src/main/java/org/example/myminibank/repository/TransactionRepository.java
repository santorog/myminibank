package org.example.myminibank.repository;

import org.example.myminibank.model.Transaction;
import org.example.myminibank.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    // Retrieves all transactions where the account was involved (either sender or receiver)
    Set<Transaction> findByAccountIdOrToAccountId(Long accountId, Long toAccountId);

    // Optionally retrieves a transaction having the listed properties - used to avoid duplication
    Optional<Transaction> findByAccountIdAndAmountAndTypeAndTimestamp(Long accountId, Double amount, TransactionType type, LocalDateTime timestamp);
}
