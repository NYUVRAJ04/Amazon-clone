package org.happiest.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.happiest.exception.TokenExpiredException;
import org.happiest.exception.TokenInvalidException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
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
@Component
public class JWTService {

    private String secretkey = "";

    public JWTService() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating secret key", e);
        }
    }

    public String generateToken(String username) {
        try {
            Map<String, Object> claims = new HashMap<>();
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(username)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 60 * 60 * 3000)) // 3 hours
                    .signWith(getKey())
                    .compact();
        } catch (JwtException e) {
            throw new TokenInvalidException("Error generating token", e);
        }
    }

    private SecretKey getKey() {
        try {
            byte[] keyBytes = Decoders.BASE64.decode(secretkey);
            return Keys.hmacShaKeyFor(keyBytes);
        } catch (IllegalArgumentException e) {
            throw new TokenInvalidException("Error decoding secret key", e);
        }
    }

    public String extractUserEmail(String token) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("Token has expired", e);
        } catch (JwtException e) {
            throw new TokenInvalidException("Error extracting user email from token", e);
        }
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new TokenExpiredException("Token has expired", e);
        } catch (JwtException e) {
            throw new TokenInvalidException("Error extracting claims from token", e);
        }
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String email = extractUserEmail(token);
            return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (TokenInvalidException | TokenExpiredException e) {
            return false; // Token is invalid or expired
        }
    }

    public boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (TokenInvalidException | TokenExpiredException e) {
            return true; // Assume expired if an exception occurs
        }
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
