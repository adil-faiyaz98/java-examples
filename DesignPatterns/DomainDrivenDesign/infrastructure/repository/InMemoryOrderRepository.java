package design.patterns.domaindrivendesign.infrastructure.repository;

import design.patterns.domaindrivendesign.domain.model.customer.CustomerId;
import design.patterns.domaindrivendesign.domain.model.order.Order;
import design.patterns.domaindrivendesign.domain.model.order.OrderId;
import design.patterns.domaindrivendesign.domain.model.order.OrderStatus;
import design.patterns.domaindrivendesign.domain.repository.OrderRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * InMemoryOrderRepository
 * 
 * In-memory implementation of the OrderRepository interface.
 * In a real application, this would be replaced with a database implementation.
 */
public class InMemoryOrderRepository implements OrderRepository {
    private final Map<OrderId, Order> orders = new HashMap<>();
    
    @Override
    public Optional<Order> findById(OrderId id) {
        return Optional.ofNullable(orders.get(id));
    }
    
    @Override
    public List<Order> findByCustomerId(CustomerId customerId) {
        return orders.values().stream()
                .filter(order -> order.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return orders.values().stream()
                .filter(order -> order.getStatus() == status)
                .collect(Collectors.toList());
    }
    
    @Override
    public Order save(Order order) {
        orders.put(order.getId(), order);
        return order;
    }
    
    @Override
    public void remove(OrderId id) {
        orders.remove(id);
    }
}
