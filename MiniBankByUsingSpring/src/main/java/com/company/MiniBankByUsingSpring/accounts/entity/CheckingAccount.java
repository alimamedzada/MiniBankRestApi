package com.company.MiniBankByUsingSpring.accounts.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("CHECKING")
public class CheckingAccount extends Accounts {

    public CheckingAccount() {
        this(BigDecimal.ZERO);
    }

    public CheckingAccount(BigDecimal balance) {
        super(balance);
    }

}
