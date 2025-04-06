package com.example.aop.aspect;

import com.example.aop.annotation.RequiresRole;
import com.example.aop.service.SecurityService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Aspect for enforcing security constraints.
 * 
 * This aspect demonstrates before advice for methods annotated with @RequiresRole.
 */
@Aspect
@Component
public class SecurityAspect {

    private static final Logger logger = LoggerFactory.getLogger(SecurityAspect.class);
    
    private final SecurityService securityService;
    
    @Autowired
    public SecurityAspect(SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * Before advice for methods annotated with @RequiresRole.
     * 
     * This advice checks if the current user has the required role before allowing the method to execute.
     */
    @Before("@annotation(com.example.aop.annotation.RequiresRole)")
    public void checkRole(JoinPoint joinPoint) {
        // Get the method signature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        
        // Get the @RequiresRole annotation
        RequiresRole requiresRole = method.getAnnotation(RequiresRole.class);
        String[] roles = requiresRole.value();
        
        // Check if the user has any of the required roles
        boolean hasRequiredRole = false;
        for (String role : roles) {
            if (securityService.hasRole(role)) {
                hasRequiredRole = true;
                break;
            }
        }
        
        // If the user doesn't have any of the required roles, throw an exception
        if (!hasRequiredRole) {
            logger.error("Access denied: User does not have any of the required roles: {}", Arrays.toString(roles));
            throw new SecurityException("Access denied: Required roles: " + Arrays.toString(roles));
        }
        
        logger.info("Access granted: User has required role for method {}", method.getName());
    }

    /**
     * Before advice for classes annotated with @RequiresRole.
     * 
     * This advice checks if the current user has the required role before allowing any method in the class to execute.
     */
    @Before("execution(* (@com.example.aop.annotation.RequiresRole *).*(..))")
    public void checkClassRole(JoinPoint joinPoint) {
        // Skip methods that are already annotated with @RequiresRole
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.isAnnotationPresent(RequiresRole.class)) {
            return;
        }
        
        // Get the class
        Class<?> targetClass = joinPoint.getTarget().getClass();
        
        // Get the @RequiresRole annotation from the class
        RequiresRole requiresRole = targetClass.getAnnotation(RequiresRole.class);
        String[] roles = requiresRole.value();
        
        // Check if the user has any of the required roles
        boolean hasRequiredRole = false;
        for (String role : roles) {
            if (securityService.hasRole(role)) {
                hasRequiredRole = true;
                break;
            }
        }
        
        // If the user doesn't have any of the required roles, throw an exception
        if (!hasRequiredRole) {
            logger.error("Access denied: User does not have any of the required roles: {}", Arrays.toString(roles));
            throw new SecurityException("Access denied: Required roles: " + Arrays.toString(roles));
        }
        
        logger.info("Access granted: User has required role for class method {}", method.getName());
    }
}
