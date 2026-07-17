package com.Internlink.backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    // Secret key used to sign the JWT
    private static final String SECRET =
            "internlinksupersecretkeyinternlinksupersecretkey123456";

    // Token validity (24 hours)
    private static final long EXPIRATION = 1000 * 60 * 60 * 24;

    // Generate signing key
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Generate JWT token
    public String generateToken(String email) {

        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    // Extract email from token
    public String extractEmail(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    // Check if token is valid
    public boolean isTokenValid(String token) {

        try {

            extractEmail(token);

            return true;

        } catch (Exception e) {

            return false;

        }
    }
}