/**
 * Legacy System Adapter Example
 * 
 * This example demonstrates a real-world scenario where the Adapter pattern is used
 * to integrate a legacy payment system with a modern payment processing interface.
 * 
 * It shows how adapters can be used to make incompatible systems work together
 * without modifying the original code.
 */
package design.patterns.adapter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class LegacySystemAdapter {
    
    public static void main(String[] args) {
        // Modern payment processor
        PaymentProcessor modernProcessor = new StripePaymentProcessor();
        
        // Process a payment with the modern processor
        System.out.println("Using modern payment processor:");
        processPayment(modernProcessor, "customer123", 99.99, "USD");
        
        // Legacy payment system
        LegacyPaymentSystem legacySystem = new LegacyPaymentSystem();
        
        // Create an adapter for the legacy system
        PaymentProcessor legacyAdapter = new LegacyPaymentAdapter(legacySystem);
        
        // Process a payment with the legacy system through the adapter
        System.out.println("\nUsing legacy payment system through adapter:");
        processPayment(legacyAdapter, "customer456", 149.99, "EUR");
        
        // Try to process a payment with an unsupported currency
        System.out.println("\nTrying to process a payment with unsupported currency:");
        processPayment(legacyAdapter, "customer789", 199.99, "JPY");
    }
    
    // Client code that works with the PaymentProcessor interface
    private static void processPayment(PaymentProcessor processor, String customerId, 
                                      double amount, String currency) {
        PaymentResult result = processor.processPayment(
                new PaymentRequest(customerId, amount, currency));
        
        if (result.isSuccess()) {
            System.out.println("Payment processed successfully!");
            System.out.println("Transaction ID: " + result.getTransactionId());
            System.out.println("Amount: " + result.getAmount() + " " + result.getCurrency());
            System.out.println("Timestamp: " + result.getTimestamp());
        } else {
            System.out.println("Payment failed: " + result.getErrorMessage());
        }
    }
}

/**
 * Target Interface: Modern Payment Processor
 */
interface PaymentProcessor {
    PaymentResult processPayment(PaymentRequest request);
}

/**
 * Modern Payment Request
 */
class PaymentRequest {
    private String customerId;
    private double amount;
    private String currency;
    
    public PaymentRequest(String customerId, double amount, String currency) {
        this.customerId = customerId;
        this.amount = amount;
        this.currency = currency;
    }
    
    public String getCustomerId() {
        return customerId;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public String getCurrency() {
        return currency;
    }
}

/**
 * Modern Payment Result
 */
class PaymentResult {
    private boolean success;
    private String transactionId;
    private double amount;
    private String currency;
    private Date timestamp;
    private String errorMessage;
    
    // Constructor for successful payment
    public PaymentResult(String transactionId, double amount, String currency) {
        this.success = true;
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.timestamp = new Date();
    }
    
    // Constructor for failed payment
    public PaymentResult(String errorMessage) {
        this.success = false;
        this.errorMessage = errorMessage;
        this.timestamp = new Date();
    }
    
    public boolean isSuccess() {
        return success;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public double getAmount() {
        return amount;
    }
    
    public String getCurrency() {
        return currency;
    }
    
    public Date getTimestamp() {
        return timestamp;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
}

/**
 * Concrete Implementation of Modern Payment Processor
 */
class StripePaymentProcessor implements PaymentProcessor {
    @Override
    public PaymentResult processPayment(PaymentRequest request) {
        // In a real implementation, this would call the Stripe API
        System.out.println("Processing payment with Stripe:");
        System.out.println("Customer ID: " + request.getCustomerId());
        System.out.println("Amount: " + request.getAmount() + " " + request.getCurrency());
        
        // Generate a transaction ID
        String transactionId = "stripe_" + System.currentTimeMillis();
        
        // Return a successful result
        return new PaymentResult(transactionId, request.getAmount(), request.getCurrency());
    }
}

/**
 * Adaptee: Legacy Payment System with incompatible interface
 */
class LegacyPaymentSystem {
    // Legacy system only supports certain currencies
    private final String[] supportedCurrencies = {"USD", "EUR", "GBP"};
    
    /**
     * Authorizes a payment in the legacy system
     * 
     * @param accountId The legacy account ID
     * @param amount The payment amount
     * @param currencyCode The currency code (3-letter code)
     * @return Authorization code if successful, null if failed
     */
    public String authorizePayment(String accountId, double amount, String currencyCode) {
        System.out.println("Legacy system: Authorizing payment");
        System.out.println("Account: " + accountId);
        System.out.println("Amount: " + amount + " " + currencyCode);
        
        // Check if currency is supported
        boolean currencySupported = false;
        for (String currency : supportedCurrencies) {
            if (currency.equals(currencyCode)) {
                currencySupported = true;
                break;
            }
        }
        
        if (!currencySupported) {
            System.out.println("Legacy system: Currency not supported: " + currencyCode);
            return null;
        }
        
        // Generate an authorization code
        String authCode = "AUTH" + System.currentTimeMillis();
        System.out.println("Legacy system: Payment authorized with code: " + authCode);
        
        return authCode;
    }
    
    /**
     * Captures a previously authorized payment
     * 
     * @param authorizationCode The authorization code from authorizePayment
     * @return Transaction reference number
     */
    public String capturePayment(String authorizationCode) {
        if (authorizationCode == null) {
            System.out.println("Legacy system: Cannot capture payment, authorization failed");
            return null;
        }
        
        System.out.println("Legacy system: Capturing payment with auth code: " + authorizationCode);
        
        // Generate a transaction reference
        String transactionRef = "REF" + System.currentTimeMillis();
        System.out.println("Legacy system: Payment captured with reference: " + transactionRef);
        
        return transactionRef;
    }
    
    /**
     * Gets transaction details
     * 
     * @param transactionRef The transaction reference number
     * @return Map of transaction details
     */
    public Map<String, Object> getTransactionDetails(String transactionRef) {
        Map<String, Object> details = new HashMap<>();
        details.put("reference", transactionRef);
        details.put("timestamp", new Date());
        
        return details;
    }
}

/**
 * Adapter: Adapts the Legacy Payment System to the modern PaymentProcessor interface
 */
class LegacyPaymentAdapter implements PaymentProcessor {
    private LegacyPaymentSystem legacySystem;
    
    public LegacyPaymentAdapter(LegacyPaymentSystem legacySystem) {
        this.legacySystem = legacySystem;
    }
    
    @Override
    public PaymentResult processPayment(PaymentRequest request) {
        // Step 1: Authorize the payment in the legacy system
        String authCode = legacySystem.authorizePayment(
                request.getCustomerId(),
                request.getAmount(),
                request.getCurrency());
        
        // If authorization failed
        if (authCode == null) {
            return new PaymentResult("Payment authorization failed. Currency may not be supported.");
        }
        
        // Step 2: Capture the payment
        String transactionRef = legacySystem.capturePayment(authCode);
        
        // If capture failed
        if (transactionRef == null) {
            return new PaymentResult("Payment capture failed.");
        }
        
        // Step 3: Get transaction details and create a PaymentResult
        return new PaymentResult(transactionRef, request.getAmount(), request.getCurrency());
    }
}
