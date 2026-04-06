package com.company.MiniBankWebAppByUsingRest.dto;

import java.math.BigDecimal;

public class QuickTransferDTO {

    private String customerId;
    private String toIban;
    private BigDecimal amount;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getToIban() {
        return toIban;
    }

    public void setToIban(String toIban) {
        this.toIban = toIban;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
