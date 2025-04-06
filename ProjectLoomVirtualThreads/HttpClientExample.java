package ProjectLoomVirtualThreads;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Example demonstrating virtual threads with HTTP client operations.
 * 
 * This class demonstrates:
 * 1. Making HTTP requests with virtual threads
 * 2. Comparing synchronous vs asynchronous HTTP requests
 * 3. Handling many concurrent HTTP requests
 */
public class HttpClientExample {

    private static final String[] URLS = {
            "https://www.google.com",
            "https://www.github.com",
            "https://www.stackoverflow.com",
            "https://www.wikipedia.org",
            "https://www.reddit.com"
    };
    
    private static final int REQUESTS_PER_URL = 20;
    private static final int TOTAL_REQUESTS = URLS.length * REQUESTS_PER_URL;
    private static final AtomicInteger successCounter = new AtomicInteger(0);
    private static final AtomicInteger failureCounter = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        System.out.println("=== HTTP Client with Virtual Threads Example ===");
        System.out.println("Java version: " + System.getProperty("java.version"));
        System.out.println("Total requests to make: " + TOTAL_REQUESTS);
        System.out.println();

        // Example 1: Synchronous HTTP requests with platform threads
        syncRequestsWithPlatformThreads();

        // Example 2: Synchronous HTTP requests with virtual threads
        syncRequestsWithVirtualThreads();

        // Example 3: Asynchronous HTTP requests
        asyncRequests();

