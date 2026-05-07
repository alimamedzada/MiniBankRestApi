package com.company.MiniBankWebAppByUsingRest.controller;

import com.company.MiniBankByUsingSpring.entity.Accounts;
import com.company.MiniBankByUsingSpring.entity.Customers;
import com.company.MiniBankByUsingSpring.service.inter.AccountServiceInter;
import com.company.MiniBankByUsingSpring.service.inter.CustomerServiceInter;
import com.company.MiniBankByUsingSpring.service.inter.TransactionServiceInter;
import com.company.MiniBankWebAppByUsingRest.dto.CustomersDTO;
import com.company.MiniBankWebAppByUsingRest.dto.RegisterDTO;
import com.company.MiniBankWebAppByUsingRest.dto.ResponseDTO;
import com.company.MiniBankWebAppByUsingRest.mapper.CustomersMapper;
import java.math.BigDecimal;

import com.company.MiniBankWebAppByUsingRest.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
public class RegisterRestController {

    private final PasswordEncoder passwordEncoder;
    private final CustomerServiceInter cService;
    private final CustomersMapper cMapper;

    public RegisterRestController(PasswordEncoder passwordEncoder,CustomerServiceInter cService,CustomersMapper cMapper){
        this.passwordEncoder=passwordEncoder;
        this.cMapper = cMapper;
        this.cService=cService;
    }
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> addCustomer(@RequestBody RegisterDTO rDto) {
        CustomersDTO cDto = cMapper.toDtoFromRegister(rDto);

        String encodedPassword = passwordEncoder.encode(rDto.getPassword());

        Customers customer = cMapper.toEntity(cDto);

        customer.setPassword(encodedPassword);

        cService.addCustomer(customer, rDto.getAccountType());
        return ResponseEntity.ok(ResponseDTO.of(cDto,
                "The customer has been successfully added."));
    }
}
