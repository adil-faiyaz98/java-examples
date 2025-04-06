/**
 * Builder Design Pattern
 * 
 * Intent: Separate the construction of a complex object from its representation,
 * allowing the same construction process to create different representations.
 * 
 * This example demonstrates the Builder pattern using a computer building scenario.
 * It shows how to construct complex objects step by step and produce different
 * variations using the same construction code.
 */
package design.patterns.builder;

public class BuilderPattern {
    
    public static void main(String[] args) {
        // Using the builder directly
        System.out.println("Building a custom computer:");
        Computer customComputer = new Computer.Builder()
                .setCPU("Intel Core i7")
                .setRAM("32GB")
                .setStorage("1TB SSD")
                .setGPU("NVIDIA RTX 3080")
                .setHasBluetooth(true)
                .build();
        
        System.out.println(customComputer);
        
        System.out.println("\n------------------------\n");
        
        // Using a director with predefined configurations
        ComputerDirector director = new ComputerDirector();
        
        System.out.println("Building a gaming computer:");
        Computer gamingComputer = director.buildGamingComputer();
        System.out.println(gamingComputer);
        
        System.out.println("\nBuilding an office computer:");
        Computer officeComputer = director.buildOfficeComputer();
        System.out.println(officeComputer);
    }
}

/**
 * Product class - the complex object we're building
 */
class Computer {
    // Required parameters
    private final String cpu;
    private final String ram;
    
    // Optional parameters with default values
    private final String storage;
    private final String gpu;
    private final boolean hasWifi;
    private final boolean hasBluetooth;
    private final boolean hasUSBCPorts;
    
    /**
     * Private constructor that takes a builder to construct the computer
     */
    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.gpu = builder.gpu;
        this.hasWifi = builder.hasWifi;
        this.hasBluetooth = builder.hasBluetooth;
        this.hasUSBCPorts = builder.hasUSBCPorts;
    }
    
    @Override
    public String toString() {
        return "Computer Specifications:\n" +
                "- CPU: " + cpu + "\n" +
                "- RAM: " + ram + "\n" +
                "- Storage: " + storage + "\n" +
                "- GPU: " + gpu + "\n" +
                "- WiFi: " + (hasWifi ? "Yes" : "No") + "\n" +
                "- Bluetooth: " + (hasBluetooth ? "Yes" : "No") + "\n" +
                "- USB-C Ports: " + (hasUSBCPorts ? "Yes" : "No");
    }
    
    /**
     * Builder static nested class
     */
    public static class Builder {
        // Required parameters
        private String cpu;
        private String ram;
        
        // Optional parameters with default values
        private String storage = "512GB SSD";
        private String gpu = "Integrated Graphics";
        private boolean hasWifi = false;
        private boolean hasBluetooth = false;
        private boolean hasUSBCPorts = false;
        
        /**
         * Constructor with required parameters
         */
        public Builder() {
            // Empty constructor - we'll use setter methods for required parameters too
        }
        
        /**
         * Setter methods for all parameters that return the builder itself
         * to enable method chaining
         */
        public Builder setCPU(String cpu) {
            this.cpu = cpu;
            return this;
        }
        
        public Builder setRAM(String ram) {
            this.ram = ram;
            return this;
        }
        
        public Builder setStorage(String storage) {
            this.storage = storage;
            return this;
        }
        
        public Builder setGPU(String gpu) {
            this.gpu = gpu;
            return this;
        }
        
        public Builder setHasWifi(boolean hasWifi) {
            this.hasWifi = hasWifi;
            return this;
        }
        
        public Builder setHasBluetooth(boolean hasBluetooth) {
            this.hasBluetooth = hasBluetooth;
            return this;
        }
        
        public Builder setHasUSBCPorts(boolean hasUSBCPorts) {
            this.hasUSBCPorts = hasUSBCPorts;
            return this;
        }
        
        /**
         * Build method to create the Computer instance
         */
        public Computer build() {
            // Validate object here if needed
            if (cpu == null || ram == null) {
                throw new IllegalStateException("CPU and RAM are required parameters");
            }
            
            return new Computer(this);
        }
    }
}

/**
 * Director class - optional but useful for creating pre-configured objects
 */
class ComputerDirector {
    
    /**
     * Builds a high-performance gaming computer
     */
    public Computer buildGamingComputer() {
        return new Computer.Builder()
                .setCPU("AMD Ryzen 9 5900X")
                .setRAM("64GB DDR4")
                .setStorage("2TB NVMe SSD")
                .setGPU("NVIDIA RTX 3090")
                .setHasWifi(true)
                .setHasBluetooth(true)
                .setHasUSBCPorts(true)
                .build();
    }
    
    /**
     * Builds a basic office computer
     */
    public Computer buildOfficeComputer() {
        return new Computer.Builder()
                .setCPU("Intel Core i5")
                .setRAM("16GB")
                .setStorage("512GB SSD")
                .setHasWifi(true)
                .setHasUSBCPorts(true)
                .build();
    }
}
