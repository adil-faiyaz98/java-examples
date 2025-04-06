package Spring.service;

import Spring.model.User;
import Spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * UserServiceImpl
 * 
 * Implementation of the UserService interface that demonstrates constructor injection.
 * This is the recommended approach for required dependencies in Spring.
 * 
 * The @Service annotation marks this as a Spring component that
 * can be discovered during component scanning and injected where needed.
 */
@Service
public class UserServiceImpl implements UserService {
    
    // The repository dependency
    private final UserRepository userRepository;
    
    /**
     * Constructor injection - Spring will automatically inject the UserRepository
     * 
     * This is the recommended approach for required dependencies.
     * - Makes dependencies explicit
     * - Allows for final fields (immutability)
     * - Ensures the dependency is available when the object is constructed
     */
    @Autowired // Optional in newer Spring versions if there's only one constructor
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        System.out.println("UserServiceImpl created with constructor injection");
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
