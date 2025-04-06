package OOP.vehicles;

/**
 * Flyable interface
 * 
 * Demonstrates:
 * - Interface segregation: Specific interface for flyable vehicles
 * - Capability-based design
 */
public interface Flyable {
    /**
     * Take off the vehicle
     */
    void takeOff();
    
    /**
     * Fly the vehicle at a specific altitude and speed
     */
    void fly(double altitude, double speed);
    
    /**
     * Land the vehicle
     */
    void land();
    
    /**
     * Get the maximum altitude in feet
     */
    double getMaxAltitude();
    
    /**
     * Get the wingspan in feet
     */
    double getWingspan();
}
