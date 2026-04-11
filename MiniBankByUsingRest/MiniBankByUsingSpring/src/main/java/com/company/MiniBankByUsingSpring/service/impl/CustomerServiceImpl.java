package com.company.MiniBankByUsingSpring.service.impl;

import com.company.MiniBankByUsingSpring.entity.Accounts;
import com.company.MiniBankByUsingSpring.entity.Customers;
import com.company.MiniBankByUsingSpring.repository.CustomerRepository;
import com.company.MiniBankByUsingSpring.service.inter.AccountServiceInter;
import com.company.MiniBankByUsingSpring.service.inter.CustomerServiceInter;
import com.company.MiniBankByUsingSpring.util.IdentifierUtil;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerServiceInter {

    @Autowired
    private CustomerRepository cRepository;

    @Autowired
    private AccountServiceInter aService;

    @Override
    public boolean addCustomer(Customers customer) {

        if (customer.getCustomerId() == null || customer.getCustomerId().isEmpty()) {
            String generatedId = IdentifierUtil.generateId(10);
            customer.setCustomerId(generatedId);
        }
        return addCustomer(customer, "CHECKING");
    }

    @Override
    public boolean addCustomer(Customers customer, String accountType) {

        if (customer.getCustomerId() == null || customer.getCustomerId().isEmpty()) {
            String generatedId = IdentifierUtil.generateId(10);
            customer.setCustomerId(generatedId);
        }
        Accounts acc = aService.createAccount(accountType, BigDecimal.ZERO);

        customer.addAccount(acc);

        cRepository.save(customer);
        return true;
    }

    @Override
    public boolean updateCustomer(Customers customer) {
        cRepository.save(customer);
        return true;
    }

    @Override
    public boolean deleteCustomerById(String id) {
        cRepository.deleteById(id);
        return true;
    }

    @Override
    public Customers getCustomerById(String id) {
        return cRepository.findById(id).orElse(null);
    }

    @Override
    public Customers findCustomerByUsername(String username) {
        return cRepository.findCustomerByUsername(username);
    }

    @Override
    public List<Customers> getAllCustomers() {
        return cRepository.findAll();
    }

    @Override
    public BigDecimal getTotalBalance(String customerId) {
        Customers c = getCustomerById(customerId);
        BigDecimal totalBalance = BigDecimal.ZERO;
        if (c != null) {
            List<Accounts> accounts = c.getAccounts();
            for (Accounts acc : accounts) {
                totalBalance = totalBalance.add(acc.getBalance());
            }
        }
        return totalBalance;
    }
}
