package Multithreading;

/**
 * BasicThreadCreation
 * 
 * Demonstrates the basic ways to create and start threads in Java:
 * 1. Extending the Thread class
 * 2. Implementing the Runnable interface
 * 3. Using lambda expressions (Java 8+)
 */
public class BasicThreadCreation {

    public static void main(String[] args) {
        System.out.println("===== Basic Thread Creation Examples =====");
        System.out.println("Main thread: " + Thread.currentThread().getName());
        
        // Example 1: Creating a thread by extending Thread class
        System.out.println("\n1. Creating thread by extending Thread class:");
        MyThread thread1 = new MyThread("MyThread-1");
        thread1.start();
        
        // Example 2: Creating a thread by implementing Runnable interface
        System.out.println("\n2. Creating thread by implementing Runnable interface:");
        MyRunnable myRunnable = new MyRunnable();
        Thread thread2 = new Thread(myRunnable, "RunnableThread-1");
        thread2.start();
        
        // Example 3: Creating a thread using lambda expression (Java 8+)
        System.out.println("\n3. Creating thread using lambda expression:");
        Thread thread3 = new Thread(() -> {
            System.out.println("Lambda thread started: " + Thread.currentThread().getName());
            for (int i = 1; i <= 5; i++) {
                System.out.println("Lambda thread: Count " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("Lambda thread interrupted");
                    Thread.currentThread().interrupt(); // Restore interrupted status
                }
            }
            System.out.println("Lambda thread finished");
        }, "LambdaThread-1");
        thread3.start();
        
        // Example 4: Thread with priority
        System.out.println("\n4. Creating thread with priority:");
        Thread thread4 = new Thread(() -> {
            System.out.println("Priority thread started: " + Thread.currentThread().getName());
            System.out.println("Priority: " + Thread.currentThread().getPriority());
            for (int i = 1; i <= 3; i++) {
                System.out.println("Priority thread: Count " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println("Priority thread interrupted");
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Priority thread finished");
        }, "PriorityThread-1");
        thread4.setPriority(Thread.MAX_PRIORITY); // Set maximum priority
        thread4.start();
        
        // Example 5: Joining threads
        System.out.println("\n5. Joining threads:");
        Thread thread5 = new Thread(() -> {
            System.out.println("Join thread started: " + Thread.currentThread().getName());
            for (int i = 1; i <= 3; i++) {
                System.out.println("Join thread: Count " + i);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println("Join thread interrupted");
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Join thread finished");
        }, "JoinThread-1");
        thread5.start();
        
        try {
            System.out.println("Main thread waiting for JoinThread-1 to finish...");
            thread5.join(); // Wait for thread5 to finish
            System.out.println("JoinThread-1 has finished, main thread continues");
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted while waiting");
            Thread.currentThread().interrupt();
        }
        
        // Example 6: Daemon thread
        System.out.println("\n6. Creating daemon thread:");
        Thread daemonThread = new Thread(() -> {
            System.out.println("Daemon thread started: " + Thread.currentThread().getName());
            System.out.println("Is Daemon: " + Thread.currentThread().isDaemon());
            
            // Infinite loop - but will terminate when main thread ends
            // because daemon threads don't prevent JVM from exiting
            int count = 1;
            while (true) {
                System.out.println("Daemon thread: Count " + count++);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("Daemon thread interrupted");
                    break;
                }
            }
        }, "DaemonThread-1");
        
        daemonThread.setDaemon(true); // Set as daemon thread
        daemonThread.start();
        
        // Sleep to allow other threads to run and demonstrate output
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Main thread interrupted");
            Thread.currentThread().interrupt();
        }
        
        System.out.println("\nMain thread finishing. Daemon thread will terminate.");
        System.out.println("===== End of Basic Thread Creation Examples =====");
    }
    
    /**
     * Example of creating a thread by extending the Thread class
     */
    static class MyThread extends Thread {
        
        public MyThread(String name) {
            super(name);
        }
        
        @Override
        public void run() {
            System.out.println("MyThread started: " + getName());
            for (int i = 1; i <= 5; i++) {
                System.out.println(getName() + ": Count " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println(getName() + " interrupted");
                    interrupt(); // Restore interrupted status
                }
            }
            System.out.println(getName() + " finished");
        }
    }
    
    /**
     * Example of creating a thread by implementing the Runnable interface
     */
    static class MyRunnable implements Runnable {
        
        @Override
        public void run() {
            String threadName = Thread.currentThread().getName();
            System.out.println("MyRunnable started in thread: " + threadName);
            for (int i = 1; i <= 5; i++) {
                System.out.println(threadName + ": Count " + i);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    System.out.println(threadName + " interrupted");
                    Thread.currentThread().interrupt(); // Restore interrupted status
                }
            }
            System.out.println(threadName + " finished");
        }
    }
}
