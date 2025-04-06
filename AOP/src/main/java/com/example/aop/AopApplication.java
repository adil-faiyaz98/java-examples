package com.example.aop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Main application class for AOP examples.
 * 
 * The @EnableAspectJAutoProxy annotation enables support for handling
 * components marked with @Aspect annotation.
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class AopApplication {

    public static void main(String[] args) {
        SpringApplication.run(AopApplication.class, args);
    }
}
