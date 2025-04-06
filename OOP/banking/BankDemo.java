package OOP.banking;

/**
 * Demo class for Banking system
 * 
 * Demonstrates:
 * - Polymorphism: Using base class references for derived objects
 * - Inheritance hierarchy in action
 * - Encapsulation benefits
 */
public class BankDemo {
    
    public static void main(String[] args) {
        // Create different account types
        Account regularAccount = new Account("A12345", "John Doe", 1000.0);
        SavingsAccount savingsAccount = new SavingsAccount("S67890", "Jane Smith", 
                                                         2000.0, 0.03, 500.0);
        CheckingAccount checkingAccount = new CheckingAccount("C13579", "Bob Johnson", 
                                                            1500.0, 500.0, 25.0);
        
        System.out.println("===== Initial Account Information =====");
        regularAccount.displayInfo();
        System.out.println("-------------------------");
        savingsAccount.displayInfo();
        System.out.println("-------------------------");
        checkingAccount.displayInfo();
        
        // Demonstrate account operations
        System.out.println("\n===== Account Operations =====");
        
        // Regular account operations
        System.out.println("\nRegular Account:");
        regularAccount.deposit(500.0);
        regularAccount.withdraw(200.0);
        regularAccount.withdraw(2000.0); // Should fail - insufficient funds
        
        // Savings account operations
        System.out.println("\nSavings Account:");
        savingsAccount.deposit(1000.0);
        savingsAccount.withdraw(500.0);
        savingsAccount.withdraw(2500.0); // Should fail - below minimum balance
        savingsAccount.applyInterest();
        
        // Checking account operations
        System.out.println("\nChecking Account:");
        checkingAccount.deposit(200.0);
        checkingAccount.withdraw(1000.0);
        checkingAccount.withdraw(1000.0); // Should use overdraft
        checkingAccount.withdraw(1000.0); // Should fail - exceeds overdraft limit
        
        // Display final account states
        System.out.println("\n===== Final Account Information =====");
        System.out.println(regularAccount);
        System.out.println(savingsAccount);
        System.out.println(checkingAccount);
        
        // Demonstrate polymorphism
        System.out.println("\n===== Polymorphic Deposit =====");
        depositToAccount(regularAccount, 100.0);
        depositToAccount(savingsAccount, 100.0);
        depositToAccount(checkingAccount, 100.0);
    }
    
    /**
     * Polymorphic method that works with any Account type
     */
    private static void depositToAccount(Account account, double amount) {
        System.out.println("Depositing to " + account.getClass().getSimpleName() + 
                          " (" + account.getAccountNumber() + ")");
        account.deposit(amount);
    }
}
