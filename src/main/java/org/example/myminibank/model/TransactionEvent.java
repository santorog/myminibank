package org.example.myminibank.model;

public class TransactionEvent {

    private Transaction transaction;

    public TransactionEvent(){}

    public TransactionEvent(Transaction transaction) {
        this.transaction = transaction;
    }

    public Transaction getTransaction() {
        return transaction;
    }
}