package Spring.service;

import Spring.model.User;
import Spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * SetterInjectionUserService
 * 
 * Implementation of the UserService interface that demonstrates setter injection.
 * This approach is useful for optional dependencies.
 * 
 * The @Service annotation marks this as a Spring component that
 * can be discovered during component scanning and injected where needed.
 */
@Service("setterInjectionUserService") // Named bean for demonstration
public class SetterInjectionUserService implements UserService {
    
    // The repository dependency - not final since it's set via a setter
    private UserRepository userRepository;
    
    /**
     * Default constructor
     */
    public SetterInjectionUserService() {
        System.out.println("SetterInjectionUserService created with default constructor");
    }
    
    /**
     * Setter injection - Spring will call this method to inject the UserRepository
     * 
     * This approach is useful for:
     * - Optional dependencies
     * - Reconfiguring dependencies after object creation
     * - Breaking circular dependencies
     */
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        System.out.println("Setter injection performed on SetterInjectionUserService");
        this.userRepository = userRepository;
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
