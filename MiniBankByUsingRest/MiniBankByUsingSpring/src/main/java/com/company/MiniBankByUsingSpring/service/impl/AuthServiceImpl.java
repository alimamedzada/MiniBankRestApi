package com.company.MiniBankByUsingSpring.service.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.company.MiniBankByUsingSpring.entity.Customers;
import com.company.MiniBankByUsingSpring.exception.AuthenticationException;
import com.company.MiniBankByUsingSpring.service.inter.AuthServiceInter;
import com.company.MiniBankByUsingSpring.service.inter.CustomerServiceInter;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthServiceImpl implements AuthServiceInter {

    @Autowired
    private CustomerServiceInter customerService;
    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public Customers register(Customers customer) {
        customerService.addCustomer(customer);
        return customer;
    }

    public static BCrypt.Result verify(String password, Customers customer) {
        return BCrypt.verifyer().verify(password.toCharArray(), customer.getPassword());
    }

    public Customers login(String username, String password) {
        Customers customer = customerService.findCustomerByUsername(username);
        if (customer == null) {
            logger.error("User not found: {}", username);
            throw new AuthenticationException("User not found or invalid password!");
        }

        BCrypt.Result result = verify(password, customer);

        if (result.verified) {
            logger.info("Successfully accessed");
            System.out.println(customer.getAccounts());

            return customer;
        } else {
            logger.error("wrong!");
            throw new AuthenticationException("User not found or invalid password: " + username);
        }
    }
}
