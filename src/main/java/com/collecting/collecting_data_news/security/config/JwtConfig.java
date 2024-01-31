package com.collecting.collecting_data_news.security.config;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;
import java.util.Base64;

@Configuration
public class JwtConfig {
    @Value("${jwt.secretKey}")
    public String secretKey;

    @Value("${jwt.expirationTime}")
    public long expirationTime;

    @Bean
    public SecretKey jwtSecretKey() {
        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKey.getBytes());
        return Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
    }
    @Bean
    public long getExpirationTime() {
        return expirationTime;
    }
}