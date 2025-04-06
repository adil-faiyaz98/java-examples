package OOP.banking;

/**
 * SavingsAccount class extending Account
 * 
 * Demonstrates:
 * - Inheritance: Extends Account class
 * - Method overriding: Changes withdraw behavior
 * - Encapsulation: Private interest rate
 */
public class SavingsAccount extends Account {
    private double interestRate;
    private double minimumBalance;
    
    /**
     * Constructor for SavingsAccount
     */
    public SavingsAccount(String accountNumber, String accountHolder, 
                         double initialBalance, double interestRate, 
                         double minimumBalance) {
        super(accountNumber, accountHolder, initialBalance);
        
        if (interestRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative");
        }
        this.interestRate = interestRate;
        
        if (minimumBalance < 0) {
            throw new IllegalArgumentException("Minimum balance cannot be negative");
        }
        this.minimumBalance = minimumBalance;
        
        if (initialBalance < minimumBalance) {
            throw new IllegalArgumentException("Initial balance must be at least the minimum balance");
        }
    }
    
    /**
     * Override withdraw method to enforce minimum balance
     */
    @Override
    public boolean withdraw(double amount) {
        if (getBalance() - amount < minimumBalance) {
            System.out.println("Cannot withdraw: Would fall below minimum balance of $" + 
                              String.format("%.2f", minimumBalance));
            return false;
        }
        
        return super.withdraw(amount);
    }
    
    /**
     * Apply interest to the account
     */
    public void applyInterest() {
        double interest = getBalance() * interestRate;
        deposit(interest);
        System.out.println(String.format("Interest applied: $%.2f at rate of %.2f%%", 
                          interest, interestRate * 100));
    }
    
    /**
     * Get interest rate
     */
    public double getInterestRate() {
        return interestRate;
    }
    
    /**
     * Set interest rate
     */
    public void setInterestRate(double interestRate) {
        if (interestRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative");
        }
        this.interestRate = interestRate;
    }
    
    /**
     * Get minimum balance
     */
    public double getMinimumBalance() {
        return minimumBalance;
    }
    
    /**
     * Override displayInfo to include savings account specific information
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println(String.format("Interest Rate: %.2f%%", interestRate * 100));
        System.out.println(String.format("Minimum Balance: $%.2f", minimumBalance));
    }
    
    /**
     * Overridden toString method
     */
    @Override
    public String toString() {
        return super.toString() + String.format(" [interestRate=%.2f%%, minimumBalance=$%.2f]", 
                                              interestRate * 100, minimumBalance);
    }
}
