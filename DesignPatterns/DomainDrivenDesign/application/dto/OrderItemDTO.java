package design.patterns.domaindrivendesign.application.dto;

/**
 * OrderItemDTO (Data Transfer Object)
 * 
 * Used to transfer order item data between the application layer and the presentation layer.
 */
public class OrderItemDTO {
    private String productId;
    private String productName;
    private int quantity;
    private double unitPrice;
    private double subtotal;
    
    public OrderItemDTO(String productId, String productName, int quantity, double unitPrice, double subtotal) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
    }
    
    public String getProductId() {
        return productId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public int getQuantity() {
        return quantity;
    }
    
    public double getUnitPrice() {
        return unitPrice;
    }
    
    public double getSubtotal() {
        return subtotal;
    }
    
    @Override
    public String toString() {
        return "OrderItemDTO{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", subtotal=" + subtotal +
                '}';
    }
}
