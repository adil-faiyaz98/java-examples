package design.patterns.domaindrivendesign.domain.model.customer;

import design.patterns.domaindrivendesign.domain.model.shared.DomainEvent;

import java.time.LocalDateTime;

/**
 * CustomerCreatedEvent Domain Event
 * 
 * Represents the event of a customer being created.
 * Demonstrates the OOP principle of encapsulation by containing all event data.
 */
public class CustomerCreatedEvent implements DomainEvent {
    private final CustomerId customerId;
    private final Email email;
    private final String name;
    private final LocalDateTime occurredOn;
    
    public CustomerCreatedEvent(CustomerId customerId, Email email, String name) {
        this.customerId = customerId;
        this.email = email;
        this.name = name;
        this.occurredOn = LocalDateTime.now();
    }
    
    public CustomerId getCustomerId() {
        return customerId;
    }
    
    public Email getEmail() {
        return email;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }
    
    @Override
    public String toString() {
        return "CustomerCreatedEvent{" +
                "customerId=" + customerId +
                ", email=" + email +
                ", name='" + name + '\'' +
                ", occurredOn=" + occurredOn +
                '}';
    }
}
