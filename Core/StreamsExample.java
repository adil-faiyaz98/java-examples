package Core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * StreamsExample
 * 
 * Demonstrates the use of Java 8+ Streams API for processing collections of objects.
 * Streams enable functional-style operations on collections and support operations like
 * filtering, mapping, reducing, and more.
 */
public class StreamsExample {

    public static void main(String[] args) {
        System.out.println("===== Java Streams Examples =====");
        
        // Example 1: Creating Streams
        creatingStreamsExample();
        
        // Example 2: Basic Stream Operations
        basicStreamOperationsExample();
        
        // Example 3: Intermediate Operations
        intermediateOperationsExample();
        
        // Example 4: Terminal Operations
        terminalOperationsExample();
        
        // Example 5: Collectors
        collectorsExample();
        
        // Example 6: Parallel Streams
        parallelStreamsExample();
        
        // Example 7: Practical Examples
        practicalExamplesExample();
        
        System.out.println("\n===== End of Java Streams Examples =====");
    }
    
    /**
     * Example 1: Creating Streams
     * 
     * Demonstrates different ways to create streams.
     */
    private static void creatingStreamsExample() {
        System.out.println("\n1. Creating Streams:");
        
        // 1.1 From a Collection
        List<String> list = Arrays.asList("apple", "banana", "cherry", "date");
        Stream<String> streamFromCollection = list.stream();
        System.out.println("Stream from collection: " + 
                streamFromCollection.collect(Collectors.joining(", ")));
        
        // 1.2 From an Array
        String[] array = {"apple", "banana", "cherry", "date"};
        Stream<String> streamFromArray = Arrays.stream(array);
        System.out.println("Stream from array: " + 
                streamFromArray.collect(Collectors.joining(", ")));
        
        // 1.3 Using Stream.of
        Stream<String> streamOf = Stream.of("apple", "banana", "cherry", "date");
        System.out.println("Stream using Stream.of(): " + 
                streamOf.collect(Collectors.joining(", ")));
        
        // 1.4 Using Stream.generate (infinite stream)
        Stream<Double> randomStream = Stream.generate(Math::random).limit(5);
        System.out.println("Stream using Stream.generate(): " + 
                randomStream.map(d -> String.format("%.4f", d)).collect(Collectors.joining(", ")));
        
        // 1.5 Using Stream.iterate
        Stream<Integer> iterateStream = Stream.iterate(1, n -> n + 1).limit(5);
        System.out.println("Stream using Stream.iterate(): " + 
                iterateStream.map(String::valueOf).collect(Collectors.joining(", ")));
        
        // 1.6 Empty Stream
        Stream<String> emptyStream = Stream.empty();
        System.out.println("Empty stream: " + 
                emptyStream.collect(Collectors.joining(", ", "[", "]")));
        
        // 1.7 From IntStream, LongStream, DoubleStream (primitive streams)
        IntStream intStream = IntStream.range(1, 6);
        System.out.println("IntStream: " + 
                intStream.boxed().map(String::valueOf).collect(Collectors.joining(", ")));
        
        // 1.8 From a file (lines)
        try {
            // Create a temporary file for demonstration
            Files.write(Paths.get("temp.txt"), 
                    Arrays.asList("Line 1", "Line 2", "Line 3"));
            
            Stream<String> fileStream = Files.lines(Paths.get("temp.txt"));
            System.out.println("Stream from file: " + 
                    fileStream.collect(Collectors.joining(", ")));
            
            // Clean up
            Files.delete(Paths.get("temp.txt"));
            
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
    
    /**
     * Example 2: Basic Stream Operations
     * 
     * Demonstrates basic stream operations like filtering, mapping, and sorting.
     */
    private static void basicStreamOperationsExample() {
        System.out.println("\n2. Basic Stream Operations:");
        
        List<String> fruits = Arrays.asList("apple", "banana", "cherry", "date", "elderberry", "fig", "grape");
        
        // 2.1 Filtering
        System.out.println("\n2.1 Filtering:");
        List<String> longFruits = fruits.stream()
                .filter(fruit -> fruit.length() > 5)
                .collect(Collectors.toList());
        System.out.println("Fruits with more than 5 characters: " + longFruits);
        
        // 2.2 Mapping
        System.out.println("\n2.2 Mapping:");
        List<Integer> fruitLengths = fruits.stream()
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println("Lengths of fruit names: " + fruitLengths);
        
        // 2.3 Sorting
        System.out.println("\n2.3 Sorting:");
        List<String> sortedFruits = fruits.stream()
                .sorted()
                .collect(Collectors.toList());
        System.out.println("Fruits sorted alphabetically: " + sortedFruits);
        
        List<String> sortedByLength = fruits.stream()
                .sorted(Comparator.comparing(String::length))
                .collect(Collectors.toList());
        System.out.println("Fruits sorted by length: " + sortedByLength);
        
        // 2.4 Limiting and Skipping
        System.out.println("\n2.4 Limiting and Skipping:");
        List<String> limitedFruits = fruits.stream()
                .limit(3)
                .collect(Collectors.toList());
        System.out.println("First 3 fruits: " + limitedFruits);
        
        List<String> skippedFruits = fruits.stream()
                .skip(3)
                .collect(Collectors.toList());
        System.out.println("Fruits after skipping first 3: " + skippedFruits);
        
        // 2.5 Distinct
        System.out.println("\n2.5 Distinct:");
        List<String> duplicateFruits = Arrays.asList("apple", "banana", "apple", "cherry", "banana");
        List<String> distinctFruits = duplicateFruits.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Original list with duplicates: " + duplicateFruits);
        System.out.println("Distinct fruits: " + distinctFruits);
    }
    
    /**
     * Example 3: Intermediate Operations
     * 
     * Demonstrates more advanced intermediate operations like flatMap and peek.
     */
    private static void intermediateOperationsExample() {
        System.out.println("\n3. Intermediate Operations:");
        
        // 3.1 FlatMap
        System.out.println("\n3.1 FlatMap:");
        List<List<String>> nestedList = Arrays.asList(
                Arrays.asList("apple", "banana"),
                Arrays.asList("cherry", "date"),
                Arrays.asList("elderberry")
        );
        
        List<String> flattenedList = nestedList.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
        
        System.out.println("Nested list: " + nestedList);
        System.out.println("Flattened list: " + flattenedList);
        
        // Another flatMap example with splitting words
        List<String> sentences = Arrays.asList(
                "Hello world",
                "Java streams are powerful",
                "FlatMap is useful"
        );
        
        List<String> words = sentences.stream()
                .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
                .collect(Collectors.toList());
        
        System.out.println("Sentences: " + sentences);
        System.out.println("Words: " + words);
        
        // 3.2 Peek
        System.out.println("\n3.2 Peek:");
        List<String> fruits = Arrays.asList("apple", "banana", "cherry");
        
        List<String> uppercaseFruits = fruits.stream()
                .peek(fruit -> System.out.println("Before map: " + fruit))
                .map(String::toUpperCase)
                .peek(fruit -> System.out.println("After map: " + fruit))
                .collect(Collectors.toList());
        
        System.out.println("Uppercase fruits: " + uppercaseFruits);
        
        // 3.3 Chaining multiple operations
        System.out.println("\n3.3 Chaining multiple operations:");
        List<String> result = fruits.stream()
                .filter(fruit -> fruit.length() > 5)
                .map(String::toUpperCase)
                .sorted()
                .collect(Collectors.toList());
        
        System.out.println("Result of chained operations: " + result);
    }
    
    /**
     * Example 4: Terminal Operations
     * 
     * Demonstrates terminal operations like forEach, reduce, and find operations.
     */
    private static void terminalOperationsExample() {
        System.out.println("\n4. Terminal Operations:");
        
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // 4.1 forEach
        System.out.println("\n4.1 forEach:");
        System.out.print("Printing numbers: ");
        numbers.stream().forEach(n -> System.out.print(n + " "));
        System.out.println();
        
        // 4.2 reduce
        System.out.println("\n4.2 reduce:");
        Optional<Integer> sum = numbers.stream().reduce(Integer::sum);
        System.out.println("Sum of numbers: " + sum.orElse(0));
        
        Integer product = numbers.stream().reduce(1, (a, b) -> a * b);
        System.out.println("Product of numbers: " + product);
        
        // More complex reduce example
        String concatenated = numbers.stream()
                .map(String::valueOf)
                .reduce("", (a, b) -> a.isEmpty() ? b : a + ", " + b);
        System.out.println("Concatenated numbers: " + concatenated);
        
        // 4.3 find operations
        System.out.println("\n4.3 find operations:");
        Optional<Integer> firstEven = numbers.stream()
                .filter(n -> n % 2 == 0)
                .findFirst();
        System.out.println("First even number: " + firstEven.orElse(0));
        
        Optional<Integer> anyOdd = numbers.stream()
                .filter(n -> n % 2 != 0)
                .findAny();
        System.out.println("Any odd number: " + anyOdd.orElse(0));
        
        // 4.4 match operations
        System.out.println("\n4.4 match operations:");
        boolean allPositive = numbers.stream().allMatch(n -> n > 0);
        System.out.println("All numbers are positive: " + allPositive);
        
        boolean anyEven = numbers.stream().anyMatch(n -> n % 2 == 0);
        System.out.println("Any number is even: " + anyEven);
        
        boolean noneNegative = numbers.stream().noneMatch(n -> n < 0);
        System.out.println("None of the numbers are negative: " + noneNegative);
        
        // 4.5 count, min, max
        System.out.println("\n4.5 count, min, max:");
        long count = numbers.stream().count();
        System.out.println("Count of numbers: " + count);
        
        Optional<Integer> min = numbers.stream().min(Integer::compareTo);
        System.out.println("Minimum number: " + min.orElse(0));
        
        Optional<Integer> max = numbers.stream().max(Integer::compareTo);
        System.out.println("Maximum number: " + max.orElse(0));
    }
    
    /**
     * Example 5: Collectors
     * 
     * Demonstrates various collectors for collecting stream results.
     */
    private static void collectorsExample() {
        System.out.println("\n5. Collectors:");
        
        List<Person> people = Arrays.asList(
                new Person("Alice", 25, "Engineering"),
                new Person("Bob", 30, "Marketing"),
                new Person("Charlie", 35, "Engineering"),
                new Person("David", 40, "HR"),
                new Person("Eve", 45, "Marketing"),
                new Person("Frank", 50, "Engineering")
        );
        
        // 5.1 toList, toSet, toMap
        System.out.println("\n5.1 toList, toSet, toMap:");
        List<String> namesList = people.stream()
                .map(Person::getName)
                .collect(Collectors.toList());
        System.out.println("Names as List: " + namesList);
        
        Set<String> departmentsSet = people.stream()
                .map(Person::getDepartment)
                .collect(Collectors.toSet());
        System.out.println("Departments as Set: " + departmentsSet);
        
        Map<String, Integer> nameToAgeMap = people.stream()
                .collect(Collectors.toMap(Person::getName, Person::getAge));
        System.out.println("Name to Age Map: " + nameToAgeMap);
        
        // 5.2 joining
        System.out.println("\n5.2 joining:");
        String namesJoined = people.stream()
                .map(Person::getName)
                .collect(Collectors.joining(", "));
        System.out.println("Names joined: " + namesJoined);
        
        String namesJoinedWithPrefixSuffix = people.stream()
                .map(Person::getName)
                .collect(Collectors.joining(", ", "Names: [", "]"));
        System.out.println("Names joined with prefix and suffix: " + namesJoinedWithPrefixSuffix);
        
        // 5.3 groupingBy
        System.out.println("\n5.3 groupingBy:");
        Map<String, List<Person>> peopleByDepartment = people.stream()
                .collect(Collectors.groupingBy(Person::getDepartment));
        
        System.out.println("People grouped by department:");
        peopleByDepartment.forEach((dept, peopleList) -> {
            System.out.println(dept + ": " + 
                    peopleList.stream().map(Person::getName).collect(Collectors.joining(", ")));
        });
        
        // Grouping and counting
        Map<String, Long> countByDepartment = people.stream()
                .collect(Collectors.groupingBy(Person::getDepartment, Collectors.counting()));
        System.out.println("Count by department: " + countByDepartment);
        
        // 5.4 partitioningBy
        System.out.println("\n5.4 partitioningBy:");
        Map<Boolean, List<Person>> partitionedByAge = people.stream()
                .collect(Collectors.partitioningBy(person -> person.getAge() > 35));
        
        System.out.println("People older than 35: " + 
                partitionedByAge.get(true).stream().map(Person::getName).collect(Collectors.joining(", ")));
        System.out.println("People 35 or younger: " + 
                partitionedByAge.get(false).stream().map(Person::getName).collect(Collectors.joining(", ")));
        
        // 5.5 summarizingDouble
        System.out.println("\n5.5 summarizingDouble:");
        DoubleSummaryStatistics ageStats = people.stream()
                .collect(Collectors.summarizingDouble(Person::getAge));
        
        System.out.println("Age statistics:");
        System.out.println("Count: " + ageStats.getCount());
        System.out.println("Sum: " + ageStats.getSum());
        System.out.println("Min: " + ageStats.getMin());
        System.out.println("Max: " + ageStats.getMax());
        System.out.println("Average: " + ageStats.getAverage());
        
        // 5.6 mapping
        System.out.println("\n5.6 mapping:");
        Map<String, Set<Integer>> agesByDepartment = people.stream()
                .collect(Collectors.groupingBy(
                        Person::getDepartment,
                        Collectors.mapping(Person::getAge, Collectors.toSet())
                ));
        
        System.out.println("Ages by department: " + agesByDepartment);
    }
    
    /**
     * Example 6: Parallel Streams
     * 
     * Demonstrates the use of parallel streams for concurrent processing.
     */
    private static void parallelStreamsExample() {
        System.out.println("\n6. Parallel Streams:");
        
        // Create a large list of numbers
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 10_000_000; i++) {
            numbers.add(i);
        }
        
        // 6.1 Sequential vs Parallel Performance
        System.out.println("\n6.1 Sequential vs Parallel Performance:");
        
        // Sequential sum
        long startTimeSeq = System.currentTimeMillis();
        long sequentialSum = numbers.stream()
                .mapToLong(Integer::longValue)
                .sum();
        long endTimeSeq = System.currentTimeMillis();
        
        System.out.println("Sequential sum: " + sequentialSum);
        System.out.println("Sequential time: " + (endTimeSeq - startTimeSeq) + " ms");
        
        // Parallel sum
        long startTimePar = System.currentTimeMillis();
        long parallelSum = numbers.parallelStream()
                .mapToLong(Integer::longValue)
                .sum();
        long endTimePar = System.currentTimeMillis();
        
        System.out.println("Parallel sum: " + parallelSum);
        System.out.println("Parallel time: " + (endTimePar - startTimePar) + " ms");
        
        // 6.2 Converting between sequential and parallel
        System.out.println("\n6.2 Converting between sequential and parallel:");
        
        // Start with sequential, then convert to parallel
        long count1 = numbers.stream()
                .filter(n -> n % 2 == 0)
                .parallel()
                .count();
        
        // Start with parallel, then convert to sequential
        long count2 = numbers.parallelStream()
                .filter(n -> n % 2 == 0)
                .sequential()
                .count();
        
        System.out.println("Count of even numbers (parallel): " + count1);
        System.out.println("Count of even numbers (sequential): " + count2);
        
        // 6.3 Caution with parallel streams
        System.out.println("\n6.3 Caution with parallel streams:");
        
        // Non-associative operation (string concatenation) - may produce unexpected results
        String result1 = numbers.stream()
                .limit(10)
                .map(String::valueOf)
                .reduce("", (a, b) -> a + b);
        
        String result2 = numbers.parallelStream()
                .limit(10)
                .map(String::valueOf)
                .reduce("", (a, b) -> a + b);
        
        System.out.println("Sequential concatenation: " + result1);
        System.out.println("Parallel concatenation: " + result2 + " (may be in different order)");
        
        // Using a proper combiner for parallel reduction
        String result3 = numbers.parallelStream()
                .limit(10)
                .map(String::valueOf)
                .reduce("", 
                        (a, b) -> a + b,
                        (a, b) -> a + b);
        
        System.out.println("Parallel concatenation with combiner: " + result3 + " (still may be in different order)");
    }
    
    /**
     * Example 7: Practical Examples
     * 
     * Demonstrates practical use cases for streams.
     */
    private static void practicalExamplesExample() {
        System.out.println("\n7. Practical Examples:");
        
        // Sample data
        List<Product> products = Arrays.asList(
                new Product("Laptop", 1200.0, "Electronics"),
                new Product("Phone", 800.0, "Electronics"),
                new Product("Desk", 350.0, "Furniture"),
                new Product("Chair", 150.0, "Furniture"),
                new Product("Tablet", 500.0, "Electronics"),
                new Product("Lamp", 75.0, "Furniture"),
                new Product("Keyboard", 100.0, "Electronics"),
                new Product("Mouse", 50.0, "Electronics"),
                new Product("Bookshelf", 300.0, "Furniture"),
                new Product("Monitor", 400.0, "Electronics")
        );
        
        // 7.1 Finding expensive electronics
        System.out.println("\n7.1 Finding expensive electronics:");
        List<Product> expensiveElectronics = products.stream()
                .filter(p -> "Electronics".equals(p.getCategory()))
                .filter(p -> p.getPrice() > 500)
                .collect(Collectors.toList());
        
        System.out.println("Expensive electronics:");
        expensiveElectronics.forEach(p -> 
                System.out.println("- " + p.getName() + ": $" + p.getPrice()));
        
        // 7.2 Calculate total value by category
        System.out.println("\n7.2 Calculate total value by category:");
        Map<String, Double> totalValueByCategory = products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.summingDouble(Product::getPrice)
                ));
        
