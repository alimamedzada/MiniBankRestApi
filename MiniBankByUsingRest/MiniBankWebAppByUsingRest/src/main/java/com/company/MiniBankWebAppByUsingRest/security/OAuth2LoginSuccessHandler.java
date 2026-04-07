package com.company.MiniBankWebAppByUsingRest.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

<<<<<<< HEAD
=======
    public OAuth2LoginSuccessHandler() {
        System.out.println("OAuth2LoginSuccessHandler called!");
    }

>>>>>>> 876dd8f (feat(security): integrate google oauth2 with custom jwt success handler)
    @Autowired
    private JwtUtil jwtUtil;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
<<<<<<< HEAD

=======
>>>>>>> 876dd8f (feat(security): integrate google oauth2 with custom jwt success handler)
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");

        String token = jwtUtil.generateToken(email);

        String targetUrl = frontendUrl + "/customer.html?token=" + token;
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
