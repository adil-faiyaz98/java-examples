package design.patterns.domaindrivendesign.domain.service;

import design.patterns.domaindrivendesign.domain.model.customer.PaymentMethod;
import design.patterns.domaindrivendesign.domain.model.order.OrderId;
import design.patterns.domaindrivendesign.domain.model.shared.Money;

/**
 * PaymentProcessor Interface
 * 
 * Defines the contract for processing payments.
 * Demonstrates the OOP principle of abstraction by defining a common interface
 * for different payment processors.
 */
public interface PaymentProcessor {
    /**
     * Processes a payment
     * 
     * @param orderId The order ID
     * @param amount The amount to charge
     * @param paymentMethod The payment method to use
     * @return true if the payment was successful, false otherwise
     */
    boolean processPayment(OrderId orderId, Money amount, PaymentMethod paymentMethod);
}
