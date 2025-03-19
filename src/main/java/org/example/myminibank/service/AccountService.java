package org.example.myminibank.service;

import org.example.myminibank.model.Account;
import org.example.myminibank.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // Create a new account
    public Account createAccount(Account account) {
        if (account.getBalance() != 0.0) {
            throw new IllegalArgumentException("Balance must not be provided in the request for account creation");
        }
        return accountRepository.save(account);
    }

    // Get account details by ID
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Account with ID: " + id + " could not be found"));
    }

    // Deposit money
    @Transactional
    public Account deposit(Long id, double amount) {
        Account account = getAccountById(id);
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid deposit amount: " + amount + ". Deposit must be greater than zero.");
        }
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    // Withdraw money
    @Transactional
    public Account withdraw(Long id, double amount) {
        Account account = getAccountById(id);
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
        }
        if (account.getBalance() < amount) {
            throw new IllegalStateException("Insufficient balance. Attempted to withdraw: " + amount + " but available balance is: " + account.getBalance());
        }
        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }

    // Transfer  money
    @Transactional
    public List<Account> transfer(Long fromId, Long toId, double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be greater than zero.");
        }
        if (Objects.equals(fromId, toId)) {
            throw new IllegalArgumentException("Cannot transfer money from an account to itself");
        }
        Account fromAccount = getAccountById(fromId);
        if (fromAccount.getBalance() < amount) {
            throw new IllegalStateException(
                    "Insufficient balance: Account " + fromId + " has " + fromAccount.getBalance() +
                            " but attempted to transfer " + amount + " to Account " + toId
            );

        }

        Account toAccount = getAccountById(toId);

        // Update balance on both accounts and save the changes
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        return accountRepository.saveAll(List.of(fromAccount, toAccount));
    }
}
