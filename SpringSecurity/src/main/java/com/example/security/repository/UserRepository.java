package com.example.security.repository;

import com.example.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Find a user by username.
     *
     * @param username the username to search for
     * @return an Optional containing the user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Check if a username exists.
     *
     * @param username the username to check
     * @return true if the username exists, false otherwise
     */
    Boolean existsByUsername(String username);

    /**
     * Check if an email exists.
     *
     * @param email the email to check
     * @return true if the email exists, false otherwise
     */
    Boolean existsByEmail(String email);
}
