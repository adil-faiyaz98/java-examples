# Object-Oriented Programming (OOP) Examples

This directory contains examples demonstrating the four main principles of Object-Oriented Programming:

1. **Encapsulation** - Bundling data and methods that operate on that data within a single unit (class)
2. **Inheritance** - Creating new classes that are built upon existing classes
3. **Polymorphism** - Using a single interface to represent different underlying forms (data types)
4. **Abstraction** - Hiding complex implementation details and showing only the necessary features

## Examples Overview

### 1. Shape Hierarchy
Demonstrates inheritance, polymorphism, and abstraction with geometric shapes.
- `Shape.java` - Abstract base class
- `Circle.java`, `Rectangle.java`, `Triangle.java` - Concrete implementations
- `ShapeDemo.java` - Usage example

### 2. Banking System
Demonstrates encapsulation and inheritance with a simple banking system.
- `Account.java` - Base class with encapsulated balance
- `SavingsAccount.java`, `CheckingAccount.java` - Specialized account types
- `BankDemo.java` - Usage example

### 3. Animal Kingdom
Demonstrates polymorphism and abstraction with an animal hierarchy.
- `Animal.java` - Abstract base class
- `Dog.java`, `Cat.java`, `Bird.java` - Concrete implementations
- `AnimalDemo.java` - Usage example

### 4. Vehicle System
Demonstrates interfaces and multiple inheritance concepts.
- `Vehicle.java` - Base interface
- `Drivable.java`, `Flyable.java` - Capability interfaces
- `Car.java`, `Airplane.java`, `AmphibiousVehicle.java` - Implementations
- `VehicleDemo.java` - Usage example

## Key OOP Concepts Demonstrated

### Encapsulation
- Private fields with public getters/setters
- Data validation within classes
- Information hiding

### Inheritance
- Base and derived classes
- Method overriding
- `super` keyword usage
- Extending functionality

### Polymorphism
- Method overriding
- Dynamic method dispatch
- Using base class references for derived objects
- Interfaces with multiple implementations

### Abstraction
- Abstract classes and methods
- Interfaces
- Hiding implementation details
- Focusing on essential features

## Running the Examples

Each example has a corresponding demo class with a `main` method. To run an example:

```
javac OOP/shapes/ShapeDemo.java
java OOP.shapes.ShapeDemo
```

Replace `shapes/ShapeDemo` with the path to the demo you want to run.
