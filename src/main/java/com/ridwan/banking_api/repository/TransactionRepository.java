package com.ridwan.banking_api.repository;

import com.ridwan.banking_api.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    // Fungsi untuk mengambil riwayat transaksi berdasarkan ID akun
    List<Transaction> findBySourceAccountIdOrDestinationAccountId(Long sourceId, Long destId);
}