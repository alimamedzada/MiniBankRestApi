package com.company.MiniBankByUsingSpring.repository;

import com.company.MiniBankByUsingSpring.entity.Accounts;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Accounts, String> {

    @Query("SELECT a from Accounts a where a.id= :accountId and a.customer.customerId = :customerId")
    Optional<Accounts> findByIdAndCustomerId(@Param("accountId") String accountId, @Param("customerId") String customerId);

    @Query("SELECT a from Accounts a where a.id= :accountId")
    Accounts findAccountById(@Param("accountId") String id);

    @Query("SELECT a from Accounts a where a.customer.customerId = :customerId")
    List<Accounts> findAllByCustomerId(@Param("customerId") String customerId);
}
