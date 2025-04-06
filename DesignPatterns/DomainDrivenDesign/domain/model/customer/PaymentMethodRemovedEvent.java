package design.patterns.domaindrivendesign.domain.model.customer;

import design.patterns.domaindrivendesign.domain.model.shared.DomainEvent;

import java.time.LocalDateTime;

/**
 * PaymentMethodRemovedEvent Domain Event
 * 
 * Represents the event of a payment method being removed from a customer.
 */
public class PaymentMethodRemovedEvent implements DomainEvent {
    private final CustomerId customerId;
    private final PaymentMethod paymentMethod;
    private final LocalDateTime occurredOn;
    
    public PaymentMethodRemovedEvent(CustomerId customerId, PaymentMethod paymentMethod) {
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
        return "PaymentMethodRemovedEvent{" +
                "customerId=" + customerId +
                ", paymentMethod=" + paymentMethod +
                ", occurredOn=" + occurredOn +
                '}';
    }
}
