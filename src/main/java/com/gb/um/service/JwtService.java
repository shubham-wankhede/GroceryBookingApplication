package com.gb.um.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service to perform JWT Operation : extract claims, validate token, generate toke etc.
 */
@Slf4j
@Service
public class JwtService {

    @Value("${application.security.jwt.secret}")
    private String secretKey;

    @Value("${application.security.session.expiration-in-hours}")
    private String tokenExpirationInHours;


    /**
     * Extract Username claim form given Token
     * @param token JWT Token
     * @return extracted claim subject/username from token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract expiration claim from given token
     * @param token JWT Token
     * @return extracted claim expirationDate from token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extract claim from given token by passing the function to get required claim
     * @param token         JET Token
     * @param claimResolver Function to get claims
     * @param <T>           Type
     * @return extract desired claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    /**
     * Extract all claims from given token
     * @param token JWT Token
     * @return all JWT claims
     */
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Create Signing key from secret
     * @return signing key
     */
    public Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Generate JWT Token
     * @param extraClaims extra claims
     * @param userDetails user details form DB
     * @return new JWT Token generated
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails, Long.parseLong(tokenExpirationInHours));
    }

    /**
     * Generate JWT Token
     * @param userDetails user details from DB
     * @return new JWT Token generated
     */
    public String generateToken(UserDetails userDetails) {
        return buildToken(new HashMap<>(), userDetails, Long.parseLong(tokenExpirationInHours));
    }

    /**
     * Generate JWT Token
     * @param userDetails user details from DB
     * @param expiration  JWT Token expiration in hours
     * @return new JWT Token generated
     */
    public String generateToken(UserDetails userDetails, long expiration) {
        return buildToken(new HashMap<>(), userDetails, expiration);
    }

    /**
     * Build JWT Token from given Details
     * @param extraClaims extra claims
     * @param userDetails user details from DB
     * @param expiration  JWT Token expiration in hours
     * @return new JWT Token generated
     */
    public String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000 * 60 * 60))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validate if the token is corrent and not expired
     * @param token       JWT Token
     * @param userDetails User Details from DB
     * @return true if token not expired and subject claims matches with user details else false
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        //validate username and expiration
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Check if the given token is expired or not
     * @param token JWT Token
     * @return return true if token is not expired else false
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
