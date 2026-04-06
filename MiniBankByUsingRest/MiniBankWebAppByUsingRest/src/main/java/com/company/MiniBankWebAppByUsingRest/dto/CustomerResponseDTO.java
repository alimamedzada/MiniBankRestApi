package com.company.MiniBankWebAppByUsingRest.dto;

import java.math.BigDecimal;
import java.util.List;

public class CustomerResponseDTO {

    private List<TransactionDTO> transactions;
    private CustomersDTO customer;
    private BigDecimal totalBalance;

    public List<TransactionDTO> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionDTO> transactions) {
        this.transactions = transactions;
    }

    public CustomersDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomersDTO c) {
        this.customer = c;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }
    
    
}
