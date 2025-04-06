package ProjectLoomVirtualThreads;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Example demonstrating debugging and monitoring virtual threads.
 * 
 * This class demonstrates:
 * 1. Thread dumps with virtual threads
 * 2. Monitoring virtual thread states
 * 3. Debugging techniques for virtual threads
 * 4. Pinned virtual threads
 */
public class VirtualThreadDebuggingExample {

    private static final int THREAD_COUNT = 1000;
    private static final AtomicInteger activeThreads = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {
        System.out.println("=== Virtual Thread Debugging and Monitoring Example ===");
        System.out.println("Java version: " + System.getProperty("java.version"));
        System.out.println();

        // Example 1: Thread dumps with virtual threads
        threadDumpsWithVirtualThreads();

        // Example 2: Monitoring virtual thread states
        monitoringVirtualThreadStates();

        // Example 3: Debugging techniques for virtual threads
        debuggingTechniquesForVirtualThreads();

        // Example 4: Pinned virtual threads
        pinnedVirtualThreads();
    }

    /**
     * Example 1: Thread dumps with virtual threads
     */
    private static void threadDumpsWithVirtualThreads() throws Exception {
        System.out.println("=== Example 1: Thread dumps with virtual threads ===");

        // Create a list to hold the threads
        List<Thread> threads = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(1);

        // Create virtual threads
        for (int i = 0; i < 10; i++) {
            final int threadId = i;
            Thread vthread = Thread.ofVirtual()
                    .name("debug-vthread-" + threadId)
                    .start(() -> {
                        try {
                            activeThreads.incrementAndGet();
                            System.out.println("Virtual thread " + threadId + " started");
                            
                            // Wait for the latch to be counted down
                            latch.await();
                            
                            // Simulate some work
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        } finally {
                            activeThreads.decrementAndGet();
                        }
                    });
            
            threads.add(vthread);
        }

        // Wait for all threads to start
        while (activeThreads.get() < 10) {
            Thread.sleep(10);
        }

        // Take a thread dump
        System.out.println("\nThread dump before releasing the latch:");
        printThreadDump();

        // Release the latch to let the threads continue
        latch.countDown();

        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("\nAll virtual threads completed");
        System.out.println();
    }

    /**
     * Example 2: Monitoring virtual thread states
     */
    private static void monitoringVirtualThreadStates() throws Exception {
        System.out.println("=== Example 2: Monitoring virtual thread states ===");

        // Create a virtual thread per task executor
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            CountDownLatch startLatch = new CountDownLatch(1);
            CountDownLatch completionLatch = new CountDownLatch(THREAD_COUNT);
            
            // Submit tasks
            for (int i = 0; i < THREAD_COUNT; i++) {
                final int taskId = i;
                executor.submit(() -> {
                    try {
                        // Wait for the start signal
                        startLatch.await();
                        
                        // Simulate different thread states
                        if (taskId % 4 == 0) {
                            // RUNNABLE - CPU-bound task
                            long result = 0;
                            for (int j = 0; j < 1000000; j++) {
                                result += j;
                            }
                        } else if (taskId % 4 == 1) {
                            // TIMED_WAITING - sleeping
                            Thread.sleep(500);
                        } else if (taskId % 4 == 2) {
                            // WAITING - waiting on an object
                            Object lock = new Object();
                            synchronized (lock) {
                                lock.wait(300);
                            }
                        } else {
                            // BLOCKED - trying to acquire a lock
                            Object lock = new Object();
                            synchronized (lock) {
                                Thread.sleep(200);
                            }
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        completionLatch.countDown();
                    }
                });
            }
            
            // Release the start latch to let all threads run
            startLatch.countDown();
            
            // Monitor thread states
            for (int i = 0; i < 3; i++) {
                Thread.sleep(100);
                System.out.println("\nThread state snapshot " + (i + 1) + ":");
                monitorThreadStates();
            }
            
            // Wait for all tasks to complete
            completionLatch.await();
        }
        
        System.out.println("\nAll tasks completed");
        System.out.println();
    }

