package OOP.vehicles;

import java.util.ArrayList;
import java.util.List;

/**
 * Demo class for Vehicle hierarchy
 * 
 * Demonstrates:
 * - Polymorphism: Using interfaces and base class references
 * - Dynamic method dispatch
 * - Interface segregation benefits
 */
public class VehicleDemo {
    
    public static void main(String[] args) {
        // Create different vehicle types
        Car sedan = new Car("Honda", "Accord", 2022, 140, 4, "Automatic");
        Airplane jetliner = new Airplane("Boeing", "747", 2020, 570, 225, 45000, 4);
        AmphibiousVehicle amphibious = new AmphibiousVehicle("Gibbs", "Aquada", 2021, 100, 4);
        
        // Demonstrate polymorphism with Vehicle interface
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(sedan);
        vehicles.add(jetliner);
        vehicles.add(amphibious);
        
        System.out.println("===== Vehicle Information =====");
        for (Vehicle vehicle : vehicles) {
            vehicle.displayInfo();
            System.out.println("-------------------------");
        }
        
        // Demonstrate polymorphism with Drivable interface
        List<Drivable> drivableVehicles = new ArrayList<>();
        drivableVehicles.add(sedan);
        drivableVehicles.add(amphibious);
        
        System.out.println("\n===== Drivable Vehicles =====");
        for (Drivable drivable : drivableVehicles) {
            System.out.println(drivable.getClass().getSimpleName() + 
                              " has " + drivable.getNumberOfWheels() + " wheels");
        }
        
        // Demonstrate Flyable interface
        List<Flyable> flyableVehicles = new ArrayList<>();
        flyableVehicles.add(jetliner);
        
        System.out.println("\n===== Flyable Vehicles =====");
        for (Flyable flyable : flyableVehicles) {
            System.out.println(flyable.getClass().getSimpleName() + 
                              " has wingspan of " + flyable.getWingspan() + 
                              " feet and max altitude of " + flyable.getMaxAltitude() + " feet");
        }
        
        // Demonstrate vehicle operations
        System.out.println("\n===== Vehicle Operations =====");
        
        // Car operations
        System.out.println("\nCar Operations:");
        sedan.start();
        sedan.drive(65);
        sedan.turn("right");
        sedan.brake();
        sedan.park();
        sedan.stop();
        
        // Airplane operations
        System.out.println("\nAirplane Operations:");
        jetliner.start();
        jetliner.takeOff();
        jetliner.fly(35000, 550);
        jetliner.land();
        jetliner.stop();
        
        // Amphibious vehicle operations
        System.out.println("\nAmphibious Vehicle Operations:");
        amphibious.start();
        amphibious.drive(45);
        amphibious.brake();
        amphibious.switchMode();
        amphibious.driveInWater(25);
        amphibious.switchMode();
        amphibious.park();
        amphibious.stop();
        
        // Demonstrate type checking and casting
        System.out.println("\n===== Type Checking and Casting =====");
        for (Vehicle vehicle : vehicles) {
            System.out.print(vehicle.getClass().getSimpleName() + " is ");
            
            if (vehicle instanceof Drivable) {
                System.out.print("Drivable ");
            }
            
            if (vehicle instanceof Flyable) {
                System.out.print("Flyable ");
            }
            
            if (vehicle instanceof AmphibiousVehicle) {
                System.out.print("Amphibious ");
                // Safe casting after type checking
                AmphibiousVehicle amphibiousVehicle = (AmphibiousVehicle) vehicle;
                System.out.print("(can switch between land and water modes)");
            }
            
            System.out.println();
        }
    }
}
