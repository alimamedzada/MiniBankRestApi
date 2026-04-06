package com.company.MiniBankWebAppByUsingRest.controller;

import com.company.MiniBankByUsingSpring.entity.Customers;
import com.company.MiniBankByUsingSpring.service.inter.CustomerServiceInter;
import com.company.MiniBankWebAppByUsingRest.dto.CustomersDTO;
import com.company.MiniBankWebAppByUsingRest.dto.ResponseDTO;
import com.company.MiniBankWebAppByUsingRest.mapper.CustomersMapper;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomerServiceInter customerService;
    @Autowired
    private CustomersMapper customerMapper;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> addCustomer(@RequestBody CustomersDTO customerDto) {
        Customers customer = customerMapper.toEntity(customerDto);

        String encodedPassword = passwordEncoder.encode("");
        customer.setPassword(encodedPassword);

        customerService.addCustomer(customer);

        return ResponseEntity.ok(ResponseDTO.of(customerDto, "The customer has been successfully added."));
    }
}
