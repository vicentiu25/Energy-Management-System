package com.example.usermanagmentenergysystem.authentication;

import com.example.usermanagmentenergysystem.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

@Component
public class JWTokenCreator {
    public static final int JWT_TOKEN_VALIDITY = 3000;
    private final Set<String> invalidatedTokens = new HashSet<>(); // Store invalidated tokens


    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return getJwtToken(claims, String.valueOf(user.getIdUser()), String.valueOf(user.getRole()));
    }

    public String getJwtToken(Map<String, Object> claims, String subject, String audience) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setAudience(audience).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    //retrieve username from jwt token
    public Long getUserIdFromToken(String token) {
        if(validateToken(token))
            return Long.parseLong(getClaimFromToken(token, Claims::getSubject));
        else
            return -1L;
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieveing any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // Invalidate token (on logout)
    public void invalidateToken(String token) {
        invalidatedTokens.add(token);
    }

    // Check if token is invalidated (on token validation)
    private Boolean isTokenBlacklisted(String token) {
        return invalidatedTokens.contains(token);
    }

    // Validate token (with blacklist check)
    public Boolean validateToken(String token, User user) {
        final Long userId = getUserIdFromToken(token);
        return (userId.equals(user.getIdUser()) && !isTokenExpired(token) && !isTokenBlacklisted(token));
    }

    // Validate token (with blacklist check)
    public Boolean validateToken(String token) {
        return (!isTokenExpired(token) && !isTokenBlacklisted(token));
    }

}