package com.company.MiniBankByUsingSpring.accounts.entity;

import com.company.MiniBankByUsingSpring.customers.entity.Customers;
import com.company.MiniBankByUsingSpring.transaction.entity.Transaction;
import com.company.MiniBankByUsingSpring.util.IdentifierUtil;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "account_type", discriminatorType = DiscriminatorType.STRING)
public class Accounts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "account_id")
    private String id;
    @Basic(optional = false)
    @Column(name = "balance")
    private BigDecimal balance;
    @Basic(optional = false)
    @Column(name = "create_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    @ManyToOne
    private Customers customer;
    @OneToMany(mappedBy = "fromAccount", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public Accounts() {
        this.balance = BigDecimal.ZERO;
        this.id = IdentifierUtil.generateAzerbaijanIBANId();
        this.createDate = new Date(System.currentTimeMillis());
    }

    public Accounts(Customers customer) {
        this.customer = customer;
    }

    public Accounts(BigDecimal balance) {
        this.id = IdentifierUtil.generateAzerbaijanIBANId();
        this.balance = balance;
        this.createDate = new Date(System.currentTimeMillis());
    }

    public Accounts(String accountId, BigDecimal balance, Date createDate) {
        this.id = accountId;
        this.balance = balance;
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String accountId) {
        this.id = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accounts)) {
            return false;
        }
        Accounts other = (Accounts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public void showAllAccountsBalances() {
        System.out.println("Account ID: " + id + "\033[3m" + "\u001B[1m"
                + " | Balance: " + balance + " AZN" + "\u001B[0m" + "\033[0m");
    }

    public void showBalance() {
        System.out.println("Balance: " + balance);
    }

    @Override
    public String toString() {
        return "Accounts{" + "id=" + id + ", balance=" + balance + ", createDate=" + createDate + ", customer id= " + customer.getCustomerId() + '}';
    }

}
