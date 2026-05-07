package com.company.MiniBankByUsingSpring.transaction.service.inter;

import com.company.MiniBankByUsingSpring.accounts.entity.Accounts;
import com.company.MiniBankByUsingSpring.transaction.entity.Transaction;
import java.math.BigDecimal;
import java.util.List;

public interface TransactionServiceInter {

    List<Transaction> getAllTransactions();

    Transaction createTransaction(Accounts fromAccount, String toIban, BigDecimal amount, String description);

    List<Transaction> getTransactionsByAccountId(String accountId);

    boolean addTransaction(Transaction t);

    boolean deleteTransactionById(String tId);

    boolean updateTransaction(Transaction t);

    List<Transaction> getTransactionsByCustomerId( String customerId);

    List<Transaction> getRecentTransactionsByCustomerId(String customerId, int limit);

}
