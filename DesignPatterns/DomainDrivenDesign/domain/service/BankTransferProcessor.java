package design.patterns.domaindrivendesign.domain.service;

import design.patterns.domaindrivendesign.domain.model.customer.PaymentMethod;
import design.patterns.domaindrivendesign.domain.model.order.OrderId;
import design.patterns.domaindrivendesign.domain.model.shared.Money;

/**
 * BankTransferProcessor
 * 
 * Concrete implementation of the PaymentProcessor interface for bank transfers.
 * Demonstrates the OOP principle of polymorphism by providing a specific
 * implementation of the processPayment method.
 */
public class BankTransferProcessor implements PaymentProcessor {
    
    @Override
    public boolean processPayment(OrderId orderId, Money amount, PaymentMethod paymentMethod) {
        if (paymentMethod.getType() != PaymentMethod.PaymentType.BANK_TRANSFER) {
            throw new IllegalArgumentException("Payment method must be a bank transfer");
        }
        
        if (!paymentMethod.isValid()) {
            return false;
        }
        
        // In a real implementation, this would connect to a banking API
        System.out.println("Processing bank transfer for order " + orderId);
        System.out.println("Amount: " + amount);
        System.out.println("Account: " + paymentMethod.getAccountId());
        
        // Simulate successful payment
        return true;
    }
}
