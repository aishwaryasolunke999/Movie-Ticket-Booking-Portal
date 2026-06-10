package com.moviebooking.service;

import com.moviebooking.dto.request.LoginRequest;
import com.moviebooking.dto.request.RegisterRequest;
import com.moviebooking.dto.response.JwtResponse;
import com.moviebooking.dto.response.UserResponse;

public interface AuthService {

    // Register new customer
    UserResponse register(RegisterRequest request);

    // Login and return JWT token
    JwtResponse login(LoginRequest request);
}