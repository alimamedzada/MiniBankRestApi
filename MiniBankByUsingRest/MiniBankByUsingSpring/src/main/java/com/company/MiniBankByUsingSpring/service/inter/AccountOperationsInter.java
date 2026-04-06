package com.company.MiniBankByUsingSpring.service.inter;

import java.math.BigDecimal;

public interface AccountOperationsInter {

    void deposit(String accId,BigDecimal amount);

    void withdraw(String accId,BigDecimal amount);

}
