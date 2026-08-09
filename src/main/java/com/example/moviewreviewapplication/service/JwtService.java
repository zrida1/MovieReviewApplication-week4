package com.example.moviewreviewapplication.service;

public interface JwtService {
    String generateToken(String email);
    String extractEmail(String token);

    boolean validateToken(String token);
    boolean isTokenExpired(String token);
}
