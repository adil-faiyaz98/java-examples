/**
 * Computer System Facade Example
 * 
 * This example demonstrates the Facade pattern with a computer system.
 * The ComputerFacade simplifies the process of starting up and shutting down
 * a computer by coordinating the CPU, Memory, HardDrive, and OS components.
 */
package design.patterns.facade;

public class ComputerSystemFacade {
    
    public static void main(String[] args) {
        ComputerFacade computer = new ComputerFacade();
        
        System.out.println("--- Starting Computer ---");
        computer.start();
        
        System.out.println("\n--- Running Applications ---");
        computer.runApplication("VS Code");
        computer.runApplication("Chrome");
        
        System.out.println("\n--- Shutting Down Computer ---");
        computer.shutdown();
    }
}

/**
 * Facade: ComputerFacade
 * Provides a simplified interface to the complex computer subsystem
 */
class ComputerFacade {
    private CPU cpu;
    private Memory memory;
    private HardDrive hardDrive;
    private OperatingSystem os;
    
    public ComputerFacade() {
        this.cpu = new CPU();
        this.memory = new Memory();
        this.hardDrive = new HardDrive();
        this.os = new OperatingSystem();
    }
    
    /**
     * Simplified method to start the computer
     */
    public void start() {
        cpu.freeze();
        memory.load(0, hardDrive.read(0, 1024));
        cpu.jump(0);
        cpu.execute();
        os.loadOS();
    }
    
    /**
     * Simplified method to run an application
     */
    public void runApplication(String appName) {
        os.executeApplication(appName);
    }
    
    /**
     * Simplified method to shut down the computer
     */
    public void shutdown() {
        os.closeAllApplications();
        os.shutdownOS();
        cpu.freeze();
        memory.dump();
        hardDrive.spinDown();
        System.out.println("Computer has been shut down safely.");
    }
}

/**
 * Subsystem: CPU
 */
class CPU {
    public void freeze() {
        System.out.println("CPU: Freezing processor");
    }
    
    public void jump(long position) {
        System.out.println("CPU: Jumping to position " + position);
    }
    
    public void execute() {
        System.out.println("CPU: Executing instructions");
    }
}

/**
 * Subsystem: Memory
 */
class Memory {
    public void load(long position, byte[] data) {
        System.out.println("Memory: Loading data at position " + position);
    }
    
    public void dump() {
        System.out.println("Memory: Dumping all data");
    }
}

/**
 * Subsystem: HardDrive
 */
class HardDrive {
    public byte[] read(long lba, int size) {
        System.out.println("HardDrive: Reading " + size + " bytes from sector " + lba);
        return new byte[size]; // Simulated data
    }
    
    public void write(long lba, byte[] data) {
        System.out.println("HardDrive: Writing " + data.length + " bytes to sector " + lba);
    }
    
    public void spinDown() {
        System.out.println("HardDrive: Spinning down disk");
    }
}

/**
 * Subsystem: OperatingSystem
 */
class OperatingSystem {
    public void loadOS() {
        System.out.println("OS: Loading operating system");
        System.out.println("OS: Initializing system services");
        System.out.println("OS: Starting user interface");
        System.out.println("OS: System ready");
    }
    
    public void executeApplication(String appName) {
        System.out.println("OS: Allocating memory for " + appName);
        System.out.println("OS: Loading " + appName + " into memory");
        System.out.println("OS: Starting " + appName);
        System.out.println("OS: " + appName + " is now running");
    }
    
    public void closeAllApplications() {
        System.out.println("OS: Closing all applications");
    }
    
    public void shutdownOS() {
        System.out.println("OS: Saving system state");
        System.out.println("OS: Stopping system services");
        System.out.println("OS: Preparing to power off");
    }
}
