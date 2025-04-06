package design.patterns.domaindrivendesign.domain.repository;

import design.patterns.domaindrivendesign.domain.model.customer.CustomerId;
import design.patterns.domaindrivendesign.domain.model.order.Order;
import design.patterns.domaindrivendesign.domain.model.order.OrderId;
import design.patterns.domaindrivendesign.domain.model.order.OrderStatus;

import java.util.List;
import java.util.Optional;

/**
 * OrderRepository Interface
 * 
 * Repository for the Order aggregate. Provides methods to find, save, and remove orders.
 */
public interface OrderRepository {
    /**
     * Finds an order by ID
     */
    Optional<Order> findById(OrderId id);
    
    /**
     * Finds orders by customer ID
     */
    List<Order> findByCustomerId(CustomerId customerId);
    
    /**
     * Finds orders by status
     */
    List<Order> findByStatus(OrderStatus status);
    
    /**
     * Saves an order (creates or updates)
     */
    Order save(Order order);
    
    /**
     * Removes an order
     */
    void remove(OrderId id);
}
