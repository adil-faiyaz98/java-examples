package Multithreading;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;

/**
 * LockExample
 * 
 * Demonstrates the use of locks in Java:
 * 1. ReentrantLock
 * 2. ReadWriteLock
 * 3. StampedLock (Java 8+)
 * 4. Condition
 */
public class LockExample {

    public static void main(String[] args) {
        System.out.println("===== Lock Examples =====");
        
        // Example 1: ReentrantLock
        reentrantLockExample();
        
        // Example 2: ReadWriteLock
        readWriteLockExample();
        
        // Example 3: StampedLock
        stampedLockExample();
        
        // Example 4: Lock with Condition
        lockWithConditionExample();
        
        System.out.println("\n===== End of Lock Examples =====");
    }
    
    /**
     * Example 1: ReentrantLock
     * 
     * ReentrantLock provides the same concurrency and memory semantics as the
     * synchronized keyword, but with additional features like timed waiting,
     * interruptible lock acquisition, and fairness.
     */
    private static void reentrantLockExample() {
        System.out.println("\n1. ReentrantLock Example:");
        
        // Create a counter with ReentrantLock
        LockCounter lockCounter = new LockCounter();
        
        // Create a counter with synchronized
        SynchronizedCounter syncCounter = new SynchronizedCounter();
        
        // Test with multiple threads
        ExecutorService executor = Executors.newFixedThreadPool(4);
        CountDownLatch lockLatch = new CountDownLatch(4);
        CountDownLatch syncLatch = new CountDownLatch(4);
        
        // Test ReentrantLock counter
        long lockStartTime = System.currentTimeMillis();
        for (int i = 0; i < 4; i++) {
            executor.submit(() -> {
                try {
                    for (int j = 0; j < 100000; j++) {
                        lockCounter.increment();
                    }
                } finally {
                    lockLatch.countDown();
                }
            });
        }
        
        // Test synchronized counter
        long syncStartTime = System.currentTimeMillis();
        for (int i = 0; i < 4; i++) {
            executor.submit(() -> {
                try {
                    for (int j = 0; j < 100000; j++) {
                        syncCounter.increment();
                    }
                } finally {
                    syncLatch.countDown();
                }
            });
        }
        
        try {
            // Wait for all threads to complete
            lockLatch.await();
            long lockEndTime = System.currentTimeMillis();
            
            syncLatch.await();
            long syncEndTime = System.currentTimeMillis();
            
            // Print results
            System.out.println("ReentrantLock counter value: " + lockCounter.getValue() + 
                    " (Expected: 400000)");
            System.out.println("ReentrantLock time: " + (lockEndTime - lockStartTime) + " ms");
            
            System.out.println("Synchronized counter value: " + syncCounter.getValue() + 
                    " (Expected: 400000)");
            System.out.println("Synchronized time: " + (syncEndTime - syncStartTime) + " ms");
            
            // Demonstrate tryLock
            System.out.println("\nDemonstrating tryLock:");
            boolean locked = lockCounter.tryLock();
            System.out.println("First tryLock: " + (locked ? "succeeded" : "failed"));
            
            if (locked) {
                try {
                    System.out.println("Lock acquired, incrementing counter");
                    lockCounter.increment();
                    System.out.println("New counter value: " + lockCounter.getValue());
                    
                    // Try to acquire the lock again (should succeed because ReentrantLock is reentrant)
                    boolean lockedAgain = lockCounter.tryLock();
                    System.out.println("Second tryLock (while holding the lock): " + 
                            (lockedAgain ? "succeeded" : "failed"));
                    
                    if (lockedAgain) {
                        try {
                            System.out.println("Lock acquired again, incrementing counter");
                            lockCounter.increment();
                            System.out.println("New counter value: " + lockCounter.getValue());
                        } finally {
                            lockCounter.unlock();
                            System.out.println("Second lock released");
                        }
                    }
                } finally {
                    lockCounter.unlock();
                    System.out.println("First lock released");
                }
            }
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
        }
    }
    
