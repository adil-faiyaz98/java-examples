package com.example.aop.aspect;

import com.example.aop.annotation.Auditable;
import com.example.aop.service.SecurityService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * Aspect for auditing method calls.
 * 
 * This aspect demonstrates after returning advice for methods annotated with @Auditable.
 */
@Aspect
@Component
public class AuditAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuditAspect.class);
    
    private final SecurityService securityService;
    
    @Autowired
    public AuditAspect(SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * After returning advice for methods annotated with @Auditable.
     * 
     * This advice logs audit information after the method returns successfully.
     */
    @AfterReturning(pointcut = "@annotation(com.example.aop.annotation.Auditable)", returning = "result")
    public void auditMethodCall(JoinPoint joinPoint, Object result) {
        // Get the method signature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        
        // Get the @Auditable annotation
        Auditable auditable = method.getAnnotation(Auditable.class);
        String auditAction = auditable.value().isEmpty() ? method.getName() : auditable.value();
        
        // Get the current username
        String username = securityService.getCurrentUsername();
        
        // Log the audit information
        logger.info("AUDIT: User '{}' performed action '{}' at {} with parameters {} and result {}",
                username, auditAction, LocalDateTime.now(), Arrays.toString(joinPoint.getArgs()), result);
    }

    /**
     * After returning advice for classes annotated with @Auditable.
     * 
     * This advice logs audit information for all methods in annotated classes.
     */
    @AfterReturning(pointcut = "execution(* (@com.example.aop.annotation.Auditable *).*(..))", returning = "result")
    public void auditClassMethodCall(JoinPoint joinPoint, Object result) {
        // Skip methods that are already annotated with @Auditable
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (method.isAnnotationPresent(Auditable.class)) {
            return;
        }
        
        // Get the class
        Class<?> targetClass = joinPoint.getTarget().getClass();
        
        // Get the @Auditable annotation from the class
        Auditable auditable = targetClass.getAnnotation(Auditable.class);
        String auditAction = auditable.value() + "." + method.getName();
        
        // Get the current username
        String username = securityService.getCurrentUsername();
        
        // Log the audit information
        logger.info("AUDIT: User '{}' performed action '{}' at {} with parameters {}",
                username, auditAction, LocalDateTime.now(), Arrays.toString(joinPoint.getArgs()));
    }
}
