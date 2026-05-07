package com.company.MiniBankByUsingSpring.customers.repository;

import com.company.MiniBankByUsingSpring.customers.entity.Customers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customers, String> {

    @Query("SELECT c from Customers c where c.username = :username")
    Customers findCustomerByUsername(@Param("username") String username);

}
