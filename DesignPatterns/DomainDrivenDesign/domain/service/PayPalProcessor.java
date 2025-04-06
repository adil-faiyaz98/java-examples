package design.patterns.domaindrivendesign.domain.service;

import design.patterns.domaindrivendesign.domain.model.customer.PaymentMethod;
import design.patterns.domaindrivendesign.domain.model.order.OrderId;
import design.patterns.domaindrivendesign.domain.model.shared.Money;

/**
 * PayPalProcessor
 * 
 * Concrete implementation of the PaymentProcessor interface for PayPal.
 * Demonstrates the OOP principle of polymorphism by providing a specific
 * implementation of the processPayment method.
 */
public class PayPalProcessor implements PaymentProcessor {
    
    @Override
    public boolean processPayment(OrderId orderId, Money amount, PaymentMethod paymentMethod) {
        if (paymentMethod.getType() != PaymentMethod.PaymentType.PAYPAL) {
            throw new IllegalArgumentException("Payment method must be PayPal");
        }
        
        if (!paymentMethod.isValid()) {
            return false;
        }
        
        // In a real implementation, this would connect to the PayPal API
        System.out.println("Processing PayPal payment for order " + orderId);
        System.out.println("Amount: " + amount);
        System.out.println("Account: " + paymentMethod.getAccountId());
        
        // Simulate successful payment
        return true;
    }
}
