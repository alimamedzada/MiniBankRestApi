package com.company.MiniBankByUsingSpring.customers.service.inter;

import com.company.MiniBankByUsingSpring.customers.entity.Customers;
import java.math.BigDecimal;
import java.util.List;

public interface CustomerServiceInter {

    boolean addCustomer(Customers customer);

    boolean addCustomer(Customers customer, String accountType);

    Customers getCustomerById(String id);

    Customers findCustomerByUsername(String username);

    List<Customers> getAllCustomers();

    boolean updateCustomer(Customers customer);

    boolean deleteCustomerById(String id);

    BigDecimal getTotalBalance(String customerId);
}
