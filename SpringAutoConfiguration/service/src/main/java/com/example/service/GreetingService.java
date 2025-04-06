package com.example.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple service that provides greeting functionality.
 * This service will be auto-configured by our custom auto-configuration.
 */
public class GreetingService {

    private static final Logger logger = LoggerFactory.getLogger(GreetingService.class);
    
    private final String greeting;
    private final boolean uppercase;
    
    /**
     * Creates a new GreetingService with the specified greeting and case preference.
     * 
     * @param greeting the greeting message to use
     * @param uppercase whether to convert the greeting to uppercase
     */
    public GreetingService(String greeting, boolean uppercase) {
        this.greeting = greeting;
        this.uppercase = uppercase;
        logger.info("GreetingService created with greeting: '{}', uppercase: {}", greeting, uppercase);
    }
    
    /**
     * Greets the specified name.
     * 
     * @param name the name to greet
     * @return the greeting message
     */
    public String greet(String name) {
        String message = greeting + ", " + name + "!";
        return uppercase ? message.toUpperCase() : message;
    }
    
    /**
     * Returns the configured greeting.
     * 
     * @return the greeting
     */
    public String getGreeting() {
        return greeting;
    }
    
    /**
     * Returns whether the greeting is configured to be uppercase.
     * 
     * @return true if uppercase, false otherwise
     */
    public boolean isUppercase() {
        return uppercase;
    }
}
