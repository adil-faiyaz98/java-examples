package Spring.app;

import Spring.model.User;
import Spring.repository.UserRepository;
import Spring.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * JavaConfigDemo
 * 
 * Demonstrates pure Java-based configuration without component scanning.
 * This shows how to manually configure beans and their dependencies.
 */
public class JavaConfigDemo {
    
    /**
     * Inner configuration class
     */
    @Configuration
    public static class ManualConfig {
        
        /**
         * Create a user repository bean
         */
        @Bean
        public UserRepository userRepository() {
            // Create a custom implementation of UserRepository
            return new CustomUserRepository();
        }
        
        /**
         * Create a user service bean that depends on the user repository
         */
        @Bean
        public UserService userService(UserRepository userRepository) {
            // Create a custom implementation of UserService
            return new CustomUserService(userRepository);
        }
        
        /**
         * Create a string bean
         */
        @Bean
        public String appName() {
            return "Java Config Demo";
        }
    }
    
    /**
     * Custom UserRepository implementation for demonstration
     */
    private static class CustomUserRepository implements UserRepository {
        @Override
        public java.util.Optional<User> findById(Long id) {
            System.out.println("CustomUserRepository: findById");
            return java.util.Optional.of(new User(id, "custom", "custom@example.com", "Custom", "User"));
        }
        
        @Override
        public java.util.Optional<User> findByUsername(String username) {
            System.out.println("CustomUserRepository: findByUsername");
            return java.util.Optional.of(new User(1L, username, "custom@example.com", "Custom", "User"));
        }
        
        @Override
        public java.util.List<User> findAll() {
            System.out.println("CustomUserRepository: findAll");
            java.util.List<User> users = new java.util.ArrayList<>();
            users.add(new User(1L, "custom1", "custom1@example.com", "Custom", "User1"));
            users.add(new User(2L, "custom2", "custom2@example.com", "Custom", "User2"));
            return users;
        }
        
        @Override
        public User save(User user) {
            System.out.println("CustomUserRepository: save");
            if (user.getId() == null) {
                user.setId(System.currentTimeMillis());
            }
            return user;
        }
        
        @Override
        public void deleteById(Long id) {
            System.out.println("CustomUserRepository: deleteById");
        }
    }
    
    /**
     * Custom UserService implementation for demonstration
     */
    private static class CustomUserService implements UserService {
        private final UserRepository userRepository;
        
        public CustomUserService(UserRepository userRepository) {
            this.userRepository = userRepository;
            System.out.println("CustomUserService created with repository: " + userRepository.getClass().getSimpleName());
        }
        
        @Override
        public java.util.Optional<User> getUserById(Long id) {
            return userRepository.findById(id);
        }
        
        @Override
        public java.util.Optional<User> getUserByUsername(String username) {
            return userRepository.findByUsername(username);
        }
        
        @Override
        public java.util.List<User> getAllUsers() {
            return userRepository.findAll();
        }
        
        @Override
        public User createUser(User user) {
            return userRepository.save(user);
        }
        
        @Override
        public User updateUser(User user) {
            return userRepository.save(user);
        }
        
        @Override
        public void deleteUser(Long id) {
            userRepository.deleteById(id);
        }
    }
    
    /**
     * Main method
     */
    public static void main(String[] args) {
        // Create the Spring application context using the inner configuration class
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ManualConfig.class);
        
        // Get the app name bean
        String appName = context.getBean("appName", String.class);
        System.out.println("\n=== " + appName + " ===");
        
        // Get the user service bean
        UserService userService = context.getBean(UserService.class);
        
        // Use the service to get all users
        System.out.println("\nAll users:");
        userService.getAllUsers().forEach(System.out::println);
        
        // Create a new user
        System.out.println("\nCreating a new user:");
        User newUser = new User(null, "javaconfig", "javaconfig@example.com", "Java", "Config");
        User createdUser = userService.createUser(newUser);
        System.out.println("Created: " + createdUser);
        
        // Get the user by ID
        System.out.println("\nFinding user by ID:");
        userService.getUserById(createdUser.getId()).ifPresent(System.out::println);
        
        // Close the context
        context.close();
    }
}
