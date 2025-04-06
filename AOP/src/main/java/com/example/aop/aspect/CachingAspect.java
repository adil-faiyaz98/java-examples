package com.example.aop.aspect;

import com.example.aop.annotation.Cacheable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Aspect for caching method results.
 * 
 * This aspect demonstrates around advice for methods annotated with @Cacheable.
 */
@Aspect
@Component
public class CachingAspect {

    private static final Logger logger = LoggerFactory.getLogger(CachingAspect.class);
    
    // Simple in-memory cache
    private final Map<String, CacheEntry> cache = new ConcurrentHashMap<>();

    /**
     * Around advice for methods annotated with @Cacheable.
     * 
     * This advice caches the method result and returns the cached value on subsequent calls.
     */
    @Around("@annotation(com.example.aop.annotation.Cacheable)")
    public Object cacheMethodResult(ProceedingJoinPoint joinPoint) throws Throwable {
        // Get the method signature
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        
        // Get the @Cacheable annotation
        Cacheable cacheable = method.getAnnotation(Cacheable.class);
        String key = generateCacheKey(joinPoint, cacheable);
        int ttlSeconds = cacheable.ttlSeconds();
        
        // Check if the result is in the cache and not expired
        if (cache.containsKey(key)) {
            CacheEntry entry = cache.get(key);
            if (!entry.isExpired()) {
                logger.info("Cache hit for key: {}", key);
                return entry.getValue();
            } else {
                logger.info("Cache entry expired for key: {}", key);
                cache.remove(key);
            }
        }
        
        // Execute the method and cache the result
        Object result = joinPoint.proceed();
        cache.put(key, new CacheEntry(result, ttlSeconds));
        logger.info("Cached result for key: {} with TTL: {} seconds", key, ttlSeconds);
        
        return result;
    }

    /**
     * Generate a cache key based on the method signature and arguments.
     */
    private String generateCacheKey(ProceedingJoinPoint joinPoint, Cacheable cacheable) {
        String customKey = cacheable.key();
        if (!customKey.isEmpty()) {
            return customKey;
        }
        
        // Generate a key based on the method signature and arguments
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getDeclaringTypeName() + "." + signature.getName();
        String args = Arrays.toString(joinPoint.getArgs());
        
        return methodName + ":" + args;
    }

    /**
     * Cache entry with expiration time.
     */
    private static class CacheEntry {
        private final Object value;
        private final long expirationTime;
        
        public CacheEntry(Object value, int ttlSeconds) {
            this.value = value;
            this.expirationTime = System.currentTimeMillis() + (ttlSeconds * 1000L);
        }
        
        public Object getValue() {
            return value;
        }
        
        public boolean isExpired() {
            return System.currentTimeMillis() > expirationTime;
        }
    }
}
