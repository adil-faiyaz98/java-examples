/**
 * Abstract Factory Design Pattern
 * 
 * Intent: Provide an interface for creating families of related or dependent objects
 * without specifying their concrete classes.
 * 
 * This example demonstrates creating UI components (buttons, checkboxes) for different
 * operating systems (Windows, MacOS) using the Abstract Factory pattern.
 */
package design.patterns.abstractfactory;

public class AbstractFactoryPattern {
    
    public static void main(String[] args) {
        // Create a Windows UI
        System.out.println("Creating Windows UI components:");
        createUI(new WindowsUIFactory());
        
        System.out.println("\n------------------------\n");
        
        // Create a MacOS UI
        System.out.println("Creating MacOS UI components:");
        createUI(new MacOSUIFactory());
    }
    
    /**
     * Client code that works with factories and products through abstract interfaces
     */
    private static void createUI(UIFactory factory) {
        Button button = factory.createButton();
        Checkbox checkbox = factory.createCheckbox();
        
        button.render();
        button.click();
        
        checkbox.render();
        checkbox.toggle();
    }
}

/**
 * Abstract Factory interface declares methods for creating different abstract products
 */
interface UIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

/**
 * Concrete Factory for Windows UI components
 */
class WindowsUIFactory implements UIFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }
    
    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}

/**
 * Concrete Factory for MacOS UI components
 */
class MacOSUIFactory implements UIFactory {
    @Override
    public Button createButton() {
        return new MacOSButton();
    }
    
    @Override
    public Checkbox createCheckbox() {
        return new MacOSCheckbox();
    }
}

/**
 * Abstract Product: Button interface
 */
interface Button {
    void render();
    void click();
}

/**
 * Concrete Product: Windows Button
 */
class WindowsButton implements Button {
    @Override
    public void render() {
        System.out.println("Rendering a Windows-style button");
    }
    
    @Override
    public void click() {
        System.out.println("Windows button click behavior");
    }
}

/**
 * Concrete Product: MacOS Button
 */
class MacOSButton implements Button {
    @Override
    public void render() {
        System.out.println("Rendering a MacOS-style button");
    }
    
    @Override
    public void click() {
        System.out.println("MacOS button click behavior");
    }
}

/**
 * Abstract Product: Checkbox interface
 */
interface Checkbox {
    void render();
    void toggle();
}

/**
 * Concrete Product: Windows Checkbox
 */
class WindowsCheckbox implements Checkbox {
    @Override
    public void render() {
        System.out.println("Rendering a Windows-style checkbox");
    }
    
    @Override
    public void toggle() {
        System.out.println("Windows checkbox toggle behavior");
    }
}

/**
 * Concrete Product: MacOS Checkbox
 */
class MacOSCheckbox implements Checkbox {
    @Override
    public void render() {
        System.out.println("Rendering a MacOS-style checkbox");
    }
    
    @Override
    public void toggle() {
        System.out.println("MacOS checkbox toggle behavior");
    }
}
