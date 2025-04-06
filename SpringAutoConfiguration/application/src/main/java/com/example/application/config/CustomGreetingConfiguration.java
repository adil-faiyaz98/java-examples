package com.example.application.config;

import com.example.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Custom configuration that demonstrates how to override auto-configuration.
 * 
 * This configuration will only be active when the property 'custom.greeting.enabled' is set to true.
 * When active, it will provide its own GreetingService bean, which will take precedence over
 * the auto-configured one due to the @ConditionalOnMissingBean annotation in the auto-configuration.
 */
@Configuration
@ConditionalOnProperty(prefix = "custom.greeting", name = "enabled", havingValue = "true")
public class CustomGreetingConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(CustomGreetingConfiguration.class);

    /**
     * Creates a custom GreetingService bean that will override the auto-configured one.
     * 
     * @return a custom GreetingService
     */
    @Bean
    public GreetingService customGreetingService() {
        logger.info("Creating custom GreetingService bean (overriding auto-configuration)");
        return new GreetingService("Custom greeting", true);
    }
}
