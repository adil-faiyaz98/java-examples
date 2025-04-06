package Multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolPatterns
 * 
 * Demonstrates common thread pool patterns and best practices in Java:
 * 1. Worker Thread Pool
 * 2. Thread Pool Sizing
 * 3. Fork/Join Framework
 * 4. Scheduled Tasks
 * 5. Graceful Shutdown
 */
public class ThreadPoolPatterns {

    public static void main(String[] args) {
        System.out.println("===== Thread Pool Patterns =====");
        
        // Example 1: Worker Thread Pool
        workerThreadPoolExample();
        
        // Example 2: Thread Pool Sizing
        threadPoolSizingExample();
        
        // Example 3: Fork/Join Framework
        forkJoinExample();
        
        // Example 4: Scheduled Tasks
        scheduledTasksExample();
        
        // Example 5: Graceful Shutdown
        gracefulShutdownExample();
        
        System.out.println("\n===== End of Thread Pool Patterns =====");
    }
    
    /**
     * Example 1: Worker Thread Pool
     * 
     * Demonstrates a common worker thread pool pattern where tasks are submitted
     * to a fixed-size thread pool for execution.
     */
    private static void workerThreadPoolExample() {
        System.out.println("\n1. Worker Thread Pool Example:");
        
        // Create a fixed thread pool with the number of available processors
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("Creating thread pool with " + processors + " threads (number of processors)");
        
        ExecutorService executor = Executors.newFixedThreadPool(processors);
        
        try {
            // Create a list to hold the Future objects
            List<Future<Integer>> results = new ArrayList<>();
            
            // Submit tasks to the executor
            for (int i = 0; i < 10; i++) {
                final int taskId = i;
                Future<Integer> result = executor.submit(new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                        System.out.println("Task " + taskId + " executing in " + Thread.currentThread().getName());
                        
                        // Simulate some work
                        Thread.sleep(new Random().nextInt(1000) + 500);
                        
                        // Return a result
                        return taskId * 10;
                    }
                });
                
                results.add(result);
            }
            
