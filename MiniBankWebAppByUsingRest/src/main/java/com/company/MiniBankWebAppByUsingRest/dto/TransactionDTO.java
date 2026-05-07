package com.company.MiniBankWebAppByUsingRest.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionDTO {

    private String id;
    private String fromAccountId;

    private String toIban;
    private BigDecimal amount;
    private String description;
    private LocalDateTime transactionDate;
    private String status;
    private String transactionType;
    private String recipientName;
    private String recipientSurname;

    public TransactionDTO() {
    }

//    public TransactionDTO(Transaction t) {
//        this.id = t.getId();
//        this.toIban = t.getToIban();
//        this.description = t.getDescription();
//        this.transactionDate = t.getTransactionDate();
//        this.fromAccountId = t.getFromAccount().getId();
//        this.status = t.getStatus();
//    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(String fromAccountId) {
        this.fromAccountId = fromAccountId;
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

    public String getTransactionType() {

        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getFormattedDate() {
        if (transactionDate == null) {
            return "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
        return transactionDate.format(formatter);
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getRecipientSurname() {
        return recipientSurname;
    }

    public void setRecipientSurname(String recipientSurname) {
        this.recipientSurname = recipientSurname;
    }
}

//    public Transaction toEntity(AccountServiceInter accountService) {
//        Accounts fromAcc = accountService.findAccountById(fromAccountId);
//        Transaction t = new Transaction();
//        t.setId(this.id);
//        t.setAmount(this.amount);
//        t.setDescription(this.description);
//        t.setFromAccount(fromAcc);
//        t.setToIban(this.toIban);
//        t.setStatus(this.status);
//        return t;
//    }
