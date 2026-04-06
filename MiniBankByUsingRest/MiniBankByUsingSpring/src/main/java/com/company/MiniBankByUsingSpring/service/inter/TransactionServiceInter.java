package com.company.MiniBankByUsingSpring.service.inter;

import com.company.MiniBankByUsingSpring.entity.Transaction;
import java.math.BigDecimal;
import java.util.List;

public interface TransactionServiceInter {

    List<Transaction> getAllTransactions();

    Transaction createTransaction(String fromAccountId, String toIban, BigDecimal amount, String description);

    List<Transaction> getTransactionsByAccountId(String accountId);

    boolean addTransaction(Transaction t);

    boolean deleteTransactionById(String tId);

    boolean updateTransaction(Transaction t);

    public List<Transaction> getTransactionsByCustomerId( String customerId);

    List<Transaction> getRecentTransactionsByCustomerId(String customerId, int limit);

}
