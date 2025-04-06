package design.patterns.domaindrivendesign.domain.model.order;

/**
 * OrderId Value Object
 * 
 * Represents the unique identifier for an Order entity.
 * This is a value object because it's immutable and has no identity of its own.
 */
public final class OrderId {
    private final String id;
    
    public OrderId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be empty");
        }
        this.id = id;
    }
    
    public String getValue() {
        return id;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        OrderId orderId = (OrderId) o;
        return id.equals(orderId.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    @Override
    public String toString() {
        return id;
    }
}
