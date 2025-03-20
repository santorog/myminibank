package org.example.myminibank.controller;

import org.example.myminibank.model.Transaction;
import org.example.myminibank.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public Set<Transaction> transactions(@PathVariable Long id) {
        return transactionService.getTransactionsByAccountId(id);
    }

}
