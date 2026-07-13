package com.ridwan.banking_api.service;

import com.ridwan.banking_api.entity.Account;
import com.ridwan.banking_api.dto.AccountRequest;
import java.math.BigDecimal;

public interface AccountService {
    Account createAccount(AccountRequest request);

    Account getAccount(String accountNumber);

    Account deposit(String accountNumber, BigDecimal amount);

    Account withdraw(String accountNumber, BigDecimal amount);
}