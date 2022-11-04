package com.example.springdataintro.services;

import com.example.springdataintro.models.Account;
import com.example.springdataintro.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void withdrawMoney(BigDecimal money, Long id) {
        Optional<Account> byId = accountRepository.findAccountById(id);

        if (byId.isEmpty()) {
            throw new RuntimeException("The given account is not present!");
        }

        BigDecimal oldBalance = byId.get().getBalance();
        if (oldBalance.compareTo(money) < 0) {
            throw new RuntimeException("Not enough balance!");
        }

        BigDecimal newBalance = oldBalance.subtract(money);
        byId.get().setBalance(newBalance);

        this.accountRepository.save(byId.get());
    }

    @Override
    public void transferMoney(BigDecimal money, Long id) {
        if (money.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Transfer fee cannot be negative number!");
        }

        Optional<Account> byId = accountRepository.findAccountById(id);

        if (byId.isEmpty()) {
            throw new RuntimeException("The given account is not present!");
        }

        BigDecimal oldBalance = byId.get().getBalance();
        BigDecimal newBalance = oldBalance.add(money);

        byId.get().setBalance(newBalance);

        this.accountRepository.save(byId.get());
    }
}
