package com.company.MiniBankByUsingSpring.service.inter;

import com.company.MiniBankByUsingSpring.entity.Accounts;
import com.company.MiniBankByUsingSpring.entity.Customers;
import java.math.BigDecimal;
import java.util.List;

public interface AccountServiceInter {

    Accounts createAccount(String accountType, BigDecimal balance);

    boolean deleteAccount(String id);

    List<Accounts> showAccounts(Customers customer);

    boolean updateAccount(Accounts account);

    Accounts findAccountById(String id);

    boolean transfer(String fromAccId, String toIban, BigDecimal amount, String description);

    List<Accounts> findAllByCustomerId(String customerId);

    boolean qucikTransfer(String customerId, String toIban, BigDecimal amount);

}
//    Accounts getAccount(Customers customer);
//    void showAllAccountsBalance(Customers customer);
