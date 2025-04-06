package OOP.shapes;

/**
 * Abstract Shape class
 * 
 * Demonstrates:
 * - Abstraction: Abstract class with abstract methods
 * - Encapsulation: Private fields with getters/setters
 */
public abstract class Shape {
    // Protected fields accessible to subclasses
    protected String color;
    
    /**
     * Constructor for Shape
     */
    public Shape(String color) {
        this.color = color;
    }
    
    /**
     * Abstract method to calculate area
     * Each shape must implement its own area calculation
     */
    public abstract double calculateArea();
    
    /**
     * Abstract method to calculate perimeter
     * Each shape must implement its own perimeter calculation
     */
    public abstract double calculatePerimeter();
    
    /**
     * Concrete method shared by all shapes
     */
    public String getColor() {
        return color;
    }
    
    /**
     * Concrete method shared by all shapes
     */
    public void setColor(String color) {
        this.color = color;
    }
    
    /**
     * Template method that uses abstract methods
     * Demonstrates the Template Method pattern
     */
    public final void displayInfo() {
        System.out.println("Shape Type: " + getClass().getSimpleName());
        System.out.println("Color: " + color);
        System.out.println("Area: " + calculateArea());
        System.out.println("Perimeter: " + calculatePerimeter());
    }
    
    /**
     * Overridden toString method
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " [color=" + color + "]";
    }
}
