package org.example.myminibank.service;

import org.example.myminibank.dto.AccountApiResponse;
import org.example.myminibank.model.Account;
import org.example.myminibank.model.Transaction;
import org.example.myminibank.model.TransactionEvent;
import org.example.myminibank.model.TransactionType;
import org.example.myminibank.repository.AccountRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    public AccountService(AccountRepository accountRepository, KafkaTemplate<String, TransactionEvent> kafkaTemplate) {
        this.accountRepository = accountRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    // Create a new account
    public AccountApiResponse createAccount(Account account) {
        if (account.getBalance() != 0.0) {
            return AccountApiResponse.of("Balance must not be provided in the request for account creation");
        }
        accountRepository.save(account);

        return AccountApiResponse.of("Account successfully created for: {} with id: {}", account.getName(), account.getId());
    }

    // Get account details by ID
    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Account with ID: " + id + " could not be found"));
    }

    // Check the money on the account
    public AccountApiResponse balance(Long id) {
        Account account = getAccountById(id);
        return AccountApiResponse.of("Balance on Account {}: {}", account.getId(), account.getBalance());
    }

    // Deposit money
    @Transactional
    public AccountApiResponse deposit(Long id, double amount) {
        Account account = getAccountById(id);
        if (amount <= 0) {
            return AccountApiResponse.of("Deposit amount must be greater than zero but was {}", amount);
        }
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        Transaction transaction = new Transaction(TransactionType.DEPOSIT, id, amount);
        kafkaTemplate.send("transaction-events", new TransactionEvent(transaction));

        return AccountApiResponse.of("Successfully deposited {} to account {}", amount, id);
    }

    // Withdraw money
    @Transactional
    public AccountApiResponse withdraw(Long id, double amount) {
        Account account = getAccountById(id);
        if (amount <= 0) {
            return AccountApiResponse.of("Withdrawal amount must be greater than zero but was {}", amount);
        }
        if (account.getBalance() < amount) {
            return AccountApiResponse.of("Insufficient balance. Attempted to withdraw: {} but available balance is: {}", amount, account.getBalance());
        }
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        Transaction transaction = new Transaction(TransactionType.WITHDRAWAL, id, amount);
        kafkaTemplate.send("transaction-events", new TransactionEvent(transaction));

        return AccountApiResponse.of("Successfully withdrew {} from account {}", amount, id);
    }

    // Transfer  money
    @Transactional
    public AccountApiResponse transfer(Long fromId, Long toId, double amount, String reference) {
        if (amount <= 0) {
            return AccountApiResponse.of("Transfer amount must be greater than zero but was {}", amount);
        }
        if (Objects.equals(fromId, toId)) {
            return AccountApiResponse.of("Cannot transfer money from an account to itself");
        }

        Account fromAccount = getAccountById(fromId);
        Account toAccount = getAccountById(toId);

        if (fromAccount.getBalance() < amount) {
            return AccountApiResponse.of("Insufficient balance: Account {} has {} but attempted to transfer {} to Account {}", fromId, fromAccount.getBalance(), amount, toId);
        }

        // Update balance on both accounts and save the changes
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction senderTransaction =
                new Transaction(TransactionType.TRANSFER, fromId, toId, fromId, -amount, reference);
        Transaction receiverTransaction =
                new Transaction(TransactionType.TRANSFER, fromId, toId, toId, amount, reference);

        kafkaTemplate.send("transaction-events", new TransactionEvent(senderTransaction));
        kafkaTemplate.send("transaction-events", new TransactionEvent(receiverTransaction));

        return AccountApiResponse.of("Successfully transferred {} from Account {} to Account {}", amount, fromId, toId);
    }
}
