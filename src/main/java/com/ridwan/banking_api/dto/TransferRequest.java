package com.ridwan.banking_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public class TransferRequest {
    @NotBlank(message = "Nomor rekening asal wajib diisi")
    private String sourceAccountNumber;

    @NotBlank(message = "Nomor rekening tujuan wajib diisi")
    private String destinationAccountNumber;

    @NotNull(message = "Jumlah transfer wajib diisi")
    @Positive(message = "Jumlah transfer harus lebih besar dari nol")
    private BigDecimal amount;

    // GETTERS
    public String getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public String getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    // SETTERS
    public void setSourceAccountNumber(String sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    public void setDestinationAccountNumber(String destinationAccountNumber) {
        this.destinationAccountNumber = destinationAccountNumber;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}