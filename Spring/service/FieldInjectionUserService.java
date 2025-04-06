package Spring.service;

import Spring.model.User;
import Spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * FieldInjectionUserService
 * 
 * Implementation of the UserService interface that demonstrates field injection.
 * This approach is simpler to write but less recommended due to testability issues.
 * 
 * The @Service annotation marks this as a Spring component that
 * can be discovered during component scanning and injected where needed.
 */
@Service("fieldInjectionUserService") // Named bean for demonstration
public class FieldInjectionUserService implements UserService {
    
    /**
     * Field injection - Spring will inject the UserRepository directly into this field
     * 
     * This approach:
     * - Is simpler to write (less boilerplate)
     * - But makes testing harder (no way to inject dependencies without Spring)
     * - Makes dependencies less explicit
     * - Doesn't work with final fields
     */
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Default constructor
     */
    public FieldInjectionUserService() {
        System.out.println("FieldInjectionUserService created with default constructor");
        System.out.println("Note: userRepository is null here, will be injected after construction");
    }
    
    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @Override
    public User createUser(User user) {
        // Ensure the user doesn't have an ID (it should be assigned by the repository)
        user.setId(null);
        return userRepository.save(user);
    }
    
    @Override
    public User updateUser(User user) {
        // Ensure the user exists
        if (user.getId() == null || !userRepository.findById(user.getId()).isPresent()) {
            throw new IllegalArgumentException("Cannot update non-existent user");
        }
        
        return userRepository.save(user);
    }
    
    @Override
    public void deleteUser(Long id) {
        // Ensure the user exists
        if (!userRepository.findById(id).isPresent()) {
            throw new IllegalArgumentException("Cannot delete non-existent user");
        }
        
        userRepository.deleteById(id);
    }
}
