package com.moviebooking.repository;

import com.moviebooking.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email — used in login + JWT filter
    Optional<User> findByEmail(String email);

    // Check if email already exists — used in register
    Boolean existsByEmail(String email);
}
