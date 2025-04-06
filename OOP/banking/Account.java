package OOP.banking;

/**
 * Base Account class
 * 
 * Demonstrates:
 * - Encapsulation: Private fields with controlled access
 * - Validation: Ensuring balance doesn't go below zero
 */
public class Account {
    private String accountNumber;
    private String accountHolder;
    private double balance;
    
    /**
     * Constructor for Account
     */
    public Account(String accountNumber, String accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        
        if (initialBalance < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        this.balance = initialBalance;
    }
    
    /**
     * Deposit money into the account
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
        System.out.println(String.format("Deposited: $%.2f. New balance: $%.2f", amount, balance));
    }
    
    /**
     * Withdraw money from the account
     */
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        
        if (amount > balance) {
            System.out.println("Insufficient funds");
            return false;
        }
        
        balance -= amount;
        System.out.println(String.format("Withdrew: $%.2f. New balance: $%.2f", amount, balance));
        return true;
    }
    
    /**
     * Get the current balance
     */
    public double getBalance() {
        return balance;
    }
    
    /**
     * Protected method to set balance directly (for use by subclasses)
     */
    protected void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = balance;
    }
    
    /**
     * Get account number
     */
    public String getAccountNumber() {
        return accountNumber;
    }
    
    /**
     * Get account holder name
     */
    public String getAccountHolder() {
        return accountHolder;
    }
    
    /**
     * Set account holder name
     */
    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }
    
    /**
     * Display account information
     */
    public void displayInfo() {
        System.out.println("Account Type: " + getClass().getSimpleName());
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Holder: " + accountHolder);
        System.out.println(String.format("Current Balance: $%.2f", balance));
    }
    
    /**
     * Overridden toString method
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " [accountNumber=" + accountNumber + 
               ", accountHolder=" + accountHolder + 
               ", balance=$" + String.format("%.2f", balance) + "]";
    }
}
