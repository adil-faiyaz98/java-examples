package com.example.aop.service;

import org.springframework.stereotype.Service;

/**
 * Service for security-related operations.
 * 
 * This class provides methods for checking user roles and permissions.
 */
@Service
public class SecurityService {

    /**
     * Check if the current user has the specified role.
     * 
     * In a real application, this would check against the authenticated user.
     * For this example, we'll just simulate the check.
     * 
     * @param role the role to check
     * @return true if the user has the role, false otherwise
     */
    public boolean hasRole(String role) {
        // For demonstration purposes, let's assume the current user has ADMIN role
        return "ADMIN".equals(role);
    }

    /**
     * Get the current username.
     * 
     * In a real application, this would get the username from the authenticated user.
     * For this example, we'll just return a fixed value.
     * 
     * @return the current username
     */
    public String getCurrentUsername() {
        // For demonstration purposes, let's return a fixed username
        return "admin";
    }
}
