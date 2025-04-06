package OOP.animals;

/**
 * Dog class extending Animal
 * 
 * Demonstrates:
 * - Inheritance: Extends Animal class
 * - Polymorphism: Overrides abstract methods
 * - Encapsulation: Private fields with getters/setters
 */
public class Dog extends Animal {
    private String breed;
    private boolean isTrained;
    
    /**
     * Constructor for Dog
     */
    public Dog(String name, int age, String breed, double weight, boolean isTrained) {
        super(name, age, "Canis familiaris", weight);
        this.breed = breed;
        this.isTrained = isTrained;
    }
    
    /**
     * Implementation of abstract method from Animal
     */
    @Override
    public String makeSound() {
        return "Woof! Woof!";
    }
    
    /**
     * Implementation of abstract method from Animal
     */
    @Override
    public void move() {
        System.out.println(name + " is running on four legs");
    }
    
    /**
     * Implementation of abstract method from Animal
     */
    @Override
    public void eat(String food) {
        System.out.println(name + " is eating " + food + " from a bowl");
    }
    
    /**
     * Dog-specific method
     */
    public void fetch(String item) {
        System.out.println(name + " is fetching the " + item);
    }
    
    /**
     * Dog-specific method
     */
    public void bark() {
        System.out.println(name + " says: " + makeSound());
    }
    
    /**
     * Dog-specific method that depends on training
     */
    public void performTrick(String trick) {
        if (isTrained) {
            System.out.println(name + " performs trick: " + trick);
        } else {
            System.out.println(name + " doesn't know how to " + trick);
        }
    }
    
    /**
     * Getters and setters for Dog-specific fields
     */
    public String getBreed() {
        return breed;
    }
    
    public boolean isTrained() {
        return isTrained;
    }
    
    public void setTrained(boolean trained) {
        isTrained = trained;
    }
    
    /**
     * Override displayInfo to include dog-specific information
     */
    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Breed: " + breed);
        System.out.println("Trained: " + (isTrained ? "Yes" : "No"));
    }
    
    /**
     * Overridden toString method
     */
    @Override
    public String toString() {
        return super.toString() + " [breed=" + breed + ", trained=" + isTrained + "]";
    }
}
