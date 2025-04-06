package Core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * BasicExceptionHandling
 * 
 * Demonstrates the fundamentals of exception handling in Java:
 * - Simple try-catch blocks
 * - Catching multiple exceptions
 * - Exception hierarchy
 * - Multi-catch syntax (Java 7+)
 */
public class BasicExceptionHandling {

    public static void main(String[] args) {
        System.out.println("===== Basic Exception Handling Examples =====\n");
        
        // Example 1: Simple try-catch with arithmetic exception
        simpleArithmeticException();
        
        // Example 2: Catching multiple exceptions
        multipleExceptions();
        
        // Example 3: Exception hierarchy
        exceptionHierarchy();
        
        // Example 4: Multi-catch syntax (Java 7+)
        multiCatchSyntax();
        
        // Example 5: Checked vs. Unchecked exceptions
        checkedVsUnchecked();
    }
    
    /**
     * Example 1: Simple try-catch with arithmetic exception
     */
    private static void simpleArithmeticException() {
        System.out.println("Example 1: Simple try-catch with arithmetic exception");
        
        try {
            // This will cause an ArithmeticException
            int result = 10 / 0;
            System.out.println("Result: " + result); // This line will not execute
        } catch (ArithmeticException e) {
            System.out.println("Caught an arithmetic exception: " + e.getMessage());
            // Optionally print the stack trace for debugging
            // e.printStackTrace();
        }
        
        System.out.println("Execution continues after the exception\n");
    }
    
    /**
     * Example 2: Catching multiple exceptions
     */
    private static void multipleExceptions() {
        System.out.println("Example 2: Catching multiple exceptions");
        
        try {
            // Get user input to demonstrate different exceptions
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter an index (0-4) to access in an array: ");
            int index = scanner.nextInt();
            
            int[] numbers = {1, 2, 3, 4, 5};
            System.out.println("Value at index " + index + ": " + numbers[index]);
            
            System.out.print("Enter a divisor: ");
            int divisor = scanner.nextInt();
            
            int result = numbers[index] / divisor;
            System.out.println("Result of division: " + result);
            
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array index out of bounds: " + e.getMessage());
        } catch (ArithmeticException e) {
            System.out.println("Arithmetic error: " + e.getMessage());
        } catch (Exception e) {
            // This will catch any other exceptions that weren't caught above
            System.out.println("Some other exception occurred: " + e.getMessage());
        }
        
        System.out.println("Execution continues after multiple exceptions handling\n");
    }
    
    /**
     * Example 3: Exception hierarchy
     */
    private static void exceptionHierarchy() {
        System.out.println("Example 3: Exception hierarchy");
        
        try {
            // This could cause various exceptions
            Object obj = null;
            obj.toString(); // Will cause NullPointerException
            
        } catch (NullPointerException e) {
            System.out.println("Caught NullPointerException: " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println("Caught RuntimeException: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Caught Exception: " + e.getMessage());
        }
        
        // Important: Order matters! More specific exceptions must come before more general ones
        // The following would cause a compilation error:
        /*
        try {
            // Some code
        } catch (Exception e) {
            // Handle general exception
        } catch (NullPointerException e) { // Compilation error: exception has already been caught
            // Handle null pointer
        }
        */
        
        System.out.println("Execution continues after exception hierarchy example\n");
    }
    
    /**
     * Example 4: Multi-catch syntax (Java 7+)
     */
    private static void multiCatchSyntax() {
        System.out.println("Example 4: Multi-catch syntax (Java 7+)");
        
        try {
            // Code that might throw different exceptions
            String str = args[0]; // Potential ArrayIndexOutOfBoundsException
            int num = Integer.parseInt(str); // Potential NumberFormatException
            
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            // Handle both exceptions the same way
            System.out.println("Caught either ArrayIndexOutOfBoundsException or NumberFormatException: " + e.getMessage());
            System.out.println("Exception type: " + e.getClass().getSimpleName());
        }
        
        System.out.println("Execution continues after multi-catch example\n");
    }
    
    /**
     * Example 5: Checked vs. Unchecked exceptions
     */
    private static void checkedVsUnchecked() {
        System.out.println("Example 5: Checked vs. Unchecked exceptions");
        
        // Unchecked exception - doesn't need to be caught or declared
        System.out.println("Unchecked exception example:");
        try {
            int[] arr = new int[5];
            arr[10] = 50; // ArrayIndexOutOfBoundsException (unchecked)
        } catch (RuntimeException e) {
            System.out.println("Caught unchecked exception: " + e.getClass().getSimpleName());
        }
        
        // Checked exception - must be caught or declared
        System.out.println("\nChecked exception example:");
        try {
            // FileNotFoundException is a checked exception
            FileReader file = new FileReader("nonexistent.txt");
        } catch (FileNotFoundException e) {
            System.out.println("Caught checked exception: " + e.getClass().getSimpleName());
            System.out.println("Message: " + e.getMessage());
        }
        
        // The following would cause a compilation error if not caught or declared:
        /*
        // This won't compile without try-catch or throws declaration
        FileReader file = new FileReader("nonexistent.txt");
        */
        
        System.out.println("\nExecution continues after checked vs. unchecked example\n");
    }
}
