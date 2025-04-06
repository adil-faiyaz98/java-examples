package design.patterns.domaindrivendesign.domain.model.customer;

import design.patterns.domaindrivendesign.domain.model.shared.ValueObject;

/**
 * CustomerId Value Object
 *
 * Represents the unique identifier for a Customer entity.
 * This is a value object because it's immutable and has no identity of its own.
 *
 * Demonstrates OOP principles:
 * - Immutability: all fields are final
 * - Implementation of an interface: implements ValueObject
 * - Encapsulation: private fields with controlled access
 */
public final class CustomerId implements ValueObject {
    private final String id;

    public CustomerId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer ID cannot be empty");
        }
        this.id = id;
    }

    public String getValue() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CustomerId that = (CustomerId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return id;
    }
}
