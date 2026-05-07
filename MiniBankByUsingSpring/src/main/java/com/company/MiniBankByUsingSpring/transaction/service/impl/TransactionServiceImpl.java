package com.company.MiniBankByUsingSpring.transaction.service.impl;

import com.company.MiniBankByUsingSpring.accounts.entity.Accounts;
import com.company.MiniBankByUsingSpring.transaction.entity.Transaction;
import com.company.MiniBankByUsingSpring.accounts.repository.AccountRepository;
import com.company.MiniBankByUsingSpring.transaction.repository.TransactionRepository;
import com.company.MiniBankByUsingSpring.transaction.service.inter.TransactionServiceInter;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionServiceInter {

    private final TransactionRepository tRepository;
    private final AccountRepository aRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository,AccountRepository aRepository) {
        this.tRepository = transactionRepository;
        this.aRepository = aRepository;
    }


    @Override
    public Transaction createTransaction(Accounts fromAccount, String toIban,
            BigDecimal amount, String description) {

        Transaction t = new Transaction();
        t.setFromAccount(fromAccount);
        t.setAmount(amount);
        t.setDescription(description);
        t.setToIban(toIban);

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
