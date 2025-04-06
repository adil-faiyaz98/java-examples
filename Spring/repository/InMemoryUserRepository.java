package Spring.repository;

import Spring.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * InMemoryUserRepository
 * 
 * An in-memory implementation of the UserRepository interface.
 * The @Repository annotation marks this as a Spring component that
 * can be discovered during component scanning and injected where needed.
 */
@Repository
public class InMemoryUserRepository implements UserRepository {
    
    // In-memory storage for users
    private final Map<Long, User> users = new HashMap<>();
    
    // ID generator
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    /**
     * Constructor that initializes the repository with some sample data
     */
    public InMemoryUserRepository() {
        // Add some sample users
        save(new User(null, "jdoe", "john.doe@example.com", "John", "Doe"));
        save(new User(null, "asmith", "alice.smith@example.com", "Alice", "Smith"));
        save(new User(null, "bjohnson", "bob.johnson@example.com", "Bob", "Johnson"));
    }
    
    @Override
    public Optional<User> findById(Long id) {
        return Optional.ofNullable(users.get(id));
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        return users.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }
    
    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
    
    @Override
    public User save(User user) {
        // If the user doesn't have an ID, generate one
        if (user.getId() == null) {
            user.setId(idGenerator.getAndIncrement());
        }
        
        // Save the user to the map
        users.put(user.getId(), user);
        
        return user;
    }
    
    @Override
    public void deleteById(Long id) {
        users.remove(id);
    }
}