    /**
     * Example 3: Debugging techniques for virtual threads
     */
    private static void debuggingTechniquesForVirtualThreads() throws Exception {
        System.out.println("=== Example 3: Debugging techniques for virtual threads ===");

        // Create a thread factory that adds debugging information
        ThreadFactory debuggingFactory = Thread.ofVirtual()
                .name("debug-", 0)
                .factory();
        
        // Create a list to hold the threads
        List<Thread> threads = new ArrayList<>();
        
        // Create virtual threads with the debugging factory
        for (int i = 0; i < 5; i++) {
            final int threadId = i;
            Thread vthread = debuggingFactory.newThread(() -> {
                // Add thread-local debugging context
                ThreadLocal<String> debugContext = new ThreadLocal<>();
                debugContext.set("Context for thread " + threadId);
                
                try {
                    System.out.println("Thread " + Thread.currentThread().getName() + " started");
                    System.out.println("Debug context: " + debugContext.get());
                    
                    // Demonstrate exception handling
                    if (threadId == 3) {
                        throw new RuntimeException("Deliberate exception in thread " + threadId);
                    }
                    
                    // Simulate some work
                    Thread.sleep(100);
                    
                    System.out.println("Thread " + Thread.currentThread().getName() + " completed");
                } catch (Exception e) {
                    System.err.println("Exception in " + Thread.currentThread().getName() + ": " + e.getMessage());
                    e.printStackTrace();
                }
            });
            
            threads.add(vthread);
        }
        
        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }
        
        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }
        
        System.out.println("\nDebugging techniques:");
        System.out.println("1. Use meaningful thread names");
        System.out.println("2. Add thread-local context for debugging");
        System.out.println("3. Proper exception handling and logging");
        System.out.println("4. Use thread dumps to diagnose issues");
        System.out.println("5. Monitor thread states");
        System.out.println();
    }

    /**
     * Example 4: Pinned virtual threads
     */
    private static void pinnedVirtualThreads() throws Exception {
        System.out.println("=== Example 4: Pinned virtual threads ===");

        // Create a virtual thread per task executor
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            CountDownLatch latch = new CountDownLatch(2);
            
            // Submit a task that will get pinned (using synchronized on a monitor)
            executor.submit(() -> {
                try {
                    System.out.println("Starting pinned thread task");
                    
                    // This will cause the virtual thread to be pinned to its carrier thread
                    // because synchronized blocks prevent unmounting
                    synchronized (VirtualThreadDebuggingExample.class) {
                        System.out.println("Pinned thread acquired lock");
                        
                        // Simulate I/O operation while holding the lock
                        // This would normally allow a virtual thread to yield, but not when pinned
                        System.out.println("Pinned thread sleeping (simulating I/O)");
                        Thread.sleep(2000);
                        
                        System.out.println("Pinned thread completed work");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
            
            // Submit another task that will try to use the same lock
            executor.submit(() -> {
                try {
                    // Give the first thread time to acquire the lock
                    Thread.sleep(100);
                    
                    System.out.println("Starting second thread task");
                    
                    // This will block waiting for the lock
                    synchronized (VirtualThreadDebuggingExample.class) {
                        System.out.println("Second thread acquired lock");
                        Thread.sleep(100);
                    }
                    
                    System.out.println("Second thread completed work");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
            
            // Wait for both tasks to complete
            latch.await();
        }
        
        System.out.println("\nPinned virtual threads explanation:");
        System.out.println("1. Virtual threads can be 'pinned' to their carrier thread in certain situations");
        System.out.println("2. Common causes of pinning:");
        System.out.println("   - Synchronized blocks/methods (as demonstrated)");
        System.out.println("   - Native methods that park");
        System.out.println("   - Some blocking operations in legacy code");
        System.out.println("3. Pinning reduces the efficiency of virtual threads");
        System.out.println("4. Best practice: Avoid synchronized in favor of ReentrantLock");
        System.out.println();
        
        // Demonstrate the alternative using ReentrantLock
        System.out.println("Alternative to synchronized using ReentrantLock:");
        System.out.println("java.util.concurrent.locks.ReentrantLock lock = new ReentrantLock();");
        System.out.println("lock.lock();");
        System.out.println("try {");
        System.out.println("    // Critical section");
        System.out.println("} finally {");
        System.out.println("    lock.unlock();");
        System.out.println("}");
        System.out.println();
    }

    /**
     * Print a thread dump
     */
    private static void printThreadDump() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(true, true);
        
        // Filter to show only our virtual threads
        List<ThreadInfo> virtualThreads = new ArrayList<>();
        for (ThreadInfo info : threadInfos) {
            if (info.getThreadName().startsWith("debug-vthread")) {
                virtualThreads.add(info);
            }
        }
        
        System.out.println("Virtual threads: " + virtualThreads.size());
        for (ThreadInfo info : virtualThreads) {
            System.out.println("Thread: " + info.getThreadName() + 
                    " (ID: " + info.getThreadId() + ", State: " + info.getThreadState() + ")");
            
            // Print stack trace
            StackTraceElement[] stackTrace = info.getStackTrace();
            for (StackTraceElement element : stackTrace) {
                System.out.println("    at " + element);
            }
            System.out.println();
        }
    }

    /**
     * Monitor thread states
     */
    private static void monitorThreadStates() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        ThreadInfo[] threadInfos = threadMXBean.dumpAllThreads(false, false);
        
        // Count threads by state
        int newCount = 0;
        int runnableCount = 0;
        int blockedCount = 0;
        int waitingCount = 0;
        int timedWaitingCount = 0;
        int terminatedCount = 0;
        
        for (ThreadInfo info : threadInfos) {
            switch (info.getThreadState()) {
                case NEW:
                    newCount++;
                    break;
                case RUNNABLE:
                    runnableCount++;
                    break;
                case BLOCKED:
                    blockedCount++;
                    break;
                case WAITING:
                    waitingCount++;
                    break;
                case TIMED_WAITING:
                    timedWaitingCount++;
                    break;
                case TERMINATED:
                    terminatedCount++;
                    break;
            }
        }
        
        System.out.println("Thread state counts:");
        System.out.println("NEW: " + newCount);
        System.out.println("RUNNABLE: " + runnableCount);
        System.out.println("BLOCKED: " + blockedCount);
        System.out.println("WAITING: " + waitingCount);
        System.out.println("TIMED_WAITING: " + timedWaitingCount);
        System.out.println("TERMINATED: " + terminatedCount);
        System.out.println("Total: " + threadInfos.length);
    }
}
