package OOP.vehicles;

/**
 * Drivable interface
 * 
 * Demonstrates:
 * - Interface segregation: Specific interface for drivable vehicles
 * - Capability-based design
 */
public interface Drivable {
    /**
     * Drive the vehicle at a specific speed
     */
    void drive(double speed);
    
    /**
     * Brake the vehicle
     */
    void brake();
    
    /**
     * Turn the vehicle in a direction
     */
    void turn(String direction);
    
    /**
     * Park the vehicle
     */
    void park();
    
    /**
     * Get the number of wheels
     */
    int getNumberOfWheels();
}