        // Example 4: Many concurrent requests with virtual threads
        manyConcurrentRequests();
    }

    /**
     * Example 1: Synchronous HTTP requests with platform threads
     */
    private static void syncRequestsWithPlatformThreads() throws Exception {
        System.out.println("=== Example 1: Synchronous HTTP requests with platform threads ===");
        resetCounters();

        Instant start = Instant.now();

        // Create a fixed thread pool
        try (ExecutorService executor = Executors.newFixedThreadPool(10)) {
            List<CompletableFuture<Void>> futures = new ArrayList<>();

            // Submit tasks to make HTTP requests
            for (String url : URLS) {
                for (int i = 0; i < REQUESTS_PER_URL; i++) {
                    final int requestId = i;
                    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        try {
                            HttpClient client = HttpClient.newBuilder()
                                    .connectTimeout(Duration.ofSeconds(10))
                                    .build();

                            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(URI.create(url))
                                    .timeout(Duration.ofSeconds(10))
                                    .GET()
                                    .build();

                            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
                            
                            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                                successCounter.incrementAndGet();
                            } else {
                                failureCounter.incrementAndGet();
                            }
                            
                            if (requestId == 0) {
                                System.out.println("Request to " + url + " completed with status: " + response.statusCode());
                            }
                        } catch (IOException | InterruptedException e) {
                            failureCounter.incrementAndGet();
                            System.out.println("Error requesting " + url + ": " + e.getMessage());
                        }
                    }, executor);
                    
                    futures.add(future);
                }
            }

            // Wait for all futures to complete
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        }

        Instant end = Instant.now();
        printResults(start, end);
    }

    /**
     * Example 2: Synchronous HTTP requests with virtual threads
     */
    private static void syncRequestsWithVirtualThreads() throws Exception {
        System.out.println("=== Example 2: Synchronous HTTP requests with virtual threads ===");
        resetCounters();

        Instant start = Instant.now();

        // Create a virtual thread per task executor
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            List<CompletableFuture<Void>> futures = new ArrayList<>();

            // Submit tasks to make HTTP requests
            for (String url : URLS) {
                for (int i = 0; i < REQUESTS_PER_URL; i++) {
                    final int requestId = i;
                    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        try {
                            HttpClient client = HttpClient.newBuilder()
                                    .connectTimeout(Duration.ofSeconds(10))
                                    .build();

                            HttpRequest request = HttpRequest.newBuilder()
                                    .uri(URI.create(url))
                                    .timeout(Duration.ofSeconds(10))
                                    .GET()
                                    .build();

                            HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
                            
                            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                                successCounter.incrementAndGet();
                            } else {
                                failureCounter.incrementAndGet();
                            }
                            
                            if (requestId == 0) {
                                System.out.println("Request to " + url + " completed with status: " + response.statusCode());
                            }
                        } catch (IOException | InterruptedException e) {
                            failureCounter.incrementAndGet();
                            System.out.println("Error requesting " + url + ": " + e.getMessage());
                        }
                    }, executor);
                    
                    futures.add(future);
                }
            }

            // Wait for all futures to complete
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        }

        Instant end = Instant.now();
        printResults(start, end);
    }

    /**
     * Example 3: Asynchronous HTTP requests
     */
    private static void asyncRequests() throws Exception {
        System.out.println("=== Example 3: Asynchronous HTTP requests ===");
        resetCounters();

        Instant start = Instant.now();

        // Create an HTTP client
        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        List<CompletableFuture<HttpResponse<Void>>> futures = new ArrayList<>();

        // Make asynchronous HTTP requests
        for (String url : URLS) {
            for (int i = 0; i < REQUESTS_PER_URL; i++) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .timeout(Duration.ofSeconds(10))
                        .GET()
                        .build();

                CompletableFuture<HttpResponse<Void>> future = client.sendAsync(request, HttpResponse.BodyHandlers.discarding())
                        .thenApply(response -> {
                            if (response.statusCode() >= 200 && response.statusCode() < 300) {
                                successCounter.incrementAndGet();
                            } else {
                                failureCounter.incrementAndGet();
                            }
                            return response;
                        })
                        .exceptionally(e -> {
                            failureCounter.incrementAndGet();
                            System.out.println("Error requesting " + url + ": " + e.getMessage());
                            return null;
                        });

                futures.add(future);
            }
        }

        // Wait for all futures to complete
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        Instant end = Instant.now();
        printResults(start, end);
    }

    /**
     * Example 4: Many concurrent requests with virtual threads
     */
    private static void manyConcurrentRequests() throws Exception {
        System.out.println("=== Example 4: Many concurrent requests with virtual threads ===");
        resetCounters();

        // Number of concurrent requests
        final int CONCURRENT_REQUESTS = 1000;
        
        Instant start = Instant.now();

        // Create a virtual thread per task executor
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            // Submit many concurrent tasks
            for (int i = 0; i < CONCURRENT_REQUESTS; i++) {
                final int requestId = i;
                executor.submit(() -> {
                    try {
                        // Select a URL from the list
                        String url = URLS[requestId % URLS.length];
                        
                        HttpClient client = HttpClient.newBuilder()
                                .connectTimeout(Duration.ofSeconds(10))
                                .build();

                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(url))
                                .timeout(Duration.ofSeconds(10))
                                .GET()
                                .build();

                        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
                        
                        if (response.statusCode() >= 200 && response.statusCode() < 300) {
                            successCounter.incrementAndGet();
                        } else {
                            failureCounter.incrementAndGet();
                        }
                        
                        if (requestId % 100 == 0) {
                            System.out.println("Request " + requestId + " to " + url + " completed with status: " + response.statusCode());
                        }
                    } catch (IOException | InterruptedException e) {
                        failureCounter.incrementAndGet();
                        if (requestId % 100 == 0) {
                            System.out.println("Error in request " + requestId + ": " + e.getMessage());
                        }
                    }
                });
            }
        }

        // Wait for all tasks to complete (or timeout after 30 seconds)
        while (successCounter.get() + failureCounter.get() < CONCURRENT_REQUESTS) {
            if (Duration.between(start, Instant.now()).toSeconds() > 30) {
                System.out.println("Timeout waiting for all requests to complete");
                break;
            }
            TimeUnit.MILLISECONDS.sleep(100);
        }

        Instant end = Instant.now();
        System.out.println("Completed " + (successCounter.get() + failureCounter.get()) + " out of " + CONCURRENT_REQUESTS + " requests");
        printResults(start, end);
    }

    /**
     * Reset the success and failure counters
     */
    private static void resetCounters() {
        successCounter.set(0);
        failureCounter.set(0);
    }

    /**
     * Print the results of the HTTP requests
     */
    private static void printResults(Instant start, Instant end) {
        Duration duration = Duration.between(start, end);
        System.out.println("Total time: " + duration.toMillis() + " ms");
        System.out.println("Successful requests: " + successCounter.get());
        System.out.println("Failed requests: " + failureCounter.get());
        System.out.println("Requests per second: " + 
                String.format("%.2f", (successCounter.get() + failureCounter.get()) / (duration.toMillis() / 1000.0)));
        System.out.println();
    }
}
