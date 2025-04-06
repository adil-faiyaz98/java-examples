package ProjectLoomVirtualThreads;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Example demonstrating virtual threads with database operations.
 * 
 * This class demonstrates:
 * 1. Database operations with virtual threads
 * 2. Comparing with platform threads
 * 3. Connection pooling considerations
 * 
 * Note: This example uses H2 in-memory database for simplicity.
 * You need to add H2 dependency to run this example:
 * - com.h2database:h2:2.2.220
 */
public class DatabaseExample {

    private static final String JDBC_URL = "jdbc:h2:mem:virtualthreadsdb;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    
    private static final int THREAD_COUNT = 100;
    private static final int OPERATIONS_PER_THREAD = 10;
    private static final AtomicInteger completedOperations = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        System.out.println("=== Database Operations with Virtual Threads Example ===");
        System.out.println("Java version: " + System.getProperty("java.version"));
        System.out.println();

        try {
            // Initialize database
            initializeDatabase();

            // Example 1: Database operations with platform threads
            databaseOperationsWithPlatformThreads();

            // Example 2: Database operations with virtual threads
            databaseOperationsWithVirtualThreads();

            // Example 3: Connection pooling considerations
            connectionPoolingConsiderations();
        } catch (ClassNotFoundException e) {
            System.out.println("H2 database driver not found. Please add H2 dependency to run this example.");
            System.out.println("Maven: <dependency><groupId>com.h2database</groupId><artifactId>h2</artifactId><version>2.2.220</version></dependency>");
            System.out.println("Gradle: implementation 'com.h2database:h2:2.2.220'");
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Clean up database
            cleanupDatabase();
        }
    }

    /**
     * Example 1: Database operations with platform threads
     */
    private static void databaseOperationsWithPlatformThreads() throws Exception {
        System.out.println("=== Example 1: Database operations with platform threads ===");
        completedOperations.set(0);

        Instant start = Instant.now();

        // Create a fixed thread pool
        try (ExecutorService executor = Executors.newFixedThreadPool(20)) {
            CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

            // Submit tasks to perform database operations
            for (int i = 0; i < THREAD_COUNT; i++) {
                final int threadId = i;
                executor.submit(() -> {
                    try {
                        performDatabaseOperations(threadId);
                    } catch (SQLException e) {
                        System.err.println("Error in thread " + threadId + ": " + e.getMessage());
                    } finally {
                        latch.countDown();
                    }
                });
            }

            // Wait for all tasks to complete
            latch.await();
        }

        Instant end = Instant.now();
        printResults("Database operations with platform threads", start, end);
    }

    /**
     * Example 2: Database operations with virtual threads
     */
    private static void databaseOperationsWithVirtualThreads() throws Exception {
        System.out.println("=== Example 2: Database operations with virtual threads ===");
        completedOperations.set(0);

        Instant start = Instant.now();

        // Create a virtual thread per task executor
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

            // Submit tasks to perform database operations
            for (int i = 0; i < THREAD_COUNT; i++) {
                final int threadId = i;
                executor.submit(() -> {
                    try {
                        performDatabaseOperations(threadId);
                    } catch (SQLException e) {
                        System.err.println("Error in thread " + threadId + ": " + e.getMessage());
                    } finally {
                        latch.countDown();
                    }
                });
            }

            // Wait for all tasks to complete
            latch.await();
        }

        Instant end = Instant.now();
        printResults("Database operations with virtual threads", start, end);
    }

    /**
     * Example 3: Connection pooling considerations
     */
    private static void connectionPoolingConsiderations() throws Exception {
        System.out.println("=== Example 3: Connection pooling considerations ===");
        
        System.out.println("\nConnection Pooling with Virtual Threads - Considerations:");
        System.out.println("1. Traditional connection pools were designed for platform threads");
        System.out.println("2. With virtual threads, you might need fewer pooled connections");
        System.out.println("3. Some connection pools might not work well with virtual threads");
        System.out.println("4. Best practices:");
        System.out.println("   - Use a connection pool that's virtual thread aware");
        System.out.println("   - Consider smaller pool sizes than with platform threads");
        System.out.println("   - Monitor connection usage patterns");
        System.out.println("   - Some applications might not need connection pooling with virtual threads");
        
        // Demonstrate a simple connection per operation approach
        System.out.println("\nDemonstrating connection per operation approach:");
        completedOperations.set(0);

        Instant start = Instant.now();

        // Create a virtual thread per task executor
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            CountDownLatch latch = new CountDownLatch(THREAD_COUNT);

            // Submit tasks to perform database operations
            for (int i = 0; i < THREAD_COUNT; i++) {
                final int threadId = i;
                executor.submit(() -> {
                    try {
                        performDatabaseOperationsWithoutPooling(threadId);
                    } catch (SQLException e) {
                        System.err.println("Error in thread " + threadId + ": " + e.getMessage());
                    } finally {
                        latch.countDown();
                    }
                });
            }

            // Wait for all tasks to complete
            latch.await();
        }

        Instant end = Instant.now();
        printResults("Database operations without connection pooling", start, end);
        
        System.out.println("\nNote: For real-world applications, consider using connection pools like:");
        System.out.println("1. HikariCP (popular and high-performance)");
        System.out.println("2. Apache DBCP");
        System.out.println("3. c3p0");
        System.out.println("4. Newer pools designed for virtual threads");
        System.out.println();
    }

    /**
     * Perform database operations for a thread
     */
    private static void performDatabaseOperations(int threadId) throws SQLException {
        // Get a connection from the "pool" (in this case, just creating a new connection)
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD)) {
            // Perform multiple operations
            for (int i = 0; i < OPERATIONS_PER_THREAD; i++) {
                // Insert a record
                String id = UUID.randomUUID().toString();
                String name = "Item-" + threadId + "-" + i;
                int value = threadId * 100 + i;
                
                try (PreparedStatement pstmt = connection.prepareStatement(
                        "INSERT INTO items (id, name, value) VALUES (?, ?, ?)")) {
                    pstmt.setString(1, id);
                    pstmt.setString(2, name);
                    pstmt.setInt(3, value);
                    pstmt.executeUpdate();
                }
                
                // Query the record
                try (PreparedStatement pstmt = connection.prepareStatement(
                        "SELECT * FROM items WHERE id = ?")) {
                    pstmt.setString(1, id);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            // Just access the data to simulate some work
                            rs.getString("name");
                            rs.getInt("value");
                        }
                    }
                }
                
                // Update the record
                try (PreparedStatement pstmt = connection.prepareStatement(
                        "UPDATE items SET value = ? WHERE id = ?")) {
                    pstmt.setInt(1, value + 1);
                    pstmt.setString(2, id);
                    pstmt.executeUpdate();
                }
                
                // Simulate some processing time
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                
                completedOperations.incrementAndGet();
            }
        }
    }

    /**
     * Perform database operations without connection pooling
     */
    private static void performDatabaseOperationsWithoutPooling(int threadId) throws SQLException {
        // Perform multiple operations, creating a new connection for each operation
        for (int i = 0; i < OPERATIONS_PER_THREAD; i++) {
            String id = UUID.randomUUID().toString();
            String name = "Item-" + threadId + "-" + i;
            int value = threadId * 100 + i;
            
            // Insert operation with its own connection
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                 PreparedStatement pstmt = connection.prepareStatement(
                         "INSERT INTO items (id, name, value) VALUES (?, ?, ?)")) {
                pstmt.setString(1, id);
                pstmt.setString(2, name);
                pstmt.setInt(3, value);
                pstmt.executeUpdate();
            }
            
            // Query operation with its own connection
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                 PreparedStatement pstmt = connection.prepareStatement(
                         "SELECT * FROM items WHERE id = ?")) {
                pstmt.setString(1, id);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        // Just access the data to simulate some work
                        rs.getString("name");
                        rs.getInt("value");
                    }
                }
            }
            
            // Update operation with its own connection
            try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
                 PreparedStatement pstmt = connection.prepareStatement(
                         "UPDATE items SET value = ? WHERE id = ?")) {
                pstmt.setInt(1, value + 1);
                pstmt.setString(2, id);
                pstmt.executeUpdate();
            }
            
            // Simulate some processing time
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            
            completedOperations.incrementAndGet();
        }
    }

    /**
     * Initialize the database
     */
    private static void initializeDatabase() throws ClassNotFoundException, SQLException {
        // Load the H2 JDBC driver
        Class.forName("org.h2.Driver");
        
        // Create a connection
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement stmt = connection.createStatement()) {
            
            // Create the items table
            stmt.execute("CREATE TABLE items (" +
                    "id VARCHAR(36) PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "value INT NOT NULL" +
                    ")");
            
            System.out.println("Database initialized");
        }
    }

    /**
     * Clean up the database
     */
    private static void cleanupDatabase() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement stmt = connection.createStatement()) {
            
            // Get the count of items
            try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM items")) {
                if (rs.next()) {
                    System.out.println("Total items in database: " + rs.getInt(1));
                }
            }
            
            // Drop the items table
            stmt.execute("DROP TABLE items");
            
            System.out.println("Database cleaned up");
        } catch (SQLException e) {
            System.err.println("Error cleaning up database: " + e.getMessage());
        }
    }

    /**
     * Print the results of the database operations
     */
    private static void printResults(String operation, Instant start, Instant end) {
        Duration duration = Duration.between(start, end);
        System.out.println(operation + ":");
        System.out.println("Total time: " + duration.toMillis() + " ms");
        System.out.println("Completed operations: " + completedOperations.get());
        System.out.println("Operations per second: " + 
                String.format("%.2f", completedOperations.get() / (duration.toMillis() / 1000.0)));
        System.out.println();
    }
}
