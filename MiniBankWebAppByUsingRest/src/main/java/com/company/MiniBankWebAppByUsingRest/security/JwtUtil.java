package com.company.MiniBankWebAppByUsingRest.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

@Component
public class JwtUtil {


    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private SecretKey key;

    @PostConstruct
    public void init() {
        if (SECRET_KEY == null) {
            throw new RuntimeException(" 'jwt.secret' not found!");
        }
        this.key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String extractUsername(String token) {
        Claims claim = extractAllClaims(token);
        return claim.getSubject();
    }

    public Date extractExpiration(String token) {
        Claims claim = extractAllClaims(token);
        return claim.getExpiration();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 20))
                .signWith(key)
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
