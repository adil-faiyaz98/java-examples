package Spring.app;

import Spring.config.AppConfig;
import Spring.model.User;
import Spring.service.FieldInjectionUserService;
import Spring.service.SetterInjectionUserService;
import Spring.service.UserNotificationService;
import Spring.service.UserService;
import Spring.service.UserServiceImpl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * DependencyInjectionDemo
 * 
 * Main application class that demonstrates Spring dependency injection.
 */
public class DependencyInjectionDemo {
    
    public static void main(String[] args) {
        // Create the Spring application context using Java-based configuration
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        
        // Display welcome message
        System.out.println("\n=== Welcome ===");
        System.out.println(context.getBean("welcomeMessage"));
        
        System.out.println("\n=== Constructor Injection Demo ===");
        // Get the UserService bean (constructor injection)
        UserService userService = context.getBean(UserServiceImpl.class);
        
        // Use the service to get all users
        System.out.println("All users:");
        userService.getAllUsers().forEach(System.out::println);
        
        // Create a new user
        System.out.println("\nCreating a new user:");
        User newUser = new User(null, "mwilson", "mark.wilson@example.com", "Mark", "Wilson");
        userService.createUser(newUser);
        
        // Get the user by username
        System.out.println("\nFinding user by username 'mwilson':");
        userService.getUserByUsername("mwilson").ifPresent(System.out::println);
        
        System.out.println("\n=== Setter Injection Demo ===");
        // Get the SetterInjectionUserService bean
        SetterInjectionUserService setterService = context.getBean(SetterInjectionUserService.class);
        
        // Use the service to get all users
        System.out.println("All users from setter-injected service:");
        setterService.getAllUsers().forEach(System.out::println);
        
        System.out.println("\n=== Field Injection Demo ===");
        // Get the FieldInjectionUserService bean
        FieldInjectionUserService fieldService = context.getBean(FieldInjectionUserService.class);
        
        // Use the service to get all users
        System.out.println("All users from field-injected service:");
        fieldService.getAllUsers().forEach(System.out::println);
        
        System.out.println("\n=== Qualifier Injection Demo ===");
        // Get the UserNotificationService bean
        UserNotificationService notificationService = context.getBean(UserNotificationService.class);
        
        // Get a user to notify
        User userToNotify = userService.getUserByUsername("jdoe").orElseThrow(
                () -> new RuntimeException("User not found"));
        
        // Send notifications using different methods
        System.out.println("\nSending notifications:");
        notificationService.notifyUser(userToNotify, "This is a primary notification");
        notificationService.notifyUserBySms(userToNotify, "This is an SMS notification");
        notificationService.notifyUserByAllMethods(userToNotify, "This is sent by all methods");
        
        System.out.println("\n=== Demo Complete ===");
    }
}
