package com.company.MiniBankWebAppByUsingRest.dto;

import java.math.BigDecimal;

public class CreateAccountDTO {

    private String accountType;
    private BigDecimal balance;

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

}
