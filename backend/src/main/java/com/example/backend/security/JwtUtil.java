package com.example.backend.security;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    
    public String generateToken(String email) {
        // In a real application, use a proper JWT library (e.g., io.jsonwebtoken)
        // For this task, we return a mock token based on the email.
        return "mock-jwt-token-for-" + email;
    }
}
