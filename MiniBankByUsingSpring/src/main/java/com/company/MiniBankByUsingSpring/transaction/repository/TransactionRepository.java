package com.company.MiniBankByUsingSpring.transaction.repository;

import com.company.MiniBankByUsingSpring.transaction.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query("SELECT t from Transaction t where t.toIban= :toIban ORDER BY t.transactionDate DESC")
    List<Transaction> findByToIban(@Param("toIban") String iban);

    @Query("SELECT t FROM Transaction t WHERE t.fromAccount.customer.customerId= :customerId OR t.toIban IN (SELECT a.id FROM Accounts a WHERE a.customer.customerId = :customerId )ORDER BY t.transactionDate DESC")
    List<Transaction> findAllByCustomerId(@Param("customerId") String customerId);

    @Query("SELECT t from Transaction t where t.fromAccount.id IN :accIds OR t.toIban IN :accIds ORDER BY t.transactionDate DESC")
    List<Transaction> findCustomerTransactionHistory(@Param("accIds") List<String> accountIds, Pageable pageable);
}
