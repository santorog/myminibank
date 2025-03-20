package org.example.myminibank.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotNull;
import org.example.myminibank.validator.MultipleOf;

import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TransactionType type; // Deposit, Withdraw, Transfer

    @NotNull(message = "Account ID cannot be null")
    private Long accountId; // Always populated (who made the transaction)

    private Long fromAccountId; // NULL for deposits & withdrawals
    private Long toAccountId; // NULL for deposits & withdrawals

    @NotNull(message = "Transaction amount cannot be null")
    @MultipleOf(0.01)
    private Double amount;

    @NotNull(message = "Transaction timestamp cannot be null")
    private LocalDateTime timestamp;

    private String reference; // NULL for deposits & withdrawals

    @PrePersist
    protected void onCreate() {
        this.timestamp = LocalDateTime.now();
    }


    public Transaction() {
    } // Default constructor for JPA

    // Constructor for deposits & withdrawals
    public Transaction(TransactionType type, Long accountId, Double amount) {
        this.type = type;
        this.accountId = accountId;
        this.fromAccountId = null;
        this.toAccountId = null;
        this.amount = amount;
        validateTransaction();
    }

    // Constructor for transfers
    public Transaction(TransactionType type, Long fromAccountId, Long toAccountId, Long accountId, Double amount, String reference) {
        this.type = type;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.accountId = accountId;
        this.amount = amount;
        this.reference = reference;
        validateTransaction();
    }

    // ✅ Custom Validation Method
    private void validateTransaction() {
        if (TransactionType.TRANSFER.equals(this.type)) {
            if (this.fromAccountId == null || this.toAccountId == null) {
                throw new IllegalArgumentException("Transfer transactions must have both fromAccountId and toAccountId.");
            }
            if (this.fromAccountId.equals(this.toAccountId)) {
                throw new IllegalArgumentException("Cannot transfer money to the same account.");
            }
        }
    }

    // Getters
    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getReference() {
        return reference;
    }
}
