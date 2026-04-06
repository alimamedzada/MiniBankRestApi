package com.company.MiniBankWebAppByUsingRest.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AccountsDTO {

    private String id;
    private BigDecimal balance;
    private Date createDate;
    private String customerId;
    private List<TransactionDTO> transactions = new ArrayList<>();
    private String accountType;
    private String customerUsername;

    public AccountsDTO() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

//    public AccountsDTO(Accounts account) {
//        this.id = account.getId();
//        this.balance = account.getBalance();
//        this.createDate = account.getCreateDate();
//        if (account.getCustomer() != null) {
//            this.customerId = account.getCustomer().getCustomerId();
//        }
//        List<Transaction> t = account.getTransactions();
//        for (Transaction tr : t) {
//            transactions.add(new TransactionDTO(tr));
//        }
//    }
//    public Accounts toEntity(CustomerServiceInter customerService) {
//        Accounts acc = new Accounts();
//        acc.setId(this.id);
//        acc.setBalance(this.balance);
//        acc.setCreateDate(this.createDate);
//        if (this.customerId != null) {
//            Customers tempCustomer = customerService.getCustomerById(customerId);
//            tempCustomer.setCustomerId(this.customerId);
//            acc.setCustomer(tempCustomer);
//        }
//        List<Transaction> tNew = new ArrayList<>();
//
//        for (TransactionDTO tt : transactions) {

////            tNew.add(tt.toEntity());
//        }
//        acc.setTransactions(tNew);
//        return acc;
//    }
}
