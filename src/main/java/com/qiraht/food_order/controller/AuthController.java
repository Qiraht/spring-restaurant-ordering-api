package com.qiraht.food_order.controller;

import com.qiraht.food_order.dto.ApiResponse;
import com.qiraht.food_order.dto.request.AuthRequest;
import com.qiraht.food_order.dto.response.AuthResponse;
import com.qiraht.food_order.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> postAuthLogin(@Valid @RequestBody AuthRequest request) {
        String token = authService.loginUser(request);

        AuthResponse data = new AuthResponse(token);

        return ResponseEntity.ok(
                ApiResponse.success("User login successfully", data)
        );
    }
}
