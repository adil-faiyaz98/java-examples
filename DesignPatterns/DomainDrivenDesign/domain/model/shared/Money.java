package design.patterns.domaindrivendesign.domain.model.shared;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

/**
 * Money Value Object
 * 
 * Represents a monetary amount with a currency. This is a value object
 * because it's immutable and has no identity of its own.
 */
public final class Money {
    public static final Money ZERO = new Money(BigDecimal.ZERO, Currency.getInstance("USD"));
    
    private final BigDecimal amount;
    private final Currency currency;
    
    public Money(BigDecimal amount, Currency currency) {
        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (currency == null) {
            throw new IllegalArgumentException("Currency cannot be null");
        }
        
        // Scale the amount to the currency's default fraction digits
        this.amount = amount.setScale(currency.getDefaultFractionDigits(), RoundingMode.HALF_EVEN);
        this.currency = currency;
    }
    
    public Money(double amount, Currency currency) {
        this(BigDecimal.valueOf(amount), currency);
    }
    
    public Money(double amount, String currencyCode) {
        this(BigDecimal.valueOf(amount), Currency.getInstance(currencyCode));
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public Currency getCurrency() {
        return currency;
    }
    
    public boolean isNegative() {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }
    
    public boolean isNegativeOrZero() {
        return amount.compareTo(BigDecimal.ZERO) <= 0;
    }
    
    public boolean isZero() {
        return amount.compareTo(BigDecimal.ZERO) == 0;
    }
    
    public boolean isPositive() {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }
    
    public Money add(Money money) {
        if (!this.currency.equals(money.currency)) {
            throw new IllegalArgumentException("Cannot add money with different currencies");
        }
        
        return new Money(this.amount.add(money.amount), this.currency);
    }
    
    public Money subtract(Money money) {
        if (!this.currency.equals(money.currency)) {
            throw new IllegalArgumentException("Cannot subtract money with different currencies");
        }
        
        return new Money(this.amount.subtract(money.amount), this.currency);
    }
    
    public Money multiply(double multiplier) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(multiplier)), this.currency);
    }
    
    public Money multiply(int multiplier) {
        return new Money(this.amount.multiply(BigDecimal.valueOf(multiplier)), this.currency);
    }
    
    public Money divide(double divisor) {
        if (divisor == 0) {
            throw new IllegalArgumentException("Cannot divide by zero");
        }
        
        return new Money(
                this.amount.divide(BigDecimal.valueOf(divisor), RoundingMode.HALF_EVEN),
                this.currency
        );
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Money money = (Money) o;
        if (!amount.equals(money.amount)) return false;
        return currency.equals(money.currency);
    }
    
    @Override
    public int hashCode() {
        int result = amount.hashCode();
        result = 31 * result + currency.hashCode();
        return result;
    }
    
    @Override
    public String toString() {
        return currency.getSymbol() + amount.toString();
    }
}
