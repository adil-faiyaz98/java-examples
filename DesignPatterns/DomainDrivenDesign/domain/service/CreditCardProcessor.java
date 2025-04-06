package design.patterns.domaindrivendesign.domain.service;

import design.patterns.domaindrivendesign.domain.model.customer.PaymentMethod;
import design.patterns.domaindrivendesign.domain.model.order.OrderId;
import design.patterns.domaindrivendesign.domain.model.shared.Money;

/**
 * CreditCardProcessor
 * 
 * Concrete implementation of the PaymentProcessor interface for credit cards.
 * Demonstrates the OOP principle of polymorphism by providing a specific
 * implementation of the processPayment method.
 */
public class CreditCardProcessor implements PaymentProcessor {
    
    @Override
    public boolean processPayment(OrderId orderId, Money amount, PaymentMethod paymentMethod) {
        if (paymentMethod.getType() != PaymentMethod.PaymentType.CREDIT_CARD) {
            throw new IllegalArgumentException("Payment method must be a credit card");
        }
        
        if (!paymentMethod.isValid()) {
            return false;
        }
        
        // In a real implementation, this would connect to a payment gateway
        System.out.println("Processing credit card payment for order " + orderId);
        System.out.println("Amount: " + amount);
        System.out.println("Card: " + paymentMethod.getCardNumber());
        
        // Simulate successful payment
        return true;
    }
}
