package com.ridwan.banking_api.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relasi ke akun asal (source)
    @ManyToOne
    @JoinColumn(name = "source_account_id", nullable = false)
    private Account sourceAccount;

    // Relasi ke akun tujuan (destination)
    @ManyToOne
    @JoinColumn(name = "destination_account_id", nullable = false)
    private Account destinationAccount;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "transfer_date", nullable = false)
    private LocalDateTime transferDate;

    @PrePersist
    protected void onCreate() {
        this.transferDate = LocalDateTime.now();
    }
}