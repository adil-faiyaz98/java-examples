package design.patterns.domaindrivendesign.domain.model.customer;

import design.patterns.domaindrivendesign.domain.model.shared.DomainEvent;

import java.time.LocalDateTime;

/**
 * PaymentMethodAddedEvent Domain Event
 * 
 * Represents the event of a payment method being added to a customer.
 */
public class PaymentMethodAddedEvent implements DomainEvent {
    private final CustomerId customerId;
    private final PaymentMethod paymentMethod;
    private final LocalDateTime occurredOn;
    
    public PaymentMethodAddedEvent(CustomerId customerId, PaymentMethod paymentMethod) {
        this.customerId = customerId;
        this.paymentMethod = paymentMethod;
        this.occurredOn = LocalDateTime.now();
    }
    
    public CustomerId getCustomerId() {
        return customerId;
    }
    
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    
    @Override
    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }
    
    @Override
    public String toString() {
        return "PaymentMethodAddedEvent{" +
                "customerId=" + customerId +
                ", paymentMethod=" + paymentMethod +
                ", occurredOn=" + occurredOn +
                '}';
    }
}
