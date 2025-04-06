package Core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * EffectiveExceptionHandling
 * 
 * Demonstrates best practices and common pitfalls in exception handling:
 * - Proper exception handling patterns
 * - Common anti-patterns to avoid
 * - Logging exceptions properly
 * - Exception handling strategies
 */
public class EffectiveExceptionHandling {
    
    // Create a logger for this class
    private static final Logger LOGGER = Logger.getLogger(EffectiveExceptionHandling.class.getName());

    public static void main(String[] args) {
        System.out.println("===== Effective Exception Handling Examples =====\n");
        
        // Example 1: Don't catch exceptions you can't handle
        catchingAppropriateExceptions();
        
        // Example 2: Don't swallow exceptions
        doNotSwallowExceptions();
        
        // Example 3: Proper logging of exceptions
        properExceptionLogging();
        
        // Example 4: Wrapping exceptions
        wrappingExceptions();
        
        // Example 5: Specific vs. general exception handling
        specificVsGeneralExceptions();
        
        // Example 6: Clean up resources properly
        cleanupResources();
    }
    
    /**
     * Example 1: Don't catch exceptions you can't handle
     */
    private static void catchingAppropriateExceptions() {
        System.out.println("Example 1: Catching appropriate exceptions");
        
        System.out.println("Good practice - only catch exceptions you can handle:");
        try {
            int value = parseInteger("abc");
            System.out.println("Parsed value: " + value);
        } catch (NumberFormatException e) {
            // We can handle this specific exception with a meaningful fallback
            System.out.println("Invalid number format. Using default value instead.");
            int defaultValue = 0;
            System.out.println("Using default value: " + defaultValue);
        }
        
        System.out.println("\nBad practice - catching Exception when you can only handle specific ones:");
        try {
            // This could throw various exceptions, not just NumberFormatException
            int value = parseInteger("123");
            int result = 100 / value; // Potential ArithmeticException
            System.out.println("Result: " + result);
        } catch (Exception e) {
            // Too general - we're catching exceptions we might not be able to handle properly
            System.out.println("Something went wrong: " + e.getMessage());
            // This doesn't provide a meaningful recovery strategy for all possible exceptions
        }
        
        System.out.println("After catching appropriate exceptions example\n");
    }
    
    private static int parseInteger(String str) {
        return Integer.parseInt(str);
    }
    
    /**
     * Example 2: Don't swallow exceptions
     */
    private static void doNotSwallowExceptions() {
        System.out.println("Example 2: Don't swallow exceptions");
        
        System.out.println("Bad practice - swallowing exceptions:");
        try {
            riskyOperation();
        } catch (Exception e) {
            // Empty catch block or not doing anything with the exception
            // This is a bad practice known as "exception swallowing"
        }
        
        System.out.println("\nGood practice - proper handling or propagation:");
        try {
            riskyOperation();
        } catch (RuntimeException e) {
            // At minimum, log the exception
            System.out.println("Error occurred: " + e.getMessage());
            
            // And/or rethrow if you can't handle it
            // throw e;
        }
        
        System.out.println("After don't swallow exceptions example\n");
    }
    
    private static void riskyOperation() {
        throw new RuntimeException("Something went wrong in the risky operation");
    }
    
    /**
     * Example 3: Proper logging of exceptions
     */
    private static void properExceptionLogging() {
        System.out.println("Example 3: Proper logging of exceptions");
        
        System.out.println("Bad practice - just printing the exception message:");
        try {
            throw new IOException("File not found");
        } catch (IOException e) {
            // Just printing the message loses the stack trace
            System.out.println("Error: " + e.getMessage());
        }
        
        System.out.println("\nGood practice - logging with proper level and stack trace:");
        try {
            throw new IOException("Database connection failed");
        } catch (IOException e) {
            // Log with appropriate level and include the exception for stack trace
            LOGGER.log(Level.SEVERE, "Failed to connect to database", e);
            
            // For demonstration purposes, also print to console
            System.out.println("Properly logged the exception with stack trace information");
        }
        
        System.out.println("After proper exception logging example\n");
    }
    
    /**
     * Example 4: Wrapping exceptions
     */
    private static void wrappingExceptions() {
        System.out.println("Example 4: Wrapping exceptions");
        
        try {
            // Call a method that might throw a low-level exception
            processBusinessOperation();
        } catch (BusinessException e) {
            System.out.println("Business operation failed: " + e.getMessage());
            
            // Get the original cause
            Throwable cause = e.getCause();
            if (cause != null) {
                System.out.println("Original technical error: " + cause.getMessage());
            }
        }
        
        System.out.println("After wrapping exceptions example\n");
    }
    
    private static void processBusinessOperation() throws BusinessException {
        try {
            // Simulate a low-level SQL exception
            throw new SQLException("Database query syntax error");
        } catch (SQLException e) {
            // Wrap the technical exception in a business-level exception
            throw new BusinessException("Unable to complete customer registration", e);
        }
    }
    
    /**
     * Example 5: Specific vs. general exception handling
     */
    private static void specificVsGeneralExceptions() {
        System.out.println("Example 5: Specific vs. general exception handling");
        
        System.out.println("Good practice - catch specific exceptions first:");
        try {
            // This could throw different types of exceptions
            int[] array = new int[5];
            array[10] = 100; // ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException e) {
            // Handle specific exception
            System.out.println("Array index out of bounds: " + e.getMessage());
        } catch (RuntimeException e) {
            // Handle more general exception
            System.out.println("Runtime exception: " + e.getMessage());
        } catch (Exception e) {
            // Handle most general exception
            System.out.println("General exception: " + e.getMessage());
        }
        
        System.out.println("\nBad practice - this won't compile because general exception is caught first:");
        /*
        try {
            int[] array = new int[5];
            array[10] = 100;
        } catch (Exception e) {
            // General exception caught first
            System.out.println("Exception: " + e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            // This is unreachable - compiler error
            System.out.println("Array index out of bounds: " + e.getMessage());
        }
        */
        
        System.out.println("After specific vs. general exceptions example\n");
    }
    
    /**
     * Example 6: Clean up resources properly
     */
    private static void cleanupResources() {
        System.out.println("Example 6: Clean up resources properly");
        
        System.out.println("Bad practice - resource might not be closed if exception occurs:");
        BufferedReader badReader = null;
        try {
            badReader = new BufferedReader(new FileReader("nonexistent.txt"));
            String line = badReader.readLine();
            System.out.println("Read line: " + line);
            badReader.close(); // This won't execute if an exception occurs above
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
        }
        
        System.out.println("\nGood practice - using try-with-resources:");
        try (BufferedReader goodReader = new BufferedReader(new FileReader("nonexistent.txt"))) {
            String line = goodReader.readLine();
            System.out.println("Read line: " + line);
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
        }
        // Resource is automatically closed, even if an exception occurs
        
        System.out.println("\nAlternative good practice - using finally block:");
        BufferedReader finallyReader = null;
        try {
            finallyReader = new BufferedReader(new FileReader("nonexistent.txt"));
            String line = finallyReader.readLine();
            System.out.println("Read line: " + line);
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
        } finally {
            // Close the resource in finally block
            if (finallyReader != null) {
                try {
                    finallyReader.close();
                    System.out.println("Reader closed in finally block");
                } catch (IOException e) {
                    System.out.println("Error closing reader: " + e.getMessage());
                }
            }
        }
        
        System.out.println("After clean up resources example\n");
    }
    
    /**
     * Custom business exception
     */
    static class BusinessException extends Exception {
        public BusinessException(String message) {
            super(message);
        }
        
        public BusinessException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
