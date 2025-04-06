# Spring Dependency Injection Example

This directory contains examples demonstrating Dependency Injection (DI) using the Spring Framework. The examples showcase different types of dependency injection and Spring configuration approaches.

## What is Dependency Injection?

Dependency Injection is a design pattern that implements Inversion of Control (IoC) for resolving dependencies. Instead of having components create or find their dependencies, the dependencies are "injected" into the component from the outside.

### Benefits of Dependency Injection

1. **Reduced coupling** between classes and their dependencies
2. **Improved testability** by allowing mock implementations to be injected
3. **Greater modularity** and reusability of code
4. **Cleaner, more maintainable code** with clear separation of concerns
5. **Easier configuration changes** without modifying code

## Types of Dependency Injection Demonstrated

### 1. Constructor Injection
- Dependencies are provided through a class constructor
- Recommended approach in Spring applications
- Ensures all required dependencies are available at object creation
- Supports immutability (final fields)

### 2. Setter Injection
- Dependencies are provided through setter methods
- Useful for optional dependencies
- Allows for reconfiguration after object creation

### 3. Field Injection
- Dependencies are injected directly into fields
- Uses reflection to inject dependencies
- Simplest to write but harder to test and less explicit

## Spring Configuration Approaches

### 1. Java-based Configuration
- Uses `@Configuration` classes with `@Bean` methods
- Type-safe and refactoring-friendly
- Allows for programmatic bean creation logic

### 2. Annotation-based Configuration
- Uses annotations like `@Component`, `@Service`, `@Repository`
- Component scanning with `@ComponentScan`
- Autowiring with `@Autowired`

### 3. XML-based Configuration (Legacy)
- Traditional Spring configuration approach
- Useful for legacy applications

## Project Structure

- `model/` - Domain model classes
- `repository/` - Data access layer interfaces and implementations
- `service/` - Business logic layer interfaces and implementations
- `config/` - Spring configuration classes
- `app/` - Application entry point and demo classes

## Key Spring Annotations Used

- `@Component`, `@Service`, `@Repository` - Stereotype annotations for component scanning
- `@Autowired` - Marks a dependency to be injected
- `@Configuration` - Marks a class as a source of bean definitions
- `@Bean` - Declares a bean to be managed by Spring
- `@Primary` - Marks a bean as the primary choice when multiple candidates exist
- `@Qualifier` - Specifies which bean to inject when multiple candidates exist
- `@Profile` - Conditionally enables beans based on active profiles

## Running the Examples

Each example has a corresponding demo class with a `main` method. To run an example:

```
javac -cp path/to/spring-jars Spring/app/DependencyInjectionDemo.java
java -cp path/to/spring-jars:. Spring.app.DependencyInjectionDemo
```

## Dependencies

This example requires the following Spring dependencies:
- spring-core
- spring-context
- spring-beans