            // Process the results as they become available
            for (int i = 0; i < results.size(); i++) {
                try {
                    Integer result = results.get(i).get(); // This will block until the result is available
                    System.out.println("Task " + i + " result: " + result);
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Error getting result of task " + i + ": " + e.getMessage());
                }
            }
            
        } finally {
            // Shutdown the executor when done
            executor.shutdown();
            try {
                // Wait for all tasks to complete or timeout after 5 seconds
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
    
    /**
     * Example 2: Thread Pool Sizing
     * 
     * Demonstrates different thread pool sizes and their impact on performance.
     */
    private static void threadPoolSizingExample() {
        System.out.println("\n2. Thread Pool Sizing Example:");
        
        // Get the number of available processors
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("Available processors: " + processors);
        
        // Test with different thread pool sizes
        testThreadPoolSize(1, "Single Thread");
        testThreadPoolSize(processors, "Processor Count");
        testThreadPoolSize(processors * 2, "Double Processor Count");
        
        // For I/O-bound tasks, a larger thread pool might be beneficial
        System.out.println("\nFor I/O-bound tasks, a larger thread pool might be beneficial.");
        System.out.println("For CPU-bound tasks, a thread pool size equal to the number of processors is usually optimal.");
    }
    
    /**
     * Helper method to test a specific thread pool size
     */
    private static void testThreadPoolSize(int poolSize, String description) {
        System.out.println("\nTesting thread pool size: " + poolSize + " (" + description + ")");
        
        ExecutorService executor = Executors.newFixedThreadPool(poolSize);
        
        try {
            // Create a list to hold the Future objects
            List<Future<Long>> results = new ArrayList<>();
            
            // Record start time
            long startTime = System.currentTimeMillis();
            
            // Submit CPU-intensive tasks to the executor
            for (int i = 0; i < 20; i++) {
                final int taskId = i;
                Future<Long> result = executor.submit(new Callable<Long>() {
                    @Override
                    public Long call() throws Exception {
                        // Simulate CPU-intensive work (calculating prime numbers)
                        long sum = 0;
                        for (int j = 0; j < 1000000; j++) {
                            sum += isPrime(j) ? 1 : 0;
                        }
                        return sum;
                    }
                });
                
                results.add(result);
            }
            
            // Wait for all tasks to complete
            long totalPrimes = 0;
            for (Future<Long> result : results) {
                try {
                    totalPrimes += result.get();
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Error getting result: " + e.getMessage());
                }
            }
            
            // Record end time
            long endTime = System.currentTimeMillis();
            
            // Print results
            System.out.println("Total primes found: " + totalPrimes);
            System.out.println("Time taken: " + (endTime - startTime) + " ms");
            
        } finally {
            // Shutdown the executor when done
            executor.shutdown();
            try {
                if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
    
    /**
     * Helper method to check if a number is prime
     */
    private static boolean isPrime(int n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        
        return true;
    }
    
    /**
     * Example 3: Fork/Join Framework
     * 
     * Demonstrates the Fork/Join framework for parallel recursive tasks.
     */
    private static void forkJoinExample() {
        System.out.println("\n3. Fork/Join Framework Example:");
        
        // Create a large array of random numbers
        int[] numbers = new int[100_000_000];
        Random random = new Random();
        
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(100);
        }
        
        // Create a ForkJoinPool (by default, it uses the number of available processors)
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        
        System.out.println("ForkJoinPool parallelism: " + forkJoinPool.getParallelism());
        
        // Create a task to sum the array
        SumTask task = new SumTask(numbers, 0, numbers.length);
        
        // Record start time
        long startTime = System.currentTimeMillis();
        
        // Execute the task
        long sum = forkJoinPool.invoke(task);
        
        // Record end time
        long endTime = System.currentTimeMillis();
        
        // Print results
        System.out.println("Sum: " + sum);
        System.out.println("Time taken with Fork/Join: " + (endTime - startTime) + " ms");
        
        // Compare with sequential sum
        startTime = System.currentTimeMillis();
        
        long sequentialSum = 0;
        for (int number : numbers) {
            sequentialSum += number;
        }
        
        endTime = System.currentTimeMillis();
        
        System.out.println("Sequential sum: " + sequentialSum);
        System.out.println("Time taken sequentially: " + (endTime - startTime) + " ms");
    }
    
    /**
     * RecursiveTask for summing an array using the Fork/Join framework
     */
    static class SumTask extends RecursiveTask<Long> {
        private static final int THRESHOLD = 10_000; // Threshold for sequential processing
        private final int[] array;
        private final int start;
        private final int end;
        
        public SumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }
        
        @Override
        protected Long compute() {
            int length = end - start;
            
            // If the task is small enough, compute it sequentially
            if (length <= THRESHOLD) {
                return computeSequentially();
            }
            
            // Otherwise, split the task into two subtasks
            int middle = start + length / 2;
            
            SumTask leftTask = new SumTask(array, start, middle);
            SumTask rightTask = new SumTask(array, middle, end);
            
            // Fork the left task (execute it asynchronously)
            leftTask.fork();
            
            // Compute the right task directly
            long rightResult = rightTask.compute();
            
            // Join the left task (wait for its result)
            long leftResult = leftTask.join();
            
            // Combine the results
            return leftResult + rightResult;
        }
        
        private long computeSequentially() {
            long sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        }
    }
    
    /**
     * Example 4: Scheduled Tasks
     * 
     * Demonstrates scheduling tasks to run periodically or after a delay.
     */
    private static void scheduledTasksExample() {
        System.out.println("\n4. Scheduled Tasks Example:");
        
        // Create a scheduled executor with 2 threads
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        
        try {
            // Schedule a task to run after a delay
            System.out.println("Scheduling a task to run after 1 second");
            scheduler.schedule(() -> {
                System.out.println("Delayed task executed at: " + System.currentTimeMillis() + " ms");
            }, 1, TimeUnit.SECONDS);
            
            // Schedule a task to run periodically
            System.out.println("Scheduling a task to run every 500 ms");
            scheduler.scheduleAtFixedRate(() -> {
                System.out.println("Periodic task executed at: " + System.currentTimeMillis() + " ms");
            }, 0, 500, TimeUnit.MILLISECONDS);
            
            // Schedule a task with fixed delay between executions
            System.out.println("Scheduling a task with fixed delay of 700 ms");
            scheduler.scheduleWithFixedDelay(() -> {
                System.out.println("Fixed delay task executed at: " + System.currentTimeMillis() + " ms");
                
                // Simulate varying execution time
                try {
                    Thread.sleep(new Random().nextInt(200));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, 0, 700, TimeUnit.MILLISECONDS);
            
            // Let the scheduled tasks run for a while
            Thread.sleep(3000);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            // Shutdown the scheduler when done
            System.out.println("Shutting down scheduler");
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(5, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
    
    /**
     * Example 5: Graceful Shutdown
     * 
     * Demonstrates how to gracefully shut down an ExecutorService.
     */
    private static void gracefulShutdownExample() {
        System.out.println("\n5. Graceful Shutdown Example:");
        
        // Create a fixed thread pool
        ExecutorService executor = Executors.newFixedThreadPool(4);
        
        try {
            // Submit some long-running tasks
            for (int i = 0; i < 10; i++) {
                final int taskId = i;
                executor.submit(() -> {
                    try {
                        System.out.println("Task " + taskId + " started");
                        
                        // Simulate long-running task
                        Thread.sleep(new Random().nextInt(2000) + 1000);
                        
                        System.out.println("Task " + taskId + " completed");
                        return taskId;
                    } catch (InterruptedException e) {
                        System.out.println("Task " + taskId + " interrupted");
                        Thread.currentThread().interrupt();
                        return null;
                    }
                });
            }
            
            // Let some tasks start
            Thread.sleep(500);
            
            // Initiate graceful shutdown
            System.out.println("\nInitiating graceful shutdown...");
            
            // Step 1: Reject new tasks
            executor.shutdown();
            
            // Step 2: Wait for running tasks to complete (with timeout)
            System.out.println("Waiting for running tasks to complete...");
            boolean terminated = executor.awaitTermination(3, TimeUnit.SECONDS);
            
            if (terminated) {
                System.out.println("All tasks completed successfully");
            } else {
                // Step 3: If timeout occurs, cancel remaining tasks
                System.out.println("Timeout occurred. Cancelling remaining tasks...");
                List<Runnable> cancelledTasks = executor.shutdownNow();
                System.out.println("Cancelled " + cancelledTasks.size() + " tasks");
                
                // Step 4: Wait again for tasks to respond to interruption
                terminated = executor.awaitTermination(2, TimeUnit.SECONDS);
                System.out.println("Final termination status: " + 
                        (terminated ? "all tasks terminated" : "some tasks did not terminate"));
            }
            
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
            
            // If the main thread is interrupted, cancel all tasks
            List<Runnable> cancelledTasks = executor.shutdownNow();
            System.out.println("Cancelled " + cancelledTasks.size() + " tasks due to interruption");
            
            Thread.currentThread().interrupt();
        }
    }
}
