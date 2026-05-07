package com.company.MiniBankByUsingSpring.accounts.repository;

import com.company.MiniBankByUsingSpring.accounts.entity.Accounts;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Accounts, String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a from Accounts a where a.customer.customerId = :customerId")
    List<Accounts> findAllByCustomerId(@Param("customerId") String customerId);
}
