# Observer Design Pattern

## Intent
Define a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically.

## Problem
How do we establish relationships between objects where one object needs to notify many others about its changes, without making them tightly coupled?

Some scenarios where this is useful:
- A data source that needs to update multiple UI elements
- An event system where multiple handlers respond to the same event
- A publish-subscribe system where publishers don't need to know about subscribers

## Solution
The Observer pattern solves this by:
1. Defining a Subject interface that knows its observers and provides methods to add/remove them
2. Defining an Observer interface with an update method that gets called when the subject changes
3. Having concrete subjects notify all registered observers when their state changes
4. Having concrete observers register with subjects they're interested in

## Implementation Models

This example demonstrates two common implementation models:

1. **Push Model**
   - The subject pushes all the changed data to observers
   - Observers receive data whether they need it or not
   - Example: `WeatherStation` pushes temperature, humidity, and pressure to all displays

2. **Pull Model**
   - The subject simply notifies observers that something changed
   - Observers pull only the data they need from the subject
   - Example: `WeatherData` notifies displays, which then pull specific measurements

## Structure

1. **Subject Interface**
   - Defines methods to register, remove, and notify observers
   - Examples: `Subject` and `PullSubject`

2. **Concrete Subject**
   - Maintains a list of observers
   - Notifies them when its state changes
   - Examples: `WeatherStation` and `WeatherData`

3. **Observer Interface**
   - Defines an update method that subjects call
   - Examples: `Observer` and `PullObserver`

4. **Concrete Observer**
   - Implements the Observer interface
   - Registers with a subject to receive updates
   - Examples: `CurrentConditionsDisplay`, `StatisticsDisplay`, etc.

## Benefits

- **Loose Coupling**: Subjects and observers are loosely coupled
- **Open/Closed Principle**: Add new observers without modifying the subject
- **Dynamic Relationships**: Establish and change relationships at runtime
- **Broadcast Communication**: One-to-many notification

## When to Use

- When a change to one object requires changing others, and you don't know how many objects need to change
- When an object should be able to notify other objects without making assumptions about them
- When a framework needs to allow different parts to interact without knowing about each other

## Real-World Examples

- Java's `Observable` class and `Observer` interface (deprecated in Java 9)
- Event listeners in GUI frameworks
- Model-View-Controller (MVC) architecture
- Reactive programming libraries like RxJava
- Event handling systems
- Publish-subscribe messaging systems

## Java Implementation Notes

- Java's built-in Observable/Observer is now deprecated
- Consider using the `PropertyChangeListener` mechanism for simple cases
- For complex scenarios, implement your own observer pattern or use reactive libraries
- Be careful about memory leaks - observers should be properly unregistered when no longer needed
