package design.patterns.domaindrivendesign;

import design.patterns.domaindrivendesign.application.dto.OrderDTO;
import design.patterns.domaindrivendesign.application.service.OrderApplicationService;
import design.patterns.domaindrivendesign.domain.model.customer.Address;
import design.patterns.domaindrivendesign.domain.model.customer.Customer;
import design.patterns.domaindrivendesign.domain.model.customer.Email;
import design.patterns.domaindrivendesign.domain.model.customer.PaymentMethod;
import design.patterns.domaindrivendesign.domain.model.order.OrderPlacedEvent;
import design.patterns.domaindrivendesign.domain.repository.CustomerRepository;
import design.patterns.domaindrivendesign.domain.repository.OrderRepository;
import design.patterns.domaindrivendesign.domain.service.OrderProcessingService;
import design.patterns.domaindrivendesign.infrastructure.event.SimpleEventPublisher;
import design.patterns.domaindrivendesign.infrastructure.repository.InMemoryCustomerRepository;
import design.patterns.domaindrivendesign.infrastructure.repository.InMemoryOrderRepository;

import java.time.YearMonth;

/**
 * Main class to demonstrate the Domain-Driven Design implementation
 */
public class Main {
    public static void main(String[] args) {
        // Set up infrastructure
        SimpleEventPublisher eventPublisher = new SimpleEventPublisher();
        CustomerRepository customerRepository = new InMemoryCustomerRepository();
        OrderRepository orderRepository = new InMemoryOrderRepository();
        
        // Set up domain services
        OrderProcessingService orderProcessingService = new OrderProcessingService(eventPublisher);
        
        // Set up application services
        OrderApplicationService orderApplicationService = new OrderApplicationService(
                orderRepository, customerRepository, orderProcessingService);
        
        // Register event handlers
        eventPublisher.registerHandler(OrderPlacedEvent.class, event -> {
            System.out.println("Order placed event handler: " + event);
            System.out.println("Sending confirmation email to customer...");
            System.out.println("Notifying warehouse to prepare shipment...");
        });
        
        // Create a customer
        System.out.println("Creating a new customer...");
        Customer customer = new Customer("John Doe", new Email("john.doe@example.com"));
        
        // Add customer details
        Address address = new Address("123 Main St", "New York", "NY", "10001", "USA");
        customer.setShippingAddress(address);
        customer.setBillingAddress(address);
        
        // Add payment method
        PaymentMethod paymentMethod = new PaymentMethod("4111111111111111", YearMonth.of(2025, 12));
        customer.addPaymentMethod(paymentMethod);
        
        // Save the customer
        customerRepository.save(customer);
        System.out.println("Customer created: " + customer);
        
        // Create an order
        System.out.println("\nCreating a new order...");
        OrderDTO orderDTO = orderApplicationService.createOrder(customer.getId().getValue());
        System.out.println("Order created: " + orderDTO);
        
        // Add items to the order
        System.out.println("\nAdding items to the order...");
        orderDTO = orderApplicationService.addOrderItem(
                orderDTO.getId(), "PROD-001", "Laptop", 1, 1299.99);
        orderDTO = orderApplicationService.addOrderItem(
                orderDTO.getId(), "PROD-002", "Mouse", 1, 25.99);
        orderDTO = orderApplicationService.addOrderItem(
                orderDTO.getId(), "PROD-003", "Keyboard", 1, 49.99);
        System.out.println("Items added to order: " + orderDTO);
        
        // Place the order
        System.out.println("\nPlacing the order...");
        orderDTO = orderApplicationService.placeOrder(orderDTO.getId());
        System.out.println("Order placed: " + orderDTO);
        
        // Ship the order
        System.out.println("\nShipping the order...");
        orderDTO = orderApplicationService.shipOrder(orderDTO.getId());
        System.out.println("Order shipped: " + orderDTO);
        
        // Deliver the order
        System.out.println("\nDelivering the order...");
        orderDTO = orderApplicationService.deliverOrder(orderDTO.getId());
        System.out.println("Order delivered: " + orderDTO);
        
        // Create another order and then cancel it
        System.out.println("\nCreating another order...");
        OrderDTO order2DTO = orderApplicationService.createOrder(customer.getId().getValue());
        order2DTO = orderApplicationService.addOrderItem(
                order2DTO.getId(), "PROD-004", "Headphones", 1, 99.99);
        System.out.println("Second order created: " + order2DTO);
        
        // Cancel the order
        System.out.println("\nCancelling the second order...");
        order2DTO = orderApplicationService.cancelOrder(order2DTO.getId());
        System.out.println("Order cancelled: " + order2DTO);
        
        // Get all orders for the customer
        System.out.println("\nGetting all orders for the customer...");
        orderApplicationService.getOrdersByCustomer(customer.getId().getValue())
                .forEach(order -> System.out.println("Order: " + order));
    }
}
