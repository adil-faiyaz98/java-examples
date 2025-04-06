package OOP.vehicles;

/**
 * AbstractVehicle class implementing common Vehicle functionality
 * 
 * Demonstrates:
 * - Abstract class: Partial implementation of an interface
 * - Template pattern: Common implementation with specific parts deferred to subclasses
 * - Code reuse through inheritance
 */
public abstract class AbstractVehicle implements Vehicle {
    protected String make;
    protected String model;
    protected int year;
    protected double maxSpeed;
    protected boolean isRunning;
    
    /**
     * Constructor for AbstractVehicle
     */
    public AbstractVehicle(String make, String model, int year, double maxSpeed) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.maxSpeed = maxSpeed;
        this.isRunning = false;
    }
    
    /**
     * Implementation of start method
     */
    @Override
    public void start() {
        if (!isRunning) {
            isRunning = true;
            System.out.println(getClass().getSimpleName() + " started");
        } else {
            System.out.println(getClass().getSimpleName() + " is already running");
        }
    }
    
    /**
     * Implementation of stop method
     */
    @Override
    public void stop() {
        if (isRunning) {
            isRunning = false;
            System.out.println(getClass().getSimpleName() + " stopped");
        } else {
            System.out.println(getClass().getSimpleName() + " is already stopped");
        }
    }
    
    /**
     * Implementation of getMake method
     */
    @Override
    public String getMake() {
        return make;
    }
    
    /**
     * Implementation of getModel method
     */
    @Override
    public String getModel() {
        return model;
    }
    
    /**
     * Implementation of getYear method
     */
    @Override
    public int getYear() {
        return year;
    }
    
    /**
     * Implementation of getMaxSpeed method
     */
    @Override
    public double getMaxSpeed() {
        return maxSpeed;
    }
    
    /**
     * Implementation of displayInfo method
     */
    @Override
    public void displayInfo() {
        System.out.println("Vehicle Type: " + getClass().getSimpleName());
        System.out.println("Make: " + make);
        System.out.println("Model: " + model);
        System.out.println("Year: " + year);
        System.out.println("Max Speed: " + maxSpeed + " mph");
        System.out.println("Status: " + (isRunning ? "Running" : "Stopped"));
    }
    
    /**
     * Check if the vehicle is running
     */
    public boolean isRunning() {
        return isRunning;
    }
    
    /**
     * Overridden toString method
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " [make=" + make + ", model=" + model + 
               ", year=" + year + ", maxSpeed=" + maxSpeed + " mph]";
    }
}
