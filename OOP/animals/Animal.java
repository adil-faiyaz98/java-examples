package OOP.animals;

/**
 * Abstract Animal class
 * 
 * Demonstrates:
 * - Abstraction: Abstract class with abstract methods
 * - Encapsulation: Protected fields with getters/setters
 * - Template method pattern
 */
public abstract class Animal {
    protected String name;
    protected int age;
    protected String species;
    protected double weight;
    
    /**
     * Constructor for Animal
     */
    public Animal(String name, int age, String species, double weight) {
        this.name = name;
        this.age = age;
        this.species = species;
        this.weight = weight;
    }
    
    /**
     * Abstract method for animal sound
     * Each animal must implement its own sound
     */
    public abstract String makeSound();
    
    /**
     * Abstract method for animal movement
     * Each animal must implement its own movement
     */
    public abstract void move();
    
    /**
     * Abstract method for animal eating behavior
     * Each animal must implement its own eating behavior
     */
    public abstract void eat(String food);
    
    /**
     * Concrete method shared by all animals
     */
    public void sleep() {
        System.out.println(name + " is sleeping...");
    }
    
    /**
     * Template method that uses abstract methods
     * Demonstrates the Template Method pattern
     */
    public final void performDailyActivities() {
        System.out.println(name + " is starting daily activities:");
        System.out.println("- Wakes up");
        move();
        System.out.println("- Makes sound: " + makeSound());
        eat("food");
        sleep();
    }
    
    /**
     * Getters and setters
     */
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
        this.age = age;
    }
    
    public String getSpecies() {
        return species;
    }
    
    public double getWeight() {
        return weight;
    }
    
    public void setWeight(double weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
        this.weight = weight;
    }
    
    /**
     * Display animal information
     */
    public void displayInfo() {
        System.out.println("Animal Type: " + getClass().getSimpleName());
        System.out.println("Name: " + name);
        System.out.println("Species: " + species);
        System.out.println("Age: " + age + " years");
        System.out.println("Weight: " + weight + " kg");
    }
    
    /**
     * Overridden toString method
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " [name=" + name + ", species=" + species + 
               ", age=" + age + ", weight=" + weight + " kg]";
    }
}
