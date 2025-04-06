package design.patterns.domaindrivendesign.domain.model.shared;

/**
 * Value Object Interface
 * 
 * Defines the contract for all value objects in the domain model.
 * Demonstrates the OOP principle of abstraction by defining a common interface
 * for all value objects.
 */
public interface ValueObject {
    /**
     * Value objects must implement equals and hashCode based on their attributes
     */
    @Override
    boolean equals(Object o);
    
    @Override
    int hashCode();
}
