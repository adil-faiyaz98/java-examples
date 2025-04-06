package Core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * InputProcessingExample
 * 
 * Demonstrates different ways to read input, parse it, and map it to objects.
 * Compares Scanner and BufferedReader approaches.
 */
public class InputProcessingExample {

    public static void main(String[] args) {
        System.out.println("===== Input Processing Examples =====\n");
        
        // Example 1: Reading and parsing console input with Scanner
        readConsoleWithScanner();
        
        // Example 2: Reading and parsing console input with BufferedReader
        readConsoleWithBufferedReader();
        
        // Example 3: Reading and parsing a file with Scanner
        readFileWithScanner("sample_data.txt");
        
        // Example 4: Reading and parsing a file with BufferedReader
        readFileWithBufferedReader("sample_data.txt");
        
        // Example 5: Processing CSV data and mapping to objects
        processCsvData("employees.csv");
        
        // Example 6: Processing structured data and creating a data map
        processStructuredData("config.txt");
        
        // Example 7: Performance comparison
        performanceComparison("large_file.txt");
    }
    
    /**
     * Example 1: Reading and parsing console input with Scanner
     */
    private static void readConsoleWithScanner() {
        System.out.println("Example 1: Reading console input with Scanner");
        
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter your age: ");
            int age = scanner.nextInt();
            scanner.nextLine(); // Consume the newline left-over
            
            System.out.print("Enter your favorite programming languages (comma-separated): ");
            String languagesInput = scanner.nextLine();
            
            // Parse the comma-separated languages
            String[] languages = languagesInput.split(",");
            List<String> languageList = new ArrayList<>();
            for (String language : languages) {
                languageList.add(language.trim());
            }
            
            // Create a Person object
            Person person = new Person(name, age, languageList);
            
            System.out.println("\nPerson created from Scanner input:");
            System.out.println(person);
            
        } catch (Exception e) {
            System.out.println("Error reading input with Scanner: " + e.getMessage());
        }
        
        // Note: We don't close the scanner here because it would close System.in
        // which we might want to use again later
        
