/**
 * Strategy Design Pattern
 * 
 * Intent: Define a family of algorithms, encapsulate each one, and make them interchangeable.
 * Strategy lets the algorithm vary independently from clients that use it.
 * 
 * This example demonstrates a payment processing system that can use different payment strategies
 * (credit card, PayPal, cryptocurrency) without changing the checkout process.
 */
package design.patterns.strategy;

import java.util.ArrayList;
import java.util.List;

public class StrategyPattern {
    
    public static void main(String[] args) {
        // Create a shopping cart
        ShoppingCart cart = new ShoppingCart();
        
        // Add some items
        cart.addItem(new Item("Laptop", 1299.99));
        cart.addItem(new Item("Headphones", 99.99));
        cart.addItem(new Item("Mouse", 25.99));
        
        // Display cart contents and total
        cart.displayItems();
        
        // Process payment with different strategies
        System.out.println("\nPaying with Credit Card:");
        cart.setPaymentStrategy(new CreditCardStrategy("John Doe", "1234567890123456", "123", "12/25"));
        cart.checkout();
        
        System.out.println("\nPaying with PayPal:");
        cart.setPaymentStrategy(new PayPalStrategy("john.doe@example.com", "password123"));
        cart.checkout();
        
        System.out.println("\nPaying with Bitcoin:");
        cart.setPaymentStrategy(new BitcoinStrategy("1A1zP1eP5QGefi2DMPTfTL5SLmv7DivfNa"));
        cart.checkout();
    }
}

/**
 * Item class to represent products in the shopping cart
 */
class Item {
    private String name;
    private double price;
    
    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }
    
    public String getName() {
        return name;
    }
    
    public double getPrice() {
        return price;
    }
}

/**
 * Strategy interface for payment methods
 */
interface PaymentStrategy {
    void pay(double amount);
}

/**
 * Concrete Strategy: Credit Card Payment
 */
class CreditCardStrategy implements PaymentStrategy {
    private String name;
    private String cardNumber;
    private String cvv;
    private String expiryDate;
    
    public CreditCardStrategy(String name, String cardNumber, String cvv, String expiryDate) {
        this.name = name;
        this.cardNumber = cardNumber;
        this.cvv = cvv;
        this.expiryDate = expiryDate;
    }
    
    @Override
    public void pay(double amount) {
        // In a real application, this would connect to a payment gateway
        System.out.println(amount + " paid with credit card.");
        System.out.println("Card holder: " + name);
        System.out.println("Card number: " + maskCardNumber(cardNumber));
        System.out.println("Expiry date: " + expiryDate);
    }
    
    private String maskCardNumber(String cardNumber) {
        // Show only last 4 digits
        return "xxxx-xxxx-xxxx-" + cardNumber.substring(cardNumber.length() - 4);
    }
}

/**
 * Concrete Strategy: PayPal Payment
 */
class PayPalStrategy implements PaymentStrategy {
    private String email;
    private String password;
    
    public PayPalStrategy(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    @Override
    public void pay(double amount) {
        // In a real application, this would connect to PayPal API
        System.out.println(amount + " paid using PayPal.");
        System.out.println("PayPal account: " + email);
    }
}

/**
 * Concrete Strategy: Bitcoin Payment
 */
class BitcoinStrategy implements PaymentStrategy {
    private String walletAddress;
    
    public BitcoinStrategy(String walletAddress) {
        this.walletAddress = walletAddress;
    }
    
    @Override
    public void pay(double amount) {
        // In a real application, this would generate a Bitcoin transaction
        System.out.println(amount + " paid using Bitcoin.");
        System.out.println("Bitcoin wallet: " + walletAddress);
        System.out.println("Current BTC exchange rate applied.");
    }
}

/**
 * Context: Shopping Cart
 * Uses a payment strategy to checkout
 */
class ShoppingCart {
    private List<Item> items;
    private PaymentStrategy paymentStrategy;
    
    public ShoppingCart() {
        this.items = new ArrayList<>();
    }
    
    public void addItem(Item item) {
        items.add(item);
    }
    
    public void removeItem(Item item) {
        items.remove(item);
    }
    
    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }
    
    public double calculateTotal() {
        return items.stream().mapToDouble(Item::getPrice).sum();
    }
    
    public void displayItems() {
        System.out.println("Shopping Cart Contents:");
        System.out.println("------------------------");
        
        for (Item item : items) {
            System.out.printf("%-15s $%.2f%n", item.getName(), item.getPrice());
        }
        
        System.out.printf("------------------------%n");
        System.out.printf("Total:          $%.2f%n", calculateTotal());
    }
    
    public void checkout() {
        if (paymentStrategy == null) {
            System.out.println("Please set a payment method before checkout.");
            return;
        }
        
        double amount = calculateTotal();
        paymentStrategy.pay(amount);
    }
}
