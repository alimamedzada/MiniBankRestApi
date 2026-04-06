package com.company.MiniBankWebAppByUsingRest.dto;

import java.util.List;

public class TransactionHistoryResponseDTO {

    private List<TransactionDTO> filteredList;
    private CustomersDTO customer;

    public List<TransactionDTO> getFilteredList() {
        return filteredList;
    }

    public void setFilteredList(List<TransactionDTO> filteredList) {
        this.filteredList = filteredList;
    }

    public CustomersDTO getCustomer() {
        return customer;
    }

    public void setCustomer(CustomersDTO customer) {
        this.customer = customer;
    }
    
    
}
