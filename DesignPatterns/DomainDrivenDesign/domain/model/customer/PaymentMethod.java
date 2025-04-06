package design.patterns.domaindrivendesign.domain.model.customer;

import java.time.YearMonth;

/**
 * PaymentMethod Value Object
 * 
 * Represents a payment method like a credit card. This is a value object
 * because it's immutable and has no identity of its own.
 */
public final class PaymentMethod {
    public enum PaymentType {
        CREDIT_CARD,
        PAYPAL,
        BANK_TRANSFER
    }
    
    private final PaymentType type;
    private final String cardNumber; // Only for CREDIT_CARD
    private final YearMonth expiryDate; // Only for CREDIT_CARD
    private final String accountId; // For PAYPAL or BANK_TRANSFER
    
    // Constructor for credit card
    public PaymentMethod(String cardNumber, YearMonth expiryDate) {
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Card number cannot be empty");
        }
        if (expiryDate == null) {
            throw new IllegalArgumentException("Expiry date cannot be null");
        }
        if (expiryDate.isBefore(YearMonth.now())) {
            throw new IllegalArgumentException("Card has expired");
        }
        
        this.type = PaymentType.CREDIT_CARD;
        this.cardNumber = maskCardNumber(cardNumber);
        this.expiryDate = expiryDate;
        this.accountId = null;
    }
    
    // Constructor for PayPal or bank transfer
    public PaymentMethod(PaymentType type, String accountId) {
        if (type == null) {
            throw new IllegalArgumentException("Payment type cannot be null");
        }
        if (type == PaymentType.CREDIT_CARD) {
            throw new IllegalArgumentException("Use the credit card constructor for credit cards");
        }
        if (accountId == null || accountId.trim().isEmpty()) {
            throw new IllegalArgumentException("Account ID cannot be empty");
        }
        
        this.type = type;
        this.cardNumber = null;
        this.expiryDate = null;
        this.accountId = accountId;
    }
    
    public PaymentType getType() {
        return type;
    }
    
    public String getCardNumber() {
        return cardNumber;
    }
    
    public YearMonth getExpiryDate() {
        return expiryDate;
    }
    
    public String getAccountId() {
        return accountId;
    }
    
    public boolean isValid() {
        if (type == PaymentType.CREDIT_CARD) {
            return expiryDate != null && expiryDate.isAfter(YearMonth.now());
        }
        return true;
    }
    
    private String maskCardNumber(String cardNumber) {
        // Keep only the last 4 digits visible
        if (cardNumber.length() <= 4) {
            return cardNumber;
        }
        
        String lastFourDigits = cardNumber.substring(cardNumber.length() - 4);
        StringBuilder masked = new StringBuilder();
        for (int i = 0; i < cardNumber.length() - 4; i++) {
            masked.append("*");
        }
        masked.append(lastFourDigits);
        
        return masked.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        PaymentMethod that = (PaymentMethod) o;
        if (type != that.type) return false;
        
        if (type == PaymentType.CREDIT_CARD) {
            if (!cardNumber.equals(that.cardNumber)) return false;
            return expiryDate.equals(that.expiryDate);
        } else {
            return accountId.equals(that.accountId);
        }
    }
    
    @Override
    public int hashCode() {
        int result = type.hashCode();
        if (type == PaymentType.CREDIT_CARD) {
            result = 31 * result + cardNumber.hashCode();
            result = 31 * result + expiryDate.hashCode();
        } else {
            result = 31 * result + accountId.hashCode();
        }
        return result;
    }
    
    @Override
    public String toString() {
        if (type == PaymentType.CREDIT_CARD) {
            return "Credit Card: " + cardNumber + ", Expires: " + expiryDate;
        } else {
            return type + ": " + accountId;
        }
    }
}
