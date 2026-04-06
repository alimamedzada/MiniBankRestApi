package com.company.MiniBankByUsingSpring.service.inter;

import com.company.MiniBankByUsingSpring.entity.Customers;
import java.math.BigDecimal;
import java.util.List;

public interface CustomerServiceInter {

    boolean addCustomer(Customers customer);

    Customers getCustomerById(String id);

    Customers findCustomerByUsername(String username);

    List<Customers> getAllCustomers();

    boolean updateCustomer(Customers customer);

    boolean deleteCustomerById(String id);

    BigDecimal getTotalBalance(String customerId);
}
