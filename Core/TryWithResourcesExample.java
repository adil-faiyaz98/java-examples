package Core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * TryWithResourcesExample
 * 
 * Demonstrates the try-with-resources statement introduced in Java 7:
 * - Basic try-with-resources
 * - Multiple resources
 * - Comparison with traditional try-finally
 * - Suppressed exceptions
 * - Custom AutoCloseable resources
 */
public class TryWithResourcesExample {

    public static void main(String[] args) {
        System.out.println("===== Try-With-Resources Examples =====\n");
        
        // Example 1: Basic try-with-resources
        basicTryWithResources();
        
        // Example 2: Multiple resources
        multipleResources();
        
        // Example 3: Comparison with traditional try-finally
        System.out.println("\nExample 3: Comparison with traditional try-finally");
        System.out.println("Using traditional try-finally:");
        traditionalTryFinally();
        System.out.println("\nUsing try-with-resources:");
        modernTryWithResources();
        
        // Example 4: Suppressed exceptions
        suppressedExceptions();
        
        // Example 5: Custom AutoCloseable resource
        customAutoCloseable();
    }
    
    /**
     * Example 1: Basic try-with-resources
     */
    private static void basicTryWithResources() {
        System.out.println("Example 1: Basic try-with-resources");
        
        // The resource (BufferedReader) will be automatically closed when the try block exits
        try (BufferedReader reader = new BufferedReader(new StringReader("Hello, World!"))) {
            String line = reader.readLine();
            System.out.println("Read line: " + line);
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
        }
        
        // No need for a finally block to close the reader!
        System.out.println("BufferedReader has been automatically closed");
    }
    
    /**
     * Example 2: Multiple resources
     */
    private static void multipleResources() {
        System.out.println("\nExample 2: Multiple resources");
        
        // Multiple resources can be declared in the try-with-resources statement
        // They will be closed in reverse order (last-in-first-out)
        try (
            BufferedReader reader1 = new BufferedReader(new StringReader("First reader"));
            BufferedReader reader2 = new BufferedReader(new StringReader("Second reader"))
        ) {
            String line1 = reader1.readLine();
            String line2 = reader2.readLine();
            
            System.out.println("Read from first reader: " + line1);
            System.out.println("Read from second reader: " + line2);
            
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
        }
        
        System.out.println("Both readers have been automatically closed");
    }
    
    /**
     * Example 3a: Traditional try-finally approach (pre-Java 7)
     */
    private static void traditionalTryFinally() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new StringReader("Traditional approach"));
            String line = reader.readLine();
            System.out.println("Read line: " + line);
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
        } finally {
            // Must manually close the resource
            if (reader != null) {
                try {
                    reader.close();
                    System.out.println("Reader closed in finally block");
                } catch (IOException e) {
                    System.out.println("Error closing reader: " + e.getMessage());
                }
            }
        }
    }
    
    /**
     * Example 3b: Modern try-with-resources approach (Java 7+)
     */
    private static void modernTryWithResources() {
        try (BufferedReader reader = new BufferedReader(new StringReader("Modern approach"))) {
            String line = reader.readLine();
            System.out.println("Read line: " + line);
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
        }
        // Resource automatically closed!
        System.out.println("Reader automatically closed");
    }
    
    /**
     * Example 4: Suppressed exceptions
     */
    private static void suppressedExceptions() {
        System.out.println("\nExample 4: Suppressed exceptions");
        
        try (AutoCloseableResource resource = new AutoCloseableResource("Resource with exception")) {
            System.out.println("In try block with resource: " + resource.getName());
            
            // Throw an exception from the try block
            throw new RuntimeException("Exception from try block");
            
        } catch (Exception e) {
            System.out.println("Caught exception: " + e.getMessage());
            
            // Get suppressed exceptions (from close method)
            Throwable[] suppressed = e.getSuppressed();
            System.out.println("Number of suppressed exceptions: " + suppressed.length);
            
            for (Throwable t : suppressed) {
                System.out.println("Suppressed exception: " + t.getMessage());
            }
        }
    }
    
    /**
     * Example 5: Custom AutoCloseable resource
     */
    private static void customAutoCloseable() {
        System.out.println("\nExample 5: Custom AutoCloseable resource");
        
        try (DatabaseConnection dbConnection = new DatabaseConnection("jdbc:mydb://localhost/test", "user", "password")) {
            System.out.println("Connected to database: " + dbConnection.getUrl());
            dbConnection.executeQuery("SELECT * FROM users");
        } catch (Exception e) {
            System.out.println("Database operation failed: " + e.getMessage());
        }
    }
    
    /**
     * Custom AutoCloseable resource that throws an exception when closed
     */
    static class AutoCloseableResource implements AutoCloseable {
        private String name;
        
        public AutoCloseableResource(String name) {
            this.name = name;
            System.out.println("Resource created: " + name);
        }
        
        public String getName() {
            return name;
        }
        
        @Override
        public void close() throws Exception {
            System.out.println("Closing resource: " + name);
            // Simulate an exception during closing
            throw new Exception("Exception while closing resource: " + name);
        }
    }
    
    /**
     * Custom AutoCloseable resource for database connections
     */
    static class DatabaseConnection implements AutoCloseable {
        private String url;
        private String username;
        private String password;
        private boolean connected;
        
        public DatabaseConnection(String url, String username, String password) {
            this.url = url;
            this.username = username;
            this.password = password;
            
            // Simulate connecting to a database
            System.out.println("Connecting to database: " + url);
            this.connected = true;
        }
        
        public String getUrl() {
            return url;
        }
        
        public void executeQuery(String sql) {
            if (!connected) {
                throw new IllegalStateException("Not connected to database");
            }
            
            System.out.println("Executing SQL: " + sql);
            // Simulate query execution
        }
        
        @Override
        public void close() {
            if (connected) {
                System.out.println("Closing database connection to: " + url);
                connected = false;
            }
        }
    }
}
