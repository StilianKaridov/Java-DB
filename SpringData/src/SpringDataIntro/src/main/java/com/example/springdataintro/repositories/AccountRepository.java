package com.example.springdataintro.repositories;

import com.example.springdataintro.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findAccountById(Long id);

    List<Account> getAccountsByBalanceIsGreaterThan(BigDecimal balance);

    List<Account> getAccountsByBalanceIsLessThan(BigDecimal balance);
}
