package com.campusconnect.authservice.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtConstants {
    @Value("${jwt.secret}")
    public String SECRET;

    @Value("${jwt.expiration-ms}")
    public long EXPIRATION;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}
