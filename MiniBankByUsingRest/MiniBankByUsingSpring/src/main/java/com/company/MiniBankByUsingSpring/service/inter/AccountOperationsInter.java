package com.company.MiniBankByUsingSpring.service.inter;

import com.company.MiniBankByUsingSpring.entity.Accounts;
import java.math.BigDecimal;

public interface AccountOperationsInter {

    void deposit(Accounts acc, BigDecimal amount);

    void withdraw(Accounts acc, BigDecimal amount);

}
