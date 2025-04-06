# Builder Design Pattern

## Intent
Separate the construction of a complex object from its representation, allowing the same construction process to create different representations.

## Problem
How do we create complex objects with many parameters, some optional, without:
- Creating telescoping constructors (multiple constructors with different parameter combinations)
- Using JavaBeans pattern (setters) which leaves the object in an inconsistent state during construction
- Creating complex, hard-to-read client code

## Solution
The Builder pattern solves this by:
1. Extracting the object construction code into a separate Builder class
2. Creating a step-by-step interface for constructing the object
3. Allowing the construction of different representations using the same process
4. Ensuring the object is only built when all required parameters are set

## Structure

This example demonstrates building computers with various configurations:

1. **Product**
   - `Computer` - The complex object being built

2. **Builder**
   - `Computer.Builder` - Interface with methods for setting each component

3. **Director** (Optional)
   - `ComputerDirector` - Creates pre-configured objects using the builder

4. **Client**
   - Uses the builder directly or works with a director

## Implementation Approaches

1. **Inner Static Builder** (Used in this example)
   - Builder is a static nested class inside the product
   - Provides good encapsulation and readability

2. **Separate Builder Hierarchy**
   - Used when building different product variants
   - Abstract builder with concrete implementations

3. **Fluent Interface / Method Chaining**
   - Each setter returns the builder itself
   - Allows for readable, chainable method calls

## Benefits

- **Readability**: Creates clear, readable object construction code
- **Flexibility**: Allows different representations from the same construction process
- **Immutability**: Can create immutable objects safely
- **Parameter Control**: Can enforce validation rules during construction
- **Step-by-Step Construction**: Builds objects piece by piece

## When to Use

- When object creation involves many parameters
- When many parameters are optional or have default values
- When you need immutable objects
- When you want to create different representations of an object
- When construction needs to be separated from representation

## Real-World Examples

- StringBuilder in Java
- DocumentBuilder in Java XML processing
- Retrofit.Builder for API clients
- AlertDialog.Builder in Android
- StringBuilder and StringBuffer in Java