        System.out.println("\nAfter Scanner console input example\n");
    }
    
    /**
     * Example 2: Reading and parsing console input with BufferedReader
     */
    private static void readConsoleWithBufferedReader() {
        System.out.println("Example 2: Reading console input with BufferedReader");
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Enter your name: ");
            String name = reader.readLine();
            
            System.out.print("Enter your age: ");
            int age = Integer.parseInt(reader.readLine());
            
            System.out.print("Enter your favorite programming languages (comma-separated): ");
            String languagesInput = reader.readLine();
            
            // Parse the comma-separated languages
            String[] languages = languagesInput.split(",");
            List<String> languageList = new ArrayList<>();
            for (String language : languages) {
                languageList.add(language.trim());
            }
            
            // Create a Person object
            Person person = new Person(name, age, languageList);
            
            System.out.println("\nPerson created from BufferedReader input:");
            System.out.println(person);
            
        } catch (IOException e) {
            System.out.println("Error reading input with BufferedReader: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number format: " + e.getMessage());
        }
        
        System.out.println("\nAfter BufferedReader console input example\n");
    }
    
    /**
     * Example 3: Reading and parsing a file with Scanner
     */
    private static void readFileWithScanner(String filename) {
        System.out.println("Example 3: Reading file with Scanner");
        
        try (Scanner scanner = new Scanner(new FileReader(filename))) {
            List<String> lines = new ArrayList<>();
            
            // Read all lines from the file
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }
            
            System.out.println("Read " + lines.size() + " lines from file using Scanner");
            
            // Process the first few lines
            int linesToShow = Math.min(3, lines.size());
            System.out.println("First " + linesToShow + " lines:");
            for (int i = 0; i < linesToShow; i++) {
                System.out.println((i + 1) + ": " + lines.get(i));
            }
            
        } catch (IOException e) {
            System.out.println("Error reading file with Scanner: " + e.getMessage());
        }
        
        System.out.println("\nAfter Scanner file input example\n");
    }
    
    /**
     * Example 4: Reading and parsing a file with BufferedReader
     */
    private static void readFileWithBufferedReader(String filename) {
        System.out.println("Example 4: Reading file with BufferedReader");
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            List<String> lines = new ArrayList<>();
            String line;
            
            // Read all lines from the file
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            
            System.out.println("Read " + lines.size() + " lines from file using BufferedReader");
            
            // Process the first few lines
            int linesToShow = Math.min(3, lines.size());
            System.out.println("First " + linesToShow + " lines:");
            for (int i = 0; i < linesToShow; i++) {
                System.out.println((i + 1) + ": " + lines.get(i));
            }
            
        } catch (IOException e) {
            System.out.println("Error reading file with BufferedReader: " + e.getMessage());
        }
        
        System.out.println("\nAfter BufferedReader file input example\n");
    }
    
    /**
     * Example 5: Processing CSV data and mapping to objects
     */
    private static void processCsvData(String filename) {
        System.out.println("Example 5: Processing CSV data and mapping to objects");
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            List<Employee> employees = new ArrayList<>();
            String line;
            boolean isHeader = true;
            
            // Read and process each line
            while ((line = reader.readLine()) != null) {
                // Skip the header line
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                
                // Parse the CSV line
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    int id = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String department = parts[2].trim();
                    double salary = Double.parseDouble(parts[3].trim());
                    
                    // Create an Employee object and add to the list
                    employees.add(new Employee(id, name, department, salary));
                }
            }
            
            System.out.println("Processed " + employees.size() + " employees from CSV");
            
            // Display the employees
            for (Employee employee : employees) {
                System.out.println(employee);
            }
            
            // Calculate average salary by department
            Map<String, Double> avgSalaryByDept = employees.stream()
                    .collect(Collectors.groupingBy(
                            Employee::getDepartment,
                            Collectors.averagingDouble(Employee::getSalary)
                    ));
            
            System.out.println("\nAverage salary by department:");
            avgSalaryByDept.forEach((dept, avgSalary) -> 
                    System.out.printf("%s: $%.2f%n", dept, avgSalary));
            
        } catch (IOException e) {
            System.out.println("Error processing CSV file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number in CSV: " + e.getMessage());
        }
        
        System.out.println("\nAfter CSV processing example\n");
    }
    
    /**
     * Example 6: Processing structured data and creating a data map
     */
    private static void processStructuredData(String filename) {
        System.out.println("Example 6: Processing structured data and creating a data map");
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            Map<String, Map<String, String>> configSections = new HashMap<>();
            String line;
            String currentSection = null;
            Map<String, String> currentSectionMap = null;
            
            // Read and process each line
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                // Skip empty lines and comments
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                
                // Check if this is a section header [section]
                if (line.startsWith("[") && line.endsWith("]")) {
                    currentSection = line.substring(1, line.length() - 1);
                    currentSectionMap = new HashMap<>();
                    configSections.put(currentSection, currentSectionMap);
                    continue;
                }
                
                // Process key-value pairs
                if (currentSection != null && line.contains("=")) {
                    int equalsPos = line.indexOf('=');
                    String key = line.substring(0, equalsPos).trim();
                    String value = line.substring(equalsPos + 1).trim();
                    
                    currentSectionMap.put(key, value);
                }
            }
            
            System.out.println("Processed configuration with " + configSections.size() + " sections");
            
            // Display the configuration
            for (Map.Entry<String, Map<String, String>> section : configSections.entrySet()) {
                System.out.println("[" + section.getKey() + "]");
                
                for (Map.Entry<String, String> entry : section.getValue().entrySet()) {
                    System.out.println("  " + entry.getKey() + " = " + entry.getValue());
                }
                
                System.out.println();
            }
            
            // Use the configuration data
            if (configSections.containsKey("database")) {
                Map<String, String> dbConfig = configSections.get("database");
                System.out.println("Database Configuration:");
                System.out.println("Host: " + dbConfig.getOrDefault("host", "localhost"));
                System.out.println("Port: " + dbConfig.getOrDefault("port", "3306"));
                System.out.println("Username: " + dbConfig.getOrDefault("username", "root"));
            }
            
        } catch (IOException e) {
            System.out.println("Error processing configuration file: " + e.getMessage());
        }
        
        System.out.println("\nAfter structured data processing example\n");
    }
    
    /**
     * Example 7: Performance comparison between Scanner and BufferedReader
     */
    private static void performanceComparison(String filename) {
        System.out.println("Example 7: Performance comparison");
        
        // Test BufferedReader performance
        long startTime = System.currentTimeMillis();
        int lineCountBR = 0;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            while (reader.readLine() != null) {
                lineCountBR++;
            }
        } catch (IOException e) {
            System.out.println("Error with BufferedReader: " + e.getMessage());
        }
        
        long brTime = System.currentTimeMillis() - startTime;
        
        // Test Scanner performance
        startTime = System.currentTimeMillis();
        int lineCountScanner = 0;
        
        try (Scanner scanner = new Scanner(new FileReader(filename))) {
            while (scanner.hasNextLine()) {
                scanner.nextLine();
                lineCountScanner++;
            }
        } catch (IOException e) {
            System.out.println("Error with Scanner: " + e.getMessage());
        }
        
        long scannerTime = System.currentTimeMillis() - startTime;
        
        // Report results
        System.out.println("BufferedReader processed " + lineCountBR + " lines in " + brTime + " ms");
        System.out.println("Scanner processed " + lineCountScanner + " lines in " + scannerTime + " ms");
        System.out.println("BufferedReader was " + (scannerTime / (double) brTime) + " times faster");
        
        System.out.println("\nAfter performance comparison\n");
    }
    
    /**
     * Person class for mapping input data
     */
    static class Person {
        private String name;
        private int age;
        private List<String> programmingLanguages;
        
        public Person(String name, int age, List<String> programmingLanguages) {
            this.name = name;
            this.age = age;
            this.programmingLanguages = programmingLanguages;
        }
        
        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", programmingLanguages=" + programmingLanguages +
                    '}';
        }
    }
    
    /**
     * Employee class for mapping CSV data
     */
    static class Employee {
        private int id;
        private String name;
        private String department;
        private double salary;
        
        public Employee(int id, String name, String department, double salary) {
            this.id = id;
            this.name = name;
            this.department = department;
            this.salary = salary;
        }
        
        public int getId() {
            return id;
        }
        
        public String getName() {
            return name;
        }
        
        public String getDepartment() {
            return department;
        }
        
        public double getSalary() {
            return salary;
        }
        
        @Override
        public String toString() {
            return "Employee{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", department='" + department + '\'' +
                    ", salary=" + salary +
                    '}';
        }
    }
}
