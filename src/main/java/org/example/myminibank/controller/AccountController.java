package org.example.myminibank.controller;

import org.example.myminibank.dto.AccountApiResponse;
import org.example.myminibank.model.Account;
import org.example.myminibank.service.AccountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public AccountApiResponse createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @GetMapping("/{id}/balance")
    public AccountApiResponse balance(@PathVariable Long id) {
        return accountService.balance(id);
    }

    @PostMapping("/{id}/deposit")
    public AccountApiResponse deposit(@PathVariable Long id, @RequestParam double amount) {
        return accountService.deposit(id, amount);
    }

    @PostMapping("/{id}/withdraw")
    public AccountApiResponse withdraw(@PathVariable Long id, @RequestParam double amount) {
        return accountService.withdraw(id, amount);
    }

    @PostMapping("/{id}/transfer")
    public AccountApiResponse transfer(@PathVariable Long id, @RequestParam Long to, @RequestParam double amount, @RequestParam String reference) {
        return accountService.transfer(id, to, amount, reference);
    }

}
