package design.patterns.domaindrivendesign.domain.service;

import design.patterns.domaindrivendesign.domain.model.customer.Customer;
import design.patterns.domaindrivendesign.domain.model.customer.PaymentMethod;
import design.patterns.domaindrivendesign.domain.model.order.Order;
import design.patterns.domaindrivendesign.domain.model.order.OrderId;
import design.patterns.domaindrivendesign.domain.model.order.OrderPlacedEvent;
import design.patterns.domaindrivendesign.domain.model.shared.DomainEventPublisher;

/**
 * OrderProcessingService Domain Service
 * 
 * Encapsulates domain logic that doesn't naturally fit into entities or value objects.
 * This service handles the processing of orders, including payment processing and
 * coordination with other domain objects.
 */
public class OrderProcessingService {
    private final DomainEventPublisher eventPublisher;
    
    public OrderProcessingService(DomainEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    
    /**
     * Places an order and publishes the OrderPlacedEvent
     */
    public OrderId placeOrder(Order order, Customer customer) {
        // Validate customer has a payment method
        if (customer.getDefaultPaymentMethod() == null) {
            throw new IllegalStateException("Customer has no payment method");
        }
        
        // Validate customer has a shipping address
        if (customer.getShippingAddress() == null) {
            throw new IllegalStateException("Customer has no shipping address");
        }
        
        // Place the order
        OrderPlacedEvent event = order.placeOrder();
        
        // Publish the event
        eventPublisher.publish(event);
        
        return order.getId();
    }
    
    /**
     * Processes payment for an order
     */
    public void processPayment(Order order, PaymentMethod paymentMethod) {
        // In a real application, this would integrate with a payment gateway
        System.out.println("Processing payment for order " + order.getId() + " using " + paymentMethod);
        
        // Update order status
        order.processPayment();
    }
    
    /**
     * Ships an order
     */
    public void shipOrder(Order order) {
        // In a real application, this would integrate with a shipping service
        System.out.println("Shipping order " + order.getId() + " to " + order.getShippingAddress());
        
        // Update order status
        order.ship();
    }
    
    /**
     * Delivers an order
     */
    public void deliverOrder(Order order) {
        // In a real application, this would update delivery status
        System.out.println("Marking order " + order.getId() + " as delivered");
        
        // Update order status
        order.deliver();
    }
    
    /**
     * Cancels an order
     */
    public void cancelOrder(Order order) {
        // In a real application, this might involve refund processing
        System.out.println("Cancelling order " + order.getId());
        
        // Update order status
        order.cancel();
    }
}
