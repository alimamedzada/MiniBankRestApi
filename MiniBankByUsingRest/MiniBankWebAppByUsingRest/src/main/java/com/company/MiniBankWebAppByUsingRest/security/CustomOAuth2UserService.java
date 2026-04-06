package com.company.MiniBankWebAppByUsingRest.security;

import com.company.MiniBankByUsingSpring.entity.Customers;
import com.company.MiniBankByUsingSpring.service.inter.CustomerServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private CustomerServiceInter cService;

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
            cNew.setAge(25);
            cNew.setAzeid("AZE00000000");
            cService.addCustomer(cNew);
        }
        return oa2User;
    }
}
