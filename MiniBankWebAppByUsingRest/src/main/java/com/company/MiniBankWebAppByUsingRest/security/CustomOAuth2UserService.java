package com.company.MiniBankWebAppByUsingRest.security;

import com.company.MiniBankByUsingSpring.entity.Customers;
import com.company.MiniBankByUsingSpring.service.inter.CustomerServiceInter;
import com.company.MiniBankByUsingSpring.service.inter.TransactionServiceInter;
import com.company.MiniBankWebAppByUsingRest.mapper.CustomersMapper;
import com.company.MiniBankWebAppByUsingRest.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final CustomerServiceInter cService;

    public CustomOAuth2UserService(CustomerServiceInter cService) {
        this.cService = cService;
    }



    @Override
    public OAuth2User loadUser(OAuth2UserRequest uReq) {
        OAuth2User oa2User = super.loadUser(uReq);
        String email = oa2User.getAttribute("email");
        Customers c = cService.findCustomerByUsername(email);
        if (c == null) {
            Customers cNew = new Customers();
            cNew.setUsername(email);
            cNew.setPassword("OAUTH2_USER");
            cNew.setName(oa2User.getAttribute("name"));
            cNew.setSurname("filankesov");

            String fullName = oa2User.getAttribute("name");
            String[] parts = fullName.split(" ", 2);

            cNew.setName(parts[0]);
            cNew.setSurname(parts.length > 1 ? parts[1] : "filankesov");
            cNew.setAge(25);
            cNew.setAzeid("AZE00000000");
            cService.addCustomer(cNew);
        }
        return oa2User;
    }
}
