package com.company.MiniBankByUsingSpring.service.inter;

import com.company.MiniBankByUsingSpring.entity.Accounts;
import com.company.MiniBankByUsingSpring.entity.Customers;
import java.math.BigDecimal;
import java.util.List;

public interface AccountServiceInter {

    void createAccountByUsername(String username, String accountType, BigDecimal balance);

    Accounts findAccount(Customers customer, String accNum);

    boolean deleteAccount(String id);

    List<Accounts> showAccounts(Customers customer);

    boolean updateAccount(Customers customer, String accId);

    Accounts findAccountById(String id);

    boolean transfer(String fromAccId, String toIban, BigDecimal amount, String description);

    List<Accounts> findAccountByCustomerId(String customerId);

    boolean qucikTransfer(String customerId, String toIban, BigDecimal amount);

}
//    Accounts getAccount(Customers customer);
//    void showAllAccountsBalance(Customers customer);
