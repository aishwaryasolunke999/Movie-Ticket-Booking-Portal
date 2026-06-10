package com.moviebooking.controller;

import com.moviebooking.dto.request.LoginRequest;
import com.moviebooking.dto.request.RegisterRequest;
import com.moviebooking.dto.response.JwtResponse;
import com.moviebooking.dto.response.UserResponse;
import com.moviebooking.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Register and Login APIs")
public class AuthController {

    @Autowired
    private AuthService authService;

    // ─── REGISTER ───────────────────────────────────────
    @PostMapping("/register")
    @Operation(summary = "Register new customer account")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        UserResponse response = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ─── LOGIN ──────────────────────────────────────────
    @PostMapping("/login")
    @Operation(summary = "Login and receive JWT token")
    public ResponseEntity<JwtResponse> login(
            @Valid @RequestBody LoginRequest request) {

        JwtResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}