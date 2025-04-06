package com.example.application.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Example configuration that demonstrates various conditional annotations.
 */
@Configuration
public class ConditionalBeanExample {

    private static final Logger logger = LoggerFactory.getLogger(ConditionalBeanExample.class);

    /**
     * Bean that is only created if a specific class is on the classpath.
     * In this case, we're checking for the presence of the Environment class,
     * which is always available in a Spring application.
     */
    @Bean
    @ConditionalOnClass(Environment.class)
    public String classPathDependentBean() {
        logger.info("Creating bean that depends on a class being on the classpath");
        return "Class is available";
    }

    /**
     * Bean that is only created if a specific property is set.
     */
    @Bean
    @ConditionalOnProperty(prefix = "example", name = "property", havingValue = "enabled")
    public String propertyDependentBean() {
        logger.info("Creating bean that depends on a property being set");
        return "Property is set";
    }

    /**
     * Bean that is only created if a specific resource is available.
     */
    @Bean
    @ConditionalOnResource(resources = "classpath:example-resource.txt")
    public String resourceDependentBean() {
        logger.info("Creating bean that depends on a resource being available");
        return "Resource is available";
    }

    /**
     * Bean that is only created if no bean of the same type already exists.
     */
    @Bean
    @ConditionalOnMissingBean(name = "defaultStringBean")
    public String fallbackStringBean() {
        logger.info("Creating fallback bean because the default bean is missing");
        return "Fallback bean";
    }

    /**
     * Bean that will be created if the 'example.default-bean' property is set to true.
     * This bean has the name 'defaultStringBean', which the fallback bean depends on.
     */
    @Bean("defaultStringBean")
    @ConditionalOnProperty(prefix = "example", name = "default-bean", havingValue = "true")
    public String defaultStringBean() {
        logger.info("Creating default string bean");
        return "Default bean";
    }
}
