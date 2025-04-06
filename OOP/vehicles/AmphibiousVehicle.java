package OOP.vehicles;

/**
 * AmphibiousVehicle class extending AbstractVehicle and implementing Drivable
 * 
 * Demonstrates:
 * - Multiple interface implementation
 * - Inheritance from abstract class
 * - Multiple capabilities in one class
 */
public class AmphibiousVehicle extends AbstractVehicle implements Drivable {
    private boolean isInWaterMode;
    private double currentSpeed;
    private boolean isParked;
    private int numberOfWheels;
    
    /**
     * Constructor for AmphibiousVehicle
     */
    public AmphibiousVehicle(String make, String model, int year, double maxSpeed, int numberOfWheels) {
        super(make, model, year, maxSpeed);
        this.isInWaterMode = false;
        this.currentSpeed = 0;
        this.isParked = true;
        this.numberOfWheels = numberOfWheels;
    }
    
    /**
     * Implementation of drive method from Drivable
     */
    @Override
    public void drive(double speed) {
        if (!isRunning) {
            System.out.println("Cannot drive: Vehicle is not running");
            return;
        }
        
        if (isInWaterMode) {
            System.out.println("Cannot drive on land: Vehicle is in water mode");
            return;
        }
        
        if (isParked) {
            System.out.println("Taking vehicle out of park");
            isParked = false;
        }
        
        if (speed > maxSpeed) {
            System.out.println("Cannot exceed maximum speed of " + maxSpeed + " mph");
            currentSpeed = maxSpeed;
        } else {
            currentSpeed = speed;
        }
        
        System.out.println("Driving on land at " + currentSpeed + " mph");
    }
    
    /**
     * Implementation of brake method from Drivable
     */
    @Override
    public void brake() {
        if (currentSpeed > 0) {
            System.out.println("Applying brakes");
            currentSpeed = 0;
        } else {
            System.out.println("Vehicle is already stopped");
        }
    }
    
    /**
     * Implementation of turn method from Drivable
     */
    @Override
    public void turn(String direction) {
        if (!isRunning || isParked) {
            System.out.println("Cannot turn: Vehicle is not in motion");
            return;
        }
        
        System.out.println("Turning " + direction + (isInWaterMode ? " in water" : " on land"));
    }
    
    /**
     * Implementation of park method from Drivable
     */
    @Override
    public void park() {
        if (!isRunning) {
            System.out.println("Cannot park: Vehicle is not running");
            return;
        }
        
        if (isInWaterMode) {
            System.out.println("Cannot park in water mode");
            return;
        }
        
        if (currentSpeed > 0) {
            System.out.println("Stopping vehicle before parking");
            currentSpeed = 0;
        }
        
        isParked = true;
        System.out.println("Vehicle is now parked");
    }
    
    /**
     * Implementation of getNumberOfWheels method from Drivable
     */
    @Override
    public int getNumberOfWheels() {
        return numberOfWheels;
    }
    
    /**
     * Switch between land and water mode
     */
    public void switchMode() {
        if (currentSpeed > 0) {
            System.out.println("Cannot switch modes while moving");
            return;
        }
        
        isInWaterMode = !isInWaterMode;
        System.out.println("Switched to " + (isInWaterMode ? "water" : "land") + " mode");
    }
    
    /**
     * Drive in water (specific to amphibious vehicles)
     */
    public void driveInWater(double speed) {
        if (!isRunning) {
            System.out.println("Cannot drive: Vehicle is not running");
            return;
        }
        
        if (!isInWaterMode) {
            System.out.println("Cannot drive in water: Vehicle is in land mode");
            return;
        }
        
        if (speed > maxSpeed * 0.7) { // Typically slower in water
            System.out.println("Cannot exceed maximum water speed of " + (maxSpeed * 0.7) + " mph");
            currentSpeed = maxSpeed * 0.7;
        } else {
            currentSpeed = speed;
        }
        
        System.out.println("Driving in water at " + currentSpeed + " mph");
    }
    
    /**
     * Check if the vehicle is in water mode
     */
    public boolean isInWaterMode() {
        return isInWaterMode;
    }
    
    /**
     * Get current speed
     */
    public double getCurrentSpeed() {
        return currentSpeed;
    }
    
    /**
     * Check if the vehicle is parked
     */
    public boolean isParked() {
        return isParked;
    }
    
    /**
     * Override displayInfo to include amphibious vehicle-specific information
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Number of Wheels: " + numberOfWheels);
        System.out.println("Current Mode: " + (isInWaterMode ? "Water" : "Land"));
        System.out.println("Current Speed: " + currentSpeed + " mph");
        System.out.println("Parked: " + (isParked ? "Yes" : "No"));
    }
    
    /**
     * Overridden toString method
     */
    @Override
    public String toString() {
        return super.toString() + " [wheels=" + numberOfWheels + 
               ", mode=" + (isInWaterMode ? "Water" : "Land") + "]";
    }
}
