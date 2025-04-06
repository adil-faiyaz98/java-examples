package design.patterns.domaindrivendesign.domain.model.order;

import design.patterns.domaindrivendesign.domain.model.customer.CustomerId;
import design.patterns.domaindrivendesign.domain.model.shared.DomainEvent;
import design.patterns.domaindrivendesign.domain.model.shared.Money;

import java.time.LocalDateTime;

/**
 * OrderPlacedEvent Domain Event
 * 
 * Represents the event of an order being placed. This is a domain event
 * that can be used for communication between bounded contexts.
 */
public class OrderPlacedEvent implements DomainEvent {
    private final OrderId orderId;
    private final CustomerId customerId;
    private final Money totalAmount;
    private final LocalDateTime occurredOn;
    
    public OrderPlacedEvent(OrderId orderId, CustomerId customerId, Money totalAmount, LocalDateTime occurredOn) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.totalAmount = totalAmount;
        this.occurredOn = occurredOn;
    }
    
    public OrderId getOrderId() {
        return orderId;
    }
    
    public CustomerId getCustomerId() {
        return customerId;
    }
    
    public Money getTotalAmount() {
        return totalAmount;
    }
    
    @Override
    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }
    
    @Override
    public String toString() {
        return "OrderPlacedEvent{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", totalAmount=" + totalAmount +
                ", occurredOn=" + occurredOn +
                '}';
    }
}
