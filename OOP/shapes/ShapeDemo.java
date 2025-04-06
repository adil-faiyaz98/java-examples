package OOP.shapes;

import java.util.ArrayList;
import java.util.List;

/**
 * Demo class for Shape hierarchy
 * 
 * Demonstrates:
 * - Polymorphism: Using base class references for derived objects
 * - Dynamic method dispatch: Correct methods called based on actual object type
 */
public class ShapeDemo {
    
    public static void main(String[] args) {
        // Create different shapes
        Circle circle = new Circle("Red", 5.0);
        Rectangle rectangle = new Rectangle("Blue", 4.0, 6.0);
        Rectangle square = new Rectangle("Green", 5.0, 5.0);
        Triangle triangle = new Triangle("Yellow", 3.0, 4.0, 5.0);
        
        // Demonstrate polymorphism by storing different shapes in a list of Shape
        List<Shape> shapes = new ArrayList<>();
        shapes.add(circle);
        shapes.add(rectangle);
        shapes.add(square);
        shapes.add(triangle);
        
        // Process all shapes polymorphically
        System.out.println("===== Shape Information =====");
        for (Shape shape : shapes) {
            // Dynamic method dispatch - correct methods called based on actual object type
            shape.displayInfo();
            System.out.println("-------------------------");
        }
        
        // Demonstrate accessing specific methods of derived classes
        System.out.println("\n===== Specific Shape Features =====");
        System.out.println("Is the rectangle a square? " + rectangle.isSquare());
        System.out.println("Is the 'square' rectangle a square? " + square.isSquare());
        System.out.println("Triangle type: " + triangle.getTriangleType());
        System.out.println("Circle radius: " + circle.getRadius());
        
        // Demonstrate changing properties
        System.out.println("\n===== After Changing Properties =====");
        circle.setRadius(7.5);
        rectangle.setColor("Purple");
        
        System.out.println("Updated circle: " + circle);
        System.out.println("Updated rectangle: " + rectangle);
        
        // Calculate total area of all shapes
        double totalArea = 0;
        for (Shape shape : shapes) {
            totalArea += shape.calculateArea();
        }
        
        System.out.println("\nTotal area of all shapes: " + totalArea);
    }
}
