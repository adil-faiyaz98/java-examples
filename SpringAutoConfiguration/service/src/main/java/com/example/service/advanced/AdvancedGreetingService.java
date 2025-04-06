package com.example.service.advanced;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.Locale;

/**
 * An advanced greeting service that provides time-aware and locale-aware greetings.
 * This service will be conditionally auto-configured.
 */
public class AdvancedGreetingService {

    private static final Logger logger = LoggerFactory.getLogger(AdvancedGreetingService.class);
    
    private final Locale locale;
    private final boolean timeAware;
    
    /**
     * Creates a new AdvancedGreetingService with the specified locale and time awareness.
     * 
     * @param locale the locale to use for greetings
     * @param timeAware whether to adjust greetings based on time of day
     */
    public AdvancedGreetingService(Locale locale, boolean timeAware) {
        this.locale = locale;
        this.timeAware = timeAware;
        logger.info("AdvancedGreetingService created with locale: {}, timeAware: {}", locale, timeAware);
    }
    
    /**
     * Greets the specified name with a locale and time-aware greeting.
     * 
     * @param name the name to greet
     * @return the greeting message
     */
    public String greet(String name) {
        String greeting = getGreetingByLocaleAndTime();
        return greeting + ", " + name + "!";
    }
    
    /**
     * Returns a greeting appropriate for the configured locale and current time.
     * 
     * @return the greeting
     */
    private String getGreetingByLocaleAndTime() {
        if (!timeAware) {
            return getDefaultGreeting();
        }
        
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        
        if (hour < 12) {
            return getMorningGreeting();
        } else if (hour < 18) {
            return getAfternoonGreeting();
        } else {
            return getEveningGreeting();
        }
    }
    
    /**
     * Returns the default greeting for the configured locale.
     * 
     * @return the default greeting
     */
    private String getDefaultGreeting() {
        String language = locale.getLanguage();
        
        switch (language) {
            case "es":
                return "Hola";
            case "fr":
                return "Bonjour";
            case "de":
                return "Hallo";
            case "it":
                return "Ciao";
            case "ja":
                return "こんにちは";
            default:
                return "Hello";
        }
    }
    
    /**
     * Returns a morning greeting for the configured locale.
     * 
     * @return the morning greeting
     */
    private String getMorningGreeting() {
        String language = locale.getLanguage();
        
        switch (language) {
            case "es":
                return "Buenos días";
            case "fr":
                return "Bonjour";
            case "de":
                return "Guten Morgen";
            case "it":
                return "Buongiorno";
            case "ja":
                return "おはようございます";
            default:
                return "Good morning";
        }
    }
    
    /**
     * Returns an afternoon greeting for the configured locale.
     * 
     * @return the afternoon greeting
     */
    private String getAfternoonGreeting() {
        String language = locale.getLanguage();
        
        switch (language) {
            case "es":
                return "Buenas tardes";
            case "fr":
                return "Bon après-midi";
            case "de":
                return "Guten Tag";
            case "it":
                return "Buon pomeriggio";
            case "ja":
                return "こんにちは";
            default:
                return "Good afternoon";
        }
    }
    
    /**
     * Returns an evening greeting for the configured locale.
     * 
     * @return the evening greeting
     */
    private String getEveningGreeting() {
        String language = locale.getLanguage();
        
        switch (language) {
            case "es":
                return "Buenas noches";
            case "fr":
                return "Bonsoir";
            case "de":
                return "Guten Abend";
            case "it":
                return "Buonasera";
            case "ja":
                return "こんばんは";
            default:
                return "Good evening";
        }
    }
    
    /**
     * Returns the configured locale.
     * 
     * @return the locale
     */
    public Locale getLocale() {
        return locale;
    }
    
    /**
     * Returns whether the service is time-aware.
     * 
     * @return true if time-aware, false otherwise
     */
    public boolean isTimeAware() {
        return timeAware;
    }
}
