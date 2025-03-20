package org.example.myminibank.service;

import org.example.myminibank.model.Transaction;
import org.example.myminibank.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // Get transactions that have happened on a particular account
    public Set<Transaction> getTransactionsByAccountId(Long id) {
        return transactionRepository.findByAccountIdOrToAccountId(id, id);
    }

}
