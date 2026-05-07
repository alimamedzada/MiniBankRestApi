package com.company.MiniBankWebAppByUsingRest.dto;

import java.util.ArrayList;
import java.util.List;

public class CustomersDTO {

    private int age;
    private String name;
    private String azeid;
    private String surname;
    private String username;
    private String customerId;
    private String accountType;
    private List<AccountsDTO> accounts = new ArrayList<>();

    public CustomersDTO() {
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

//    public CustomersDTO(Customers customer) {
//        this.username = customer.getUsername();
//
//        this.name = customer.getName();
//        this.surname = customer.getSurname();
//        this.azeid = customer.getAzeid();
//        this.age = customer.getAge();
//
//        List<Accounts> accs = customer.getAccounts();
//        for (Accounts acc : accs) {
//            accounts.add(new AccountsDTO(acc));
//        }
//
//    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAzeid() {
        return azeid;
    }

    public void setAzeid(String azeid) {
        this.azeid = azeid;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<AccountsDTO> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountsDTO> accounts) {
        this.accounts = accounts;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

//    public Customers toEntity() {
//        return Customers.createNewCustomer(name, surname, age, azeid, username, password, accountType);
//    }
}
