package com.company.MiniBankByUsingSpring.service.inter;

import com.company.MiniBankByUsingSpring.entity.Customers;

public interface AuthServiceInter {

    Customers register(Customers customer);

    Customers login(String username, String password);
}
