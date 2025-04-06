package com.example.application;

import com.example.service.GreetingService;
import com.example.service.advanced.AdvancedGreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Main Spring Boot application that demonstrates auto-configuration.
 * 
 * This application doesn't explicitly configure any beans for GreetingService
 * or AdvancedGreetingService, but they will be available through auto-configuration.
 */
@SpringBootApplication
public class AutoConfigurationDemoApplication {

    private static final Logger logger = LoggerFactory.getLogger(AutoConfigurationDemoApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AutoConfigurationDemoApplication.class, args);
    }

    /**
     * Demonstrates the auto-configured beans by using them after application startup.
     * 
     * @param greetingService the auto-configured GreetingService
     * @param advancedGreetingService the auto-configured AdvancedGreetingService
     * @return a CommandLineRunner that uses the services
     */
    @Bean
    public CommandLineRunner demoAutoConfiguration(GreetingService greetingService,
                                                  AdvancedGreetingService advancedGreetingService) {
        return args -> {
            logger.info("=== Auto-Configuration Demo ===");
            
            // Use the basic greeting service
            logger.info("Basic greeting service configuration:");
            logger.info("Greeting: {}", greetingService.getGreeting());
            logger.info("Uppercase: {}", greetingService.isUppercase());
            logger.info("Result: {}", greetingService.greet("World"));
            
            // Use the advanced greeting service
            logger.info("\nAdvanced greeting service configuration:");
            logger.info("Locale: {}", advancedGreetingService.getLocale());
            logger.info("Time-aware: {}", advancedGreetingService.isTimeAware());
            logger.info("Result: {}", advancedGreetingService.greet("World"));
            
            logger.info("=== End of Auto-Configuration Demo ===");
        };
    }
}
