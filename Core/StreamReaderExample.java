package Core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * StreamReaderExample
 * 
 * Demonstrates modern Java stream-based approaches to reading, parsing, and processing data.
 * Shows how to use Java 8+ features for more concise and functional data processing.
 */
public class StreamReaderExample {

    public static void main(String[] args) {
        System.out.println("===== Stream-Based Reading and Processing Examples =====\n");
        
        // Example 1: Reading a file using Files.lines() stream
        readFileWithStream("sample_data.txt");
        
        // Example 2: Processing CSV with streams
        processCsvWithStreams("employees.csv");
        
        // Example 3: Advanced data processing with streams
        advancedDataProcessing("employees.csv");
        
        // Example 4: Reading and parsing structured data with streams
        parseStructuredDataWithStreams("config.txt");
    }
    
    /**
     * Example 1: Reading a file using Files.lines() stream
     */
    private static void readFileWithStream(String filename) {
        System.out.println("Example 1: Reading file with Stream");
        
        try {
            // Read all lines from the file as a Stream
            List<String> lines = Files.lines(Paths.get(filename))
                    .collect(Collectors.toList());
            
            System.out.println("Read " + lines.size() + " lines from file using Stream");
            
            // Process the first few lines
            int linesToShow = Math.min(3, lines.size());
            System.out.println("First " + linesToShow + " lines:");
            lines.stream()
                    .limit(linesToShow)
                    .forEach(line -> System.out.println("- " + line));
            
            // Count words in the file
            long wordCount = Files.lines(Paths.get(filename))
                    .flatMap(line -> Stream.of(line.split("\\s+")))
                    .count();
            
            System.out.println("Total word count: " + wordCount);
            
            // Find the longest line
            Optional<String> longestLine = Files.lines(Paths.get(filename))
                    .max(Comparator.comparingInt(String::length));
            
            longestLine.ifPresent(line -> 
                    System.out.println("Longest line (" + line.length() + " chars): " + line));
            
        } catch (IOException e) {
            System.out.println("Error reading file with Stream: " + e.getMessage());
        }
        
        System.out.println("\nAfter Stream file reading example\n");
    }
    
    /**
     * Example 2: Processing CSV with streams
     */
    private static void processCsvWithStreams(String filename) {
        System.out.println("Example 2: Processing CSV with streams");
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // Skip the header line and process the rest
            List<Employee> employees = reader.lines()
                    .skip(1) // Skip header
                    .map(line -> {
                        String[] parts = line.split(",");
                        return new Employee(
                                Integer.parseInt(parts[0].trim()),
                                parts[1].trim(),
                                parts[2].trim(),
                                Double.parseDouble(parts[3].trim())
                        );
                    })
                    .collect(Collectors.toList());
            
            System.out.println("Processed " + employees.size() + " employees from CSV using streams");
            
            // Display the first few employees
            employees.stream()
                    .limit(3)
                    .forEach(System.out::println);
            
            // Calculate total salary
            double totalSalary = employees.stream()
                    .mapToDouble(Employee::getSalary)
                    .sum();
            
            System.out.printf("Total salary: $%.2f%n", totalSalary);
            
            // Find highest paid employee
            Optional<Employee> highestPaid = employees.stream()
                    .max(Comparator.comparingDouble(Employee::getSalary));
            
            highestPaid.ifPresent(emp -> 
                    System.out.printf("Highest paid employee: %s with $%.2f%n", 
                            emp.getName(), emp.getSalary()));
            
        } catch (IOException e) {
            System.out.println("Error processing CSV file with streams: " + e.getMessage());
        }
        
        System.out.println("\nAfter CSV stream processing example\n");
    }
    
    /**
     * Example 3: Advanced data processing with streams
     */
    private static void advancedDataProcessing(String filename) {
        System.out.println("Example 3: Advanced data processing with streams");
        
        try (Stream<String> lines = Files.lines(Paths.get(filename)).skip(1)) {
            // Parse CSV data into Employee objects
            List<Employee> employees = lines
                    .map(line -> {
                        String[] parts = line.split(",");
                        return new Employee(
                                Integer.parseInt(parts[0].trim()),
                                parts[1].trim(),
                                parts[2].trim(),
                                Double.parseDouble(parts[3].trim())
                        );
                    })
                    .collect(Collectors.toList());
            
            // Group employees by department
            Map<String, List<Employee>> byDepartment = employees.stream()
                    .collect(Collectors.groupingBy(Employee::getDepartment));
            
            System.out.println("Employees grouped by department:");
            byDepartment.forEach((dept, emps) -> {
                System.out.println(dept + " (" + emps.size() + " employees)");
                emps.forEach(emp -> System.out.println("  - " + emp.getName()));
            });
            
            // Calculate statistics by department
            System.out.println("\nSalary statistics by department:");
            byDepartment.forEach((dept, emps) -> {
                DoubleSummaryStatistics stats = emps.stream()
                        .mapToDouble(Employee::getSalary)
                        .summaryStatistics();
                
                System.out.printf("%s Department:%n", dept);
                System.out.printf("  Count: %d%n", stats.getCount());
                System.out.printf("  Average: $%.2f%n", stats.getAverage());
                System.out.printf("  Min: $%.2f%n", stats.getMin());
                System.out.printf("  Max: $%.2f%n", stats.getMax());
                System.out.printf("  Sum: $%.2f%n", stats.getSum());
                System.out.println();
            });
            
            // Filter and transform data
            List<String> highPaidEngineers = employees.stream()
                    .filter(emp -> "Engineering".equals(emp.getDepartment()))
                    .filter(emp -> emp.getSalary() > 85000)
                    .map(emp -> emp.getName() + " ($" + emp.getSalary() + ")")
                    .collect(Collectors.toList());
            
            System.out.println("High-paid engineers (>$85,000):");
            highPaidEngineers.forEach(name -> System.out.println("- " + name));
            
        } catch (IOException e) {
            System.out.println("Error in advanced data processing: " + e.getMessage());
        }
        
        System.out.println("\nAfter advanced data processing example\n");
    }
    
    /**
     * Example 4: Reading and parsing structured data with streams
     */
    private static void parseStructuredDataWithStreams(String filename) {
        System.out.println("Example 4: Parsing structured data with streams");
        
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            
            // Extract section names
            List<String> sections = lines.stream()
                    .filter(line -> line.matches("\\[.*\\]"))
                    .map(line -> line.substring(1, line.length() - 1))
                    .collect(Collectors.toList());
            
            System.out.println("Found " + sections.size() + " configuration sections:");
            sections.forEach(section -> System.out.println("- " + section));
            
            // Extract all key-value pairs
            Map<String, String> allSettings = lines.stream()
                    .filter(line -> line.contains("="))
                    .map(line -> line.split("=", 2))
                    .collect(Collectors.toMap(
                            parts -> parts[0].trim(),
                            parts -> parts[1].trim()
                    ));
            
            System.out.println("\nFound " + allSettings.size() + " configuration settings");
            
            // Find all settings related to files
            List<String> fileSettings = allSettings.entrySet().stream()
                    .filter(entry -> entry.getKey().contains("file") || 
                                    entry.getValue().contains("."))
                    .map(entry -> entry.getKey() + " = " + entry.getValue())
                    .collect(Collectors.toList());
            
            System.out.println("\nSettings related to files:");
            fileSettings.forEach(setting -> System.out.println("- " + setting));
            
        } catch (IOException e) {
            System.out.println("Error parsing structured data with streams: " + e.getMessage());
        }
        
        System.out.println("\nAfter structured data parsing with streams\n");
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
