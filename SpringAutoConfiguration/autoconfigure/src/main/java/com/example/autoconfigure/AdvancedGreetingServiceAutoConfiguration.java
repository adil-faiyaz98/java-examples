package com.example.autoconfigure;

import com.example.service.advanced.AdvancedGreetingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auto-configuration for the AdvancedGreetingService.
 * This configuration will be automatically applied when:
 * 1. The AdvancedGreetingService class is on the classpath
 * 2. No bean of type AdvancedGreetingService is already defined
 * 3. The property greeting.service.advanced.enabled is not set to false
 * 
 * This configuration is set to be applied after the GreetingServiceAutoConfiguration.
 */
@Configuration
@ConditionalOnClass(AdvancedGreetingService.class)
@EnableConfigurationProperties(AdvancedGreetingServiceProperties.class)
@ConditionalOnProperty(prefix = "greeting.service.advanced", name = "enabled", havingValue = "true", matchIfMissing = true)
@AutoConfigureAfter(GreetingServiceAutoConfiguration.class)
public class AdvancedGreetingServiceAutoConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(AdvancedGreetingServiceAutoConfiguration.class);

    private final AdvancedGreetingServiceProperties properties;

    public AdvancedGreetingServiceAutoConfiguration(AdvancedGreetingServiceProperties properties) {
        this.properties = properties;
    }

    /**
     * Creates an AdvancedGreetingService bean if one doesn't already exist.
     * 
     * @return a new AdvancedGreetingService instance
     */
    @Bean
    @ConditionalOnMissingBean
    public AdvancedGreetingService advancedGreetingService() {
        logger.info("Auto-configuring AdvancedGreetingService with locale: {}, timeAware: {}", 
                properties.getLocale(), properties.isTimeAware());
        
        return new AdvancedGreetingService(properties.getLocale(), properties.isTimeAware());
    }
}
