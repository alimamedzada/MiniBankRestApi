package com.company.MiniBankByUsingSpring.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id")
    private Accounts fromAccount;

    @Column(name = "to_iban", nullable = false)
    private String toIban;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;

    @Column(name = "status", nullable = false)
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Accounts getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Accounts fromAccount) {
        this.fromAccount = fromAccount;
    }

    public String getToIban() {
        return toIban;
    }

    public void setToIban(String toIban) {
        this.toIban = toIban;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @PrePersist
    protected void onCreate() {
        this.transactionDate = LocalDateTime.now();
        if (this.status == null) {
            this.status = "SUCCESS";
        }
    }
}
