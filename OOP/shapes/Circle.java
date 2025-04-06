package OOP.shapes;

/**
 * Circle class extending Shape
 * 
 * Demonstrates:
 * - Inheritance: Extends Shape class
 * - Encapsulation: Private radius with validation
 * - Polymorphism: Overrides abstract methods
 */
public class Circle extends Shape {
    private double radius;
    
    /**
     * Constructor for Circle
     */
    public Circle(String color, double radius) {
        super(color);
        setRadius(radius);
    }
    
    /**
     * Getter for radius
     */
    public double getRadius() {
        return radius;
    }
    
    /**
     * Setter for radius with validation
     */
    public void setRadius(double radius) {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive");
        }
        this.radius = radius;
    }
    
    /**
     * Implementation of abstract method from Shape
     * Calculates area of circle: π * r²
     */
    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }
    
    /**
     * Implementation of abstract method from Shape
     * Calculates perimeter (circumference) of circle: 2 * π * r
     */
    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }
    
    /**
     * Overridden toString method
     */
    @Override
    public String toString() {
        return "Circle [color=" + color + ", radius=" + radius + "]";
    }
}
