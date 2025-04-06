/**
 * Decorator Design Pattern
 * 
 * Intent: Attach additional responsibilities to an object dynamically.
 * Decorators provide a flexible alternative to subclassing for extending functionality.
 * 
 * This example demonstrates a coffee ordering system where different condiments
 * can be added to a base beverage, with each addition affecting the description and cost.
 */
package design.patterns.decorator;

public class DecoratorPattern {
    
    public static void main(String[] args) {
        // Order a simple Espresso
        Beverage espresso = new Espresso();
        System.out.println(espresso.getDescription() + " $" + espresso.cost());
        
        // Order a Dark Roast with Mocha and Whip
        Beverage darkRoast = new DarkRoast();
        darkRoast = new Mocha(darkRoast);
        darkRoast = new Whip(darkRoast);
        System.out.println(darkRoast.getDescription() + " $" + darkRoast.cost());
        
        // Order a House Blend with Soy, Mocha, and Whip
        Beverage houseBlend = new HouseBlend();
        houseBlend = new Soy(houseBlend);
        houseBlend = new Mocha(houseBlend);
        houseBlend = new Whip(houseBlend);
        System.out.println(houseBlend.getDescription() + " $" + houseBlend.cost());
        
        // Order a double Mocha with Whip
        Beverage doubleMocha = new Espresso();
        doubleMocha = new Mocha(doubleMocha);
        doubleMocha = new Mocha(doubleMocha);
        doubleMocha = new Whip(doubleMocha);
        System.out.println(doubleMocha.getDescription() + " $" + doubleMocha.cost());
    }
}

/**
 * Component: Abstract base class for all beverages
 */
abstract class Beverage {
    protected String description = "Unknown Beverage";
    
    public String getDescription() {
        return description;
    }
    
    public abstract double cost();
}

/**
 * Concrete Component: Espresso
 */
class Espresso extends Beverage {
    public Espresso() {
        description = "Espresso";
    }
    
    @Override
    public double cost() {
        return 1.99;
    }
}

/**
 * Concrete Component: Dark Roast
 */
class DarkRoast extends Beverage {
    public DarkRoast() {
        description = "Dark Roast Coffee";
    }
    
    @Override
    public double cost() {
        return 0.99;
    }
}

/**
 * Concrete Component: House Blend
 */
class HouseBlend extends Beverage {
    public HouseBlend() {
        description = "House Blend Coffee";
    }
    
    @Override
    public double cost() {
        return 0.89;
    }
}

/**
 * Decorator: Abstract base class for all condiment decorators
 */
abstract class CondimentDecorator extends Beverage {
    protected Beverage beverage;
    
    public abstract String getDescription();
}

/**
 * Concrete Decorator: Mocha
 */
class Mocha extends CondimentDecorator {
    public Mocha(Beverage beverage) {
        this.beverage = beverage;
    }
    
    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Mocha";
    }
    
    @Override
    public double cost() {
        return beverage.cost() + 0.20;
    }
}

/**
 * Concrete Decorator: Soy
 */
class Soy extends CondimentDecorator {
    public Soy(Beverage beverage) {
        this.beverage = beverage;
    }
    
    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Soy";
    }
    
    @Override
    public double cost() {
        return beverage.cost() + 0.15;
    }
}

/**
 * Concrete Decorator: Whip
 */
class Whip extends CondimentDecorator {
    public Whip(Beverage beverage) {
        this.beverage = beverage;
    }
    
    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Whip";
    }
    
    @Override
    public double cost() {
        return beverage.cost() + 0.10;
    }
}
