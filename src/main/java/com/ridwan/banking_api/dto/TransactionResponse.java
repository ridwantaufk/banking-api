package com.ridwan.banking_api.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionResponse {
    private Long id;
    private String sourceAccountNumber;
    private String sourceAccountHolder;
    private String destinationAccountNumber;
    private String destinationAccountHolder;
    private BigDecimal amount;
    private LocalDateTime transferDate;

    public TransactionResponse() {
    }

    private TransactionResponse(Builder builder) {
        this.id = builder.id;
        this.sourceAccountNumber = builder.sourceAccountNumber;
        this.sourceAccountHolder = builder.sourceAccountHolder;
        this.destinationAccountNumber = builder.destinationAccountNumber;
        this.destinationAccountHolder = builder.destinationAccountHolder;
        this.amount = builder.amount;
        this.transferDate = builder.transferDate;
    }

    // GETTERS
    public Long getId() {
        return id;
    }

    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public String getSourceAccountHolder() {
        return sourceAccountHolder;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public String getDestinationAccountHolder() {
        return destinationAccountHolder;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTransferDate() {
        return transferDate;
    }

    // SETTERS
    public void setId(Long id) {
        this.id = id;
    }

    public void setSourceAccountNumber(String sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    public void setSourceAccountHolder(String sourceAccountHolder) {
        this.sourceAccountHolder = sourceAccountHolder;
    }

    public void setDestinationAccountNumber(String destinationAccountNumber) {
        this.destinationAccountNumber = destinationAccountNumber;
    }

    public void setDestinationAccountHolder(String destinationAccountHolder) {
        this.destinationAccountHolder = destinationAccountHolder;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setTransferDate(LocalDateTime transferDate) {
        this.transferDate = transferDate;
    }

    // BUILDER
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Long id;
        private String sourceAccountNumber;
        private String sourceAccountHolder;
        private String destinationAccountNumber;
        private String destinationAccountHolder;
        private BigDecimal amount;
        private LocalDateTime transferDate;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder sourceAccountNumber(String sourceAccountNumber) {
            this.sourceAccountNumber = sourceAccountNumber;
            return this;
        }

        public Builder sourceAccountHolder(String sourceAccountHolder) {
            this.sourceAccountHolder = sourceAccountHolder;
            return this;
        }

        public Builder destinationAccountNumber(String destinationAccountNumber) {
            this.destinationAccountNumber = destinationAccountNumber;
            return this;
        }

        public Builder destinationAccountHolder(String destinationAccountHolder) {
            this.destinationAccountHolder = destinationAccountHolder;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder transferDate(LocalDateTime transferDate) {
            this.transferDate = transferDate;
            return this;
        }

        public TransactionResponse build() {
            return new TransactionResponse(this);
        }
    }
}