        System.out.println("Total value by category:");
        totalValueByCategory.forEach((category, total) -> 
                System.out.println("- " + category + ": $" + total));
        
        // 7.3 Find most expensive product in each category
        System.out.println("\n7.3 Find most expensive product in each category:");
        Map<String, Optional<Product>> mostExpensiveByCategory = products.stream()
                .collect(Collectors.groupingBy(
                        Product::getCategory,
                        Collectors.maxBy(Comparator.comparing(Product::getPrice))
                ));
        
        System.out.println("Most expensive product in each category:");
        mostExpensiveByCategory.forEach((category, productOpt) -> {
            productOpt.ifPresent(product -> 
                    System.out.println("- " + category + ": " + product.getName() + 
                            " ($" + product.getPrice() + ")"));
        });
        
        // 7.4 Frequency analysis of characters in a string
        System.out.println("\n7.4 Frequency analysis of characters in a string:");
        String text = "Java streams are powerful and expressive";
        
        Map<Character, Long> charFrequency = text.chars()
                .mapToObj(c -> (char) c)
                .filter(Character::isLetter)
                .map(Character::toLowerCase)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ));
        
        System.out.println("Character frequency in text: \"" + text + "\"");
        charFrequency.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.println("- '" + entry.getKey() + "': " + entry.getValue()));
        
        // 7.5 Partitioning products by price range
        System.out.println("\n7.5 Partitioning products by price range:");
        Map<Boolean, List<Product>> productsByPriceRange = products.stream()
                .collect(Collectors.partitioningBy(p -> p.getPrice() <= 300));
        
        System.out.println("Budget products (≤ $300):");
        productsByPriceRange.get(true).forEach(p -> 
                System.out.println("- " + p.getName() + ": $" + p.getPrice()));
        
        System.out.println("\nPremium products (> $300):");
        productsByPriceRange.get(false).forEach(p -> 
                System.out.println("- " + p.getName() + ": $" + p.getPrice()));
    }
    
    /**
     * Person class for demonstration
     */
    static class Person {
        private String name;
        private int age;
        private String department;
        
        public Person(String name, int age, String department) {
            this.name = name;
            this.age = age;
            this.department = department;
        }
        
        public String getName() {
            return name;
        }
        
        public int getAge() {
            return age;
        }
        
        public String getDepartment() {
            return department;
        }
        
        @Override
        public String toString() {
            return "Person{name='" + name + "', age=" + age + ", department='" + department + "'}";
        }
    }
    
    /**
     * Product class for demonstration
     */
    static class Product {
        private String name;
        private double price;
        private String category;
        
        public Product(String name, double price, String category) {
            this.name = name;
            this.price = price;
            this.category = category;
        }
        
        public String getName() {
            return name;
        }
        
        public double getPrice() {
            return price;
        }
        
        public String getCategory() {
            return category;
        }
        
        @Override
        public String toString() {
            return "Product{name='" + name + "', price=" + price + ", category='" + category + "'}";
        }
    }
}
