//package com.company.MiniBankWebAppByUsingRest.security;
//
//import java.util.UUID;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.core.oidc.OidcScopes;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class AuthorizationServer {
//
//    @Bean
//    public SecurityFilterChain authServerSecurityFilterChain(HttpSecurity http) throws Exception {
//        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
//        // Burada "Google ile Giriş", "GitHub ile Giriş" veya 
//        // Kendi kullanıcı veritabanını buraya bağlarsın.
//        return http.build();
//    }
//
//    @Bean
//    public RegisteredClientRepository registeredClientRepository() {
//        // "MiniBankClient" (senin frontend) buraya kayıt edilir
//        RegisteredClient client = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("minibank-frontend")
//                .clientSecret("{noop}gizli-anahtar")
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .scope(OidcScopes.OPENID)
//                .build();
//        return new InMemoryRegisteredClientRepository(client);
//    }
//}
