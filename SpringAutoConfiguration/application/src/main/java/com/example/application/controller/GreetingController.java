package com.example.application.controller;

import com.example.service.GreetingService;
import com.example.service.advanced.AdvancedGreetingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that uses the auto-configured services.
 */
@RestController
@RequestMapping("/api/greetings")
public class GreetingController {

    private final GreetingService greetingService;
    private final AdvancedGreetingService advancedGreetingService;

    /**
     * Constructor that receives the auto-configured services.
     * 
     * @param greetingService the auto-configured GreetingService
     * @param advancedGreetingService the auto-configured AdvancedGreetingService
     */
    public GreetingController(GreetingService greetingService, AdvancedGreetingService advancedGreetingService) {
        this.greetingService = greetingService;
        this.advancedGreetingService = advancedGreetingService;
    }

    /**
     * Endpoint that uses the basic greeting service.
     * 
     * @param name the name to greet
     * @return the greeting message
     */
    @GetMapping("/basic/{name}")
    public String basicGreeting(@PathVariable String name) {
        return greetingService.greet(name);
    }

    /**
     * Endpoint that uses the advanced greeting service.
     * 
     * @param name the name to greet
     * @return the greeting message
     */
    @GetMapping("/advanced/{name}")
    public String advancedGreeting(@PathVariable String name) {
        return advancedGreetingService.greet(name);
    }

    /**
     * Endpoint that returns information about the configured services.
     * 
     * @return information about the services
     */
    @GetMapping("/info")
    public String serviceInfo() {
        StringBuilder info = new StringBuilder();
        
        info.append("Basic Greeting Service:\n");
        info.append("- Greeting: ").append(greetingService.getGreeting()).append("\n");
        info.append("- Uppercase: ").append(greetingService.isUppercase()).append("\n\n");
        
        info.append("Advanced Greeting Service:\n");
        info.append("- Locale: ").append(advancedGreetingService.getLocale()).append("\n");
        info.append("- Time-aware: ").append(advancedGreetingService.isTimeAware());
        
        return info.toString();
    }
}
