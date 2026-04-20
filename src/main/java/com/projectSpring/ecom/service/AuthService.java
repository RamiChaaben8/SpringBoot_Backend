package com.projectSpring.ecom.service;

import com.projectSpring.ecom.entity.User;
import com.projectSpring.ecom.repository.UserRepository;
import com.projectSpring.ecom.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Map<String, String> register(User request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : User.Role.CUSTOMER)
                .active(true)
                .createdAt(System.currentTimeMillis())
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        
        Map<String, String> response = new HashMap<>();
        response.put("token", jwtToken);
        return response;
    }

    public Map<String, String> login(String email, String password) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var jwtToken = jwtService.generateToken(user);
        
        Map<String, String> response = new HashMap<>();
        response.put("token", jwtToken);
        return response;
    }

    public Map<String, String> refreshToken(String oldToken) {
        return new HashMap<>();
    }

    public void logout(String token) {
    }
}