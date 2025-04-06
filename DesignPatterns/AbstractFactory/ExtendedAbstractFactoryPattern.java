/**
 * Extended Abstract Factory Design Pattern Example
 * 
 * This example extends the basic Abstract Factory pattern with:
 * 1. A third product type (TextBox)
 * 2. A third platform (Linux)
 * 3. A factory provider to get the appropriate factory
 * 
 * This demonstrates how the pattern scales with additional products and families.
 */
package design.patterns.abstractfactory;

public class ExtendedAbstractFactoryPattern {
    
    public static void main(String[] args) {
        // Get factory for the current operating system
        UIFactory factory = FactoryProvider.getFactory(detectOperatingSystem());
        
        // Create UI components using the factory
        createCompleteUI(factory);
    }
    
    /**
     * Simulates detecting the current operating system
     */
    private static OperatingSystem detectOperatingSystem() {
        // For demonstration, we'll just return Windows
        // In a real application, this would detect the actual OS
        return OperatingSystem.WINDOWS;
    }
    
    /**
     * Client code that creates a complete UI using the abstract factory
     */
    private static void createCompleteUI(UIFactory factory) {
        System.out.println("Creating UI for: " + factory.getClass().getSimpleName());
        System.out.println("------------------------");
        
        // Create all UI components
        Button button = factory.createButton();
        Checkbox checkbox = factory.createCheckbox();
        TextBox textBox = factory.createTextBox();
        
        // Use the components
        button.render();
        button.click();
        
        checkbox.render();
        checkbox.toggle();
        
        textBox.render();
        textBox.type("Hello, Abstract Factory Pattern!");
    }
}

/**
 * Enum representing different operating systems
 */
enum OperatingSystem {
    WINDOWS, MACOS, LINUX
}

/**
 * Factory Provider - provides the appropriate factory based on the operating system
 */
class FactoryProvider {
    public static UIFactory getFactory(OperatingSystem os) {
        switch (os) {
            case WINDOWS:
                return new WindowsUIFactory();
            case MACOS:
                return new MacOSUIFactory();
            case LINUX:
                return new LinuxUIFactory();
            default:
                throw new IllegalArgumentException("Unsupported operating system");
        }
    }
}

/**
 * Extended Abstract Factory interface with an additional product
 */
interface UIFactory {
    Button createButton();
    Checkbox createCheckbox();
    TextBox createTextBox();
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
    
    @Override
    public TextBox createTextBox() {
        return new WindowsTextBox();
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
    
    @Override
    public TextBox createTextBox() {
        return new MacOSTextBox();
    }
}

/**
 * Concrete Factory for Linux UI components
 */
class LinuxUIFactory implements UIFactory {
    @Override
    public Button createButton() {
        return new LinuxButton();
    }
    
    @Override
    public Checkbox createCheckbox() {
        return new LinuxCheckbox();
    }
    
    @Override
    public TextBox createTextBox() {
        return new LinuxTextBox();
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
 * Abstract Product: Checkbox interface
 */
interface Checkbox {
    void render();
    void toggle();
}

/**
 * Abstract Product: TextBox interface
 */
interface TextBox {
    void render();
    void type(String text);
}

/**
 * Concrete Products for Windows
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

class WindowsTextBox implements TextBox {
    @Override
    public void render() {
        System.out.println("Rendering a Windows-style text box");
    }
    
    @Override
    public void type(String text) {
        System.out.println("Typing in Windows text box: " + text);
    }
}

/**
 * Concrete Products for MacOS
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

class MacOSTextBox implements TextBox {
    @Override
    public void render() {
        System.out.println("Rendering a MacOS-style text box");
    }
    
    @Override
    public void type(String text) {
        System.out.println("Typing in MacOS text box: " + text);
    }
}

/**
 * Concrete Products for Linux
 */
class LinuxButton implements Button {
    @Override
    public void render() {
        System.out.println("Rendering a Linux-style button");
    }
    
    @Override
    public void click() {
        System.out.println("Linux button click behavior");
    }
}

class LinuxCheckbox implements Checkbox {
    @Override
    public void render() {
        System.out.println("Rendering a Linux-style checkbox");
    }
    
    @Override
    public void toggle() {
        System.out.println("Linux checkbox toggle behavior");
    }
}

class LinuxTextBox implements TextBox {
    @Override
    public void render() {
        System.out.println("Rendering a Linux-style text box");
    }
    
    @Override
    public void type(String text) {
        System.out.println("Typing in Linux text box: " + text);
    }
}
