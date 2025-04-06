package Spring.app;

import Spring.config.AppConfig;
import Spring.model.User;
import Spring.repository.UserRepository;
import Spring.service.UserService;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * ProfilesDemo
 * 
 * Demonstrates the use of Spring profiles to switch between different implementations.
 */
public class ProfilesDemo {
    
    public static void main(String[] args) {
        // Create the Spring application context with the "default" profile
        System.out.println("\n=== Running with default profile ===");
        try (AnnotationConfigApplicationContext defaultContext = new AnnotationConfigApplicationContext()) {
            defaultContext.register(AppConfig.class);
            defaultContext.refresh();
            
            // Get the repository and service beans
            UserRepository defaultRepo = defaultContext.getBean(UserRepository.class);
            UserService defaultService = defaultContext.getBean(UserService.class);
            
            System.out.println("Repository type: " + defaultRepo.getClass().getSimpleName());
            
            // Use the service to get all users
            System.out.println("\nAll users:");
            defaultService.getAllUsers().forEach(System.out::println);
        }
        
        // Create the Spring application context with the "jdbc" profile
        System.out.println("\n=== Running with jdbc profile ===");
        try (AnnotationConfigApplicationContext jdbcContext = new AnnotationConfigApplicationContext()) {
            // Set the active profile before refreshing the context
            jdbcContext.getEnvironment().setActiveProfiles("jdbc");
            jdbcContext.register(AppConfig.class);
            jdbcContext.refresh();
            
            // Get the repository and service beans
            UserRepository jdbcRepo = jdbcContext.getBean(UserRepository.class);
            UserService jdbcService = jdbcContext.getBean(UserService.class);
            
            System.out.println("Repository type: " + jdbcRepo.getClass().getSimpleName());
            
            // Use the service to get all users
            System.out.println("\nAll users:");
            jdbcService.getAllUsers().forEach(System.out::println);
            
            // Create a new user
            System.out.println("\nCreating a new user:");
            User newUser = new User(null, "jdbcuser", "jdbc@example.com", "JDBC", "User");
            User createdUser = jdbcService.createUser(newUser);
            System.out.println("Created: " + createdUser);
        }
    }
}
