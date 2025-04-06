# Aspect-Oriented Programming (AOP) Examples

This directory contains examples demonstrating Aspect-Oriented Programming (AOP) concepts in Java using Spring AOP.

## What is AOP?

Aspect-Oriented Programming is a programming paradigm that aims to increase modularity by allowing the separation of cross-cutting concerns. It does so by adding additional behavior to existing code without modifying the code itself.

## Key Concepts

### Aspect
A modularization of a concern that cuts across multiple classes. Examples include transaction management, logging, security, etc.

### Join Point
A point during the execution of a program, such as the execution of a method or the handling of an exception.

### Advice
Action taken by an aspect at a particular join point. Different types of advice include "around," "before," and "after" advice.

### Pointcut
A predicate that matches join points. Advice is associated with a pointcut expression and runs at any join point matched by the pointcut.

### Introduction
Declaring additional methods or fields on behalf of a type.

### Target Object
Object being advised by one or more aspects. Also referred to as the advised object.

### AOP Proxy
An object created by the AOP framework in order to implement the aspect contracts. In Spring AOP, a JDK dynamic proxy or a CGLIB proxy.

### Weaving
Linking aspects with other application types or objects to create an advised object.

## Examples Included

1. **Basic AOP**
   - Method execution logging
   - Performance monitoring

2. **Advice Types**
   - Before advice
   - After returning advice
   - After throwing advice
   - After (finally) advice
   - Around advice

3. **Pointcut Expressions**
   - Method execution
   - Method parameter types
   - Method return types
   - Annotations
   - Bean names

4. **Custom Annotations**
   - Creating and using custom annotations for pointcuts

5. **Advanced AOP**
   - Exception handling
   - Caching
   - Security checks

## Project Structure

- `src/main/java/com/example/aop/`
  - `aspect/` - Contains all aspect classes
  - `annotation/` - Custom annotations for AOP
  - `service/` - Service classes that will be advised
  - `controller/` - REST controllers
  - `model/` - Domain model classes
  - `config/` - Configuration classes

## Running the Examples

To run the examples, use:

```bash
./mvnw spring-boot:run
```

Then access the endpoints at:
- http://localhost:8080/api/users
- http://localhost:8080/api/products
- http://localhost:8080/api/secured

## Dependencies

- Spring Boot 2.7.x
- Spring AOP
- AspectJ
- Lombok (for reducing boilerplate code)
