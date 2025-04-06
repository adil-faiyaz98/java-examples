package com.example.autoconfigure;

import com.example.service.GreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auto-configuration for the GreetingService.
 * This configuration will be automatically applied when:
 * 1. The GreetingService class is on the classpath
 * 2. No bean of type GreetingService is already defined
 * 3. The property greeting.service.enabled is not set to false
 */
@Configuration
@ConditionalOnClass(GreetingService.class)
@EnableConfigurationProperties(GreetingServiceProperties.class)
@ConditionalOnProperty(prefix = "greeting.service", name = "enabled", havingValue = "true", matchIfMissing = true)
public class GreetingServiceAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(GreetingServiceAutoConfiguration.class);

    private final GreetingServiceProperties properties;

    public GreetingServiceAutoConfiguration(GreetingServiceProperties properties) {
        this.properties = properties;
    }

    /**
     * Creates a GreetingService bean if one doesn't already exist.
     * 
     * @return a new GreetingService instance
     */
    @Bean
    @ConditionalOnMissingBean
    public GreetingService greetingService() {
        logger.info("Auto-configuring GreetingService with greeting: '{}', uppercase: {}", 
                properties.getGreeting(), properties.isUppercase());
        
        return new GreetingService(properties.getGreeting(), properties.isUppercase());
    }
}
