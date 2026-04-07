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

<<<<<<< HEAD
=======
    public CustomOAuth2UserService() {
        System.out.println("CustomOAuth2UserService called");

    }

>>>>>>> 876dd8f (feat(security): integrate google oauth2 with custom jwt success handler)
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
<<<<<<< HEAD
            cNew.setName(oa2User.getAttribute("name"));
            cNew.setSurname("filankesov");
=======

            String fullName = oa2User.getAttribute("name");
            String[] parts = fullName.split(" ", 2);

            cNew.setName(parts[0]);
            cNew.setSurname(parts.length > 1 ? parts[1] : "filankesov");
>>>>>>> 876dd8f (feat(security): integrate google oauth2 with custom jwt success handler)
            cNew.setAge(25);
            cNew.setAzeid("AZE00000000");
            cService.addCustomer(cNew);
        }
        return oa2User;
    }
}
