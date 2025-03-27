package org.example.myminibank.events;

import org.example.myminibank.model.Transaction;

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