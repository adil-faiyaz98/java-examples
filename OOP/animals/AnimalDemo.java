package OOP.animals;

import java.util.ArrayList;
import java.util.List;

/**
 * Demo class for Animal hierarchy
 * 
 * Demonstrates:
 * - Polymorphism: Using base class references for derived objects
 * - Dynamic method dispatch: Correct methods called based on actual object type
 */
public class AnimalDemo {
    
    public static void main(String[] args) {
        // Create different animals
        Dog dog = new Dog("Buddy", 3, "Golden Retriever", 30.5, true);
        Cat cat = new Cat("Whiskers", 5, "Tabby", 4.2, true);
        Bird parrot = new Bird("Polly", 2, "African Grey Parrot", 0.4, 
                              50.0, true, "Grey");
        Bird penguin = new Bird("Pingu", 4, "Emperor Penguin", 22.0, 
                               90.0, false, "Black and White");
        
        // Demonstrate polymorphism by storing different animals in a list of Animal
        List<Animal> animals = new ArrayList<>();
        animals.add(dog);
        animals.add(cat);
        animals.add(parrot);
        animals.add(penguin);
        
        // Process all animals polymorphically
        System.out.println("===== Animal Information =====");
        for (Animal animal : animals) {
            // Dynamic method dispatch - correct methods called based on actual object type
            animal.displayInfo();
            System.out.println("Sound: " + animal.makeSound());
            System.out.println("-------------------------");
        }
        
        // Demonstrate accessing specific methods of derived classes
        System.out.println("\n===== Specific Animal Behaviors =====");
        
        System.out.println("\nDog behaviors:");
        dog.bark();
        dog.fetch("stick");
        dog.performTrick("roll over");
        
        System.out.println("\nCat behaviors:");
        cat.purr();
        cat.scratch("furniture");
        cat.hunt();
        
        System.out.println("\nBird behaviors:");
        parrot.fly();
        penguin.fly(); // Will show that penguin cannot fly
        parrot.buildNest();
        
        // Demonstrate the template method pattern
        System.out.println("\n===== Daily Activities =====");
        System.out.println("\nDog's day:");
        dog.performDailyActivities();
        
        System.out.println("\nCat's day:");
        cat.performDailyActivities();
        
        System.out.println("\nParrot's day:");
        parrot.performDailyActivities();
        
        // Demonstrate type checking and casting
        System.out.println("\n===== Type Checking and Casting =====");
        for (Animal animal : animals) {
            System.out.print(animal.getName() + " is a " + animal.getClass().getSimpleName());
            
            if (animal instanceof Dog) {
                Dog dogAnimal = (Dog) animal;
                System.out.println(" of breed " + dogAnimal.getBreed());
            } else if (animal instanceof Cat) {
                Cat catAnimal = (Cat) animal;
                System.out.println(" with " + catAnimal.getCoatColor() + " coat");
            } else if (animal instanceof Bird) {
                Bird birdAnimal = (Bird) animal;
                System.out.println(" that " + (birdAnimal.canFly() ? "can fly" : "cannot fly"));
            } else {
                System.out.println();
            }
        }
    }
}
