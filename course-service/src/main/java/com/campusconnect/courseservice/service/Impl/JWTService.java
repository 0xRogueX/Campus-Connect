package com.campusconnect.courseservice.service.Impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JWTService {
    private static final String SECRET_KEY = "bXlTdXBlclNlY3VyZUp3dFNlY3JldEtleVRoYXRJc0xvbmdFbm91Z2hGb3JIbWFjU0hBMjU2QWxnb3JpdGht";


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
            return "ADMIN".equalsIgnoreCase(extractRole(token.replace("Bearer ", "")));
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
