package com.ridwan.banking_api.service.impl;

import com.ridwan.banking_api.dto.AccountRequest;
import com.ridwan.banking_api.dto.TransactionResponse;
import com.ridwan.banking_api.dto.TransferRequest;
import com.ridwan.banking_api.entity.Account;
import com.ridwan.banking_api.entity.Transaction;
import com.ridwan.banking_api.entity.User;
import com.ridwan.banking_api.repository.TransactionRepository;
import com.ridwan.banking_api.repository.AccountRepository;
import com.ridwan.banking_api.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    @Override
    @Transactional
    public Account createAccount(AccountRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        String generatedAccountNumber = UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 10);

        Account account = new Account();
        account.setAccountNumber(generatedAccountNumber);
        account.setAccountHolderName(request.getAccountHolderName());
        account.setBalance(request.getBalance());
        account.setCustomer(user.getCustomer());

        return accountRepository.save(account);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Account getAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Rekening tidak ditemukan"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(g -> g.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !account.getCustomer().getId().equals(user.getCustomer().getId())) {
            throw new RuntimeException("Anda tidak memiliki akses ke rekening ini");
        }

        return account;
    }

    @Override
    @Transactional
    public Account deposit(String accountNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Jumlah setoran harus lebih besar dari nol");
        }

        Account account = getAccount(accountNumber);
        account.setBalance(account.getBalance().add(amount));

        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public Account withdraw(String accountNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Jumlah penarikan harus lebih besar dari nol");
        }

        Account account = getAccount(accountNumber);

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Saldo tidak mencukupi");
        }

        account.setBalance(account.getBalance().subtract(amount));
        return accountRepository.save(account);
    }

    @Override
    @Transactional
    public void transfer(TransferRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Account sourceAcc = accountRepository.findByAccountNumberWithLock(request.getSourceAccountNumber())
                .orElseThrow(() -> new RuntimeException("Rekening asal tidak ditemukan"));

        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(g -> g.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !sourceAcc.getCustomer().getId().equals(user.getCustomer().getId())) {
            throw new RuntimeException("Anda tidak memiliki akses ke rekening ini");
        }

        Account destAcc = accountRepository.findByAccountNumberWithLock(request.getDestinationAccountNumber())
                .orElseThrow(() -> new RuntimeException("Rekening tujuan tidak ditemukan"));

        if (sourceAcc.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Saldo rekening asal tidak mencukupi");
        }

        sourceAcc.setBalance(sourceAcc.getBalance().subtract(request.getAmount()));
        destAcc.setBalance(destAcc.getBalance().add(request.getAmount()));

        accountRepository.save(sourceAcc);
        accountRepository.save(destAcc);

        Transaction transaction = new Transaction();
        transaction.setSourceAccount(sourceAcc);
        transaction.setDestinationAccount(destAcc);
        transaction.setAmount(request.getAmount());

        transactionRepository.save(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TransactionResponse> getTransactionHistory(String accountNumber) {
        Account account = getAccount(accountNumber);

        List<Transaction> transactions = transactionRepository
                .findBySourceAccountIdOrDestinationAccountId(account.getId(), account.getId());

        return transactions.stream().map(tx -> TransactionResponse.builder()
                .id(tx.getId())
                .sourceAccountNumber(tx.getSourceAccount().getAccountNumber())
                .sourceAccountHolder(tx.getSourceAccount().getAccountHolderName())
                .destinationAccountNumber(tx.getDestinationAccount().getAccountNumber())
                .destinationAccountHolder(tx.getDestinationAccount().getAccountHolderName())
                .amount(tx.getAmount())
                .transferDate(tx.getTransferDate())
                .build()).collect(Collectors.toList());
    }
}