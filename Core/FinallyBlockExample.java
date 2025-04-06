package Core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * FinallyBlockExample
 * 
 * Demonstrates the behavior of finally blocks in various scenarios:
 * - Basic try-catch-finally
 * - Finally with return statements
 * - Finally with exceptions
 * - Nested try-catch-finally
 */
public class FinallyBlockExample {

    public static void main(String[] args) {
        System.out.println("===== Finally Block Examples =====\n");
        
        // Example 1: Basic try-catch-finally
        basicTryCatchFinally();
        
        // Example 2: Finally block always executes (even with return)
        System.out.println("\nReturn value from finallyWithReturn(): " + finallyWithReturn());
        
        // Example 3: Finally block with exception
        finallyWithException();
        
        // Example 4: Exception in finally block
        exceptionInFinally();
        
        // Example 5: Nested try-catch-finally
        nestedTryCatchFinally();
        
        // Example 6: Resource cleanup in finally
        resourceCleanupInFinally();
    }
    
    /**
     * Example 1: Basic try-catch-finally
     */
    private static void basicTryCatchFinally() {
        System.out.println("Example 1: Basic try-catch-finally");
        
        try {
            System.out.println("In try block");
            // Throw an exception
            throw new RuntimeException("Deliberate exception");
        } catch (RuntimeException e) {
            System.out.println("In catch block: " + e.getMessage());
        } finally {
            System.out.println("In finally block - This always executes");
        }
        
        System.out.println("After try-catch-finally");
    }
    
    /**
     * Example 2: Finally block always executes (even with return)
     */
    private static int finallyWithReturn() {
        System.out.println("\nExample 2: Finally with return statements");
        
        try {
            System.out.println("In try block");
            return 1; // This return is executed, but the finally block still runs
        } catch (Exception e) {
            System.out.println("In catch block");
            return 2; // This won't execute in this example
        } finally {
            System.out.println("In finally block - This always executes, even after return");
            // Note: If we had a return statement here, it would override the return from try/catch
            // return 3; // Uncommenting this would change the method's return value to 3
        }
    }
    
    /**
     * Example 3: Finally block with exception
     */
    private static void finallyWithException() {
        System.out.println("\nExample 3: Finally block with exception");
        
        try {
            System.out.println("In try block");
            int result = 10 / 0; // This will throw ArithmeticException
            System.out.println("This won't execute");
        } catch (NullPointerException e) {
            // This catch block doesn't match ArithmeticException
            System.out.println("In catch block for NullPointerException");
        } finally {
            System.out.println("In finally block - This executes even if exception isn't caught");
        }
        
        // This line won't execute because the exception wasn't caught
        System.out.println("This won't execute because the exception propagates");
    }
    
    /**
     * Example 4: Exception in finally block
     */
    private static void exceptionInFinally() {
        System.out.println("\nExample 4: Exception in finally block");
        
        try {
            System.out.println("In try block");
            // No exception here
        } catch (Exception e) {
            System.out.println("In catch block");
        } finally {
            System.out.println("In finally block - About to throw an exception");
            try {
                // Throwing an exception in finally
                throw new RuntimeException("Exception from finally block");
            } catch (RuntimeException e) {
                // Catching the exception to prevent it from propagating
                System.out.println("Caught exception in finally: " + e.getMessage());
            }
        }
        
        System.out.println("After try-catch-finally with exception in finally");
    }
    
    /**
     * Example 5: Nested try-catch-finally
     */
    private static void nestedTryCatchFinally() {
        System.out.println("\nExample 5: Nested try-catch-finally");
        
        try {
            System.out.println("In outer try block");
            
            try {
                System.out.println("In inner try block");
                throw new RuntimeException("Exception from inner try block");
            } catch (RuntimeException e) {
                System.out.println("In inner catch block: " + e.getMessage());
            } finally {
                System.out.println("In inner finally block");
            }
            
            System.out.println("After inner try-catch-finally");
            
        } catch (Exception e) {
            System.out.println("In outer catch block");
        } finally {
            System.out.println("In outer finally block");
        }
        
        System.out.println("After outer try-catch-finally");
    }
    
    /**
     * Example 6: Resource cleanup in finally
     */
    private static void resourceCleanupInFinally() {
        System.out.println("\nExample 6: Resource cleanup in finally");
        
        FileInputStream fis = null;
        
        try {
            System.out.println("In try block - Opening file");
            fis = new FileInputStream("nonexistent.txt"); // This will throw FileNotFoundException
            
            // Read from the file...
            int data = fis.read();
            
            System.out.println("This won't execute");
        } catch (FileNotFoundException e) {
            System.out.println("Caught FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Caught IOException: " + e.getMessage());
        } finally {
            System.out.println("In finally block - Cleaning up resources");
            
            // Close the file input stream if it was opened
            if (fis != null) {
                try {
                    fis.close();
                    System.out.println("File closed successfully");
                } catch (IOException e) {
                    System.out.println("Error closing file: " + e.getMessage());
                }
            } else {
                System.out.println("File was never opened");
            }
        }
        
        System.out.println("After resource cleanup example");
    }
}
