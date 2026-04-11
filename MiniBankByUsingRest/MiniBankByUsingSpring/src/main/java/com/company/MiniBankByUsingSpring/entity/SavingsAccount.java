package com.company.MiniBankByUsingSpring.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("SAVINGS")
public class SavingsAccount extends Accounts {

    public SavingsAccount() {
        this(BigDecimal.ZERO);
    }

    public SavingsAccount(BigDecimal balance) {
        super(balance);
    }
}
