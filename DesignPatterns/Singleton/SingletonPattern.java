/**
 * Singleton Design Pattern
 * 
 * Intent: Ensures a class has only one instance and provides a global point of access to it.
 * 
 * This implementation demonstrates a thread-safe, lazy-initialized singleton with:
 * - Double-checked locking for performance
 * - Protection against reflection attacks
 * - Serialization safety
 * - Enum-based alternative (most concise and effective approach in Java)
 */
package design.patterns.singleton;

import java.io.Serializable;

public class SingletonPattern {
    
    public static void main(String[] args) {
        // Test the classic singleton
        ClassicSingleton instance1 = ClassicSingleton.getInstance();
        ClassicSingleton instance2 = ClassicSingleton.getInstance();
        
        System.out.println("Classic Singleton:");
        System.out.println("instance1 hash: " + instance1.hashCode());
        System.out.println("instance2 hash: " + instance2.hashCode());
        System.out.println("Same instance? " + (instance1 == instance2));
        
        // Test the enum singleton
        System.out.println("\nEnum Singleton:");
        EnumSingleton enumInstance1 = EnumSingleton.INSTANCE;
        EnumSingleton enumInstance2 = EnumSingleton.INSTANCE;
        
        System.out.println("enumInstance1 hash: " + enumInstance1.hashCode());
        System.out.println("enumInstance2 hash: " + enumInstance2.hashCode());
        System.out.println("Same instance? " + (enumInstance1 == enumInstance2));
        
        // Use the singletons
        instance1.doSomething();
        enumInstance1.doSomething();
    }
}

/**
 * Classic Singleton implementation with thread-safety and serialization protection
 */
class ClassicSingleton implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // Volatile ensures visibility across threads
    private static volatile ClassicSingleton instance;
    
    // Private constructor prevents instantiation from other classes
    private ClassicSingleton() {
        // Protect against reflection attacks
        if (instance != null) {
            throw new IllegalStateException("Singleton already initialized");
        }
    }
    
    /**
     * Thread-safe singleton with double-checked locking for performance
     * 
     * @return The singleton instance
     */
    public static ClassicSingleton getInstance() {
        // First check (no synchronization needed)
        if (instance == null) {
            // Enter synchronized block only if instance is null
            synchronized (ClassicSingleton.class) {
                // Second check (with synchronization)
                if (instance == null) {
                    instance = new ClassicSingleton();
                }
            }
        }
        return instance;
    }
    
    /**
     * Protect against serialization creating a second instance
     */
    protected Object readResolve() {
        return getInstance();
    }
    
    /**
     * Example method to demonstrate singleton usage
     */
    public void doSomething() {
        System.out.println("ClassicSingleton is doing something...");
    }
}

/**
 * Enum Singleton - The most effective way to implement a Singleton in Java
 * 
 * Benefits:
 * - Thread-safe by default
 * - Serialization handled by JVM
 * - Reflection-proof
 * - Guaranteed single instance
 */
enum EnumSingleton {
    INSTANCE;
    
    /**
     * Example method to demonstrate singleton usage
     */
    public void doSomething() {
        System.out.println("EnumSingleton is doing something...");
    }
}

/**
 * Initialization-on-demand holder idiom - Another efficient thread-safe approach
 * 
 * This approach uses a static inner class that isn't loaded until needed,
 * providing lazy initialization without synchronization overhead.
 */
class LazyHolderSingleton {
    
    private LazyHolderSingleton() {
        // Private constructor
    }
    
    // Static inner class - not loaded until first access
    private static class SingletonHolder {
        private static final LazyHolderSingleton INSTANCE = new LazyHolderSingleton();
    }
    
    public static LazyHolderSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
