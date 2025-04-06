package Spring.service;

import Spring.model.User;
import java.util.List;
import java.util.Optional;

/**
 * UserService interface
 * 
 * Defines the contract for user-related business operations.
 */
public interface UserService {
    
    /**
     * Find a user by their ID
     * 
     * @param id The user ID
     * @return An Optional containing the user if found, or empty if not found
     */
    Optional<User> getUserById(Long id);
    
    /**
     * Find a user by their username
     * 
     * @param username The username to search for
     * @return An Optional containing the user if found, or empty if not found
     */
    Optional<User> getUserByUsername(String username);
    
    /**
     * Get all users in the system
     * 
     * @return A list of all users
     */
    List<User> getAllUsers();
    
    /**
     * Create a new user
     * 
     * @param user The user to create
     * @return The created user with an ID assigned
     */
    User createUser(User user);
    
    /**
     * Update an existing user
     * 
     * @param user The user to update
     * @return The updated user
     * @throws IllegalArgumentException if the user doesn't exist
     */
    User updateUser(User user);
    
    /**
     * Delete a user by their ID
     * 
     * @param id The ID of the user to delete
     * @throws IllegalArgumentException if the user doesn't exist
     */
    void deleteUser(Long id);
}
