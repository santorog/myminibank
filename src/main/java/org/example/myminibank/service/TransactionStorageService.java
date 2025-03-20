package org.example.myminibank.service;

import org.example.myminibank.model.Transaction;
import org.example.myminibank.model.TransactionEvent;
import org.example.myminibank.repository.TransactionRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TransactionStorageService {

    private final TransactionRepository transactionRepository;

    public TransactionStorageService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @KafkaListener(topics = "transaction-events", groupId = "transaction-storing-group")
    @Retryable(maxAttempts = 3)  // ✅ Retries failed transactions up to 3 times
    @Transactional
    public void consumeTransactionEvent(TransactionEvent event) {
        Transaction transaction = event.getTransaction();
        System.out.println("🔹 Received Transaction Event: " + transaction.getType() +
                " | Amount: " + transaction.getAmount() +
                " | From: " + transaction.getFromAccountId() +
                " | To: " + transaction.getToAccountId() +
                " | Reference: " + transaction.getReference());

        try {
            if (!isDuplicateTransaction(transaction)) { // ✅ Prevent duplicate transactions
                transactionRepository.save(transaction);
                System.out.println("✅ Transaction saved for Account ID: " + transaction.getAccountId());
            } else {
                System.out.println("⚠️ Duplicate transaction detected. Skipping...");
            }

        } catch (Exception e) {
            System.err.println("❌ Error saving transaction: " + e.getMessage());
        }
    }

    private boolean isDuplicateTransaction(Transaction transaction) {
        Optional<Transaction> existingTransaction = transactionRepository
                .findByAccountIdAndAmountAndTypeAndTimestamp(
                        transaction.getAccountId(),
                        transaction.getAmount(),
                        transaction.getType(),
                        transaction.getTimestamp());
        return existingTransaction.isPresent();
    }
}
