package com.ridwan.banking_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AccountResponse {
    private Long id;
    private String accountNumber;
    private String accountHolderName;
    private BigDecimal balance;
    private LocalDateTime createdAt;

    public AccountResponse() {
    }

    public AccountResponse(Long id, String accountNumber, String accountHolderName, BigDecimal balance,
            LocalDateTime createdAt) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.accountHolderName = accountHolderName;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    // GETTERS
    public Long getId() {
        return id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // SETTERS
    public void setId(Long id) {
        this.id = id;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // BUILDER
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String accountNumber;
        private String accountHolderName;
        private BigDecimal balance;
        private LocalDateTime createdAt;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder accountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
            return this;
        }

        public Builder accountHolderName(String accountHolderName) {
            this.accountHolderName = accountHolderName;
            return this;
        }

        public Builder balance(BigDecimal balance) {
            this.balance = balance;
            return this;
        }

        public Builder createdAt(LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public AccountResponse build() {
            return new AccountResponse(id, accountNumber, accountHolderName, balance, createdAt);
        }
    }
}