package design.patterns.domaindrivendesign.application.service;

import design.patterns.domaindrivendesign.application.dto.OrderDTO;
import design.patterns.domaindrivendesign.application.dto.OrderItemDTO;
import design.patterns.domaindrivendesign.domain.model.customer.Customer;
import design.patterns.domaindrivendesign.domain.model.customer.CustomerId;
import design.patterns.domaindrivendesign.domain.model.order.Order;
import design.patterns.domaindrivendesign.domain.model.order.OrderId;
import design.patterns.domaindrivendesign.domain.model.product.ProductId;
import design.patterns.domaindrivendesign.domain.model.shared.Money;
import design.patterns.domaindrivendesign.domain.repository.CustomerRepository;
import design.patterns.domaindrivendesign.domain.repository.OrderRepository;
import design.patterns.domaindrivendesign.domain.service.OrderProcessingService;

import java.util.Currency;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * OrderApplicationService
 * 
 * Application service that coordinates the domain objects to implement use cases
 * related to orders. It acts as a facade for the domain layer.
 */
public class OrderApplicationService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderProcessingService orderProcessingService;
    
    public OrderApplicationService(
            OrderRepository orderRepository,
            CustomerRepository customerRepository,
            OrderProcessingService orderProcessingService) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderProcessingService = orderProcessingService;
    }
    
    /**
     * Creates a new order for a customer
     */
    public OrderDTO createOrder(String customerId) {
        // Find the customer
        CustomerId customerIdObj = new CustomerId(customerId);
        Customer customer = customerRepository.findById(customerIdObj)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + customerId));
        
        // Validate customer has a shipping address
        if (customer.getShippingAddress() == null) {
            throw new IllegalStateException("Customer has no shipping address");
        }
        
        // Create a new order
        Order order = new Order(customerIdObj, customer.getShippingAddress());
        
        // Save the order
        order = orderRepository.save(order);
        
        // Return the order DTO
        return convertToDTO(order);
    }
    
    /**
     * Adds an item to an order
     */
    public OrderDTO addOrderItem(String orderId, String productId, String productName, int quantity, double unitPrice) {
        // Find the order
        OrderId orderIdObj = new OrderId(orderId);
        Order order = orderRepository.findById(orderIdObj)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        
        // Add the item
        ProductId productIdObj = new ProductId(productId);
        Money unitPriceObj = new Money(unitPrice, Currency.getInstance("USD"));
        order.addItem(productIdObj, productName, quantity, unitPriceObj);
        
        // Save the order
        order = orderRepository.save(order);
        
        // Return the updated order DTO
        return convertToDTO(order);
    }
    
    /**
     * Places an order
     */
    public OrderDTO placeOrder(String orderId) {
        // Find the order
        OrderId orderIdObj = new OrderId(orderId);
        Order order = orderRepository.findById(orderIdObj)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        
        // Find the customer
        Customer customer = customerRepository.findById(order.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found: " + order.getCustomerId()));
        
        // Place the order
        orderProcessingService.placeOrder(order, customer);
        
        // Process payment
        orderProcessingService.processPayment(order, customer.getDefaultPaymentMethod());
        
        // Save the order
        order = orderRepository.save(order);
        
        // Return the updated order DTO
        return convertToDTO(order);
    }
    
    /**
     * Ships an order
     */
    public OrderDTO shipOrder(String orderId) {
        // Find the order
        OrderId orderIdObj = new OrderId(orderId);
        Order order = orderRepository.findById(orderIdObj)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        
        // Ship the order
        orderProcessingService.shipOrder(order);
        
        // Save the order
        order = orderRepository.save(order);
        
        // Return the updated order DTO
        return convertToDTO(order);
    }
    
    /**
     * Delivers an order
     */
    public OrderDTO deliverOrder(String orderId) {
        // Find the order
        OrderId orderIdObj = new OrderId(orderId);
        Order order = orderRepository.findById(orderIdObj)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        
        // Deliver the order
        orderProcessingService.deliverOrder(order);
        
        // Save the order
        order = orderRepository.save(order);
        
        // Return the updated order DTO
        return convertToDTO(order);
    }
    
    /**
     * Cancels an order
     */
    public OrderDTO cancelOrder(String orderId) {
        // Find the order
        OrderId orderIdObj = new OrderId(orderId);
        Order order = orderRepository.findById(orderIdObj)
                .orElseThrow(() -> new IllegalArgumentException("Order not found: " + orderId));
        
        // Cancel the order
        orderProcessingService.cancelOrder(order);
        
        // Save the order
        order = orderRepository.save(order);
        
        // Return the updated order DTO
        return convertToDTO(order);
    }
    
    /**
     * Gets an order by ID
     */
    public Optional<OrderDTO> getOrder(String orderId) {
        OrderId orderIdObj = new OrderId(orderId);
        return orderRepository.findById(orderIdObj)
                .map(this::convertToDTO);
    }
    
    /**
     * Gets all orders for a customer
     */
    public List<OrderDTO> getOrdersByCustomer(String customerId) {
        CustomerId customerIdObj = new CustomerId(customerId);
        return orderRepository.findByCustomerId(customerIdObj)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * Converts an Order entity to an OrderDTO
     */
    private OrderDTO convertToDTO(Order order) {
        List<OrderItemDTO> itemDTOs = order.getOrderItems()
                .stream()
                .map(item -> new OrderItemDTO(
                        item.getProductId().getValue(),
                        item.getProductName(),
                        item.getQuantity(),
                        item.getUnitPrice().getAmount().doubleValue(),
                        item.getSubtotal().getAmount().doubleValue()))
                .collect(Collectors.toList());
        
        return new OrderDTO(
                order.getId().getValue(),
                order.getCustomerId().getValue(),
                order.getStatus().toString(),
                order.getOrderDate(),
                order.getTotalAmount().getAmount().doubleValue(),
                order.getShippingAddress().getFullAddress(),
                itemDTOs
        );
    }
}
