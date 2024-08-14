package com.example.PetgoraBackend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Component
public class DeviceJWTUtils {

    private final SecretKey key;
    private final long tokenExpirationTime;

    public DeviceJWTUtils(@Value("${device.jwt.secret.key}") String secretKey,
                          @Value("${device.jwt.expiration.duration}") long tokenExpirationTime) {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.tokenExpirationTime = tokenExpirationTime;
    }

    public String generateToken(String deviceId) {
        return Jwts.builder()
                .setSubject(deviceId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .signWith(key)
                .compact();
    }

    public boolean isTokenValid(String token, String deviceId) {
        final String tokenDeviceId = extractDeviceId(token);
        return (deviceId.equals(tokenDeviceId) && !isTokenExpired(token));
    }

    public String extractDeviceId(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        return claimsTFunction.apply(claimsJws.getBody());
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }
}
