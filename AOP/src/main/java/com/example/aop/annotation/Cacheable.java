package com.example.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation to mark methods for caching.
 * 
 * This annotation can be applied to methods to cache their results.
 * It will be used as a pointcut in the CachingAspect.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cacheable {
    String key() default "";
    int ttlSeconds() default 300; // 5 minutes by default
}
