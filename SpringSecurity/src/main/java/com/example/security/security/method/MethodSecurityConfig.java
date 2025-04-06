package com.example.security.security.method;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * Configuration for method-level security.
 * This configuration enables security annotations on methods.
 */
@Configuration
@EnableGlobalMethodSecurity(
    prePostEnabled = true,    // Enables @PreAuthorize and @PostAuthorize
    securedEnabled = true,    // Enables @Secured
    jsr250Enabled = true      // Enables @RolesAllowed
)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    /**
     * Configure the expression handler for method security.
     * This can be customized to add custom security expressions.
     *
     * @return the configured MethodSecurityExpressionHandler
     */
    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        
        // You can customize the expression handler here
        // For example, set a permission evaluator:
        // expressionHandler.setPermissionEvaluator(new CustomPermissionEvaluator());
        
        return expressionHandler;
    }
}
