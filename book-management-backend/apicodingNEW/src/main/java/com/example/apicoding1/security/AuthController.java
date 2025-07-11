package com.example.apicoding1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(), request.getPassword()));

            if (authentication.isAuthenticated()) {
                String token = jwtUtil.generateToken(request.getUsername());
                return new AuthResponse(token);
            } else {
                throw new RuntimeException("Invalid access");
            }
        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid Username or Password");
        }
    }
}
