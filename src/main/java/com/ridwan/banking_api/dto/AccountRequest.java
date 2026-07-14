package com.ridwan.banking_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class AccountRequest {

    @NotBlank(message = "Nama pemilik rekening tidak boleh kosong")
    private String accountHolderName;

    @NotNull(message = "Saldo awal tidak boleh kosong")
    @PositiveOrZero(message = "Saldo awal tidak boleh negatif")
    private BigDecimal balance;

    // GETTERS
    public String getAccountHolderName() {
        return accountHolderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    // SETTERS
    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}