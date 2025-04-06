# Proxy Design Pattern

## Intent
Provide a surrogate or placeholder for another object to control access to it.

## Problem
How do we control access to an object? How can we add functionality when accessing an object without changing the object itself?

Some scenarios where this is useful:
- Controlling access to sensitive operations
- Lazy initialization of expensive objects
- Adding logging or metrics when accessing objects
- Caching results of expensive operations
- Remote resource access

## Solution
The Proxy pattern solves this by:
1. Creating a new proxy class that implements the same interface as the real subject
2. Maintaining a reference to the real subject
3. Controlling access to the real subject and possibly adding functionality
4. Being transparent to clients, which work with both the proxy and real subject through the same interface

## Types of Proxies

This example demonstrates three common types of proxies:

1. **Protection Proxy**
   - Controls access to the original object based on access rights
   - Example: Document access control based on user permissions

2. **Virtual Proxy**
   - Delays the creation of expensive objects until they're actually needed
   - Example: Loading high-resolution images only when they need to be displayed

3. **Caching Proxy**
   - Caches results of expensive operations for reuse
   - Example: Caching database query results

Other types include:
- **Remote Proxy**: Represents an object in a different address space
- **Smart Reference**: Performs additional actions when an object is accessed
- **Logging Proxy**: Logs access to the service object

## Structure

1. **Subject Interface**
   - Defines the common interface for RealSubject and Proxy
   - Ensures the proxy can be used in place of the real subject

2. **RealSubject**
   - The real object that the proxy represents
   - Implements the Subject interface

3. **Proxy**
   - Maintains a reference to the RealSubject
   - Implements the Subject interface
   - Controls access to the RealSubject
   - May be responsible for creating and managing the RealSubject

## Benefits

- **Controlled Access**: Control access to the original object
- **Separation of Concerns**: Add functionality without modifying the original object
- **Open/Closed Principle**: Extend behavior without changing existing code
- **Lazy Initialization**: Create expensive objects only when needed
- **Transparency**: Clients work with both proxies and real subjects through the same interface

## When to Use

- When you need to control access to an object
- When you need lazy initialization of expensive objects
- When you need to add functionality when accessing an object
- When you need to implement caching
- When you need to manage the lifecycle of the real subject

## Real-World Examples

- Java's dynamic proxies for AOP (Aspect-Oriented Programming)
- Hibernate's lazy loading of entities
- Spring's @Transactional annotation using proxies
- Web service proxies
- JDBC connection pools
