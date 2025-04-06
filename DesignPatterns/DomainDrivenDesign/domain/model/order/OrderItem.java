package design.patterns.domaindrivendesign.domain.model.order;

import design.patterns.domaindrivendesign.domain.model.product.ProductId;
import design.patterns.domaindrivendesign.domain.model.shared.Money;

/**
 * OrderItem Entity
 * 
 * Represents an item within an order. This is an entity because it has
 * an identity within the Order aggregate (though not a global identity).
 */
public class OrderItem {
    private ProductId productId;
    private String productName;
    private int quantity;
    private Money unitPrice;
    
    /**
     * Creates a new order item
     */
    public OrderItem(ProductId productId, String productName, int quantity, Money unitPrice) {
        if (productId == null) {
            throw new IllegalArgumentException("Product ID cannot be null");
        }
        if (productName == null || productName.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (unitPrice == null || unitPrice.isNegativeOrZero()) {
            throw new IllegalArgumentException("Unit price must be positive");
        }
        
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }
    
    /**
     * Updates the quantity of the item
     */
    public void updateQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.quantity = newQuantity;
    }
    
    /**
     * Calculates the subtotal for this item
     */
    public Money getSubtotal() {
        return unitPrice.multiply(quantity);
    }
    
    /**
     * Returns the product ID
     */
    public ProductId getProductId() {
        return productId;
    }
    
    /**
     * Returns the product name
     */
    public String getProductName() {
        return productName;
    }
    
    /**
     * Returns the quantity
     */
    public int getQuantity() {
        return quantity;
    }
    
    /**
     * Returns the unit price
     */
    public Money getUnitPrice() {
        return unitPrice;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        OrderItem orderItem = (OrderItem) o;
        return productId.equals(orderItem.productId);
    }
    
    @Override
    public int hashCode() {
        return productId.hashCode();
    }
    
    @Override
    public String toString() {
        return "OrderItem{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", subtotal=" + getSubtotal() +
                '}';
    }
}
