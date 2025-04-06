package Multithreading;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * ConcurrentCollectionsExample
 * 
 * Demonstrates the use of concurrent collections in Java:
 * 1. ConcurrentHashMap
 * 2. CopyOnWriteArrayList
 * 3. ConcurrentLinkedQueue
 * 4. BlockingQueue (LinkedBlockingQueue)
 * 5. Synchronized Collections
 */
public class ConcurrentCollectionsExample {

    private static final int THREAD_COUNT = 5;
    private static final int OPERATIONS_PER_THREAD = 1000;
    
    public static void main(String[] args) {
        System.out.println("===== Concurrent Collections Examples =====");
        
        // Example 1: ConcurrentHashMap vs HashMap
        concurrentHashMapExample();
        
        // Example 2: CopyOnWriteArrayList vs ArrayList
        copyOnWriteArrayListExample();
        
        // Example 3: ConcurrentLinkedQueue
        concurrentLinkedQueueExample();
        
        // Example 4: BlockingQueue (LinkedBlockingQueue)
        blockingQueueExample();
        
        // Example 5: Synchronized Collections
        synchronizedCollectionsExample();
        
        System.out.println("\n===== End of Concurrent Collections Examples =====");
    }
    
    /**
     * Example 1: ConcurrentHashMap vs HashMap
     * 
     * ConcurrentHashMap is a thread-safe implementation of Map that allows concurrent access
     * and updates without blocking the entire map.
     */
    private static void concurrentHashMapExample() {
        System.out.println("\n1. ConcurrentHashMap vs HashMap Example:");
        
        // Create a regular HashMap (not thread-safe)
        Map<Integer, String> hashMap = new HashMap<>();
        
        // Create a ConcurrentHashMap (thread-safe)
        Map<Integer, String> concurrentHashMap = new ConcurrentHashMap<>();
        
        // Test with multiple threads
        CountDownLatch hashMapLatch = new CountDownLatch(THREAD_COUNT);
        CountDownLatch concurrentMapLatch = new CountDownLatch(THREAD_COUNT);
        
        // Test HashMap (may throw ConcurrentModificationException)
        System.out.println("Testing HashMap (not thread-safe):");
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    Random random = new Random();
                    for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                        int key = random.nextInt(1000);
                        
                        // Randomly choose between put and get operations
                        if (random.nextBoolean()) {
                            hashMap.put(key, "Value-" + threadId + "-" + j);
                        } else {
                            hashMap.get(key);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("HashMap error: " + e.getMessage());
                } finally {
                    hashMapLatch.countDown();
                }
            }, "HashMap-Thread-" + i).start();
        }
        
        // Test ConcurrentHashMap
        System.out.println("Testing ConcurrentHashMap (thread-safe):");
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    Random random = new Random();
                    for (int j = 0; j < OPERATIONS_PER_THREAD; j++) {
                        int key = random.nextInt(1000);
                        
                        // Randomly choose between put and get operations
                        if (random.nextBoolean()) {
                            concurrentHashMap.put(key, "Value-" + threadId + "-" + j);
                        } else {
                            concurrentHashMap.get(key);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("ConcurrentHashMap error: " + e.getMessage());
                } finally {
                    concurrentMapLatch.countDown();
                }
            }, "ConcurrentMap-Thread-" + i).start();
        }
        
        try {
            // Wait for all threads to complete
            boolean hashMapCompleted = hashMapLatch.await(5, TimeUnit.SECONDS);
            boolean concurrentMapCompleted = concurrentMapLatch.await(5, TimeUnit.SECONDS);
            
            System.out.println("HashMap operations " + 
                    (hashMapCompleted ? "completed successfully" : "did not complete in time"));
            System.out.println("ConcurrentHashMap operations " + 
                    (concurrentMapCompleted ? "completed successfully" : "did not complete in time"));
            
            System.out.println("HashMap size: " + hashMap.size());
            System.out.println("ConcurrentHashMap size: " + concurrentHashMap.size());
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Example 2: CopyOnWriteArrayList vs ArrayList
     * 
     * CopyOnWriteArrayList is a thread-safe variant of ArrayList where all mutative operations
     * (add, set, etc.) create a fresh copy of the underlying array.
     */
    private static void copyOnWriteArrayListExample() {
        System.out.println("\n2. CopyOnWriteArrayList vs ArrayList Example:");
        
        // Create a regular ArrayList (not thread-safe)
        List<String> arrayList = new ArrayList<>();
        
        // Create a CopyOnWriteArrayList (thread-safe)
        List<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
        
        // Test with multiple threads
        CountDownLatch arrayListLatch = new CountDownLatch(THREAD_COUNT);
        CountDownLatch copyOnWriteListLatch = new CountDownLatch(THREAD_COUNT);
        
        // Test ArrayList (may throw ConcurrentModificationException)
        System.out.println("Testing ArrayList (not thread-safe):");
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    for (int j = 0; j < 100; j++) { // Fewer operations for ArrayList
                        // Add an element
                        arrayList.add("Thread-" + threadId + "-Item-" + j);
                        
                        // Iterate over the list (may cause ConcurrentModificationException)
                        try {
                            for (String item : arrayList) {
                                // Just iterate
                            }
                        } catch (Exception e) {
                            System.out.println("ArrayList iteration error: " + e.getMessage());
                        }
                    }
                } catch (Exception e) {
                    System.out.println("ArrayList error: " + e.getMessage());
                } finally {
                    arrayListLatch.countDown();
                }
            }, "ArrayList-Thread-" + i).start();
        }
        
        // Test CopyOnWriteArrayList
        System.out.println("Testing CopyOnWriteArrayList (thread-safe):");
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    for (int j = 0; j < 100; j++) {
                        // Add an element
                        copyOnWriteArrayList.add("Thread-" + threadId + "-Item-" + j);
                        
                        // Iterate over the list (safe in CopyOnWriteArrayList)
                        for (String item : copyOnWriteArrayList) {
                            // Just iterate
                        }
                    }
                } catch (Exception e) {
                    System.out.println("CopyOnWriteArrayList error: " + e.getMessage());
                } finally {
                    copyOnWriteListLatch.countDown();
                }
            }, "CopyOnWriteList-Thread-" + i).start();
        }
        
        try {
            // Wait for all threads to complete
            boolean arrayListCompleted = arrayListLatch.await(5, TimeUnit.SECONDS);
            boolean copyOnWriteListCompleted = copyOnWriteListLatch.await(5, TimeUnit.SECONDS);
            
            System.out.println("ArrayList operations " + 
                    (arrayListCompleted ? "completed successfully" : "did not complete in time"));
            System.out.println("CopyOnWriteArrayList operations " + 
                    (copyOnWriteListCompleted ? "completed successfully" : "did not complete in time"));
            
            System.out.println("ArrayList size: " + arrayList.size());
            System.out.println("CopyOnWriteArrayList size: " + copyOnWriteArrayList.size());
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Example 3: ConcurrentLinkedQueue
     * 
     * ConcurrentLinkedQueue is a thread-safe queue based on linked nodes.
     */
    private static void concurrentLinkedQueueExample() {
        System.out.println("\n3. ConcurrentLinkedQueue Example:");
        
        // Create a ConcurrentLinkedQueue
        ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
        
        // Test with multiple producer and consumer threads
        CountDownLatch producerLatch = new CountDownLatch(THREAD_COUNT);
        CountDownLatch consumerLatch = new CountDownLatch(THREAD_COUNT);
        
        // Producer threads
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    for (int j = 0; j < 100; j++) {
                        String item = "Producer-" + threadId + "-Item-" + j;
                        queue.offer(item);
                        
                        // Simulate some work
                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    producerLatch.countDown();
                }
            }, "Producer-" + i).start();
        }
        
        // Consumer threads
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                int itemsConsumed = 0;
                try {
                    while (itemsConsumed < 100 || !producerLatch.await(0, TimeUnit.MILLISECONDS)) {
                        String item = queue.poll();
                        if (item != null) {
                            itemsConsumed++;
                        } else {
                            // No item available, wait a bit
                            Thread.sleep(10);
                        }
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    consumerLatch.countDown();
                }
            }, "Consumer-" + i).start();
        }
        
        try {
            // Wait for all threads to complete
            boolean producersCompleted = producerLatch.await(5, TimeUnit.SECONDS);
            boolean consumersCompleted = consumerLatch.await(5, TimeUnit.SECONDS);
            
            System.out.println("Producer operations " + 
                    (producersCompleted ? "completed successfully" : "did not complete in time"));
            System.out.println("Consumer operations " + 
                    (consumersCompleted ? "completed successfully" : "did not complete in time"));
            
            System.out.println("Remaining items in queue: " + queue.size());
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Example 4: BlockingQueue (LinkedBlockingQueue)
     * 
     * BlockingQueue is an interface that represents a queue that additionally supports
     * operations that wait for the queue to become non-empty when retrieving an element,
     * and wait for space to become available in the queue when storing an element.
     */
    private static void blockingQueueExample() {
        System.out.println("\n4. BlockingQueue Example:");
        
        // Create a LinkedBlockingQueue with a capacity of 10
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(10);
        
        // Test with producer and consumer threads
        CountDownLatch producerLatch = new CountDownLatch(2);
        CountDownLatch consumerLatch = new CountDownLatch(2);
        
        // Producer threads
        for (int i = 0; i < 2; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    for (int j = 0; j < 50; j++) {
                        String item = "Producer-" + threadId + "-Item-" + j;
                        
                        // put() will block if the queue is full
                        blockingQueue.put(item);
                        System.out.println("Produced: " + item + ", Queue size: " + blockingQueue.size());
                        
                        // Simulate varying production rates
                        Thread.sleep(threadId * 20 + 10);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    producerLatch.countDown();
                }
            }, "BlockingQueue-Producer-" + i).start();
        }
        
        // Consumer threads
        for (int i = 0; i < 2; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    for (int j = 0; j < 50; j++) {
                        // take() will block if the queue is empty
                        String item = blockingQueue.take();
                        System.out.println("Consumed by " + threadId + ": " + item + 
                                ", Queue size: " + blockingQueue.size());
                        
                        // Simulate varying consumption rates
                        Thread.sleep(threadId * 30 + 50);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    consumerLatch.countDown();
                }
            }, "BlockingQueue-Consumer-" + i).start();
        }
        
        try {
            // Wait for all threads to complete
            boolean producersCompleted = producerLatch.await(10, TimeUnit.SECONDS);
            boolean consumersCompleted = consumerLatch.await(10, TimeUnit.SECONDS);
            
            System.out.println("Producer operations " + 
                    (producersCompleted ? "completed successfully" : "did not complete in time"));
            System.out.println("Consumer operations " + 
                    (consumersCompleted ? "completed successfully" : "did not complete in time"));
            
            System.out.println("Remaining items in blocking queue: " + blockingQueue.size());
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Example 5: Synchronized Collections
     * 
     * Synchronized collections are created using the Collections.synchronizedXXX methods.
     * They provide thread-safety by synchronizing every method on a common lock.
     */
    private static void synchronizedCollectionsExample() {
        System.out.println("\n5. Synchronized Collections Example:");
        
        // Create a synchronized List
        List<String> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        
        // Create a synchronized Map
        Map<Integer, String> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
        
        // Test with multiple threads
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT * 2);
        
        // Threads for synchronized List
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    for (int j = 0; j < 100; j++) {
                        synchronizedList.add("Thread-" + threadId + "-Item-" + j);
                        
                        // Note: Iteration must be manually synchronized
                        synchronized (synchronizedList) {
                            for (String item : synchronizedList) {
                                // Just iterate
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Synchronized List error: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            }, "SyncList-Thread-" + i).start();
        }
        
        // Threads for synchronized Map
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    Random random = new Random();
                    for (int j = 0; j < 100; j++) {
                        int key = random.nextInt(1000);
                        
                        // Randomly choose between put and get operations
                        if (random.nextBoolean()) {
                            synchronizedMap.put(key, "Value-" + threadId + "-" + j);
                        } else {
                            synchronizedMap.get(key);
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Synchronized Map error: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            }, "SyncMap-Thread-" + i).start();
        }
        
        try {
            // Wait for all threads to complete
            boolean completed = latch.await(5, TimeUnit.SECONDS);
            
            System.out.println("Synchronized collections operations " + 
                    (completed ? "completed successfully" : "did not complete in time"));
            
            System.out.println("Synchronized List size: " + synchronizedList.size());
            System.out.println("Synchronized Map size: " + synchronizedMap.size());
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
