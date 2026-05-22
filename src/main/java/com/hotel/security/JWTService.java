package com.hotel.security;

import com.hotel.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {
  private final String secret = "ourhotelappmadebykhasimjanadinesh";

  private SecretKey getSecret() {
    return Keys.hmacShaKeyFor(secret.getBytes());
  }

  public String generateToken(User user) {
    int expirationTime = 1000 * 60 * 60;

    return Jwts.builder()
            .subject(user.getUsername())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() * expirationTime))
            .signWith(getSecret())
            .compact();
  }


  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = Jwts.parser()
            .verifyWith(getSecret())
            .build()
            .parseSignedClaims(token)
            .getPayload();

    return claimsResolver.apply(claims);
  }

  public boolean isTokenValid(String token, UserDetails user) {
    final String username = extractUsername(token);
    return (username.equals(user.getUsername()) && !isTokenExpired(token));
  }

  private boolean isTokenExpired(String token) {
    return extractClaim(token, Claims::getExpiration).before(new Date());
  }
}
