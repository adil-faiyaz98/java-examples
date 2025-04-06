package design.patterns.domaindrivendesign.application.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * OrderDTO (Data Transfer Object)
 * 
 * Used to transfer order data between the application layer and the presentation layer.
 * DTOs help to decouple the domain model from the presentation layer.
 */
public class OrderDTO {
    private String id;
    private String customerId;
    private String status;
    private LocalDateTime orderDate;
    private double totalAmount;
    private String shippingAddress;
    private List<OrderItemDTO> items;
    
    public OrderDTO(String id, String customerId, String status, LocalDateTime orderDate,
                   double totalAmount, String shippingAddress, List<OrderItemDTO> items) {
        this.id = id;
        this.customerId = customerId;
        this.status = status;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.shippingAddress = shippingAddress;
        this.items = items;
    }
    
    public String getId() {
        return id;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public String getStatus() {
        return status;
    }
    
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public String getShippingAddress() {
        return shippingAddress;
    }
    
    public List<OrderItemDTO> getItems() {
        return items;
    }
    
    @Override
    public String toString() {
        return "OrderDTO{" +
                "id='" + id + '\'' +
                ", customerId='" + customerId + '\'' +
                ", status='" + status + '\'' +
                ", orderDate=" + orderDate +
                ", totalAmount=" + totalAmount +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", items=" + items +
                '}';
    }
}
