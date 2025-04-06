package ProjectLoomVirtualThreads;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

/**
 * Performance benchmark comparing virtual threads with platform threads.
 * 
 * This class demonstrates:
 * 1. Throughput comparison
 * 2. Scaling with concurrent tasks
 * 3. Memory usage comparison
 * 4. CPU-bound vs I/O-bound tasks
 */
public class VirtualThreadPerformanceBenchmark {

    // Benchmark parameters
    private static final int WARMUP_ITERATIONS = 3;
    private static final int BENCHMARK_ITERATIONS = 5;
    private static final int[] THREAD_COUNTS = {1000, 10_000, 100_000, 1_000_000};

    // Task parameters
    private static final int TASK_DURATION_MS = 10; // Duration of simulated work
    private static final boolean SIMULATE_IO_BLOCKING = true; // Whether to simulate I/O blocking

    public static void main(String[] args) throws Exception {
        System.out.println("=== Virtual Thread Performance Benchmark ===");
        System.out.println("Java version: " + System.getProperty("java.version"));
        System.out.println("Available processors: " + Runtime.getRuntime().availableProcessors());
        System.out.println("Task type: " + (SIMULATE_IO_BLOCKING ? "I/O-bound" : "CPU-bound"));
        System.out.println("Task duration: " + TASK_DURATION_MS + " ms");
        System.out.println();

        // Warm up the JVM
        System.out.println("Warming up JVM...");
        for (int i = 0; i < WARMUP_ITERATIONS; i++) {
            runBenchmark(1000, true);
            runBenchmark(1000, false);
            System.gc(); // Suggest garbage collection between iterations
        }
        System.out.println("Warm-up complete");
        System.out.println();

        // Run the benchmark with different thread counts
        System.out.println("Starting benchmark...");
        System.out.println("Thread Count | Platform Threads | Virtual Threads | Improvement Factor");
        System.out.println("------------|-----------------|----------------|-------------------");

        for (int threadCount : THREAD_COUNTS) {
            // Skip very large thread counts for platform threads to avoid OutOfMemoryError
            if (threadCount > 100_000) {
                System.out.printf("%-12d | %-16s | ", threadCount, "SKIPPED");
                double virtualThreadTime = runBenchmark(threadCount, true);
                System.out.printf("%-15.2f | %-19s%n", virtualThreadTime, "N/A");
            } else {
                double platformThreadTime = runBenchmark(threadCount, false);
                double virtualThreadTime = runBenchmark(threadCount, true);
                double improvementFactor = platformThreadTime / virtualThreadTime;
                
                System.out.printf("%-12d | %-16.2f | %-15.2f | %-19.2fx%n", 
                        threadCount, platformThreadTime, virtualThreadTime, improvementFactor);
            }
            
            // Force garbage collection between runs
            System.gc();
            Thread.sleep(1000);
        }
    }

    /**
     * Run a benchmark with the specified number of threads.
     * 
     * @param threadCount the number of threads to use
     * @param useVirtualThreads whether to use virtual threads
     * @return the average execution time in seconds
     */
    private static double runBenchmark(int threadCount, boolean useVirtualThreads) throws Exception {
        long[] executionTimes = new long[BENCHMARK_ITERATIONS];
        
        for (int i = 0; i < BENCHMARK_ITERATIONS; i++) {
            // Record memory before test
            long memoryBefore = getUsedMemory();
            
            // Run the test
            Instant start = Instant.now();
            
            if (useVirtualThreads) {
                runWithVirtualThreads(threadCount);
            } else {
                runWithPlatformThreads(threadCount);
            }
            
            Instant end = Instant.now();
            
            // Record memory after test
            long memoryAfter = getUsedMemory();
            long memoryUsed = memoryAfter - memoryBefore;
            
            // Calculate execution time
            executionTimes[i] = Duration.between(start, end).toMillis();
            
            // Only print memory usage for the first iteration to reduce output
            if (i == 0) {
                String threadType = useVirtualThreads ? "Virtual" : "Platform";
                System.out.printf("  [%s threads: %d threads, Memory: %d MB]%n", 
                        threadType, threadCount, memoryUsed / (1024 * 1024));
            }
        }
        
        // Calculate average execution time (excluding the first run)
        long totalTime = 0;
        for (int i = 1; i < BENCHMARK_ITERATIONS; i++) {
            totalTime += executionTimes[i];
        }
        
        return totalTime / (double) (BENCHMARK_ITERATIONS - 1) / 1000.0; // Return average time in seconds
    }

    /**
     * Run the benchmark with platform threads.
     * 
     * @param threadCount the number of threads to use
     */
    private static void runWithPlatformThreads(int threadCount) throws Exception {
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger activeThreads = new AtomicInteger(0);
        LongAdder completedTasks = new LongAdder();
        
        // Create a fixed thread pool
        try (ExecutorService executor = Executors.newFixedThreadPool(
                Math.min(threadCount, Runtime.getRuntime().availableProcessors() * 2))) {
            
            // Submit tasks
            for (int i = 0; i < threadCount; i++) {
                executor.submit(() -> {
                    activeThreads.incrementAndGet();
                    try {
                        performTask();
                        completedTasks.increment();
                    } finally {
                        activeThreads.decrementAndGet();
                        latch.countDown();
                    }
                });
            }
            
            // Wait for all tasks to complete
            latch.await();
        }
    }

    /**
     * Run the benchmark with virtual threads.
     * 
     * @param threadCount the number of threads to use
     */
    private static void runWithVirtualThreads(int threadCount) throws Exception {
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger activeThreads = new AtomicInteger(0);
        LongAdder completedTasks = new LongAdder();
        
        // Create a virtual thread per task executor
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            
            // Submit tasks
            for (int i = 0; i < threadCount; i++) {
                executor.submit(() -> {
                    activeThreads.incrementAndGet();
                    try {
                        performTask();
                        completedTasks.increment();
                    } finally {
                        activeThreads.decrementAndGet();
                        latch.countDown();
                    }
                });
            }
            
            // Wait for all tasks to complete
            latch.await();
        }
    }

    /**
     * Perform a task that simulates either I/O-bound or CPU-bound work.
     */
    private static void performTask() {
        if (SIMULATE_IO_BLOCKING) {
            // Simulate I/O-bound task (blocking operation)
            try {
                Thread.sleep(TASK_DURATION_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } else {
            // Simulate CPU-bound task (busy waiting)
            long endTime = System.currentTimeMillis() + TASK_DURATION_MS;
            while (System.currentTimeMillis() < endTime) {
                // Busy wait
                for (int i = 0; i < 1000; i++) {
                    Math.sqrt(i);
                }
            }
        }
    }

    /**
     * Get the current used memory in bytes.
     * 
     * @return the used memory in bytes
     */
    private static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
