package com.example.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

/**
 * Configuration properties for the AdvancedGreetingService.
 * These properties can be set in application.properties or application.yml.
 */
@ConfigurationProperties(prefix = "greeting.service.advanced")
public class AdvancedGreetingServiceProperties {

    /**
     * The locale to use for greetings. Default is the system default.
     */
    private Locale locale = Locale.getDefault();

    /**
     * Whether to adjust greetings based on time of day. Default is true.
     */
    private boolean timeAware = true;

    /**
     * Whether the advanced greeting service is enabled. Default is true.
     */
    private boolean enabled = true;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public boolean isTimeAware() {
        return timeAware;
    }

    public void setTimeAware(boolean timeAware) {
        this.timeAware = timeAware;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
