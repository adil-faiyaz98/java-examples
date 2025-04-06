# Adapter Design Pattern

## Intent
Convert the interface of a class into another interface clients expect. Adapter lets classes work together that couldn't otherwise because of incompatible interfaces.

## Problem
How do we make classes with incompatible interfaces work together?

Some scenarios where the Adapter pattern is useful:
- Integrating a new component with an existing system
- Using a third-party library with an interface that doesn't match your application
- Converting data between different formats
- Making legacy code work with modern code
- Providing a consistent interface to similar components with different interfaces

## Solution
The Adapter pattern solves this by:
1. Implementing the interface that clients expect (target interface)
2. Wrapping an instance of the class with the incompatible interface (adaptee)
3. Translating calls from the target interface to the adaptee interface
4. Allowing clients to work with the adapter using the target interface

## Types of Adapters

This example demonstrates two types of adapters:

1. **Object Adapter**
   - Uses composition to adapt one interface to another
   - More flexible as it can adapt multiple adaptees
   - Example: `MediaAdapter` adapting `AdvancedMediaPlayer` to `MediaPlayer`

2. **Class Adapter**
   - Uses inheritance to adapt one interface to another
   - Limited by single inheritance in languages like Java
   - Example: `JSONDataAdapter` adapting `XMLData` to `JSONData`

## Structure

1. **Target Interface**
   - Defines the domain-specific interface that the client uses
   - Examples: `MediaPlayer`, `JSONData`

2. **Adaptee**
   - Defines an existing interface that needs adapting
   - Examples: `AdvancedMediaPlayer`, `XMLData`

3. **Adapter**
   - Implements the target interface
   - Translates calls to the target interface into calls to the adaptee interface
   - Examples: `MediaAdapter`, `JSONDataAdapter`

4. **Client**
   - Collaborates with objects conforming to the target interface
   - Example: The main method in `AdapterPattern`

## Benefits

- **Integration**: Allows classes with incompatible interfaces to work together
- **Reusability**: Enables reuse of existing classes with incompatible interfaces
- **Flexibility**: Adapters can add functionality while adapting interfaces
- **Separation of Concerns**: Keeps adaptation logic separate from business logic
- **Open/Closed Principle**: Allows adding new adapters without changing existing code

## When to Use

- When you want to use an existing class but its interface doesn't match what you need
- When you want to create a reusable class that cooperates with classes that don't have compatible interfaces
- When you need to use several existing subclasses but it's impractical to adapt their interface by subclassing each one

## Real-World Examples

- Java's Arrays.asList() adapts an array to a List
- Java's InputStreamReader adapts InputStream to Reader
- Database drivers that adapt a standard interface to different database systems
- UI component libraries that adapt between different frameworks
- Legacy system wrappers that provide modern interfaces

## Related Patterns

- **Bridge Pattern**: Both patterns have similar structures, but different intents. Bridge is designed up-front to let abstractions and implementations vary independently, while Adapter is retrofitted to make unrelated classes work together.
- **Decorator Pattern**: Adds responsibilities to objects without subclassing, while Adapter changes an interface.
- **Proxy Pattern**: Provides a surrogate for another object, while Adapter provides a different interface.
- **Facade Pattern**: Provides a simplified interface to a subsystem, while Adapter converts an interface to match what clients expect.
