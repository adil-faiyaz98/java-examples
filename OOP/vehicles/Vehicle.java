package OOP.vehicles;

/**
 * Vehicle interface
 * 
 * Demonstrates:
 * - Abstraction: Interface defining common vehicle operations
 * - Contract definition for all vehicles
 */
public interface Vehicle {
    /**
     * Start the vehicle
     */
    void start();
    
    /**
     * Stop the vehicle
     */
    void stop();
    
    /**
     * Get the vehicle's make
     */
    String getMake();
    
    /**
     * Get the vehicle's model
     */
    String getModel();
    
    /**
     * Get the vehicle's year
     */
    int getYear();
    
    /**
     * Get the vehicle's maximum speed in mph
     */
    double getMaxSpeed();
    
    /**
     * Display vehicle information
     */
    void displayInfo();
}
