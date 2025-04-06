package OOP.shapes;

/**
 * Triangle class extending Shape
 * 
 * Demonstrates:
 * - Inheritance: Extends Shape class
 * - Encapsulation: Private fields with validation
 * - Polymorphism: Overrides abstract methods
 */
public class Triangle extends Shape {
    private double sideA;
    private double sideB;
    private double sideC;
    
    /**
     * Constructor for Triangle
     */
    public Triangle(String color, double sideA, double sideB, double sideC) {
        super(color);
        // Validate triangle inequality theorem: sum of any two sides > third side
        if (sideA + sideB <= sideC || sideA + sideC <= sideB || sideB + sideC <= sideA) {
            throw new IllegalArgumentException("Invalid triangle sides: Triangle inequality theorem violated");
        }
        if (sideA <= 0 || sideB <= 0 || sideC <= 0) {
            throw new IllegalArgumentException("All sides must be positive");
        }
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }
    
    /**
     * Getters for sides
     */
    public double getSideA() {
        return sideA;
    }
    
    public double getSideB() {
        return sideB;
    }
    
    public double getSideC() {
        return sideC;
    }
    
    /**
     * Implementation of abstract method from Shape
     * Calculates area of triangle using Heron's formula
     */
    @Override
    public double calculateArea() {
        // Semi-perimeter
        double s = (sideA + sideB + sideC) / 2;
        // Heron's formula
        return Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));
    }
    
    /**
     * Implementation of abstract method from Shape
     * Calculates perimeter of triangle: sum of all sides
     */
    @Override
    public double calculatePerimeter() {
        return sideA + sideB + sideC;
    }
    
    /**
     * Determine the type of triangle
     */
    public String getTriangleType() {
        if (sideA == sideB && sideB == sideC) {
            return "Equilateral";
        } else if (sideA == sideB || sideB == sideC || sideA == sideC) {
            return "Isosceles";
        } else {
            return "Scalene";
        }
    }
    
    /**
     * Overridden toString method
     */
    @Override
    public String toString() {
        return "Triangle [color=" + color + ", type=" + getTriangleType() + 
               ", sides=[" + sideA + ", " + sideB + ", " + sideC + "]]";
    }
}
