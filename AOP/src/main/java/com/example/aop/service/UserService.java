package com.example.aop.service;

import com.example.aop.annotation.Auditable;
import com.example.aop.annotation.Cacheable;
import com.example.aop.annotation.LogExecutionTime;
import com.example.aop.annotation.RequiresRole;
import com.example.aop.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for managing users.
 * 
 * This class demonstrates various AOP concepts through annotations and method calls.
 */
@Service
@Auditable("UserService")
public class UserService {

    private final Map<Long, User> users = new HashMap<>();

    public UserService() {
        // Initialize with some sample data
        users.put(1L, new User(1L, "admin", "admin@example.com", "ADMIN"));
        users.put(2L, new User(2L, "user1", "user1@example.com", "USER"));
        users.put(3L, new User(3L, new String("user2"), "user2@example.com", "USER"));
    }

    /**
     * Get all users.
     * 
     * This method is marked with @LogExecutionTime to log its execution time.
     * It's also marked with @Cacheable to cache its results.
     */
    @LogExecutionTime
    @Cacheable(key = "allUsers")
    public List<User> getAllUsers() {
        simulateSlowService();
        return new ArrayList<>(users.values());
    }

    /**
     * Get a user by ID.
     * 
     * This method is marked with @LogExecutionTime to log its execution time.
     */
    @LogExecutionTime
    public User getUserById(Long id) {
        simulateSlowService();
        return users.get(id);
    }

    /**
     * Create a new user.
     * 
     * This method is marked with @Auditable to log audit information.
     * It's also marked with @RequiresRole to enforce role-based access control.
     */
    @Auditable("createUser")
    @RequiresRole({"ADMIN"})
    public User createUser(User user) {
        users.put(user.getId(), user);
        return user;
    }

    /**
     * Update an existing user.
     * 
     * This method is marked with @Auditable to log audit information.
     * It's also marked with @RequiresRole to enforce role-based access control.
     */
    @Auditable("updateUser")
    @RequiresRole({"ADMIN"})
    public User updateUser(User user) {
        if (!users.containsKey(user.getId())) {
            throw new IllegalArgumentException("User not found with ID: " + user.getId());
        }
        users.put(user.getId(), user);
        return user;
    }

    /**
     * Delete a user.
     * 
     * This method is marked with @Auditable to log audit information.
     * It's also marked with @RequiresRole to enforce role-based access control.
     */
    @Auditable("deleteUser")
    @RequiresRole({"ADMIN"})
    public void deleteUser(Long id) {
        if (!users.containsKey(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        users.remove(id);
    }

    /**
     * Simulate a slow service by sleeping for a random amount of time.
     */
    private void simulateSlowService() {
        try {
            Thread.sleep((long) (Math.random() * 200));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
