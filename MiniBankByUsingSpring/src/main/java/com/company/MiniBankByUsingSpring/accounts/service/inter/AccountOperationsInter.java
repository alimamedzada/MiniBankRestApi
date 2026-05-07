package com.company.MiniBankByUsingSpring.accounts.service.inter;

import com.company.MiniBankByUsingSpring.accounts.entity.Accounts;
import java.math.BigDecimal;

public interface AccountOperationsInter {

    void deposit(Accounts acc, BigDecimal amount);

    void withdraw(Accounts acc, BigDecimal amount);

}
