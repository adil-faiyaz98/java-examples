package Multithreading;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

/**
 * AtomicVariablesExample
 * 
 * Demonstrates the use of atomic variables in Java:
 * 1. AtomicInteger
 * 2. AtomicLong
 * 3. AtomicBoolean
 * 4. AtomicReference
 * 5. LongAdder (Java 8+)
 */
public class AtomicVariablesExample {

    private static final int THREAD_COUNT = 10;
    private static final int ITERATIONS = 100_000;
    
    public static void main(String[] args) {
        System.out.println("===== Atomic Variables Examples =====");
        
        // Example 1: AtomicInteger vs regular int
        atomicIntegerExample();
        
        // Example 2: AtomicLong
        atomicLongExample();
        
        // Example 3: AtomicBoolean
        atomicBooleanExample();
        
        // Example 4: AtomicReference
        atomicReferenceExample();
        
        // Example 5: LongAdder (Java 8+)
        longAdderExample();
        
        System.out.println("\n===== End of Atomic Variables Examples =====");
    }
    
    /**
     * Example 1: AtomicInteger vs regular int
     * 
     * AtomicInteger provides atomic operations on an int value.
     */
    private static void atomicIntegerExample() {
        System.out.println("\n1. AtomicInteger vs regular int Example:");
        
        // Regular int counter (not thread-safe)
        Counter regularCounter = new Counter();
        
        // AtomicInteger counter (thread-safe)
        AtomicCounter atomicCounter = new AtomicCounter();
        
        // Test with multiple threads
        CountDownLatch regularLatch = new CountDownLatch(THREAD_COUNT);
        CountDownLatch atomicLatch = new CountDownLatch(THREAD_COUNT);
        
        // Test regular counter
        long regularStartTime = System.currentTimeMillis();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < ITERATIONS; j++) {
                        regularCounter.increment();
                    }
                } finally {
                    regularLatch.countDown();
                }
            }, "RegularCounter-Thread-" + i).start();
        }
        
        // Test atomic counter
        long atomicStartTime = System.currentTimeMillis();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < ITERATIONS; j++) {
                        atomicCounter.increment();
                    }
                } finally {
                    atomicLatch.countDown();
                }
            }, "AtomicCounter-Thread-" + i).start();
        }
        
        try {
            // Wait for all threads to complete
            regularLatch.await();
            long regularEndTime = System.currentTimeMillis();
            
            atomicLatch.await();
            long atomicEndTime = System.currentTimeMillis();
            
            // Print results
            System.out.println("Regular counter value: " + regularCounter.getValue() + 
                    " (Expected: " + (THREAD_COUNT * ITERATIONS) + ")");
            System.out.println("Regular counter time: " + (regularEndTime - regularStartTime) + " ms");
            
            System.out.println("Atomic counter value: " + atomicCounter.getValue() + 
                    " (Expected: " + (THREAD_COUNT * ITERATIONS) + ")");
            System.out.println("Atomic counter time: " + (atomicEndTime - atomicStartTime) + " ms");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Example 2: AtomicLong
     * 
     * AtomicLong provides atomic operations on a long value.
     */
    private static void atomicLongExample() {
        System.out.println("\n2. AtomicLong Example:");
        
        // Create an AtomicLong with initial value 0
        AtomicLong atomicLong = new AtomicLong(0);
        
        // Test with multiple threads
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    for (int j = 0; j < ITERATIONS / 10; j++) {
                        // Demonstrate different atomic operations
                        
                        // Increment and get the result
                        long value1 = atomicLong.incrementAndGet();
                        
                        // Add a value and get the result
                        long value2 = atomicLong.addAndGet(threadId);
                        
                        // Get the current value and then increment
                        long value3 = atomicLong.getAndIncrement();
                        
                        // Compare and set if the current value equals the expected value
                        atomicLong.compareAndSet(value3 + 1, value3 + 10);
                    }
                } finally {
                    latch.countDown();
                }
            }, "AtomicLong-Thread-" + i).start();
        }
        
        try {
            // Wait for all threads to complete
            latch.await();
            
            // Print final value
            System.out.println("Final AtomicLong value: " + atomicLong.get());
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Example 3: AtomicBoolean
     * 
     * AtomicBoolean provides atomic operations on a boolean value.
     */
    private static void atomicBooleanExample() {
        System.out.println("\n3. AtomicBoolean Example:");
        
        // Create an AtomicBoolean with initial value false
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        
        // Create a thread that waits for the flag to be set
        Thread waiterThread = new Thread(() -> {
            System.out.println("Waiter thread started, waiting for flag to be set...");
            
            // Busy wait until the flag is set
            while (!atomicBoolean.get()) {
                // Busy waiting (in a real application, you might use wait/notify instead)
                Thread.yield();
            }
            
            System.out.println("Waiter thread detected flag change to true");
        }, "WaiterThread");
        
        // Create a thread that sets the flag
        Thread setterThread = new Thread(() -> {
            try {
                System.out.println("Setter thread started, sleeping for 1 second...");
                Thread.sleep(1000);
                
                System.out.println("Setter thread setting flag to true");
                boolean wasSet = atomicBoolean.compareAndSet(false, true);
                System.out.println("Flag was " + (wasSet ? "successfully set" : "not set") + " to true");
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, "SetterThread");
        
        // Start the threads
        waiterThread.start();
        setterThread.start();
        
        try {
            // Wait for both threads to complete
            waiterThread.join();
            setterThread.join();
            
            // Print final value
            System.out.println("Final AtomicBoolean value: " + atomicBoolean.get());
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Example 4: AtomicReference
     * 
     * AtomicReference provides atomic operations on an object reference.
     */
    private static void atomicReferenceExample() {
        System.out.println("\n4. AtomicReference Example:");
        
        // Create an AtomicReference with an initial Person object
        AtomicReference<Person> atomicReference = new AtomicReference<>(new Person("John", 30));
        
        // Test with multiple threads
        CountDownLatch latch = new CountDownLatch(THREAD_COUNT);
        
        for (int i = 0; i < THREAD_COUNT; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        // Get the current person
                        Person currentPerson = atomicReference.get();
                        
                        // Create a new person with updated values
                        Person newPerson = new Person(
                                currentPerson.getName() + "-" + threadId,
                                currentPerson.getAge() + 1
                        );
                        
                        // Try to atomically update the reference
                        if (atomicReference.compareAndSet(currentPerson, newPerson)) {
                            System.out.println("Thread " + threadId + " updated person to: " + newPerson);
                        }
                        
                        // Sleep a bit to allow other threads to run
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            }, "AtomicReference-Thread-" + i).start();
        }
        
        try {
            // Wait for all threads to complete
            latch.await();
            
            // Print final value
            System.out.println("Final Person: " + atomicReference.get());
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Example 5: LongAdder (Java 8+)
     * 
     * LongAdder is designed for high-concurrency scenarios where multiple threads
     * are frequently updating a common sum.
     */
    private static void longAdderExample() {
        System.out.println("\n5. LongAdder Example:");
        
        // Create a LongAdder
        LongAdder longAdder = new LongAdder();
        
        // Create an AtomicLong for comparison
        AtomicLong atomicLong = new AtomicLong(0);
        
        // Test with multiple threads
        CountDownLatch adderLatch = new CountDownLatch(THREAD_COUNT);
        CountDownLatch atomicLatch = new CountDownLatch(THREAD_COUNT);
        
        // Test LongAdder
        long adderStartTime = System.currentTimeMillis();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < ITERATIONS; j++) {
                        longAdder.increment();
                    }
                } finally {
                    adderLatch.countDown();
                }
            }, "LongAdder-Thread-" + i).start();
        }
        
        // Test AtomicLong
        long atomicStartTime = System.currentTimeMillis();
        for (int i = 0; i < THREAD_COUNT; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < ITERATIONS; j++) {
                        atomicLong.incrementAndGet();
                    }
                } finally {
                    atomicLatch.countDown();
                }
            }, "AtomicLong-Thread-" + i).start();
        }
        
        try {
            // Wait for all threads to complete
            adderLatch.await();
            long adderEndTime = System.currentTimeMillis();
            
            atomicLatch.await();
            long atomicEndTime = System.currentTimeMillis();
            
            // Print results
            System.out.println("LongAdder value: " + longAdder.sum() + 
                    " (Expected: " + (THREAD_COUNT * ITERATIONS) + ")");
            System.out.println("LongAdder time: " + (adderEndTime - adderStartTime) + " ms");
            
            System.out.println("AtomicLong value: " + atomicLong.get() + 
                    " (Expected: " + (THREAD_COUNT * ITERATIONS) + ")");
            System.out.println("AtomicLong time: " + (atomicEndTime - atomicStartTime) + " ms");
            
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Regular counter class (not thread-safe)
     */
    static class Counter {
        private int count = 0;
        
        public void increment() {
            count++;
        }
        
        public int getValue() {
            return count;
        }
    }
    
    /**
     * Atomic counter class (thread-safe)
     */
    static class AtomicCounter {
        private AtomicInteger count = new AtomicInteger(0);
        
        public void increment() {
            count.incrementAndGet();
        }
        
        public int getValue() {
            return count.get();
        }
    }
    
    /**
     * Person class for AtomicReference example
     */
    static class Person {
        private final String name;
        private final int age;
        
        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }
        
        public String getName() {
            return name;
        }
        
        public int getAge() {
            return age;
        }
        
        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + "}";
        }
    }
}
