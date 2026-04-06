package com.company.MiniBankByUsingSpring.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("Checking")
public class CheckingAccount extends Accounts {

    public CheckingAccount() {
    }

    public CheckingAccount(BigDecimal balance) {
        super(balance);
    }

}
