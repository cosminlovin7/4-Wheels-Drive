package com.upb.fourwheelsdrive.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.function.Function;

public interface JwtService {
    String generateJwtToken(Map<String, Object> extraClaims, UserDetails userDetails);
    boolean isTokenValid(String jwtToken, UserDetails userDetails);
    String getUsernameFromToken(String jwtToken);
    <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver);
    boolean isTokenExpired(String jwtToken);
}
