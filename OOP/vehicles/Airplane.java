package OOP.vehicles;

/**
 * Airplane class extending AbstractVehicle and implementing Flyable
 * 
 * Demonstrates:
 * - Multiple interface implementation
 * - Inheritance from abstract class
 * - Specific implementation of interfaces
 */
public class Airplane extends AbstractVehicle implements Flyable {
    private double wingspan;
    private double maxAltitude;
    private int numberOfEngines;
    private boolean isFlying;
    private double currentAltitude;
    private double currentSpeed;
    
    /**
     * Constructor for Airplane
     */
    public Airplane(String make, String model, int year, double maxSpeed,
                   double wingspan, double maxAltitude, int numberOfEngines) {
        super(make, model, year, maxSpeed);
        this.wingspan = wingspan;
        this.maxAltitude = maxAltitude;
        this.numberOfEngines = numberOfEngines;
        this.isFlying = false;
        this.currentAltitude = 0;
        this.currentSpeed = 0;
    }
    
    /**
     * Implementation of takeOff method from Flyable
     */
    @Override
    public void takeOff() {
        if (!isRunning) {
            System.out.println("Cannot take off: Airplane is not running");
            return;
        }
        
        if (isFlying) {
            System.out.println("Airplane is already flying");
            return;
        }
        
        System.out.println("Taking off...");
        isFlying = true;
        currentAltitude = 1000; // Initial altitude after takeoff
        currentSpeed = 200;     // Initial speed after takeoff
        System.out.println("Airplane is now airborne at " + currentAltitude + " feet");
    }
    
    /**
     * Implementation of fly method from Flyable
     */
    @Override
    public void fly(double altitude, double speed) {
        if (!isRunning || !isFlying) {
            System.out.println("Cannot fly: Airplane is not running or hasn't taken off");
            return;
        }
        
        if (altitude > maxAltitude) {
            System.out.println("Cannot exceed maximum altitude of " + maxAltitude + " feet");
            currentAltitude = maxAltitude;
        } else {
            currentAltitude = altitude;
        }
        
        if (speed > maxSpeed) {
            System.out.println("Cannot exceed maximum speed of " + maxSpeed + " mph");
            currentSpeed = maxSpeed;
        } else {
            currentSpeed = speed;
        }
        
        System.out.println("Flying at altitude of " + currentAltitude + 
                          " feet and speed of " + currentSpeed + " mph");
    }
    
    /**
     * Implementation of land method from Flyable
     */
    @Override
    public void land() {
        if (!isRunning || !isFlying) {
            System.out.println("Cannot land: Airplane is not flying");
            return;
        }
        
        System.out.println("Landing...");
        isFlying = false;
        currentAltitude = 0;
        currentSpeed = 0;
        System.out.println("Airplane has landed safely");
    }
    
    /**
     * Implementation of getMaxAltitude method from Flyable
     */
    @Override
    public double getMaxAltitude() {
        return maxAltitude;
    }
    
    /**
     * Implementation of getWingspan method from Flyable
     */
    @Override
    public double getWingspan() {
        return wingspan;
    }
    
    /**
     * Get number of engines
     */
    public int getNumberOfEngines() {
        return numberOfEngines;
    }
    
    /**
     * Check if the airplane is flying
     */
    public boolean isFlying() {
        return isFlying;
    }
    
    /**
     * Get current altitude
     */
    public double getCurrentAltitude() {
        return currentAltitude;
    }
    
    /**
     * Get current speed
     */
    public double getCurrentSpeed() {
        return currentSpeed;
    }
    
    /**
     * Override displayInfo to include airplane-specific information
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Wingspan: " + wingspan + " feet");
        System.out.println("Maximum Altitude: " + maxAltitude + " feet");
        System.out.println("Number of Engines: " + numberOfEngines);
        System.out.println("Flying: " + (isFlying ? "Yes" : "No"));
        System.out.println("Current Altitude: " + currentAltitude + " feet");
        System.out.println("Current Speed: " + currentSpeed + " mph");
    }
    
    /**
     * Overridden toString method
     */
    @Override
    public String toString() {
        return super.toString() + " [wingspan=" + wingspan + " feet" + 
               ", maxAltitude=" + maxAltitude + " feet" + 
               ", engines=" + numberOfEngines + "]";
    }
}
