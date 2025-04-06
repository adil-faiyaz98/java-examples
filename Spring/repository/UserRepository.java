package Spring.repository;

import Spring.model.User;
import java.util.List;
import java.util.Optional;

/**
 * UserRepository interface
 * 
 * Defines the contract for accessing user data.
 * This is the dependency that will be injected into services.
 */
public interface UserRepository {
    
    /**
     * Find a user by their ID
     * 
     * @param id The user ID
     * @return An Optional containing the user if found, or empty if not found
     */
    Optional<User> findById(Long id);
    
    /**
     * Find a user by their username
     * 
     * @param username The username to search for
     * @return An Optional containing the user if found, or empty if not found
     */
    Optional<User> findByUsername(String username);
    
    /**
     * Find all users in the system
     * 
     * @return A list of all users
     */
    List<User> findAll();
    
    /**
     * Save a user to the repository
     * 
     * @param user The user to save
     * @return The saved user (possibly with an updated ID)
     */
    User save(User user);
    
    /**
     * Delete a user from the repository
     * 
     * @param id The ID of the user to delete
     */
    void deleteById(Long id);
}
