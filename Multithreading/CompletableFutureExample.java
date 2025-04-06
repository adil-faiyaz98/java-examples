package Multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * CompletableFutureExample
 * 
 * Demonstrates the use of CompletableFuture in Java 8+:
 * 1. Creating and completing futures
 * 2. Chaining operations (thenApply, thenAccept, thenRun)
 * 3. Combining futures (thenCombine, allOf, anyOf)
 * 4. Error handling (exceptionally, handle)
 * 5. Async operations (supplyAsync, thenApplyAsync)
 */
public class CompletableFutureExample {

    public static void main(String[] args) {
        System.out.println("===== CompletableFuture Examples =====");
        
        // Example 1: Creating and completing futures
        creatingAndCompletingFutures();
        
        // Example 2: Chaining operations
        chainingOperations();
        
        // Example 3: Combining futures
        combiningFutures();
        
        // Example 4: Error handling
        errorHandling();
        
        // Example 5: Async operations
        asyncOperations();
        
        System.out.println("\n===== End of CompletableFuture Examples =====");
    }
    
    /**
     * Example 1: Creating and completing futures
     */
    private static void creatingAndCompletingFutures() {
        System.out.println("\n1. Creating and Completing Futures:");
        
        // Create a CompletableFuture
        CompletableFuture<String> future = new CompletableFuture<>();
        
        // Complete the future in another thread
        new Thread(() -> {
            try {
                System.out.println("Computing result...");
                Thread.sleep(1000);
                future.complete("Result is ready!");
            } catch (InterruptedException e) {
                future.completeExceptionally(e);
                Thread.currentThread().interrupt();
            }
        }).start();
        
        // Wait for the future to complete
        try {
            String result = future.get(); // Blocks until the future is completed
            System.out.println("Got result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error getting result: " + e.getMessage());
        }
        
        // Create a CompletableFuture that's already completed
        CompletableFuture<String> completedFuture = CompletableFuture.completedFuture("Already completed");
        
        try {
            String result = completedFuture.get();
            System.out.println("Got result from completed future: " + result);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error getting result: " + e.getMessage());
        }
        
        // Create a CompletableFuture with supplyAsync
        CompletableFuture<String> supplyFuture = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("Computing result asynchronously...");
                Thread.sleep(500);
                return "Async result is ready!";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        });
        
        try {
            String result = supplyFuture.get();
            System.out.println("Got async result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error getting async result: " + e.getMessage());
        }
    }
    
    /**
     * Example 2: Chaining operations
     */
    private static void chainingOperations() {
        System.out.println("\n2. Chaining Operations:");
        
        // Create a CompletableFuture with supplyAsync
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("Fetching data...");
                Thread.sleep(500);
                return "Raw data";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        });
        
        // Chain operations with thenApply (transform the result)
        CompletableFuture<String> processedFuture = future.thenApply(data -> {
            System.out.println("Processing data: " + data);
            return data.toUpperCase();
        });
        
        // Chain operations with thenApply again
        CompletableFuture<Integer> lengthFuture = processedFuture.thenApply(processed -> {
            System.out.println("Calculating length of: " + processed);
            return processed.length();
        });
        
        // Chain operations with thenAccept (consume the result)
        lengthFuture.thenAccept(length -> {
            System.out.println("The length is: " + length);
        });
        
        // Chain operations with thenRun (run an action after completion)
        lengthFuture.thenRun(() -> {
            System.out.println("All processing completed");
        });
        
        // Wait for all operations to complete
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Demonstrate chaining in a single expression
        System.out.println("\nChaining in a single expression:");
        CompletableFuture.supplyAsync(() -> {
            System.out.println("Generating a number...");
            return new Random().nextInt(100);
        }).thenApply(number -> {
            System.out.println("Doubling the number: " + number);
            return number * 2;
        }).thenApply(doubled -> {
            System.out.println("Converting to string: " + doubled);
            return "Result: " + doubled;
        }).thenAccept(result -> {
            System.out.println("Final result: " + result);
        }).join(); // Wait for completion
    }
    
    /**
     * Example 3: Combining futures
     */
    private static void combiningFutures() {
        System.out.println("\n3. Combining Futures:");
        
        // Create two CompletableFutures
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("Fetching data from source 1...");
                Thread.sleep(500);
                return "Data from source 1";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        });
        
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("Fetching data from source 2...");
                Thread.sleep(700);
                return "Data from source 2";
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
        });
        
        // Combine two futures with thenCombine
        CompletableFuture<String> combinedFuture = future1.thenCombine(future2, (result1, result2) -> {
            System.out.println("Combining results...");
            return result1 + " + " + result2;
        });
        
        try {
            String result = combinedFuture.get();
            System.out.println("Combined result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error getting combined result: " + e.getMessage());
        }
        
        // Wait for multiple futures with allOf
        System.out.println("\nWaiting for all futures with allOf:");
        List<CompletableFuture<String>> futures = new ArrayList<>();
        
        for (int i = 0; i < 5; i++) {
            final int index = i;
            futures.add(CompletableFuture.supplyAsync(() -> {
                try {
                    int sleepTime = new Random().nextInt(500) + 500;
                    System.out.println("Task " + index + " sleeping for " + sleepTime + " ms");
                    Thread.sleep(sleepTime);
                    return "Result " + index;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }));
        }
        
        // Create a CompletableFuture that completes when all futures complete
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(
                futures.toArray(new CompletableFuture[0]));
        
        // Get all results when all futures complete
        CompletableFuture<List<String>> allResults = allFutures.thenApply(v -> 
                futures.stream()
                        .map(CompletableFuture::join)
                        .collect(Collectors.toList()));
        
        try {
            List<String> results = allResults.get();
            System.out.println("All results: " + results);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error getting all results: " + e.getMessage());
        }
        
        // Complete when any future completes with anyOf
        System.out.println("\nWaiting for any future with anyOf:");
        List<CompletableFuture<String>> moreFutures = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            final int index = i;
            moreFutures.add(CompletableFuture.supplyAsync(() -> {
                try {
                    int sleepTime = (index + 1) * 500;
                    System.out.println("Task " + index + " sleeping for " + sleepTime + " ms");
                    Thread.sleep(sleepTime);
                    return "Result " + index;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }));
        }
        
        // Create a CompletableFuture that completes when any future completes
        CompletableFuture<Object> anyFuture = CompletableFuture.anyOf(
                moreFutures.toArray(new CompletableFuture[0]));
        
        try {
            Object result = anyFuture.get();
            System.out.println("First result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error getting first result: " + e.getMessage());
        }
    }
    
    /**
     * Example 4: Error handling
     */
    private static void errorHandling() {
        System.out.println("\n4. Error Handling:");
        
        // Create a CompletableFuture that throws an exception
        CompletableFuture<String> failedFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Executing task that will fail...");
            throw new RuntimeException("Deliberate failure");
        });
        
        // Handle the exception with exceptionally
        CompletableFuture<String> recoveredFuture = failedFuture.exceptionally(ex -> {
            System.out.println("Recovering from exception: " + ex.getMessage());
            return "Recovery value";
        });
        
        try {
            String result = recoveredFuture.get();
            System.out.println("Recovered result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error getting recovered result: " + e.getMessage());
        }
        
        // Handle both success and failure with handle
        CompletableFuture<String> successFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("Executing task that will succeed...");
            return "Success value";
        });
        
        CompletableFuture<String> handledSuccessFuture = successFuture.handle((result, ex) -> {
            if (ex != null) {
                System.out.println("Handling exception: " + ex.getMessage());
                return "Fallback value";
            } else {
                System.out.println("Handling success: " + result);
                return result.toUpperCase();
            }
        });
        
        try {
            String result = handledSuccessFuture.get();
            System.out.println("Handled success result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error getting handled success result: " + e.getMessage());
        }
        
        // Handle both success and failure with handle for a failing future
        CompletableFuture<String> handledFailureFuture = failedFuture.handle((result, ex) -> {
            if (ex != null) {
                System.out.println("Handling exception: " + ex.getMessage());
                return "Fallback value";
            } else {
                System.out.println("Handling success: " + result);
                return result.toUpperCase();
            }
        });
        
        try {
            String result = handledFailureFuture.get();
            System.out.println("Handled failure result: " + result);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Error getting handled failure result: " + e.getMessage());
        }
    }
    
    /**
     * Example 5: Async operations
     */
    private static void asyncOperations() {
        System.out.println("\n5. Async Operations:");
        
        // Create a custom executor
        ExecutorService executor = Executors.newFixedThreadPool(4);
        
        try {
            // Create a CompletableFuture with supplyAsync using the custom executor
            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                System.out.println("Supplying value in thread: " + Thread.currentThread().getName());
                return "Initial value";
            }, executor);
            
            // Chain operations asynchronously
            CompletableFuture<String> asyncFuture = future
                    .thenApplyAsync(value -> {
                        System.out.println("First transformation in thread: " + Thread.currentThread().getName());
                        return value + " -> transformed";
                    }, executor)
                    .thenApplyAsync(value -> {
                        System.out.println("Second transformation in thread: " + Thread.currentThread().getName());
                        return value + " -> transformed again";
                    }, executor)
                    .thenApplyAsync(value -> {
                        System.out.println("Third transformation in thread: " + Thread.currentThread().getName());
                        return value.toUpperCase();
                    }, executor);
            
            // Wait for the result
            try {
                String result = asyncFuture.get();
                System.out.println("Final async result: " + result);
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Error getting async result: " + e.getMessage());
            }
            
            // Demonstrate async composition
            System.out.println("\nAsync composition:");
            CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
                try {
                    System.out.println("Future 1 running in thread: " + Thread.currentThread().getName());
                    Thread.sleep(500);
                    return "Result 1";
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }, executor);
            
            CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
                try {
                    System.out.println("Future 2 running in thread: " + Thread.currentThread().getName());
                    Thread.sleep(300);
                    return "Result 2";
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }, executor);
            
            // Combine the futures asynchronously
            CompletableFuture<String> combinedFuture = future1.thenCombineAsync(future2, (r1, r2) -> {
                System.out.println("Combining results in thread: " + Thread.currentThread().getName());
                return r1 + " + " + r2;
            }, executor);
            
            try {
                String result = combinedFuture.get();
                System.out.println("Combined async result: " + result);
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Error getting combined async result: " + e.getMessage());
            }
            
            // Demonstrate thenCompose for chaining async operations that return CompletableFuture
            System.out.println("\nChaining async operations with thenCompose:");
            CompletableFuture<String> composedFuture = CompletableFuture.supplyAsync(() -> {
                System.out.println("First operation in thread: " + Thread.currentThread().getName());
                return "First result";
            }, executor).thenComposeAsync(firstResult -> {
                System.out.println("Second operation in thread: " + Thread.currentThread().getName());
                System.out.println("Using first result: " + firstResult);
                
                // Return a new CompletableFuture
                return CompletableFuture.supplyAsync(() -> {
                    System.out.println("Nested operation in thread: " + Thread.currentThread().getName());
                    return firstResult + " -> extended with second result";
                }, executor);
            }, executor);
            
            try {
                String result = composedFuture.get();
                System.out.println("Composed async result: " + result);
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Error getting composed async result: " + e.getMessage());
            }
            
        } finally {
            // Shutdown the executor
            executor.shutdown();
            try {
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }
}
