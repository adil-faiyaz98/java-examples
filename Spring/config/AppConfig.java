package Spring.config;

import Spring.repository.UserRepository;
import Spring.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * AppConfig
 * 
 * Spring Java-based configuration class.
 * This demonstrates how to configure Spring beans using Java code.
 * 
 * The @ComponentScan annotation tells Spring where to look for components
 * (classes annotated with @Component, @Service, @Repository, etc.)
 */
@Configuration
@ComponentScan(basePackages = {"Spring.repository", "Spring.service"})
public class AppConfig {
    
    /**
     * Example of a @Bean method that could be used instead of component scanning.
     * This is commented out because we're using component scanning in this example.
     * 
     * @Bean methods are useful when:
     * - You need to configure third-party classes that you can't annotate
     * - You need complex initialization logic
     * - You need to choose between different implementations conditionally
     */
    /*
    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserServiceImpl(userRepository);
    }
    */
    
    /**
     * Example of a @Bean method that creates a simple string bean.
     * This demonstrates how to create and configure beans programmatically.
     */
    @Bean
    public String applicationName() {
        return "Spring Dependency Injection Demo";
    }
    
    /**
     * Example of a @Bean method that depends on another bean.
     * This demonstrates how to inject dependencies into @Bean methods.
     */
    @Bean
    public String welcomeMessage(String applicationName) {
        return "Welcome to " + applicationName + "!";
    }
}
