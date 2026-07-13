package com.ridwan.banking_api.repository;

import com.ridwan.banking_api.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    // Spring Data JPA otomatis membuatkan query "SELECT * FROM accounts WHERE
    // account_number = ?" di balik layar
    Optional<Account> findByAccountNumber(String accountNumber);
}