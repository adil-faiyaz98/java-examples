package ProjectLoomVirtualThreads;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
 * Example demonstrating virtual threads with file I/O operations.
 * 
 * This class demonstrates:
 * 1. Reading and writing files with virtual threads
 * 2. Comparing with platform threads
 * 3. Handling many concurrent file operations
 */
public class FileOperationsExample {

    private static final int FILE_COUNT = 100;
    private static final int LINES_PER_FILE = 1000;
    private static final String TEST_DIR = "virtual_threads_test";
    private static final AtomicInteger completedOperations = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        System.out.println("=== File Operations with Virtual Threads Example ===");
        System.out.println("Java version: " + System.getProperty("java.version"));
        System.out.println();

        // Create test directory
        createTestDirectory();

        try {
            // Example 1: Writing files with platform threads
            writeFilesWithPlatformThreads();

            // Example 2: Writing files with virtual threads
            writeFilesWithVirtualThreads();

            // Example 3: Reading files with platform threads
            readFilesWithPlatformThreads();

            // Example 4: Reading files with virtual threads
            readFilesWithVirtualThreads();

            // Example 5: Many concurrent file operations with virtual threads
            manyConcurrentFileOperations();
        } finally {
            // Clean up test directory
            cleanupTestDirectory();
        }
    }

    /**
     * Example 1: Writing files with platform threads
     */
    private static void writeFilesWithPlatformThreads() throws Exception {
        System.out.println("=== Example 1: Writing files with platform threads ===");
        completedOperations.set(0);

        Instant start = Instant.now();

        // Create a fixed thread pool
        try (ExecutorService executor = Executors.newFixedThreadPool(10)) {
            CountDownLatch latch = new CountDownLatch(FILE_COUNT);

            // Submit tasks to write files
            for (int i = 0; i < FILE_COUNT; i++) {
                final int fileIndex = i;
                executor.submit(() -> {
                    try {
                        writeFile(fileIndex);
                        completedOperations.incrementAndGet();
                    } catch (IOException e) {
                        System.err.println("Error writing file " + fileIndex + ": " + e.getMessage());
                    } finally {
                        latch.countDown();
                    }
                });
            }

            // Wait for all tasks to complete
            latch.await();
        }

        Instant end = Instant.now();
        printResults("Writing files with platform threads", start, end);
    }

    /**
     * Example 2: Writing files with virtual threads
     */
    private static void writeFilesWithVirtualThreads() throws Exception {
        System.out.println("=== Example 2: Writing files with virtual threads ===");
        completedOperations.set(0);

        Instant start = Instant.now();

        // Create a virtual thread per task executor
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            CountDownLatch latch = new CountDownLatch(FILE_COUNT);

            // Submit tasks to write files
            for (int i = 0; i < FILE_COUNT; i++) {
                final int fileIndex = i;
                executor.submit(() -> {
                    try {
                        writeFile(fileIndex);
                        completedOperations.incrementAndGet();
                    } catch (IOException e) {
                        System.err.println("Error writing file " + fileIndex + ": " + e.getMessage());
                    } finally {
                        latch.countDown();
                    }
                });
            }

            // Wait for all tasks to complete
            latch.await();
        }

        Instant end = Instant.now();
        printResults("Writing files with virtual threads", start, end);
    }

    /**
     * Example 3: Reading files with platform threads
     */
    private static void readFilesWithPlatformThreads() throws Exception {
        System.out.println("=== Example 3: Reading files with platform threads ===");
        completedOperations.set(0);

        Instant start = Instant.now();

        // Create a fixed thread pool
        try (ExecutorService executor = Executors.newFixedThreadPool(10)) {
            CountDownLatch latch = new CountDownLatch(FILE_COUNT);

            // Submit tasks to read files
            for (int i = 0; i < FILE_COUNT; i++) {
                final int fileIndex = i;
                executor.submit(() -> {
                    try {
                        int lineCount = readFile(fileIndex);
                        completedOperations.incrementAndGet();
                        if (fileIndex % 10 == 0) {
                            System.out.println("Read " + lineCount + " lines from file " + fileIndex);
                        }
                    } catch (IOException e) {
                        System.err.println("Error reading file " + fileIndex + ": " + e.getMessage());
                    } finally {
                        latch.countDown();
                    }
                });
            }

            // Wait for all tasks to complete
            latch.await();
        }

        Instant end = Instant.now();
        printResults("Reading files with platform threads", start, end);
    }

    /**
     * Example 4: Reading files with virtual threads
     */
    private static void readFilesWithVirtualThreads() throws Exception {
        System.out.println("=== Example 4: Reading files with virtual threads ===");
        completedOperations.set(0);

        Instant start = Instant.now();

        // Create a virtual thread per task executor
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            CountDownLatch latch = new CountDownLatch(FILE_COUNT);

            // Submit tasks to read files
            for (int i = 0; i < FILE_COUNT; i++) {
                final int fileIndex = i;
                executor.submit(() -> {
                    try {
                        int lineCount = readFile(fileIndex);
                        completedOperations.incrementAndGet();
                        if (fileIndex % 10 == 0) {
                            System.out.println("Read " + lineCount + " lines from file " + fileIndex);
                        }
                    } catch (IOException e) {
                        System.err.println("Error reading file " + fileIndex + ": " + e.getMessage());
                    } finally {
                        latch.countDown();
                    }
                });
            }

            // Wait for all tasks to complete
            latch.await();
        }

        Instant end = Instant.now();
        printResults("Reading files with virtual threads", start, end);
    }

    /**
     * Example 5: Many concurrent file operations with virtual threads
     */
    private static void manyConcurrentFileOperations() throws Exception {
        System.out.println("=== Example 5: Many concurrent file operations with virtual threads ===");
        completedOperations.set(0);

        // Number of operations to perform
        final int OPERATION_COUNT = 1000;
        
        Instant start = Instant.now();

        // Create a virtual thread per task executor
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            CountDownLatch latch = new CountDownLatch(OPERATION_COUNT);
            List<Path> tempFiles = new ArrayList<>();

            // Submit tasks for various file operations
            for (int i = 0; i < OPERATION_COUNT; i++) {
                final int operationId = i;
                executor.submit(() -> {
                    try {
                        // Perform different operations based on the operation ID
                        if (operationId % 3 == 0) {
                            // Create a new temporary file
                            Path tempFile = Files.createTempFile(
                                    Path.of(TEST_DIR), 
                                    "temp_" + operationId + "_", 
                                    ".txt");
                            synchronized (tempFiles) {
                                tempFiles.add(tempFile);
                            }
                            
                            // Write some data to the file
                            Files.writeString(tempFile, "Data for operation " + operationId + "\n" +
                                    "Thread: " + Thread.currentThread() + "\n" +
                                    "Random UUID: " + UUID.randomUUID());
                            
                        } else if (operationId % 3 == 1) {
                            // Read an existing file
                            int fileIndex = operationId % FILE_COUNT;
                            readFile(fileIndex);
                            
                        } else {
                            // List files in the test directory
                            Files.list(Path.of(TEST_DIR))
                                    .limit(10)
                                    .forEach(path -> {
                                        // Just access the path to simulate some work
                                        path.getFileName().toString();
                                    });
                        }
                        
                        completedOperations.incrementAndGet();
                        if (operationId % 100 == 0) {
                            System.out.println("Completed operation " + operationId);
                        }
                    } catch (IOException e) {
                        System.err.println("Error in operation " + operationId + ": " + e.getMessage());
                    } finally {
                        latch.countDown();
                    }
                });
            }

            // Wait for all tasks to complete
            latch.await();
            
            // Clean up temporary files
            for (Path tempFile : tempFiles) {
                try {
                    Files.deleteIfExists(tempFile);
                } catch (IOException e) {
                    System.err.println("Error deleting temporary file: " + e.getMessage());
                }
            }
        }

        Instant end = Instant.now();
        printResults("Many concurrent file operations", start, end);
    }

    /**
     * Write a file with the specified index
     */
    private static void writeFile(int fileIndex) throws IOException {
        File file = new File(TEST_DIR, "file_" + fileIndex + ".txt");
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (int i = 0; i < LINES_PER_FILE; i++) {
                writer.write("Line " + i + " of file " + fileIndex + ": " + UUID.randomUUID());
                writer.newLine();
            }
        }
    }

    /**
     * Read a file with the specified index and return the number of lines
     */
    private static int readFile(int fileIndex) throws IOException {
        File file = new File(TEST_DIR, "file_" + fileIndex + ".txt");
        int lineCount = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineCount++;
            }
        }
        
        return lineCount;
    }

    /**
     * Create the test directory
     */
    private static void createTestDirectory() throws IOException {
        File dir = new File(TEST_DIR);
        if (!dir.exists()) {
            if (!dir.mkdir()) {
                throw new IOException("Failed to create test directory");
            }
        }
        System.out.println("Created test directory: " + dir.getAbsolutePath());
    }

    /**
     * Clean up the test directory
     */
    private static void cleanupTestDirectory() {
        File dir = new File(TEST_DIR);
        if (dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.delete()) {
                        System.err.println("Failed to delete file: " + file.getAbsolutePath());
                    }
                }
            }
            if (!dir.delete()) {
                System.err.println("Failed to delete test directory");
            } else {
                System.out.println("Cleaned up test directory");
            }
        }
    }

    /**
     * Print the results of the file operations
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
