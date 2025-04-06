package com.example.aop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Aspect for logging method calls.
 * 
 * This aspect demonstrates various types of advice:
 * - Before advice: Executed before the method call
 * - After returning advice: Executed after the method returns successfully
 * - After throwing advice: Executed after the method throws an exception
 * - After (finally) advice: Executed after the method finishes (either successfully or with an exception)
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Pointcut for all methods in the service package.
     */
    @Pointcut("execution(* com.example.aop.service.*.*(..))")
    public void serviceLayer() {}

    /**
     * Pointcut for all methods in the controller package.
     */
    @Pointcut("execution(* com.example.aop.controller.*.*(..))")
    public void controllerLayer() {}

    /**
     * Before advice for service layer methods.
     * 
     * This advice logs method entry with parameters.
     */
    @Before("serviceLayer()")
    public void beforeServiceMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        logger.info("Entering service method: {} with arguments: {}", methodName, Arrays.toString(args));
    }

    /**
     * After returning advice for service layer methods.
     * 
     * This advice logs method exit with return value.
     */
    @AfterReturning(pointcut = "serviceLayer()", returning = "result")
    public void afterReturningServiceMethod(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("Service method {} completed successfully with result: {}", methodName, result);
    }

    /**
     * After throwing advice for service layer methods.
     * 
     * This advice logs method exceptions.
     */
    @AfterThrowing(pointcut = "serviceLayer()", throwing = "ex")
    public void afterThrowingServiceMethod(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.error("Service method {} threw an exception: {}", methodName, ex.getMessage());
    }

    /**
     * After (finally) advice for service layer methods.
     * 
     * This advice logs method completion regardless of outcome.
     */
    @After("serviceLayer()")
    public void afterServiceMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("Exiting service method: {}", methodName);
    }

    /**
     * Before advice for controller layer methods.
     * 
     * This advice logs API requests.
     */
    @Before("controllerLayer()")
    public void beforeControllerMethod(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        logger.info("API Request: {} with arguments: {}", methodName, Arrays.toString(args));
    }

    /**
     * After returning advice for controller layer methods.
     * 
     * This advice logs API responses.
     */
    @AfterReturning(pointcut = "controllerLayer()", returning = "result")
    public void afterReturningControllerMethod(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("API Response from {}: {}", methodName, result);
    }
}
