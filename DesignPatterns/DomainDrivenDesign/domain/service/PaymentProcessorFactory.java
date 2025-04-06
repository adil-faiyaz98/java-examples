package design.patterns.domaindrivendesign.domain.service;

import design.patterns.domaindrivendesign.domain.model.customer.PaymentMethod;

/**
 * PaymentProcessorFactory
 * 
 * Factory for creating payment processors based on payment method type.
 * Demonstrates the OOP Factory pattern and the principle of encapsulation
 * by hiding the creation logic of payment processors.
 */
public class PaymentProcessorFactory {
    
    /**
     * Creates a payment processor for the given payment method
     */
    public static PaymentProcessor createProcessor(PaymentMethod paymentMethod) {
        switch (paymentMethod.getType()) {
            case CREDIT_CARD:
                return new CreditCardProcessor();
            case PAYPAL:
                return new PayPalProcessor();
            case BANK_TRANSFER:
                return new BankTransferProcessor();
            default:
                throw new IllegalArgumentException("Unsupported payment method: " + paymentMethod.getType());
        }
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private PaymentProcessorFactory() {
        // This class should not be instantiated
    }
}
