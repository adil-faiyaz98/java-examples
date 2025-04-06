package ProjectLoomVirtualThreads;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Example demonstrating structured concurrency with virtual threads.
 * 
 * This class demonstrates:
 * 1. Basic structured concurrency
 * 2. Error handling in structured concurrency
 * 3. Timeouts and cancellation
 * 4. Comparing with traditional approaches
 * 
 * Note: Structured concurrency requires JDK 21 or later.
 */
public class StructuredConcurrencyExample {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Structured Concurrency Example ===");
        System.out.println("Java version: " + System.getProperty("java.version"));
        System.out.println();

        try {
            // Example 1: Basic structured concurrency
            basicStructuredConcurrency();

            // Example 2: Error handling in structured concurrency
            errorHandlingInStructuredConcurrency();

            // Example 3: Timeouts and cancellation
            timeoutsAndCancellation();

            // Example 4: Comparing with traditional approaches
            compareWithTraditionalApproach();
            
        } catch (UnsupportedOperationException e) {
            System.out.println("Structured concurrency is not supported in this Java version.");
            System.out.println("Please use JDK 21 or later to run this example.");
        }
    }

    /**
     * Example 1: Basic structured concurrency
     */
    private static void basicStructuredConcurrency() throws Exception {
        System.out.println("=== Example 1: Basic structured concurrency ===");

        // Using StructuredTaskScope to manage concurrent tasks
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            // Fork two subtasks
            Subtask<String> task1 = scope.fork(() -> {
                System.out.println("Task 1 running in: " + Thread.currentThread());
                Thread.sleep(1000);
                return "Result from Task 1";
            });

            Subtask<String> task2 = scope.fork(() -> {
                System.out.println("Task 2 running in: " + Thread.currentThread());
                Thread.sleep(500);
                return "Result from Task 2";
            });

            // Wait for all tasks to complete or one to fail
            scope.join();
            
            // Check for exceptions
            scope.throwIfFailed(e -> new RuntimeException("Task failed", e));

            // Get the results
            String result1 = task1.get();
            String result2 = task2.get();

            System.out.println("Task 1 result: " + result1);
            System.out.println("Task 2 result: " + result2);
        }
        
        System.out.println();
    }

    /**
     * Example 2: Error handling in structured concurrency
     */
    private static void errorHandlingInStructuredConcurrency() throws Exception {
        System.out.println("=== Example 2: Error handling in structured concurrency ===");

        try {
            // Using StructuredTaskScope to manage concurrent tasks
            try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                // Fork two subtasks, one of which will fail
                Subtask<String> task1 = scope.fork(() -> {
                    System.out.println("Task 1 running in: " + Thread.currentThread());
                    Thread.sleep(1000);
                    return "Result from Task 1";
                });

                Subtask<String> task2 = scope.fork(() -> {
                    System.out.println("Task 2 running in: " + Thread.currentThread());
                    Thread.sleep(500);
                    throw new RuntimeException("Task 2 failed deliberately");
                });

                // Wait for all tasks to complete or one to fail
                scope.join();
                
                // This will throw an exception because task2 failed
                scope.throwIfFailed(e -> new RuntimeException("Task failed", e));

                // We won't reach here because of the exception
                String result1 = task1.get();
                String result2 = task2.get();
                System.out.println("Task 1 result: " + result1);
                System.out.println("Task 2 result: " + result2);
            }
        } catch (Exception e) {
            System.out.println("Caught exception: " + e.getMessage());
            if (e.getCause() != null) {
                System.out.println("Cause: " + e.getCause().getMessage());
            }
        }

        // Example with ShutdownOnSuccess
        System.out.println("\nUsing ShutdownOnSuccess:");
        try (var scope = new StructuredTaskScope.ShutdownOnSuccess<String>()) {
            // Fork multiple tasks, we only need one to succeed
            scope.fork(() -> {
                System.out.println("Fast task running in: " + Thread.currentThread());
                Thread.sleep(500);
                return "Result from fast task";
            });

            scope.fork(() -> {
                System.out.println("Slow task running in: " + Thread.currentThread());
                Thread.sleep(2000);
                return "Result from slow task";
            });

            scope.fork(() -> {
                System.out.println("Failing task running in: " + Thread.currentThread());
                Thread.sleep(300);
                throw new RuntimeException("This task fails");
            });

            // Wait for the first successful result
            scope.join();
            
            // Get the result of the first successful task
            String result = scope.result();
            System.out.println("First successful result: " + result);
        }
        
        System.out.println();
    }

    /**
     * Example 3: Timeouts and cancellation
     */
    private static void timeoutsAndCancellation() throws Exception {
        System.out.println("=== Example 3: Timeouts and cancellation ===");

        try {
            // Using StructuredTaskScope with a timeout
            try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                // Fork tasks with different durations
                Subtask<String> task1 = scope.fork(() -> {
                    System.out.println("Short task running in: " + Thread.currentThread());
                    Thread.sleep(500);
                    return "Result from short task";
                });

                Subtask<String> task2 = scope.fork(() -> {
                    System.out.println("Long task running in: " + Thread.currentThread());
                    try {
                        Thread.sleep(5000);
                        return "Result from long task";
                    } catch (InterruptedException e) {
                        System.out.println("Long task was interrupted");
                        throw e;
                    }
                });

                // Wait with a timeout
                try {
                    scope.joinUntil(java.time.Instant.now().plus(Duration.ofSeconds(1)));
                    System.out.println("All tasks completed within timeout");
                } catch (TimeoutException e) {
                    System.out.println("Timeout occurred, some tasks didn't complete in time");
                }
                
                // Check task states
                System.out.println("Short task state: " + (task1.state() == Subtask.State.SUCCESS ? "SUCCESS" : "INCOMPLETE"));
                System.out.println("Long task state: " + (task2.state() == Subtask.State.SUCCESS ? "SUCCESS" : "INCOMPLETE"));
                
                // Get result from completed task
                if (task1.state() == Subtask.State.SUCCESS) {
                    System.out.println("Short task result: " + task1.get());
                }
            }
        } catch (Exception e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
        
        System.out.println();
    }

    /**
     * Example 4: Comparing with traditional approach
     */
    private static void compareWithTraditionalApproach() throws Exception {
        System.out.println("=== Example 4: Comparing with traditional approach ===");

        // Traditional approach with ExecutorService and Future
        System.out.println("Traditional approach with ExecutorService:");
        traditionalApproach();

        // Structured concurrency approach
        System.out.println("\nStructured concurrency approach:");
        structuredApproach();
        
        System.out.println();
    }

    /**
     * Traditional approach with ExecutorService and Future
     */
    private static void traditionalApproach() {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Submit tasks
            List<Future<String>> futures = IntStream.range(0, 5)
                    .mapToObj(i -> executor.submit(() -> {
                        if (i == 3) {
                            throw new RuntimeException("Task " + i + " failed");
                        }
                        return "Result " + i;
                    }))
                    .toList();
            
            // Process results
            for (int i = 0; i < futures.size(); i++) {
                try {
                    String result = futures.get(i).get();
                    System.out.println("Task " + i + " result: " + result);
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Task " + i + " failed: " + e.getCause().getMessage());
                }
            }
        }
    }

    /**
     * Structured concurrency approach
     */
    private static void structuredApproach() throws Exception {
        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            // Fork tasks
            List<Subtask<String>> tasks = IntStream.range(0, 5)
                    .mapToObj(i -> scope.fork(() -> {
                        if (i == 3) {
                            throw new RuntimeException("Task " + i + " failed");
                        }
                        return "Result " + i;
                    }))
                    .toList();
            
            // Join and handle exceptions
            try {
                scope.join();
                scope.throwIfFailed(e -> new RuntimeException("A task failed", e));
                
                // Process results
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println("Task " + i + " result: " + tasks.get(i).get());
                }
            } catch (Exception e) {
                System.out.println("Caught exception: " + e.getMessage());
                
                // We can still get results from successful tasks
                for (int i = 0; i < tasks.size(); i++) {
                    if (tasks.get(i).state() == Subtask.State.SUCCESS) {
                        System.out.println("Task " + i + " result: " + tasks.get(i).get());
                    } else if (tasks.get(i).state() == Subtask.State.FAILED) {
                        System.out.println("Task " + i + " failed");
                    }
                }
            }
        }
    }

    /**
     * Custom implementation of ShutdownOnSuccess for demonstration purposes.
     * This shows how you can create custom scopes for specific use cases.
     */
    static class CustomScope<T> extends StructuredTaskScope<T> {
        private volatile T firstResult;
        private volatile Throwable firstException;
        private volatile boolean isDone = false;

        @Override
        protected void handleComplete(Subtask<? extends T> subtask) {
            if (isDone) return;
            
            switch (subtask.state()) {
                case SUCCESS:
                    if (firstResult == null) {
                        firstResult = subtask.get();
                        isDone = true;
                        shutdown();
                    }
                    break;
                case FAILED:
                    if (firstException == null) {
                        firstException = subtask.exception();
                    }
                    break;
                default:
                    // Ignore other states
            }
        }

        public T result() throws ExecutionException {
            if (firstResult != null) {
                return firstResult;
            }
            if (firstException != null) {
                throw new ExecutionException(firstException);
            }
            throw new IllegalStateException("No result available");
        }
    }
}
