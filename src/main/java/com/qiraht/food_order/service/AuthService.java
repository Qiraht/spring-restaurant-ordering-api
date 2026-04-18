package com.qiraht.food_order.service;

import com.qiraht.food_order.config.CustomUserDetails;
import com.qiraht.food_order.dto.request.AuthRequest;
import com.qiraht.food_order.dto.response.AuthResponse;
import com.qiraht.food_order.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse loginUser(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        CustomUserDetails userDetails = (CustomUserDetails) Objects.requireNonNull(
                authentication.getPrincipal(), "Principal should not be null");

        String token = jwtUtil.generateToken(userDetails);

        return new AuthResponse(token);
    }
}
