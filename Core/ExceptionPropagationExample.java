package Core;

import java.io.IOException;

/**
 * ExceptionPropagationExample
 * 
 * Demonstrates how exceptions propagate through the call stack:
 * - Unchecked exception propagation
 * - Checked exception propagation
 * - Re-throwing exceptions
 * - Partial handling of exceptions
 */
public class ExceptionPropagationExample {

    public static void main(String[] args) {
        System.out.println("===== Exception Propagation Examples =====\n");
        
        // Example 1: Unchecked exception propagation
        uncheckedExceptionPropagation();
        
        // Example 2: Checked exception propagation
        checkedExceptionPropagation();
        
        // Example 3: Re-throwing exceptions
        rethrowingExceptions();
        
        // Example 4: Partial handling of exceptions
        partialHandling();
    }
    
    /**
     * Example 1: Unchecked exception propagation
     */
    private static void uncheckedExceptionPropagation() {
        System.out.println("Example 1: Unchecked exception propagation");
        
        try {
            // Call a chain of methods that will eventually throw an unchecked exception
            methodA();
        } catch (RuntimeException e) {
            System.out.println("Caught in main: " + e.getMessage());
            System.out.println("Stack trace shows the propagation path:");
            
            // Print the first few elements of the stack trace
            StackTraceElement[] stackTrace = e.getStackTrace();
            for (int i = 0; i < Math.min(4, stackTrace.length); i++) {
                System.out.println("  at " + stackTrace[i]);
            }
        }
        
        System.out.println("After unchecked exception propagation example\n");
    }
    
    // Chain of method calls for unchecked exception propagation
    private static void methodA() {
        System.out.println("In methodA()");
        methodB();
    }
    
    private static void methodB() {
        System.out.println("In methodB()");
        methodC();
    }
    
    private static void methodC() {
        System.out.println("In methodC()");
        // Throw an unchecked exception
        throw new RuntimeException("Exception thrown in methodC");
    }
    
    /**
     * Example 2: Checked exception propagation
     */
    private static void checkedExceptionPropagation() {
        System.out.println("Example 2: Checked exception propagation");
        
        try {
            // Call a chain of methods that will eventually throw a checked exception
            methodX();
        } catch (IOException e) {
            System.out.println("Caught in main: " + e.getMessage());
        }
        
        System.out.println("After checked exception propagation example\n");
    }
    
    // Chain of method calls for checked exception propagation
    private static void methodX() throws IOException {
        System.out.println("In methodX()");
        methodY();
    }
    
    private static void methodY() throws IOException {
        System.out.println("In methodY()");
        methodZ();
    }
    
    private static void methodZ() throws IOException {
        System.out.println("In methodZ()");
        // Throw a checked exception
        throw new IOException("Checked exception thrown in methodZ");
    }
    
    /**
     * Example 3: Re-throwing exceptions
     */
    private static void rethrowingExceptions() {
        System.out.println("Example 3: Re-throwing exceptions");
        
        try {
            processFile("data.txt");
        } catch (IOException e) {
            System.out.println("Caught in main: " + e.getMessage());
            
            // Check if there's a cause
            if (e.getCause() != null) {
                System.out.println("Original cause: " + e.getCause().getMessage());
            }
        }
        
        System.out.println("After re-throwing exceptions example\n");
    }
    
    private static void processFile(String filename) throws IOException {
        try {
            readFile(filename);
        } catch (IOException e) {
            System.out.println("Caught in processFile: " + e.getMessage());
            
            // Re-throw the exception with additional context
            throw new IOException("Error processing file: " + filename, e);
        }
    }
    
    private static void readFile(String filename) throws IOException {
        // Simulate a file reading error
        throw new IOException("File not found: " + filename);
    }
    
    /**
     * Example 4: Partial handling of exceptions
     */
    private static void partialHandling() {
        System.out.println("Example 4: Partial handling of exceptions");
        
        try {
            performOperation();
        } catch (Exception e) {
            System.out.println("Caught in main: " + e.getMessage());
        }
        
        System.out.println("After partial handling example\n");
    }
    
    private static void performOperation() {
        try {
            // This will throw an ArithmeticException
            int result = 10 / 0;
        } catch (ArithmeticException e) {
            System.out.println("Caught ArithmeticException: " + e.getMessage());
            
            // Perform some cleanup
            System.out.println("Performing cleanup after arithmetic error");
            
            // Re-throw a different exception
            throw new IllegalStateException("Operation failed due to calculation error", e);
        }
    }
}
