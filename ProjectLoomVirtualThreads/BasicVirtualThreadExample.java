package ProjectLoomVirtualThreads;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Basic examples of creating and using virtual threads.
 * 
 * This class demonstrates:
 * 1. Creating virtual threads directly
 * 2. Using virtual thread factory
 * 3. Using virtual thread executor service
 * 4. Comparing with platform threads
 */
public class BasicVirtualThreadExample {

    private static final int THREAD_COUNT = 100_000;
    private static final AtomicInteger completedTasks = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        System.out.println("Java version: " + System.getProperty("java.version"));
        System.out.println("Available processors: " + Runtime.getRuntime().availableProcessors());
        System.out.println();

        // Example 1: Creating a single virtual thread
        createSingleVirtualThread();

        // Example 2: Using virtual thread factory
        usingVirtualThreadFactory();

        // Example 3: Using virtual thread executor service
        usingVirtualThreadExecutor();

        // Example 4: Comparing virtual threads with platform threads
        compareWithPlatformThreads();
    }

    /**
     * Example 1: Creating a single virtual thread
     */
    private static void createSingleVirtualThread() throws Exception {
        System.out.println("=== Example 1: Creating a single virtual thread ===");

        // Create and start a virtual thread
        Thread vthread = Thread.ofVirtual().name("my-virtual-thread").start(() -> {
            System.out.println("Running in virtual thread: " + Thread.currentThread());
            System.out.println("Is virtual: " + Thread.currentThread().isVirtual());
        });

        // Wait for the virtual thread to complete
        vthread.join();
        System.out.println();
    }

    /**
     * Example 2: Using virtual thread factory
     */
    private static void usingVirtualThreadFactory() throws Exception {
        System.out.println("=== Example 2: Using virtual thread factory ===");

        // Create a virtual thread factory
        ThreadFactory factory = Thread.ofVirtual().name("vthread-", 1).factory();

        // Create threads using the factory
        Thread vthread1 = factory.newThread(() -> {
            System.out.println("Thread from factory: " + Thread.currentThread().getName());
        });

        Thread vthread2 = factory.newThread(() -> {
            System.out.println("Another thread from factory: " + Thread.currentThread().getName());
        });

        // Start and wait for the threads
        vthread1.start();
        vthread2.start();
        vthread1.join();
        vthread2.join();
        System.out.println();
    }

    /**
     * Example 3: Using virtual thread executor service
     */
    private static void usingVirtualThreadExecutor() throws Exception {
        System.out.println("=== Example 3: Using virtual thread executor service ===");

        completedTasks.set(0);
        Instant start = Instant.now();

        // Create a virtual thread per task executor
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Submit many tasks
            for (int i = 0; i < THREAD_COUNT; i++) {
                final int taskId = i;
                executor.submit(() -> {
                    // Simulate some work
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    completedTasks.incrementAndGet();
                    if (taskId % 10000 == 0) {
                        System.out.println("Task " + taskId + " completed in " + Thread.currentThread());
                    }
                });
            }
            // The executor is automatically closed when exiting the try-with-resources block
        }

        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println("Completed " + completedTasks.get() + " tasks in " + duration.toMillis() + " ms");
        System.out.println();
    }

    /**
     * Example 4: Comparing virtual threads with platform threads
     */
    private static void compareWithPlatformThreads() throws Exception {
        System.out.println("=== Example 4: Comparing virtual threads with platform threads ===");

        // Test with platform threads
        System.out.println("Testing with platform threads:");
        testWithExecutor(Executors.newFixedThreadPool(200), 10000);

        // Test with virtual threads
        System.out.println("\nTesting with virtual threads:");
        testWithExecutor(Executors.newVirtualThreadPerTaskExecutor(), 10000);
        System.out.println();
    }

    /**
     * Helper method to test an executor service
     */
    private static void testWithExecutor(ExecutorService executor, int taskCount) throws Exception {
        completedTasks.set(0);
        Instant start = Instant.now();

        try (executor) {
            for (int i = 0; i < taskCount; i++) {
                executor.submit(() -> {
                    try {
                        // Simulate a task with I/O (blocking operation)
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    completedTasks.incrementAndGet();
                });
            }
        }

        Instant end = Instant.now();
        Duration duration = Duration.between(start, end);
        System.out.println("Completed " + completedTasks.get() + " tasks in " + duration.toMillis() + " ms");
        System.out.println("Thread type: " + (executor.getClass().getName().contains("Virtual") ? "Virtual" : "Platform"));
        System.out.println("Memory usage: " + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / (1024 * 1024) + " MB");
    }
}
