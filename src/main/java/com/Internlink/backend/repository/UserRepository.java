package com.Internlink.backend.repository;

import com.Internlink.backend.entity.User;
import com.Internlink.backend.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    // Find user by email (for login)
    Optional<User> findByEmail(String email);

    // Find user by phone number
    Optional<User> findByPhoneNumber(String phoneNumber);

    // Find all users with a specific role
    List<User> findByRole(UserRole role);

    // Check if email exists
    boolean existsByEmail(String email);

    // Check if phone exists
    boolean existsByPhoneNumber(String phoneNumber);
}