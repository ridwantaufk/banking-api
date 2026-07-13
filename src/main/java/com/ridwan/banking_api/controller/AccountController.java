package com.ridwan.banking_api.controller;

import com.ridwan.banking_api.dto.AccountRequest;
import com.ridwan.banking_api.dto.TransferRequest;
import com.ridwan.banking_api.entity.Account;
import com.ridwan.banking_api.service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    // 1. Endpoint Membuat Rekening Baru
    @PostMapping
    public ResponseEntity<Account> createAccount(@Valid @RequestBody AccountRequest request) {
        Account createdAccount = accountService.createAccount(request);
        return new ResponseEntity<>(createdAccount, HttpStatus.CREATED);
    }

    // 2. Endpoint Mengambil Data Rekening / Cek Saldo
    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccount(@PathVariable String accountNumber) {
        Account account = accountService.getAccount(accountNumber);
        return ResponseEntity.ok(account);
    }

    // 3. Endpoint Setor Tunai (Deposit)
    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<Account> deposit(
            @PathVariable String accountNumber,
            @RequestBody Map<String, BigDecimal> request) {

        BigDecimal amount = request.get("amount");
        Account updatedAccount = accountService.deposit(accountNumber, amount);
        return ResponseEntity.ok(updatedAccount);
    }

    // 4. Endpoint Tarik Tunai (Withdraw)
    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<Account> withdraw(
            @PathVariable String accountNumber,
            @RequestBody Map<String, BigDecimal> request) {

        BigDecimal amount = request.get("amount");
        Account updatedAccount = accountService.withdraw(accountNumber, amount);
        return ResponseEntity.ok(updatedAccount);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@Valid @RequestBody TransferRequest request) {
        accountService.transfer(request);
        return ResponseEntity.ok("Transfer berhasil dilakukan");
    }
}