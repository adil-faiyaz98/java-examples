package Core;

/**
 * CustomExceptionExample
 * 
 * Demonstrates creating and using custom exceptions:
 * - Creating custom checked exceptions
 * - Creating custom unchecked exceptions
 * - Adding context information to exceptions
 * - Exception chaining
 */
public class CustomExceptionExample {

    public static void main(String[] args) {
        System.out.println("===== Custom Exception Examples =====\n");
        
        // Example 1: Using custom checked exception
        customCheckedException();
        
        // Example 2: Using custom unchecked exception
        customUncheckedException();
        
        // Example 3: Exception with context information
        exceptionWithContext();
        
        // Example 4: Exception chaining
        exceptionChaining();
    }
    
    /**
     * Example 1: Using custom checked exception
     */
    private static void customCheckedException() {
        System.out.println("Example 1: Using custom checked exception");
        
        try {
            validateUsername("user@123");  // Valid username
            validateUsername("ab");        // Too short - will throw exception
        } catch (InvalidUsernameException e) {
            System.out.println("Caught InvalidUsernameException: " + e.getMessage());
        }
        
        System.out.println("After custom checked exception example\n");
    }
    
    /**
     * Validates a username and throws a custom checked exception if invalid
     */
    private static void validateUsername(String username) throws InvalidUsernameException {
        if (username == null) {
            throw new InvalidUsernameException("Username cannot be null");
        }
        
        if (username.length() < 3) {
            throw new InvalidUsernameException("Username is too short (minimum 3 characters)");
        }
        
        System.out.println("Username '" + username + "' is valid");
    }
    
    /**
     * Example 2: Using custom unchecked exception
     */
    private static void customUncheckedException() {
        System.out.println("Example 2: Using custom unchecked exception");
        
        try {
            processPayment(100.0);  // Valid amount
            processPayment(-50.0);  // Negative amount - will throw exception
        } catch (InvalidPaymentException e) {
            System.out.println("Caught InvalidPaymentException: " + e.getMessage());
        }
        
        System.out.println("After custom unchecked exception example\n");
    }
    
    /**
     * Processes a payment and throws a custom unchecked exception if invalid
     */
    private static void processPayment(double amount) {
        if (amount <= 0) {
            throw new InvalidPaymentException("Payment amount must be positive: " + amount);
        }
        
        System.out.println("Processing payment of $" + amount);
    }
    
    /**
     * Example 3: Exception with context information
     */
    private static void exceptionWithContext() {
        System.out.println("Example 3: Exception with context information");
        
        try {
            User user = new User(1, "john_doe");
            updateUserProfile(user, null);  // Null profile - will throw exception
        } catch (UserProfileException e) {
            System.out.println("Caught UserProfileException: " + e.getMessage());
            System.out.println("User ID: " + e.getUserId());
            System.out.println("Username: " + e.getUsername());
        }
        
        System.out.println("After exception with context example\n");
    }
    
    /**
     * Updates a user profile and throws an exception with context information if there's an error
     */
    private static void updateUserProfile(User user, String profile) {
        if (profile == null) {
            throw new UserProfileException("Profile data cannot be null", user.getId(), user.getUsername());
        }
        
        System.out.println("Updating profile for user: " + user.getUsername());
    }
    
    /**
     * Example 4: Exception chaining
     */
    private static void exceptionChaining() {
        System.out.println("Example 4: Exception chaining");
        
        try {
            saveUserData(new User(2, "jane_smith"));
        } catch (UserDataException e) {
            System.out.println("Caught UserDataException: " + e.getMessage());
            
            // Get the cause of this exception
            Throwable cause = e.getCause();
            if (cause != null) {
                System.out.println("Caused by: " + cause.getClass().getSimpleName() + " - " + cause.getMessage());
            }
        }
    }
    
    /**
     * Saves user data and demonstrates exception chaining
     */
    private static void saveUserData(User user) throws UserDataException {
        try {
            // Simulate a database operation that throws an exception
            throw new DatabaseException("Failed to connect to database");
        } catch (DatabaseException e) {
            // Wrap the original exception in a higher-level exception
            throw new UserDataException("Could not save user data", e);
        }
    }
    
    /**
     * Simple User class for the examples
     */
    static class User {
        private int id;
        private String username;
        
        public User(int id, String username) {
            this.id = id;
            this.username = username;
        }
        
        public int getId() {
            return id;
        }
        
        public String getUsername() {
            return username;
        }
    }
    
    /**
     * Custom checked exception for invalid usernames
     */
    static class InvalidUsernameException extends Exception {
        public InvalidUsernameException(String message) {
            super(message);
        }
    }
    
    /**
     * Custom unchecked exception for invalid payments
     */
    static class InvalidPaymentException extends RuntimeException {
        public InvalidPaymentException(String message) {
            super(message);
        }
    }
    
    /**
     * Custom exception with context information
     */
    static class UserProfileException extends RuntimeException {
        private int userId;
        private String username;
        
        public UserProfileException(String message, int userId, String username) {
            super(message);
            this.userId = userId;
            this.username = username;
        }
        
        public int getUserId() {
            return userId;
        }
        
        public String getUsername() {
            return username;
        }
    }
    
    /**
     * Custom exception for database operations
     */
    static class DatabaseException extends Exception {
        public DatabaseException(String message) {
            super(message);
        }
    }
    
    /**
     * Custom exception for user data operations with exception chaining
     */
    static class UserDataException extends Exception {
        public UserDataException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
