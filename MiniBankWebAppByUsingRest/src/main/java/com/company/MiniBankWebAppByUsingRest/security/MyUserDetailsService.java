package com.company.MiniBankWebAppByUsingRest.security;

import com.company.MiniBankByUsingSpring.entity.Customers;
import com.company.MiniBankByUsingSpring.service.inter.CustomerServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final CustomerServiceInter customerService;

    public MyUserDetailsService(CustomerServiceInter customerService) {
        this.customerService = customerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customers customer = customerService.findCustomerByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException("Kullanıcı bulunamadı: " + username);
        }
        return new User(
                customer.getUsername(),
                customer.getPassword(),
                new ArrayList<>()
        );
    }
}