    /**
     * Example 2: ReadWriteLock
     * 
     * ReadWriteLock maintains a pair of locks, one for read-only operations and one for writing.
     * The read lock may be held simultaneously by multiple reader threads, while the write lock
     * is exclusive.
     */
    private static void readWriteLockExample() {
        System.out.println("\n2. ReadWriteLock Example:");
        
        // Create a cache with ReadWriteLock
        ReadWriteCache<String, String> cache = new ReadWriteCache<>();
        
        // Test with multiple reader and writer threads
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(10);
        
        // Writer threads (fewer)
        for (int i = 0; i < 2; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    Random random = new Random();
                    for (int j = 0; j < 10; j++) {
                        String key = "key-" + random.nextInt(20);
                        String value = "value-" + threadId + "-" + j;
                        
                        cache.put(key, value);
                        System.out.println("Writer " + threadId + " put: " + key + " = " + value);
                        
                        // Sleep to allow readers to run
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }
        
        // Reader threads (more)
        for (int i = 0; i < 8; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    Random random = new Random();
                    for (int j = 0; j < 20; j++) {
                        String key = "key-" + random.nextInt(20);
                        
                        String value = cache.get(key);
                        System.out.println("Reader " + threadId + " got: " + key + " = " + value);
                        
                        // Sleep a bit
                        Thread.sleep(50);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }
        
        try {
            // Wait for all threads to complete
            latch.await();
            
            // Print cache size
            System.out.println("Final cache size: " + cache.size());
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
        }
    }
    
    /**
     * Example 3: StampedLock
     * 
     * StampedLock is a capability-based lock with three modes for controlling read/write access.
     * It's designed as an alternative to ReadWriteLock for certain use cases, with optimistic reading.
     */
    private static void stampedLockExample() {
        System.out.println("\n3. StampedLock Example:");
        
        // Create a point with StampedLock
        StampedPoint point = new StampedPoint();
        
        // Test with multiple reader and writer threads
        ExecutorService executor = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(10);
        
        // Writer threads (fewer)
        for (int i = 0; i < 2; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    Random random = new Random();
                    for (int j = 0; j < 5; j++) {
                        double newX = random.nextDouble() * 100;
                        double newY = random.nextDouble() * 100;
                        
                        point.move(newX, newY);
                        System.out.println("Writer " + threadId + " moved point to: (" + newX + ", " + newY + ")");
                        
                        // Sleep to allow readers to run
                        Thread.sleep(200);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }
        
        // Reader threads (more)
        for (int i = 0; i < 8; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        // Randomly choose between optimistic and regular read
                        boolean useOptimistic = j % 2 == 0;
                        
                        double distance;
                        if (useOptimistic) {
                            distance = point.distanceFromOriginOptimistic();
                            System.out.println("Reader " + threadId + " got distance (optimistic): " + distance);
                        } else {
                            distance = point.distanceFromOrigin();
                            System.out.println("Reader " + threadId + " got distance (read lock): " + distance);
                        }
                        
                        // Sleep a bit
                        Thread.sleep(50);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }
        
        try {
            // Wait for all threads to complete
            latch.await();
            
            // Print final point
            System.out.println("Final point: " + point);
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
        }
    }
    
    /**
     * Example 4: Lock with Condition
     * 
     * Condition provides a way for threads to wait for a specific condition to become true.
     * It's similar to wait/notify but with more flexibility.
     */
    private static void lockWithConditionExample() {
        System.out.println("\n4. Lock with Condition Example:");
        
        // Create a bounded buffer with lock and conditions
        BoundedBuffer<String> buffer = new BoundedBuffer<>(5);
        
        // Test with producer and consumer threads
        ExecutorService executor = Executors.newFixedThreadPool(4);
        CountDownLatch latch = new CountDownLatch(4);
        
        // Producer threads
        for (int i = 0; i < 2; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        String item = "Item-" + threadId + "-" + j;
                        
                        buffer.put(item);
                        System.out.println("Producer " + threadId + " put: " + item + 
                                ", Buffer size: " + buffer.size());
                        
                        // Sleep a bit
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }
        
        // Consumer threads
        for (int i = 0; i < 2; i++) {
            final int threadId = i;
            executor.submit(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        String item = buffer.take();
                        System.out.println("Consumer " + threadId + " took: " + item + 
                                ", Buffer size: " + buffer.size());
                        
                        // Sleep a bit
                        Thread.sleep(200);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }
        
        try {
            // Wait for all threads to complete
            latch.await();
            
            // Print final buffer size
            System.out.println("Final buffer size: " + buffer.size());
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
        }
    }
    
    /**
     * Counter with ReentrantLock
     */
    static class LockCounter {
        private long count = 0;
        private final ReentrantLock lock = new ReentrantLock();
        
        public void increment() {
            lock.lock();
            try {
                count++;
            } finally {
                lock.unlock();
            }
        }
        
        public long getValue() {
            lock.lock();
            try {
                return count;
            } finally {
                lock.unlock();
            }
        }
        
        public boolean tryLock() {
            return lock.tryLock();
        }
        
        public void unlock() {
            lock.unlock();
        }
    }
    
    /**
     * Counter with synchronized
     */
    static class SynchronizedCounter {
        private long count = 0;
        
        public synchronized void increment() {
            count++;
        }
        
        public synchronized long getValue() {
            return count;
        }
    }
    
    /**
     * Cache with ReadWriteLock
     */
    static class ReadWriteCache<K, V> {
        private final Map<K, V> map = new HashMap<>();
        private final ReadWriteLock lock = new ReentrantReadWriteLock();
        private final Lock readLock = lock.readLock();
        private final Lock writeLock = lock.writeLock();
        
        public V get(K key) {
            readLock.lock();
            try {
                return map.get(key);
            } finally {
                readLock.unlock();
            }
        }
        
        public void put(K key, V value) {
            writeLock.lock();
            try {
                map.put(key, value);
            } finally {
                writeLock.unlock();
            }
        }
        
        public int size() {
            readLock.lock();
            try {
                return map.size();
            } finally {
                readLock.unlock();
            }
        }
    }
    
    /**
     * Point with StampedLock
     */
    static class StampedPoint {
        private double x, y;
        private final StampedLock lock = new StampedLock();
        
        public void move(double deltaX, double deltaY) {
            long stamp = lock.writeLock();
            try {
                x += deltaX;
                y += deltaY;
            } finally {
                lock.unlockWrite(stamp);
            }
        }
        
        public double distanceFromOrigin() {
            long stamp = lock.readLock();
            try {
                return Math.sqrt(x * x + y * y);
            } finally {
                lock.unlockRead(stamp);
            }
        }
        
        public double distanceFromOriginOptimistic() {
            // Optimistic read - doesn't block writers
            long stamp = lock.tryOptimisticRead();
            double currentX = x, currentY = y;
            
            // Check if the stamp is still valid (no writes occurred)
            if (!lock.validate(stamp)) {
                // Stamp is invalid, fall back to regular read lock
                stamp = lock.readLock();
                try {
                    currentX = x;
                    currentY = y;
                } finally {
                    lock.unlockRead(stamp);
                }
            }
            
            return Math.sqrt(currentX * currentX + currentY * currentY);
        }
        
        @Override
        public String toString() {
            long stamp = lock.readLock();
            try {
                return "Point{x=" + x + ", y=" + y + "}";
            } finally {
                lock.unlockRead(stamp);
            }
        }
    }
    
    /**
     * Bounded buffer with lock and conditions
     */
    static class BoundedBuffer<E> {
        private final E[] items;
        private int putIndex, takeIndex, count;
        
        private final ReentrantLock lock = new ReentrantLock();
        private final java.util.concurrent.locks.Condition notEmpty = lock.newCondition();
        private final java.util.concurrent.locks.Condition notFull = lock.newCondition();
        
        @SuppressWarnings("unchecked")
        public BoundedBuffer(int capacity) {
            items = (E[]) new Object[capacity];
        }
        
        public void put(E item) throws InterruptedException {
            lock.lock();
            try {
                // Wait until the buffer is not full
                while (count == items.length) {
                    notFull.await();
                }
                
                // Insert the item
                items[putIndex] = item;
                putIndex = (putIndex + 1) % items.length;
                count++;
                
                // Signal that the buffer is not empty
                notEmpty.signal();
            } finally {
                lock.unlock();
            }
        }
        
        public E take() throws InterruptedException {
            lock.lock();
            try {
                // Wait until the buffer is not empty
                while (count == 0) {
                    notEmpty.await();
                }
                
                // Remove the item
                E item = items[takeIndex];
                items[takeIndex] = null; // Help GC
                takeIndex = (takeIndex + 1) % items.length;
                count--;
                
                // Signal that the buffer is not full
                notFull.signal();
                
                return item;
            } finally {
                lock.unlock();
            }
        }
        
        public int size() {
            lock.lock();
            try {
                return count;
            } finally {
                lock.unlock();
            }
        }
    }
}
