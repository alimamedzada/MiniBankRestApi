package com.company.MiniBankWebAppByUsingRest.controller;

import com.company.MiniBankByUsingSpring.service.inter.AccountServiceInter;
import com.company.MiniBankByUsingSpring.service.inter.CustomerServiceInter;
import com.company.MiniBankWebAppByUsingRest.dto.AccountsDTO;
import com.company.MiniBankWebAppByUsingRest.dto.AccountsResponseDTO;
import com.company.MiniBankWebAppByUsingRest.dto.CreateAccountDTO;
import com.company.MiniBankWebAppByUsingRest.dto.CustomersDTO;
import com.company.MiniBankWebAppByUsingRest.dto.ResponseDTO;
import com.company.MiniBankWebAppByUsingRest.mapper.AccountsMapper;
import com.company.MiniBankWebAppByUsingRest.mapper.CustomersMapper;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class AccountsRestController {

    @Autowired
    private CustomerServiceInter cService;
    @Autowired
    private AccountsMapper aMapper;
    @Autowired
    private CustomersMapper cMapper;
    @Autowired
    private AccountServiceInter aService;

    @GetMapping("/accounts")
    public ResponseEntity<ResponseDTO> getAccountsPage(Principal principal) {
        CustomersDTO customer = cMapper.toDto(cService.findCustomerByUsername(principal.getName()));

        BigDecimal totalBalance = cService.getTotalBalance(customer.getCustomerId());
        List<AccountsDTO> list = customer.getAccounts();
        AccountsResponseDTO asDto = new AccountsResponseDTO();

        asDto.setAccounts(list);
        asDto.setTotalBalance(totalBalance);

        return ResponseEntity.ok(ResponseDTO.of(asDto));
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<ResponseDTO> getAccountDetails(@PathVariable String id) {
        AccountsDTO account = aMapper.toDto(aService.findAccountById(id));

        return ResponseEntity.ok(ResponseDTO.of(account));
    }

    @PostMapping("/create-new-account")
    public ResponseEntity<ResponseDTO> openAccount(@RequestBody CreateAccountDTO cadto, Principal principal) {

        aService.createAccountByUsername(principal.getName(), cadto.getAccountType(), cadto.getBalance());
        return ResponseEntity.ok(ResponseDTO.of(null, "New Account has been successfully created!"));
    }
}
