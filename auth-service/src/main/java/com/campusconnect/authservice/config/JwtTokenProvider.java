package com.campusconnect.authservice.config;

import com.campusconnect.authservice.model.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenProvider {
    // Use a static final secret key to ensure it remains the same across all instances
    // In production, this should be loaded from a secure configuration source
    private static final String SECRET_KEY = "bXlTdXBlclNlY3VyZUp3dFNlY3JldEtleVRoYXRJc0xvbmdFbm91Z2hGb3JIbWFjU0hBMjU2QWxnb3JpdGht";

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", user.getId());
        claims.put("role", user.getRoles().name());
        claims.put("name", user.getUsername());
        claims.put("email", user.getEmail());

        return Jwts.builder()
                .claims(claims)
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isAdmin(String token) {
        try {
            return "ADMIN".equalsIgnoreCase(extractRole(token));
        } catch (Exception e) {
            // Log exception here for debugging
            return false;
        }
    }

    public boolean isAdminOrFaculty(String token) {
        try {
            String role = extractRole(token);
            return "FACULTY".equals(role) || "ADMIN".equals(role);
        } catch (Exception e) {
            // Log exception here for debugging
            return false;
        }
    }
}