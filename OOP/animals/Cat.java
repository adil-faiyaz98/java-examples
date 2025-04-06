package OOP.animals;

/**
 * Cat class extending Animal
 * 
 * Demonstrates:
 * - Inheritance: Extends Animal class
 * - Polymorphism: Overrides abstract methods
 * - Encapsulation: Private fields with getters/setters
 */
public class Cat extends Animal {
    private String coatColor;
    private boolean isIndoor;
    
    /**
     * Constructor for Cat
     */
    public Cat(String name, int age, String coatColor, double weight, boolean isIndoor) {
        super(name, age, "Felis catus", weight);
        this.coatColor = coatColor;
        this.isIndoor = isIndoor;
    }
    
    /**
     * Implementation of abstract method from Animal
     */
    @Override
    public String makeSound() {
        return "Meow!";
    }
    
    /**
     * Implementation of abstract method from Animal
     */
    @Override
    public void move() {
        System.out.println(name + " is prowling silently");
    }
    
    /**
     * Implementation of abstract method from Animal
     */
    @Override
    public void eat(String food) {
        System.out.println(name + " is eating " + food + " delicately");
    }
    
    /**
     * Cat-specific method
     */
    public void purr() {
        System.out.println(name + " is purring contentedly");
    }
    
    /**
     * Cat-specific method
     */
    public void scratch(String item) {
        System.out.println(name + " is scratching the " + item);
    }
    
    /**
     * Cat-specific method
     */
    public void hunt() {
        if (!isIndoor) {
            System.out.println(name + " is hunting for mice");
        } else {
            System.out.println(name + " is hunting toy mice indoors");
        }
    }
    
    /**
     * Getters and setters for Cat-specific fields
     */
    public String getCoatColor() {
        return coatColor;
    }
    
    public boolean isIndoor() {
        return isIndoor;
    }
    
    public void setIndoor(boolean indoor) {
        isIndoor = indoor;
    }
    
    /**
     * Override displayInfo to include cat-specific information
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Coat Color: " + coatColor);
        System.out.println("Indoor Cat: " + (isIndoor ? "Yes" : "No"));
    }
    
    /**
     * Overridden toString method
     */
    @Override
    public String toString() {
        return super.toString() + " [coatColor=" + coatColor + ", indoor=" + isIndoor + "]";
    }
}
