package com.company.MiniBankWebAppByUsingRest.dto;

import java.math.BigDecimal;
import java.util.List;

public class AccountsResponseDTO {

    private List<AccountsDTO> accounts;
    private BigDecimal totalBalance;

    public List<AccountsDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountsDTO> accounts) {
        this.accounts = accounts;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

}
