# Spring Security Examples

This directory contains examples demonstrating various features of Spring Security, including authentication, authorization, and security configurations.

## Overview

Spring Security is a powerful and highly customizable authentication and access-control framework. It is the de-facto standard for securing Spring-based applications.

## Examples Included

1. **Basic Authentication**
   - Form-based login
   - HTTP Basic authentication
   - Remember-me functionality

2. **Authorization**
   - Role-based access control
   - Method-level security
   - URL-based security

3. **Custom Authentication**
   - Custom UserDetailsService
   - Custom authentication providers
   - Custom authentication filters

4. **OAuth2 and JWT**
   - JWT token-based authentication
   - OAuth2 client configuration
   - Resource server configuration

5. **Security Configurations**
   - WebSecurityConfigurerAdapter (legacy approach)
   - SecurityFilterChain (modern approach)
   - CORS and CSRF protection

## Project Structure

- `config/` - Security configuration classes
- `controller/` - REST controllers for testing security
- `model/` - Domain model classes
- `repository/` - Data access layer
- `service/` - Service layer including security services
- `filter/` - Custom security filters
- `util/` - Utility classes for security

## Key Concepts

### Authentication

Authentication is the process of verifying who a user is. Spring Security provides several authentication mechanisms:

- **Form Login**: Traditional username/password form
- **HTTP Basic**: Authentication via HTTP headers
- **OAuth2/OpenID Connect**: Authentication via external identity providers
- **JWT**: JSON Web Token-based authentication
- **Remember-Me**: Persistent login sessions

### Authorization

Authorization is the process of determining if a user has permission to access a resource or perform an action:

- **Role-Based Access Control (RBAC)**: Permissions based on roles
- **Method Security**: Securing methods with annotations
- **URL Security**: Securing URLs with patterns
- **Expression-Based Access Control**: Using SpEL expressions

### Security Filters

Spring Security uses a chain of servlet filters to apply security. Key filters include:

- **UsernamePasswordAuthenticationFilter**: Processes form login
- **BasicAuthenticationFilter**: Processes HTTP Basic authentication
- **JwtAuthenticationFilter**: Processes JWT tokens
- **FilterSecurityInterceptor**: Makes access control decisions

## Running the Examples

To run the examples, use:

```bash
./mvnw spring-boot:run
```

## Testing the Security

The examples include various endpoints to test different security features:

- `/api/public`: Accessible to all users
- `/api/user`: Accessible to authenticated users
- `/api/admin`: Accessible to users with ADMIN role
- `/api/auth/login`: Authentication endpoint
- `/api/auth/token`: JWT token endpoint

## Dependencies

- Spring Boot 2.7.0+
- Spring Security 5.7.0+
- Java 8+
