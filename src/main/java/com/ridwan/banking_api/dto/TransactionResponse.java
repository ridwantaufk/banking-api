package com.ridwan.banking_api.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private Long id;
    private String sourceAccountNumber;
    private String sourceAccountHolder;
    private String destinationAccountNumber;
    private String destinationAccountHolder;
    private BigDecimal amount;
    private LocalDateTime transferDate;
}