package design.patterns.domaindrivendesign.domain.model.order;

import design.patterns.domaindrivendesign.domain.model.customer.Address;
import design.patterns.domaindrivendesign.domain.model.customer.CustomerId;
import design.patterns.domaindrivendesign.domain.model.shared.AggregateRoot;
import design.patterns.domaindrivendesign.domain.model.shared.Money;
import design.patterns.domaindrivendesign.domain.model.product.ProductId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Order Entity - Aggregate Root
 *
 * Represents an order in the system. This is an entity with a distinct identity
 * and is the aggregate root for the Order aggregate.
 *
 * Demonstrates OOP principles:
 * - Inheritance: extends AggregateRoot
 * - Encapsulation: private fields with public methods
 * - Polymorphism: different behavior based on order status
 * - State pattern: behavior changes based on order status
 */
public class Order extends AggregateRoot<OrderId> {
    // id is inherited from AggregateRoot
    private CustomerId customerId;
    private List<OrderItem> orderItems;
    private OrderStatus status;
    private Address shippingAddress;
    private LocalDateTime orderDate;
    private Money totalAmount;

    /**
     * Creates a new order for a customer
     */
    public Order(CustomerId customerId, Address shippingAddress) {
        this.id = new OrderId(UUID.randomUUID().toString());
        this.customerId = customerId;
        this.orderItems = new ArrayList<>();
        this.status = OrderStatus.CREATED;
        this.shippingAddress = shippingAddress;
        this.orderDate = LocalDateTime.now();
        this.totalAmount = Money.ZERO;

        // Validate invariants
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        if (shippingAddress == null) {
            throw new IllegalArgumentException("Shipping address cannot be null");
        }
    }

    /**
     * Reconstitutes an order from persistence
     */
    public Order(OrderId id, CustomerId customerId, List<OrderItem> orderItems,
                OrderStatus status, Address shippingAddress,
                LocalDateTime orderDate, Money totalAmount) {
        this.id = id;
        this.customerId = customerId;
        this.orderItems = new ArrayList<>(orderItems);
        this.status = status;
        this.shippingAddress = shippingAddress;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
    }

    /**
     * Adds an item to the order
     */
    public void addItem(ProductId productId, String productName, int quantity, Money unitPrice) {
        // Validate order state
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Cannot modify items for order with status: " + status);
        }

        // Validate parameters
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (unitPrice == null || unitPrice.isNegativeOrZero()) {
            throw new IllegalArgumentException("Unit price must be positive");
        }

        // Check if the product is already in the order
        for (OrderItem item : orderItems) {
            if (item.getProductId().equals(productId)) {
                // Update quantity instead of adding a new item
                item.updateQuantity(item.getQuantity() + quantity);
                recalculateTotalAmount();
                return;
            }
        }

        // Add new order item
        OrderItem newItem = new OrderItem(productId, productName, quantity, unitPrice);
        orderItems.add(newItem);

        // Recalculate total
        recalculateTotalAmount();
    }

    /**
     * Removes an item from the order
     */
    public void removeItem(ProductId productId) {
        // Validate order state
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Cannot modify items for order with status: " + status);
        }

        // Find and remove the item
        orderItems.removeIf(item -> item.getProductId().equals(productId));

        // Recalculate total
        recalculateTotalAmount();
    }

    /**
     * Updates the quantity of an item
     */
    public void updateItemQuantity(ProductId productId, int newQuantity) {
        // Validate order state
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Cannot modify items for order with status: " + status);
        }

        // Validate parameters
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        // Find and update the item
        for (OrderItem item : orderItems) {
            if (item.getProductId().equals(productId)) {
                item.updateQuantity(newQuantity);
                recalculateTotalAmount();
                return;
            }
        }

        throw new IllegalArgumentException("Product not found in order: " + productId);
    }

    /**
     * Places the order, changing its status to PLACED
     */
    public OrderPlacedEvent placeOrder() {
        // Validate order state
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Cannot place order with status: " + status);
        }

        // Validate order has items
        if (orderItems.isEmpty()) {
            throw new IllegalStateException("Cannot place an empty order");
        }

        // Update status
        status = OrderStatus.PLACED;

        // Create and return domain event
        return new OrderPlacedEvent(id, customerId, totalAmount, orderDate);
    }

    /**
     * Processes payment for the order
     */
    public void processPayment() {
        // Validate order state
        if (status != OrderStatus.PLACED) {
            throw new IllegalStateException("Cannot process payment for order with status: " + status);
        }

        // Update status
        status = OrderStatus.PAID;
    }

    /**
     * Ships the order
     */
    public void ship() {
        // Validate order state
        if (status != OrderStatus.PAID) {
            throw new IllegalStateException("Cannot ship order with status: " + status);
        }

        // Update status
        status = OrderStatus.SHIPPED;
    }

    /**
     * Delivers the order
     */
    public void deliver() {
        // Validate order state
        if (status != OrderStatus.SHIPPED) {
            throw new IllegalStateException("Cannot deliver order with status: " + status);
        }

        // Update status
        status = OrderStatus.DELIVERED;
    }

    /**
     * Cancels the order
     */
    public void cancel() {
        // Validate order state
        if (status == OrderStatus.SHIPPED || status == OrderStatus.DELIVERED || status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot cancel order with status: " + status);
        }

        // Update status
        status = OrderStatus.CANCELLED;
    }

    /**
     * Recalculates the total amount of the order
     */
    private void recalculateTotalAmount() {
        totalAmount = Money.ZERO;
        for (OrderItem item : orderItems) {
            totalAmount = totalAmount.add(item.getSubtotal());
        }
    }

    /**
     * Returns the order ID
     */
    public OrderId getId() {
        return id;
    }

    /**
     * Returns the customer ID
     */
    public CustomerId getCustomerId() {
        return customerId;
    }

    /**
     * Returns an unmodifiable list of order items
     */
    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(orderItems);
    }

    /**
     * Returns the order status
     */
    public OrderStatus getStatus() {
        return status;
    }

    /**
     * Returns the shipping address
     */
    public Address getShippingAddress() {
        return shippingAddress;
    }

    /**
     * Returns the order date
     */
    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    /**
     * Returns the total amount
     */
    public Money getTotalAmount() {
        return totalAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;
        return id.equals(order.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", status=" + status +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", items=" + orderItems.size() +
                '}';
    }
}
