package OOP.banking;

/**
 * CheckingAccount class extending Account
 * 
 * Demonstrates:
 * - Inheritance: Extends Account class
 * - Method overriding: Adds overdraft functionality
 * - Encapsulation: Private overdraft limit
 */
public class CheckingAccount extends Account {
    private double overdraftLimit;
    private double overdraftFee;
    
    /**
     * Constructor for CheckingAccount
     */
    public CheckingAccount(String accountNumber, String accountHolder, 
                          double initialBalance, double overdraftLimit,
                          double overdraftFee) {
        super(accountNumber, accountHolder, initialBalance);
        
        if (overdraftLimit < 0) {
            throw new IllegalArgumentException("Overdraft limit cannot be negative");
        }
        this.overdraftLimit = overdraftLimit;
        
        if (overdraftFee < 0) {
            throw new IllegalArgumentException("Overdraft fee cannot be negative");
        }
        this.overdraftFee = overdraftFee;
    }
    
    /**
     * Override withdraw method to allow overdrafts up to the limit
     */
    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        
        // Check if withdrawal is within regular balance or overdraft limit
        if (getBalance() >= amount) {
            // Regular withdrawal
            return super.withdraw(amount);
        } else if (getBalance() + overdraftLimit >= amount) {
            // Overdraft withdrawal
            double overdraftAmount = amount - getBalance();
            
            // Apply overdraft fee
            double totalDeduction = amount + overdraftFee;
            
            // Set the new balance (can be negative within overdraft limit)
            setBalance(getBalance() - totalDeduction);
            
            System.out.println(String.format(
                "Withdrew: $%.2f (includes overdraft of $%.2f)", 
                amount, overdraftAmount));
            System.out.println(String.format(
                "Overdraft fee: $%.2f. New balance: $%.2f", 
                overdraftFee, getBalance()));
            
            return true;
        } else {
            System.out.println("Exceeds overdraft limit");
            return false;
        }
    }
    
    /**
     * Get overdraft limit
     */
    public double getOverdraftLimit() {
        return overdraftLimit;
    }
    
    /**
     * Set overdraft limit
     */
    public void setOverdraftLimit(double overdraftLimit) {
        if (overdraftLimit < 0) {
            throw new IllegalArgumentException("Overdraft limit cannot be negative");
        }
        this.overdraftLimit = overdraftLimit;
    }
    
    /**
     * Get overdraft fee
     */
    public double getOverdraftFee() {
        return overdraftFee;
    }
    
    /**
     * Set overdraft fee
     */
    public void setOverdraftFee(double overdraftFee) {
        if (overdraftFee < 0) {
            throw new IllegalArgumentException("Overdraft fee cannot be negative");
        }
        this.overdraftFee = overdraftFee;
    }
    
    /**
     * Override displayInfo to include checking account specific information
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println(String.format("Overdraft Limit: $%.2f", overdraftLimit));
        System.out.println(String.format("Overdraft Fee: $%.2f", overdraftFee));
    }
    
    /**
     * Overridden toString method
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" [overdraftLimit=$%.2f, overdraftFee=$%.2f]", 
                                              overdraftLimit, overdraftFee);
    }
}
