# Strategy Design Pattern

## Intent
Define a family of algorithms, encapsulate each one, and make them interchangeable. Strategy lets the algorithm vary independently from clients that use it.

## Problem
How do we design a system where different algorithms or behaviors can be selected at runtime without hardcoding them into client classes?

Some scenarios where this is useful:
- Different payment methods in a checkout process
- Various sorting algorithms for different data types
- Multiple validation strategies for different types of input
- Different compression algorithms for various file types

## Solution
The Strategy pattern solves this by:
1. Defining a family of algorithms (strategies)
2. Encapsulating each algorithm in a separate class
3. Making the strategies interchangeable by having them implement a common interface
4. Allowing the client to select and use different strategies at runtime

## Structure

1. **Strategy Interface**
   - Declares an interface common to all supported algorithms
   - Example: `PaymentStrategy`

2. **Concrete Strategies**
   - Implement the algorithm using the Strategy interface
   - Examples: `CreditCardStrategy`, `PayPalStrategy`, `BitcoinStrategy`

3. **Context**
   - Maintains a reference to a Strategy object
   - May define an interface that lets Strategy access its data
   - Example: `ShoppingCart`

4. **Client**
   - Creates and configures the Context with the desired Strategy
   - Example: The main method in `StrategyPattern`

## Benefits

- **Encapsulation**: Each algorithm is encapsulated in its own class
- **Flexibility**: Algorithms can be switched at runtime
- **Isolation**: Changes to an algorithm don't affect the client
- **Elimination of Conditional Statements**: Replaces complex conditionals with strategy objects
- **Open/Closed Principle**: New strategies can be added without modifying existing code

## When to Use

- When you want to define a family of algorithms
- When you need to select an algorithm at runtime
- When you have multiple variants of an algorithm
- When an algorithm uses data that clients shouldn't know about
- When a class has many behaviors that appear as conditional statements

## Real-World Examples

- Payment processing systems with multiple payment methods
- Compression utilities that support different algorithms
- Navigation apps that offer different routing strategies
- Sorting libraries with various sorting algorithms
- Authentication systems with multiple authentication methods

## Comparison with Related Patterns

- **Template Method**: Defines the skeleton of an algorithm, with some steps deferred to subclasses. Strategy is more flexible but requires more code.
- **Command**: Encapsulates a request as an object. Strategy focuses on interchangeable algorithms.
- **State**: Similar to Strategy, but State allows an object to change its behavior when its internal state changes.
- **Decorator**: Adds responsibilities to objects. Strategy changes the guts of the object.

## Implementation Notes

- Consider making Strategy objects stateless so they can be shared
- Use composition over inheritance to include the Strategy in the Context
- Consider using lambda expressions for simple strategies in languages that support them
