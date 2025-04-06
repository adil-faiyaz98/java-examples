package Spring.repository;

import Spring.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * JdbcUserRepository
 * 
 * A simulated JDBC implementation of the UserRepository interface.
 * This is used to demonstrate how Spring can switch implementations based on profiles.
 * 
 * The @Profile annotation ensures this bean is only created when the "jdbc" profile is active.
 */
@Repository
@Profile("jdbc")
public class JdbcUserRepository implements UserRepository {
    
    /**
     * Constructor that simulates setting up a database connection
     */
    public JdbcUserRepository() {
        System.out.println("Initializing JDBC User Repository");
        System.out.println("Connecting to database...");
        // In a real application, this would set up connection pools, etc.
    }
    
    @Override
    public Optional<User> findById(Long id) {
        System.out.println("JDBC: Finding user by ID: " + id);
        // In a real application, this would execute a SQL query
        
        // Simulate finding a user
        if (id == 1L) {
            return Optional.of(new User(1L, "jdoe", "john.doe@example.com", "John", "Doe"));
        }
        
        return Optional.empty();
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        System.out.println("JDBC: Finding user by username: " + username);
        // In a real application, this would execute a SQL query
        
        // Simulate finding a user
        if ("jdoe".equals(username)) {
            return Optional.of(new User(1L, "jdoe", "john.doe@example.com", "John", "Doe"));
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<User> findAll() {
        System.out.println("JDBC: Finding all users");
        // In a real application, this would execute a SQL query
        
        // Simulate finding all users
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "jdoe", "john.doe@example.com", "John", "Doe"));
        users.add(new User(2L, "asmith", "alice.smith@example.com", "Alice", "Smith"));
        
        return users;
    }
    
    @Override
    public User save(User user) {
        System.out.println("JDBC: Saving user: " + user);
        // In a real application, this would execute a SQL query
        
        // Simulate saving a user
        if (user.getId() == null) {
            user.setId(System.currentTimeMillis());
        }
        
        return user;
    }
    
    @Override
    public void deleteById(Long id) {
        System.out.println("JDBC: Deleting user with ID: " + id);
        // In a real application, this would execute a SQL query
    }
}
