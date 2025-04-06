package Multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ExecutorServiceExample
 * 
 * Demonstrates the use of ExecutorService and thread pools in Java:
 * 1. Fixed thread pool
 * 2. Cached thread pool
 * 3. Single thread executor
 * 4. Scheduled thread pool
 * 5. Submitting tasks and getting results with Future
 */
public class ExecutorServiceExample {

    public static void main(String[] args) {
        System.out.println("===== ExecutorService and Thread Pool Examples =====");
        
        // Example 1: Fixed thread pool
        fixedThreadPoolExample();
        
        // Example 2: Cached thread pool
        cachedThreadPoolExample();
        
        // Example 3: Single thread executor
        singleThreadExecutorExample();
        
        // Example 4: Scheduled thread pool
        scheduledThreadPoolExample();
        
        // Example 5: Submitting tasks and getting results with Future
        futureExample();
        
        System.out.println("\n===== End of ExecutorService Examples =====");
    }
    
    /**
     * Example 1: Fixed thread pool
     * 
     * A fixed thread pool creates a specified number of threads and reuses them.
     * If a thread is busy, the task waits in a queue until a thread becomes available.
     */
    private static void fixedThreadPoolExample() {
        System.out.println("\n1. Fixed Thread Pool Example:");
        
        // Create a fixed thread pool with 3 threads
        ExecutorService executor = Executors.newFixedThreadPool(3);
        
        try {
            // Submit 5 tasks to the executor
            for (int i = 1; i <= 5; i++) {
                final int taskId = i;
                executor.execute(() -> {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("Task " + taskId + " executing in " + threadName);
                    
                    // Simulate some work
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    
                    System.out.println("Task " + taskId + " completed in " + threadName);
                });
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
        
        System.out.println("Fixed thread pool tasks submitted and completed");
    }
    
    /**
     * Example 2: Cached thread pool
     * 
     * A cached thread pool creates new threads as needed and reuses idle threads.
     * Threads that remain idle for 60 seconds are terminated and removed from the pool.
     */
    private static void cachedThreadPoolExample() {
        System.out.println("\n2. Cached Thread Pool Example:");
        
        // Create a cached thread pool
        ExecutorService executor = Executors.newCachedThreadPool();
        
        try {
            // Submit 10 tasks to the executor
            for (int i = 1; i <= 10; i++) {
                final int taskId = i;
                executor.execute(() -> {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("Task " + taskId + " executing in " + threadName);
                    
                    // Simulate some work
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    
                    System.out.println("Task " + taskId + " completed in " + threadName);
                });
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
        
        System.out.println("Cached thread pool tasks submitted and completed");
    }
    
    /**
     * Example 3: Single thread executor
     * 
     * A single thread executor uses a single worker thread to execute tasks from a queue.
     * Tasks are guaranteed to execute sequentially in the order they were submitted.
     */
    private static void singleThreadExecutorExample() {
        System.out.println("\n3. Single Thread Executor Example:");
        
        // Create a single thread executor
        ExecutorService executor = Executors.newSingleThreadExecutor();
        
        try {
            // Submit 5 tasks to the executor
            for (int i = 1; i <= 5; i++) {
                final int taskId = i;
                executor.execute(() -> {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("Task " + taskId + " executing in " + threadName);
                    
                    // Simulate some work
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    
                    System.out.println("Task " + taskId + " completed in " + threadName);
                });
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
        
        System.out.println("Single thread executor tasks submitted and completed");
    }
    
    /**
     * Example 4: Scheduled thread pool
     * 
     * A scheduled thread pool can schedule tasks to run after a delay or periodically.
     */
    private static void scheduledThreadPoolExample() {
        System.out.println("\n4. Scheduled Thread Pool Example:");
        
        // Create a scheduled thread pool with 2 threads
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        
        try {
            // Schedule a task to run after a delay
            executor.schedule(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("Delayed task executing in " + threadName);
            }, 1, TimeUnit.SECONDS);
            
            // Schedule a task to run periodically
            executor.scheduleAtFixedRate(() -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("Periodic task executing in " + threadName);
            }, 0, 500, TimeUnit.MILLISECONDS);
            
            // Let the scheduled tasks run for a while
            Thread.sleep(2000);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
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
        
        System.out.println("Scheduled thread pool tasks submitted and completed");
    }
    
    /**
     * Example 5: Submitting tasks and getting results with Future
     * 
     * Future represents the result of an asynchronous computation.
     * It provides methods to check if the computation is complete, wait for its completion, and retrieve the result.
     */
    private static void futureExample() {
        System.out.println("\n5. Future Example:");
        
        // Create a fixed thread pool
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        try {
            // Submit a Callable task that returns a result
            Future<Integer> future1 = executor.submit(new SumCallable(10));
            
            // Submit another Callable task
            Future<Integer> future2 = executor.submit(new SumCallable(100));
            
            // Submit multiple tasks and collect their futures
            List<Future<Integer>> futures = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                final int base = i * 10;
                futures.add(executor.submit(() -> {
                    // Calculate sum from 1 to base
                    int sum = 0;
                    for (int j = 1; j <= base; j++) {
                        sum += j;
                    }
                    return sum;
                }));
            }
            
            // Get the result of the first task
            try {
                Integer result1 = future1.get(); // This will block until the result is available
                System.out.println("Result of first task: " + result1);
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Error getting result of first task: " + e.getMessage());
            }
            
            // Get the result of the second task with a timeout
            try {
                Integer result2 = future2.get(2, TimeUnit.SECONDS); // This will block with a timeout
                System.out.println("Result of second task: " + result2);
            } catch (InterruptedException | ExecutionException | java.util.concurrent.TimeoutException e) {
                System.out.println("Error or timeout getting result of second task: " + e.getMessage());
            }
            
            // Get the results of all tasks
            System.out.println("Results of all tasks:");
            for (int i = 0; i < futures.size(); i++) {
                try {
                    Integer result = futures.get(i).get();
                    System.out.println("Task " + (i + 1) + " result: " + result);
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Error getting result of task " + (i + 1) + ": " + e.getMessage());
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
        
        System.out.println("Future tasks submitted and completed");
    }
    
    /**
     * A Callable task that calculates the sum of numbers from 1 to n
     */
    static class SumCallable implements Callable<Integer> {
        private final int n;
        
        public SumCallable(int n) {
            this.n = n;
        }
        
        @Override
        public Integer call() throws Exception {
            System.out.println("Calculating sum from 1 to " + n + " in " + Thread.currentThread().getName());
            
            // Simulate some work
            Thread.sleep(1000);
            
            // Calculate sum from 1 to n
            int sum = 0;
            for (int i = 1; i <= n; i++) {
                sum += i;
            }
            
            return sum;
        }
    }
}
