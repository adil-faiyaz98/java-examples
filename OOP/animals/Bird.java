package OOP.animals;

/**
 * Bird class extending Animal
 * 
 * Demonstrates:
 * - Inheritance: Extends Animal class
 * - Polymorphism: Overrides abstract methods
 * - Encapsulation: Private fields with getters/setters
 */
public class Bird extends Animal {
    private double wingspan;
    private boolean canFly;
    private String featherColor;
    
    /**
     * Constructor for Bird
     */
    public Bird(String name, int age, String species, double weight, 
               double wingspan, boolean canFly, String featherColor) {
        super(name, age, species, weight);
        this.wingspan = wingspan;
        this.canFly = canFly;
        this.featherColor = featherColor;
    }
    
    /**
     * Implementation of abstract method from Animal
     */
    @Override
    public String makeSound() {
        return "Tweet! Tweet!";
    }
    
    /**
     * Implementation of abstract method from Animal
     */
    @Override
    public void move() {
        if (canFly) {
            System.out.println(name + " is flying through the air");
        } else {
            System.out.println(name + " is hopping around");
        }
    }
    
    /**
     * Implementation of abstract method from Animal
     */
    @Override
    public void eat(String food) {
        System.out.println(name + " is pecking at " + food);
    }
    
    /**
     * Bird-specific method
     */
    public void fly() {
        if (canFly) {
            System.out.println(name + " is soaring through the sky");
        } else {
            System.out.println(name + " cannot fly");
        }
    }
    
    /**
     * Bird-specific method
     */
    public void buildNest() {
        System.out.println(name + " is building a nest");
    }
    
    /**
     * Bird-specific method
     */
    public void layEgg() {
        System.out.println(name + " has laid an egg");
    }
    
    /**
     * Getters and setters for Bird-specific fields
     */
    public double getWingspan() {
        return wingspan;
    }
    
    public boolean canFly() {
        return canFly;
    }
    
    public String getFeatherColor() {
        return featherColor;
    }
    
    public void setFeatherColor(String featherColor) {
        this.featherColor = featherColor;
    }
    
    /**
     * Override displayInfo to include bird-specific information
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Wingspan: " + wingspan + " cm");
        System.out.println("Can Fly: " + (canFly ? "Yes" : "No"));
        System.out.println("Feather Color: " + featherColor);
    }
    
    /**
     * Overridden toString method
     */
    @Override
    public String toString() {
        return super.toString() + " [wingspan=" + wingspan + " cm, canFly=" + canFly + 
               ", featherColor=" + featherColor + "]";
    }
}
