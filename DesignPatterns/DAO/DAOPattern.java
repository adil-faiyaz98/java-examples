/**
 * Data Access Object (DAO) Design Pattern
 * 
 * Intent: Separate the data access logic from business logic by providing an abstract interface
 * to a database or other persistence mechanism.
 * 
 * This example demonstrates a DAO implementation for a User entity with in-memory storage.
 * In a real application, the DAO would connect to a database, file system, or external service.
 */
package design.patterns.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DAOPattern {
    
    public static void main(String[] args) {
        // Create a DAO
        UserDAO userDAO = new UserDAOImpl();
        
        // Create some users
        User user1 = new User(1, "John Doe", "john.doe@example.com", "password123");
        User user2 = new User(2, "Jane Smith", "jane.smith@example.com", "password456");
        User user3 = new User(3, "Bob Johnson", "bob.johnson@example.com", "password789");
        
        // Save users
        System.out.println("Saving users...");
        userDAO.save(user1);
        userDAO.save(user2);
        userDAO.save(user3);
        
        // Find a user by ID
        System.out.println("\nFinding user by ID (2):");
        Optional<User> foundUser = userDAO.findById(2);
        foundUser.ifPresent(System.out::println);
        
        // Find all users
        System.out.println("\nFinding all users:");
        List<User> allUsers = userDAO.findAll();
        allUsers.forEach(System.out::println);
        
        // Update a user
        System.out.println("\nUpdating user with ID 1:");
        User updatedUser = new User(1, "John Doe", "john.updated@example.com", "newpassword123");
        userDAO.update(updatedUser);
        
        // Verify the update
        System.out.println("\nVerifying update:");
        userDAO.findById(1).ifPresent(System.out::println);
        
        // Delete a user
        System.out.println("\nDeleting user with ID 3:");
        userDAO.delete(3);
        
        // Verify the deletion
        System.out.println("\nVerifying deletion (finding all users):");
        userDAO.findAll().forEach(System.out::println);
        
        // Try to find a deleted user
        System.out.println("\nTrying to find deleted user (ID 3):");
        Optional<User> deletedUser = userDAO.findById(3);
        if (deletedUser.isPresent()) {
            System.out.println(deletedUser.get());
        } else {
            System.out.println("User not found");
        }
        
        // Find user by email
        System.out.println("\nFinding user by email (jane.smith@example.com):");
        Optional<User> userByEmail = userDAO.findByEmail("jane.smith@example.com");
        userByEmail.ifPresent(System.out::println);
    }
}

/**
 * Model class representing a User entity
 */
class User {
    private int id;
    private String name;
    private String email;
    private String password;
    
    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    
    // Getters and setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='[PROTECTED]'" +
                '}';
    }
}

/**
 * DAO Interface for User entity
 * Defines the standard operations to be performed on the User model
 */
interface UserDAO {
    // CRUD operations
    void save(User user);
    Optional<User> findById(int id);
    List<User> findAll();
    void update(User user);
    void delete(int id);
    
    // Additional operations
    Optional<User> findByEmail(String email);
}

/**
 * Concrete DAO implementation for User entity using in-memory storage
 * In a real application, this would use JDBC, JPA, Hibernate, or another persistence mechanism
 */
class UserDAOImpl implements UserDAO {
    // In-memory database simulation
    private Map<Integer, User> users = new HashMap<>();
    
    @Override
    public void save(User user) {
        users.put(user.getId(), user);
        System.out.println("User saved: " + user.getName());
    }
    
    @Override
    public Optional<User> findById(int id) {
        return Optional.ofNullable(users.get(id));
    }
    
    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }
    
    @Override
    public void update(User user) {
        if (users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            System.out.println("User updated: " + user.getName());
        } else {
            System.out.println("Cannot update user. User not found with ID: " + user.getId());
        }
    }
    
    @Override
    public void delete(int id) {
        if (users.containsKey(id)) {
            User removedUser = users.remove(id);
            System.out.println("User deleted: " + removedUser.getName());
        } else {
            System.out.println("Cannot delete user. User not found with ID: " + id);
        }
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        return users.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }
}
