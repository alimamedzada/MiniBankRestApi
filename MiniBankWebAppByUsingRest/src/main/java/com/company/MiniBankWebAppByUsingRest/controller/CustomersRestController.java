package com.company.MiniBankWebAppByUsingRest.controller;

import com.company.MiniBankByUsingSpring.service.inter.AccountServiceInter;
import com.company.MiniBankByUsingSpring.service.inter.CustomerServiceInter;
import com.company.MiniBankByUsingSpring.service.inter.TransactionServiceInter;
import com.company.MiniBankWebAppByUsingRest.dto.AccountsDTO;
import com.company.MiniBankWebAppByUsingRest.dto.CustomerResponseDTO;
import com.company.MiniBankWebAppByUsingRest.dto.CustomersDTO;
import com.company.MiniBankWebAppByUsingRest.dto.ResponseDTO;
import com.company.MiniBankWebAppByUsingRest.dto.TransactionDTO;
import com.company.MiniBankWebAppByUsingRest.mapper.AccountsMapper;
import com.company.MiniBankWebAppByUsingRest.mapper.CustomersMapper;
import com.company.MiniBankWebAppByUsingRest.mapper.TransactionMapper;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class CustomersRestController {

    private final CustomerServiceInter cService;
    private final CustomersMapper cMapper;
    private final TransactionServiceInter tService;
    private final TransactionMapper tMapper;

    public CustomersRestController(CustomerServiceInter cService,TransactionMapper tMapper,CustomersMapper cMapper,TransactionServiceInter tService){
        this.tMapper=tMapper;
        this.cMapper= cMapper;
        this.cService=cService;
        this.tService=tService;
    }

    @GetMapping("/customer")
    public ResponseEntity<ResponseDTO> showCustomerPage(Principal principal) {

        CustomersDTO customer = cMapper.toDto(cService.findCustomerByUsername(principal.getName()));
        BigDecimal totalBalance = cService.getTotalBalance(customer.getCustomerId());

        List<TransactionDTO> transactions = tMapper.toDtoList(tService.getRecentTransactionsByCustomerId(customer.getCustomerId(), 2));

        List<String> myAccountIds = new ArrayList<>();
        for (AccountsDTO acc : customer.getAccounts()) {
            myAccountIds.add(acc.getId());
        }
        for (TransactionDTO tDto : transactions) {
            boolean isFromMe = myAccountIds.contains(tDto.getFromAccountId());
            boolean isToMe = myAccountIds.contains(tDto.getToIban());

            if (isFromMe && isToMe) {
                tDto.setTransactionType("INTERNAL");
            } else if (isFromMe) {
                tDto.setTransactionType("OUTGOING");
            } else {
                tDto.setTransactionType("INCOMING");
            }
        }
        CustomerResponseDTO crDto = new CustomerResponseDTO();
        crDto.setCustomer(customer);
        crDto.setTotalBalance(totalBalance);
        crDto.setTransactions(transactions);

        return ResponseEntity.ok(ResponseDTO.of(crDto));
    }
}
