package com.company.MiniBankWebAppByUsingRest.dto;

import java.util.List;

public class MoneyTransferResponseDTO {

    private List<TransactionDTO> TransactionList;
    private List<AccountsDTO> AccountsList;
    private CustomersDTO customer;

    public List<TransactionDTO> getTransactionList() {
        return TransactionList;
    }

    public void setTransactionList(List<TransactionDTO> TransactionList) {
        this.TransactionList = TransactionList;
    }

    public List<AccountsDTO> getAccountsList() {
        return AccountsList;
    }

    public void setAccountsList(List<AccountsDTO> AccountsList) {
        this.AccountsList = AccountsList;
    }

    public CustomersDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomersDTO customer) {
        this.customer = customer;
    }

}
