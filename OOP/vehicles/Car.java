package OOP.vehicles;

/**
 * Car class extending AbstractVehicle and implementing Drivable
 * 
 * Demonstrates:
 * - Multiple interface implementation
 * - Inheritance from abstract class
 * - Specific implementation of interfaces
 */
public class Car extends AbstractVehicle implements Drivable {
    private int numberOfDoors;
    private String transmissionType;
    private boolean isParked;
    private double currentSpeed;
    
    /**
     * Constructor for Car
     */
    public Car(String make, String model, int year, double maxSpeed, 
              int numberOfDoors, String transmissionType) {
        super(make, model, year, maxSpeed);
        this.numberOfDoors = numberOfDoors;
        this.transmissionType = transmissionType;
        this.isParked = true;
        this.currentSpeed = 0;
    }
    
    /**
     * Implementation of drive method from Drivable
     */
    @Override
    public void drive(double speed) {
        if (!isRunning) {
            System.out.println("Cannot drive: Car is not running");
            return;
        }
        
        if (isParked) {
            System.out.println("Taking car out of park");
            isParked = false;
        }
        
        if (speed > maxSpeed) {
            System.out.println("Cannot exceed maximum speed of " + maxSpeed + " mph");
            currentSpeed = maxSpeed;
        } else {
            currentSpeed = speed;
        }
        
        System.out.println("Driving at " + currentSpeed + " mph");
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
            System.out.println("Car is already stopped");
        }
    }
    
    /**
     * Implementation of turn method from Drivable
     */
    @Override
    public void turn(String direction) {
        if (!isRunning || isParked) {
            System.out.println("Cannot turn: Car is not in motion");
            return;
        }
        
        System.out.println("Turning " + direction);
    }
    
    /**
     * Implementation of park method from Drivable
     */
    @Override
    public void park() {
        if (!isRunning) {
            System.out.println("Cannot park: Car is not running");
            return;
        }
        
        if (currentSpeed > 0) {
            System.out.println("Stopping car before parking");
            currentSpeed = 0;
        }
        
        isParked = true;
        System.out.println("Car is now parked");
    }
    
    /**
     * Implementation of getNumberOfWheels method from Drivable
     */
    @Override
    public int getNumberOfWheels() {
        return 4;
    }
    
    /**
     * Get number of doors
     */
    public int getNumberOfDoors() {
        return numberOfDoors;
    }
    
    /**
     * Get transmission type
     */
    public String getTransmissionType() {
        return transmissionType;
    }
    
    /**
     * Get current speed
     */
    public double getCurrentSpeed() {
        return currentSpeed;
    }
    
    /**
     * Check if the car is parked
     */
    public boolean isParked() {
        return isParked;
    }
    
    /**
     * Override displayInfo to include car-specific information
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Number of Doors: " + numberOfDoors);
        System.out.println("Transmission Type: " + transmissionType);
        System.out.println("Number of Wheels: " + getNumberOfWheels());
        System.out.println("Current Speed: " + currentSpeed + " mph");
        System.out.println("Parked: " + (isParked ? "Yes" : "No"));
    }
    
    /**
     * Overridden toString method
     */
    @Override
    public String toString() {
        return super.toString() + " [doors=" + numberOfDoors + 
               ", transmission=" + transmissionType + 
               ", wheels=" + getNumberOfWheels() + "]";
    }
}
