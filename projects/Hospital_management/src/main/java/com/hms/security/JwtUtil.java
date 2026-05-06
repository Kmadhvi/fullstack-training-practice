package com.hms.security;

import com.hms.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    private final SecretKey secretKey;
    private final long expirationMinutes;
    private final String issuer;

    public JwtUtil(
            @Value("${hms.security.jwt.secret}") String secret,
            @Value("${hms.security.jwt.expiration-minutes}") long expirationMinutes,
            @Value("${hms.security.jwt.issuer}") String issuer
    ) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMinutes = expirationMinutes;
        this.issuer = issuer;
    }

    public String generateToken(User user) {
        Instant now = Instant.now();
        Long departmentId = user.getDepartment() == null ? null : user.getDepartment().getId();
        return Jwts.builder()
                .issuer(issuer)
                .subject(user.getEmail())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plus(expirationMinutes, ChronoUnit.MINUTES)))
                .claims(Map.of(
                        "userId", user.getId(),
                        "email", user.getEmail(),
                        "role", user.getRole().name(),
                        "departmentId", departmentId == null ? "" : departmentId
                ))
                .signWith(secretKey)
                .compact();
    }

    public String extractEmail(String token) {
        return parseClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, CustomUserDetails userDetails) {
        String email = extractEmail(token);
        return email.equals(userDetails.getUsername()) && parseClaims(token).getExpiration().after(new Date());
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .requireIssuer(issuer)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
