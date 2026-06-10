package com.moviebooking.service.impl;

import com.moviebooking.dto.request.LoginRequest;
import com.moviebooking.dto.request.RegisterRequest;
import com.moviebooking.dto.response.JwtResponse;
import com.moviebooking.dto.response.UserResponse;
import com.moviebooking.entity.User;
import com.moviebooking.enums.Role;
import com.moviebooking.exception.ResourceAlreadyExistsException;
import com.moviebooking.repository.UserRepository;
import com.moviebooking.security.jwt.JwtUtils;
import com.moviebooking.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    @Transactional
    public UserResponse register(RegisterRequest request) {

        // Step 1: Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResourceAlreadyExistsException(
                "Email already registered: " + request.getEmail()
            );
        }

        // Step 2: Build User entity
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_CUSTOMER)
                .build();

        // Step 3: Save to DB
        User savedUser = userRepository.save(user);

        // Step 4: Return UserResponse
        return mapToUserResponse(savedUser);
    }

    @Override
    public JwtResponse login(LoginRequest request) {

        // Step 1: Authenticate email + password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Step 2: Set authentication in SecurityContext
        SecurityContextHolder.getContext()
                             .setAuthentication(authentication);

        // Step 3: Generate JWT token
        String jwt = jwtUtils.generateTokenFromEmail(request.getEmail());

        // Step 4: Fetch user details
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                    new RuntimeException("User not found")
                );

        // Step 5: Return JwtResponse with token + user info
        return new JwtResponse(
                jwt,
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
    }

    // Helper method — map User entity to UserResponse
    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .createdAt(user.getCreatedAt())
                .build();
    }
}