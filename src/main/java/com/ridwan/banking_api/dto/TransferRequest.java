package com.ridwan.banking_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class TransferRequest {

    @NotBlank(message = "Nomor rekening asal wajib diisi")
    private String sourceAccountNumber;

    @NotBlank(message = "Nomor rekening tujuan wajib diisi")
    private String destinationAccountNumber;

    @NotNull(message = "Jumlah transfer wajib diisi")
    @Positive(message = "Jumlah transfer harus lebih besar dari nol")
    private BigDecimal amount;
}