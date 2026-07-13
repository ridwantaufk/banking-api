package com.ridwan.banking_api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class AccountRequest {

    @NotBlank(message = "Nama pemilik rekening tidak boleh kosong")
    private String accountHolderName;

    @NotNull(message = "Saldo awal tidak boleh kosong")
    @PositiveOrZero(message = "Saldo awal tidak boleh negatif")
    private BigDecimal balance;
}