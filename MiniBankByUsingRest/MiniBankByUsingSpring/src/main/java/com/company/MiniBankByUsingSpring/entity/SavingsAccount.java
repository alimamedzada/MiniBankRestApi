package com.company.MiniBankByUsingSpring.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("Savings")
public class SavingsAccount extends Accounts {

    public SavingsAccount() {
        super();
    }

    public SavingsAccount(BigDecimal balance) {
        super(balance);
    }
}
