package design.patterns.domaindrivendesign.domain.model.order;

/**
 * OrderStatus Enumeration
 *
 * Represents the possible states of an order in its lifecycle.
 *
 * Demonstrates the OOP State pattern where behavior changes based on state.
 * Each status represents a different state in the order lifecycle.
 */
public enum OrderStatus {
    CREATED,    // Order has been created but not yet placed
    PLACED,     // Order has been placed but not yet paid
    PAID,       // Order has been paid but not yet shipped
    SHIPPED,    // Order has been shipped but not yet delivered
    DELIVERED,  // Order has been delivered
    CANCELLED   // Order has been cancelled
}
