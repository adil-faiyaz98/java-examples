package com.example.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

/**
 * Configuration properties for the GreetingService.
 * These properties can be set in application.properties or application.yml.
 */
@ConfigurationProperties(prefix = "greeting.service")
@Validated
public class GreetingServiceProperties {

    /**
     * The greeting message to use. Default is "Hello".
     */
    @NotEmpty
    private String greeting = "Hello";

    /**
     * Whether to convert the greeting to uppercase. Default is false.
     */
    private boolean uppercase = false;

    /**
     * Whether the greeting service is enabled. Default is true.
     */
    private boolean enabled = true;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }

    public boolean isUppercase() {
        return uppercase;
    }

    public void setUppercase(boolean uppercase) {
        this.uppercase = uppercase;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
