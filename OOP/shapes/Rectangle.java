package OOP.shapes;

/**
 * Rectangle class extending Shape
 * 
 * Demonstrates:
 * - Inheritance: Extends Shape class
 * - Encapsulation: Private fields with validation
 * - Polymorphism: Overrides abstract methods
 */
public class Rectangle extends Shape {
    private double length;
    private double width;
    
    /**
     * Constructor for Rectangle
     */
    public Rectangle(String color, double length, double width) {
        super(color);
        setLength(length);
        setWidth(width);
    }
    
    /**
     * Getter for length
     */
    public double getLength() {
        return length;
    }
    
    /**
     * Setter for length with validation
     */
    public void setLength(double length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive");
        }
        this.length = length;
    }
    
    /**
     * Getter for width
     */
    public double getWidth() {
        return width;
    }
    
    /**
     * Setter for width with validation
     */
    public void setWidth(double width) {
        if (width <= 0) {
            throw new IllegalArgumentException("Width must be positive");
        }
        this.width = width;
    }
    
    /**
     * Implementation of abstract method from Shape
     * Calculates area of rectangle: length * width
     */
    @Override
    public double calculateArea() {
        return length * width;
    }
    
    /**
     * Implementation of abstract method from Shape
     * Calculates perimeter of rectangle: 2 * (length + width)
     */
    @Override
    public double calculatePerimeter() {
        return 2 * (length + width);
    }
    
    /**
     * Overridden toString method
     */
    @Override
    public String toString() {
        return "Rectangle [color=" + color + ", length=" + length + ", width=" + width + "]";
    }
    
    /**
     * Check if the rectangle is a square
     */
    public boolean isSquare() {
        return length == width;
    }
}
