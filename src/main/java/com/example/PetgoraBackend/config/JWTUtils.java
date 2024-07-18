package com.example.PetgoraBackend.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Component
public class JWTUtils {

    private final SecretKey key;
    private final long expirationTime;
    private final long refreshExpirationTime;


    public JWTUtils(@Value("${jwt.secret.key}") String secretKey,
                    @Value("${jwt.expiration.duration}") long expirationTime,
                    @Value("${jwt.refresh.expiration.duration:3600000}") long refreshExpirationTime)
       {


            byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);

        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.expirationTime = expirationTime;
        this.refreshExpirationTime = refreshExpirationTime;
    }


    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(key)
                .compact();
    }



    public ResponseCookie generateRefreshTokenCookie(UserDetails userDetails) {
        String refreshToken = generateRefreshToken(userDetails);
        return ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshExpirationTime / 1000)
                .sameSite("Strict")
                .build();
    }
    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationTime))
                .signWith(key)
                .compact();
    }


    public ResponseCookie generateJwtCookie(UserDetails userDetails) {
        String jwt = generateToken(userDetails);
        return ResponseCookie.from("jwt", jwt)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(expirationTime / 1000)
                .sameSite("Strict")
                .build();
    }



    public String extractUsername(String token) {
        return extractClaims(token, Claims::getSubject);
    }


    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(key)
                .build().parseClaimsJws(token);
        return claimsTFunction.apply(claimsJws.getBody());
    }
    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie.from("jwt", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .sameSite("Strict")
                .build();
    }

}
