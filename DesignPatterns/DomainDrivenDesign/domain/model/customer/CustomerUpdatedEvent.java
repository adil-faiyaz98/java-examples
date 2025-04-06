package design.patterns.domaindrivendesign.domain.model.customer;

import design.patterns.domaindrivendesign.domain.model.shared.DomainEvent;

import java.time.LocalDateTime;

/**
 * CustomerUpdatedEvent Domain Event
 * 
 * Represents the event of a customer being updated.
 */
public class CustomerUpdatedEvent implements DomainEvent {
    private final CustomerId customerId;
    private final String fieldName;
    private final String newValue;
    private final LocalDateTime occurredOn;
    
    public CustomerUpdatedEvent(CustomerId customerId, String fieldName, String newValue) {
        this.customerId = customerId;
        this.fieldName = fieldName;
        this.newValue = newValue;
        this.occurredOn = LocalDateTime.now();
    }
    
    public CustomerId getCustomerId() {
        return customerId;
    }
    
    public String getFieldName() {
        return fieldName;
    }
    
    public String getNewValue() {
        return newValue;
    }
    
    @Override
    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }
    
    @Override
    public String toString() {
        return "CustomerUpdatedEvent{" +
                "customerId=" + customerId +
                ", fieldName='" + fieldName + '\'' +
                ", newValue='" + newValue + '\'' +
                ", occurredOn=" + occurredOn +
                '}';
    }
}
