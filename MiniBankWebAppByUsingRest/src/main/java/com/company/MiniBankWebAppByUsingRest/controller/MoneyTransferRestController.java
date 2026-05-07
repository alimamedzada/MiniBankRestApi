package com.company.MiniBankWebAppByUsingRest.controller;

import com.company.MiniBankByUsingSpring.entity.Customers;
import com.company.MiniBankByUsingSpring.service.inter.AccountServiceInter;
import com.company.MiniBankByUsingSpring.service.inter.CustomerServiceInter;
import com.company.MiniBankByUsingSpring.service.inter.TransactionServiceInter;
import com.company.MiniBankWebAppByUsingRest.dto.AccountsDTO;
import com.company.MiniBankWebAppByUsingRest.dto.CustomersDTO;
import com.company.MiniBankWebAppByUsingRest.dto.MoneyTransferResponseDTO;
import com.company.MiniBankWebAppByUsingRest.dto.QuickTransferDTO;
import com.company.MiniBankWebAppByUsingRest.dto.ResponseDTO;
import com.company.MiniBankWebAppByUsingRest.dto.TransactionDTO;
import com.company.MiniBankWebAppByUsingRest.mapper.AccountsMapper;
import com.company.MiniBankWebAppByUsingRest.mapper.CustomersMapper;
import com.company.MiniBankWebAppByUsingRest.mapper.TransactionMapper;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class MoneyTransferRestController {

    private final AccountServiceInter aService;
    private final AccountsMapper aMapper;
    private final CustomerServiceInter cService;
    private final CustomersMapper cMapper;
    private final TransactionServiceInter tService;
    private final TransactionMapper tMapper;

    public MoneyTransferRestController(AccountsMapper aMapper,AccountServiceInter aService,CustomerServiceInter cService,TransactionMapper tMapper,CustomersMapper cMapper,TransactionServiceInter tService){
        this.tMapper=tMapper;
        this.cMapper= cMapper;
        this.aMapper= aMapper;
        this.cService=cService;
        this.aService=aService;
        this.tService=tService;
    }

    @GetMapping("/money-transfer")
    public ResponseEntity<ResponseDTO> showMoneyTransferPage(Principal principal) {

        CustomersDTO customer = cMapper.toDto(cService.findCustomerByUsername(principal.getName()));
        List<AccountsDTO> accList = customer.getAccounts();
        List<TransactionDTO> listT = tMapper.toDtoList(tService.getTransactionsByCustomerId(customer.getCustomerId()));
        for (TransactionDTO t : listT) {
            AccountsDTO toAccount = aMapper.toDto(aService.findAccountById(t.getToIban()));
            CustomersDTO accCustomer = cMapper.toDto(cService.getCustomerById(toAccount.getCustomerId()));

            String name = accCustomer.getName();
            String surname = accCustomer.getSurname();

            t.setRecipientName(name);
            t.setRecipientSurname(surname);
        }
        MoneyTransferResponseDTO mtrDto = new MoneyTransferResponseDTO();

        mtrDto.setAccountsList(accList);
        mtrDto.setCustomer(customer);
        mtrDto.setTransactionList(listT);

        return ResponseEntity.ok(ResponseDTO.of(mtrDto));
    }

    @PostMapping("/transfer-process")
    public ResponseEntity<ResponseDTO> transferMoney(@RequestBody TransactionDTO tDto) {
        aService.transfer(
                tDto.getFromAccountId(),
                tDto.getToIban(),
                tDto.getAmount(),
                tDto.getDescription()
        );

        return ResponseEntity.ok(ResponseDTO.of(tDto, "The transfer process has been successfully completed."));
    }

    @PostMapping("/quick-transfer-process")
    public ResponseEntity<ResponseDTO> quickTransferMoney(@RequestBody QuickTransferDTO dto, Principal principal) {
        Customers c = cService.findCustomerByUsername(principal.getName());

        dto.setCustomerId(c.getCustomerId());

        aService.qucikTransfer(dto.getCustomerId(), dto.getToIban(), dto.getAmount());

        return ResponseEntity.ok(ResponseDTO.of(dto, "The transfer process has been successfully completed."));
    }
}
