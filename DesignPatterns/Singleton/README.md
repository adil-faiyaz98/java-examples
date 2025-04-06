# Singleton Design Pattern

## Intent
Ensure a class has only one instance and provide a global point of access to it.

## Problem
Sometimes we need to have exactly one instance of a class that is accessible from multiple parts of an application. For example:
- Database connection pools
- Configuration managers
- Thread pools
- Caches
- Logging systems

## Solution
The Singleton pattern solves this by:
1. Making the constructor private to prevent direct instantiation
2. Creating a static method that returns the single instance
3. Ensuring thread-safety when creating the instance

## Implementation Approaches

This example demonstrates three different Singleton implementations:

1. **Classic Singleton** (`ClassicSingleton`)
   - Uses double-checked locking for thread safety
   - Protects against reflection attacks
   - Handles serialization correctly

2. **Enum Singleton** (`EnumSingleton`)
   - The most concise and effective approach in Java
   - Thread-safe by default
   - Serialization handled by JVM
   - Reflection-proof

3. **Initialization-on-demand Holder** (`LazyHolderSingleton`)
   - Uses a static inner class for lazy initialization
   - Thread-safe without synchronization overhead
   - Clean and efficient

## Best Practices

- Use the Enum approach when possible (simplest and most robust)
- Ensure thread safety in multi-threaded environments
- Protect against reflection attacks
- Handle serialization correctly
- Consider using dependency injection as an alternative

## When to Use

- When exactly one instance of a class is needed
- When the instance should be accessible globally
- When lazy initialization is beneficial

## When to Avoid

- When the single instance is not necessary
- When global state is problematic
- When testing becomes difficult due to shared state
