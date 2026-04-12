package com.company.MiniBankByUsingSpring.service.impl;

import com.company.MiniBankByUsingSpring.entity.Accounts;
import com.company.MiniBankByUsingSpring.entity.Transaction;
import com.company.MiniBankByUsingSpring.repository.AccountRepository;
import com.company.MiniBankByUsingSpring.repository.TransactionRepository;
import com.company.MiniBankByUsingSpring.service.inter.TransactionServiceInter;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionServiceInter {

    @Autowired
    private TransactionRepository tRepository;
    @Autowired
    @Lazy
    private AccountRepository aRepository;

    @Override
    public Transaction createTransaction(String fromAccountId, String toIban,
            BigDecimal amount, String description) {
        Optional<Accounts> fromAcc = aRepository.findById(fromAccountId);
        Optional<Accounts> toAcc = aRepository.findById(toIban);

        Transaction t = new Transaction();
        t.setFromAccount(fromAcc.get());
        t.setAmount(amount);
        t.setDescription(description);
        t.setToIban(toAcc != null ? toAcc.get().getId() : toIban);

        return t;
    }

    @Override
    public List<Transaction> getTransactionsByAccountId(String accountId) {
        return tRepository.findByToIban(accountId);
    }

    @Override
    public boolean addTransaction(Transaction t) {
        tRepository.save(t);
        return true;
    }

    @Override
    public boolean deleteTransactionById(String tId) {
        tRepository.deleteById(tId);
        return true;
    }

    @Override
    public boolean updateTransaction(Transaction t) {
        tRepository.save(t);
        return true;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return tRepository.findAll();
    }

    @Override
    public List<Transaction> getTransactionsByCustomerId(String customerId) {
        return tRepository.findAllByCustomerId(customerId);
    }

    @Override
    public List<Transaction> getRecentTransactionsByCustomerId(
            String customerId, int limit) {
        List<Accounts> accounts = aRepository.findAllByCustomerId(customerId);
        List<String> accIds = new ArrayList<>();
        for (Accounts acc : accounts) {
            accIds.add(acc.getId());
        }
        return tRepository.findCustomerTransactionHistory(
                accIds, Pageable.ofSize(limit));
    }

}
