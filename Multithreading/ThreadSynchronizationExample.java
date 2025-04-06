package Multithreading;

/**
 * ThreadSynchronizationExample
 * 
 * Demonstrates different thread synchronization techniques in Java:
 * 1. Synchronized methods
 * 2. Synchronized blocks
 * 3. Static synchronization
 * 4. The volatile keyword
 * 5. Thread communication with wait() and notify()
 */
public class ThreadSynchronizationExample {

    public static void main(String[] args) {
        System.out.println("===== Thread Synchronization Examples =====");
        
        // Example 1: Problem without synchronization
        System.out.println("\n1. Problem without synchronization:");
        Counter unsafeCounter = new Counter();
        
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                unsafeCounter.increment();
            }
        }, "IncrementThread-1");
        
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                unsafeCounter.increment();
            }
        }, "IncrementThread-2");
        
        thread1.start();
        thread2.start();
        
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Unsafe counter value: " + unsafeCounter.getCount() + 
                " (Expected: 2000, but may be less due to race condition)");
        
        // Example 2: Synchronized method
        System.out.println("\n2. Using synchronized method:");
        SynchronizedCounter syncCounter = new SynchronizedCounter();
        
        Thread thread3 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                syncCounter.increment();
            }
        }, "SyncIncrementThread-1");
        
        Thread thread4 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                syncCounter.increment();
            }
        }, "SyncIncrementThread-2");
        
        thread3.start();
        thread4.start();
        
        try {
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Synchronized counter value: " + syncCounter.getCount() + 
                " (Expected: 2000, should be correct)");
        
        // Example 3: Synchronized block
        System.out.println("\n3. Using synchronized block:");
        BlockSynchronizedCounter blockCounter = new BlockSynchronizedCounter();
        
        Thread thread5 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                blockCounter.increment();
            }
        }, "BlockSyncThread-1");
        
        Thread thread6 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                blockCounter.increment();
            }
        }, "BlockSyncThread-2");
        
        thread5.start();
        thread6.start();
        
        try {
            thread5.join();
            thread6.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Block synchronized counter value: " + blockCounter.getCount() + 
                " (Expected: 2000, should be correct)");
        
        // Example 4: Static synchronization
        System.out.println("\n4. Using static synchronization:");
        
        Thread thread7 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                StaticSynchronizedCounter.increment();
            }
        }, "StaticSyncThread-1");
        
        Thread thread8 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                StaticSynchronizedCounter.increment();
            }
        }, "StaticSyncThread-2");
        
        thread7.start();
        thread8.start();
        
        try {
            thread7.join();
            thread8.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("Static synchronized counter value: " + StaticSynchronizedCounter.getCount() + 
                " (Expected: 2000, should be correct)");
        
        // Example 5: Volatile keyword
        System.out.println("\n5. Using volatile keyword:");
        VolatileExample volatileExample = new VolatileExample();
        
        Thread writerThread = new Thread(() -> {
            try {
                Thread.sleep(100); // Sleep to ensure reader thread starts first
                System.out.println("Writer thread is changing the flag");
                volatileExample.setFlag(true);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "WriterThread");
        
        Thread readerThread = new Thread(() -> {
            System.out.println("Reader thread waiting for flag change");
            while (!volatileExample.isFlag()) {
                // Busy wait - in a real application, you might use wait/notify instead
            }
            System.out.println("Reader thread detected flag change");
        }, "ReaderThread");
        
        readerThread.start();
        writerThread.start();
        
        try {
            writerThread.join();
            readerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Example 6: Wait and notify
        System.out.println("\n6. Using wait() and notify():");
        MessageQueue messageQueue = new MessageQueue();
        
        Thread producerThread = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    String message = "Message " + i;
                    System.out.println("Producer producing: " + message);
                    messageQueue.put(message);
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "ProducerThread");
        
        Thread consumerThread = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    String message = messageQueue.take();
                    System.out.println("Consumer consumed: " + message);
                    Thread.sleep(200);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "ConsumerThread");
        
        consumerThread.start();
        producerThread.start();
        
        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\n===== End of Thread Synchronization Examples =====");
    }
    
    /**
     * Unsynchronized counter - will have race conditions
     */
    static class Counter {
        private int count = 0;
        
        public void increment() {
            count++; // This is not an atomic operation!
        }
        
        public int getCount() {
            return count;
        }
    }
    
    /**
     * Counter with synchronized method
     */
    static class SynchronizedCounter {
        private int count = 0;
        
        // Synchronized method - only one thread can execute this at a time
        public synchronized void increment() {
            count++;
        }
        
        public synchronized int getCount() {
            return count;
        }
    }
    
    /**
     * Counter with synchronized block
     */
    static class BlockSynchronizedCounter {
        private int count = 0;
        private final Object lock = new Object(); // Object used for synchronization
        
        public void increment() {
            // Synchronized block - only one thread can execute this block at a time
            synchronized (lock) {
                count++;
            }
        }
        
        public int getCount() {
            synchronized (lock) {
                return count;
            }
        }
    }
    
    /**
     * Counter with static synchronization
     */
    static class StaticSynchronizedCounter {
        private static int count = 0;
        
        // Static synchronized method - locks on the class object
        public static synchronized void increment() {
            count++;
        }
        
        public static synchronized int getCount() {
            return count;
        }
    }
    
    /**
     * Example of using the volatile keyword
     */
    static class VolatileExample {
        // The volatile keyword ensures that changes to the flag variable
        // are immediately visible to all threads
        private volatile boolean flag = false;
        
        public boolean isFlag() {
            return flag;
        }
        
        public void setFlag(boolean flag) {
            this.flag = flag;
        }
    }
    
    /**
     * Example of using wait() and notify() for thread communication
     */
    static class MessageQueue {
        private String message;
        private boolean hasMessage = false;
        
        // Put a message in the queue
        public synchronized void put(String message) throws InterruptedException {
            // Wait until the previous message has been consumed
            while (hasMessage) {
                wait(); // Releases the lock and waits to be notified
            }
            
            // Store the message and set the flag
            this.message = message;
            hasMessage = true;
            
            // Notify any waiting consumer
            notify();
        }
        
        // Take a message from the queue
        public synchronized String take() throws InterruptedException {
            // Wait until there is a message to consume
            while (!hasMessage) {
                wait(); // Releases the lock and waits to be notified
            }
            
            // Get the message and reset the flag
            String result = message;
            hasMessage = false;
            
            // Notify any waiting producer
            notify();
            
            return result;
        }
    }
}
