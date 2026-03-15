package com.campusconnect.materialsservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {

    // same base64-encoded secret as your other services
    private static final String SECRET_KEY =
            "bXlTdXBlclNlY3VyZUp3dFNlY3JldEtleVRoYXRJc0xvbmdFbm91Z2hGb3JIbWFjU0hBMjU2QWxnb3JpdGht";

    private SecretKey key;

    @PostConstruct
    public void init() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims parseAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token.replace("Bearer ", ""))
                .getBody();
    }

    private <T> T extractClaim(String token, Function<Claims, T> fn) {
        return fn.apply(parseAllClaims(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    public boolean isAdminOrFaculty(String token) {
        try {
            String role = extractRole(token);
            return "ADMIN".equalsIgnoreCase(role) || "FACULTY".equalsIgnoreCase(role);
        } catch (Exception ex) {
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        Date exp = extractClaim(token, Claims::getExpiration);
        return exp.before(new Date());
    }
}
