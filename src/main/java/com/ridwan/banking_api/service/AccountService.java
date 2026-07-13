package com.ridwan.banking_api.service;

import com.ridwan.banking_api.entity.Account;
import com.ridwan.banking_api.dto.AccountRequest;
import com.ridwan.banking_api.dto.TransferRequest;
import com.ridwan.banking_api.dto.TransactionResponse;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService {
    Account createAccount(AccountRequest request);

    Account getAccount(String accountNumber);

    Account deposit(String accountNumber, BigDecimal amount);

    Account withdraw(String accountNumber, BigDecimal amount);

    void transfer(TransferRequest request);

    List<TransactionResponse> getTransactionHistory(String accountNumber);
}