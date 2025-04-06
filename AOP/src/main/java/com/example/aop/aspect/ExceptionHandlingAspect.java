package com.example.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Aspect for handling exceptions in controller methods.
 * 
 * This aspect demonstrates around advice for controller methods to provide
 * consistent exception handling and error responses.
 * 
 * The @Order annotation ensures this aspect runs before other aspects.
 */
@Aspect
@Component
@Order(1)
public class ExceptionHandlingAspect {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlingAspect.class);

    /**
     * Around advice for controller methods.
     * 
     * This advice catches exceptions thrown by controller methods and returns
     * appropriate error responses.
     */
    @Around("execution(* com.example.aop.controller.*.*(..))")
    public Object handleControllerExceptions(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // Proceed with the method execution
            return joinPoint.proceed();
        } catch (IllegalArgumentException e) {
            // Handle bad request exceptions
            return handleException(e, HttpStatus.BAD_REQUEST);
        } catch (SecurityException e) {
            // Handle security exceptions
            return handleException(e, HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            // Handle all other exceptions
            return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Helper method to create an error response.
     */
    private ResponseEntity<Map<String, Object>> handleException(Exception e, HttpStatus status) {
        // Get the current request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        
        // Log the exception
        logger.error("Exception in {} {}: {}", request.getMethod(), request.getRequestURI(), e.getMessage(), e);
        
        // Create an error response
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status.value());
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("message", e.getMessage());
        errorResponse.put("path", request.getRequestURI());
        
        return new ResponseEntity<>(errorResponse, status);
    }
}
