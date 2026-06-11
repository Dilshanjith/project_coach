package com.example.backend.service;

import com.example.backend.dto.LoginRequest;
import com.example.backend.dto.LoginResponse;
import com.example.backend.dto.SignupRequest;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import com.example.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponse login(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        
        // Basic validation for this task. 
        // In reality, use password encoders and proper Spring Security authentication manager.
        if (userOptional.isPresent() && userOptional.get().getPassword().equals(request.getPassword())) {
            String token = jwtUtil.generateToken(request.getEmail());
            return new LoginResponse(token);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }

    public LoginResponse signup(SignupRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        User newUser = new User(request.getEmail(), request.getPassword());
        userRepository.save(newUser);

        String token = jwtUtil.generateToken(request.getEmail());
        return new LoginResponse(token);
    }
}
