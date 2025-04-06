# Decorator Design Pattern

## Intent
Attach additional responsibilities to an object dynamically. Decorators provide a flexible alternative to subclassing for extending functionality.

## Problem
How do we add behavior or state to individual objects at run-time without affecting other objects of the same class?

Some challenges the Decorator pattern addresses:
- Adding responsibilities to objects without subclassing
- Implementing feature-rich systems that don't rely on complex inheritance hierarchies
- Supporting the addition and removal of responsibilities at runtime
- Combining multiple behaviors in a flexible way

## Solution
The Decorator pattern solves this by:
1. Creating a component interface or abstract class that defines the core behavior
2. Implementing concrete components that provide the basic functionality
3. Creating a decorator abstract class that implements the component interface and contains a reference to a component
4. Implementing concrete decorators that add specific responsibilities

## Structure

1. **Component**
   - Defines the interface for objects that can have responsibilities added to them
   - Example: `Beverage` abstract class

2. **Concrete Component**
   - Defines an object to which additional responsibilities can be attached
   - Examples: `Espresso`, `DarkRoast`, `HouseBlend` classes

3. **Decorator**
   - Maintains a reference to a Component object and conforms to Component's interface
   - Example: `CondimentDecorator` abstract class

4. **Concrete Decorator**
   - Adds responsibilities to the component
   - Examples: `Mocha`, `Soy`, `Whip` classes

## Benefits

- **Open/Closed Principle**: Classes are open for extension but closed for modification
- **Single Responsibility Principle**: Responsibilities are divided among classes
- **Flexibility**: Responsibilities can be added and removed at runtime
- **Composition over Inheritance**: Uses object composition rather than inheritance
- **Granularity**: Allows for a more fine-grained approach to adding features

## When to Use

- When you need to add responsibilities to objects dynamically and transparently
- When extension by subclassing is impractical or impossible
- When you want to add and remove responsibilities at runtime
- When you want to avoid a feature-laden class hierarchy at the top of your design

## Real-World Examples

- Java I/O classes (FileInputStream, BufferedInputStream, etc.)
- UI component libraries with scrollable, bordered, or resizable components
- Middleware in web frameworks
- Caching, logging, or transaction management in enterprise applications
- Graphics rendering pipelines

## Implementation Considerations

- **Interface Conformance**: Ensure decorators conform to the component interface
- **Transparency**: Clients should be unaware they're dealing with a decorator
- **Lightweight Decorators**: Keep decorators simple and focused on a single responsibility
- **Order of Decoration**: Consider whether the order of decoration matters
- **Consistency**: Ensure consistent behavior regardless of decoration

## Related Patterns

- **Composite Pattern**: Decorators are similar to Composite but have different intent
- **Strategy Pattern**: Decorators change the skin of an object, strategies change the guts
- **Adapter Pattern**: Adapters change an interface, decorators enhance responsibilities
- **Chain of Responsibility**: Both can be used to add behavior to objects
