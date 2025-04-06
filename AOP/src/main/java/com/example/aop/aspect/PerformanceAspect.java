package com.example.aop.aspect;

import com.example.aop.annotation.LogExecutionTime;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for monitoring method execution time.
 * 
 * This aspect demonstrates around advice, which surrounds the method execution
 * and can control whether the method is executed and what value is returned.
 */
@Aspect
@Component
public class PerformanceAspect {

    private static final Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);

    /**
     * Around advice for methods annotated with @LogExecutionTime.
     * 
     * This advice measures and logs the execution time of the method.
     */
    @Around("@annotation(logExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint, LogExecutionTime logExecutionTime) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        // Proceed with the method execution
        Object result = joinPoint.proceed();
        
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("Method {} executed in {} ms", methodName, executionTime);
        
        return result;
    }

    /**
     * Around advice for all service methods.
     * 
     * This advice logs slow method executions (taking more than 100ms).
     */
    @Around("execution(* com.example.aop.service.*.*(..))")
    public Object logSlowServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        // Proceed with the method execution
        Object result = joinPoint.proceed();
        
        long endTime = System.currentTimeMillis();
        long executionTime = endTime - startTime;
        
        // Log only if execution time is greater than 100ms
        if (executionTime > 100) {
            String methodName = joinPoint.getSignature().toShortString();
            logger.warn("Slow service method detected: {} took {} ms", methodName, executionTime);
        }
        
        return result;
    }
}
