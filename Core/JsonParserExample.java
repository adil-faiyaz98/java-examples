package Core;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JsonParserExample
 * 
 * Demonstrates how to build a simple JSON parser that reads JSON data
 * and maps it to Java objects. This example shows parsing without using
 * external libraries, for educational purposes.
 */
public class JsonParserExample {

    public static void main(String[] args) {
        System.out.println("===== JSON Parser Examples =====\n");
        
        // Example 1: Parse a simple JSON string
        parseSimpleJson();
        
        // Example 2: Parse a JSON file with an array of objects
        parseJsonFile("users.json");
        
        // Example 3: Build a custom JSON parser
        buildCustomJsonParser();
        
        // Example 4: Map JSON to custom objects
        mapJsonToObjects("products.json");
    }
    
    /**
     * Example 1: Parse a simple JSON string
     */
    private static void parseSimpleJson() {
        System.out.println("Example 1: Parse a simple JSON string");
        
        String jsonString = "{"
                + "\"name\": \"John Doe\","
                + "\"age\": 30,"
                + "\"email\": \"john.doe@example.com\","
                + "\"isActive\": true,"
                + "\"address\": {"
                + "  \"street\": \"123 Main St\","
                + "  \"city\": \"Anytown\","
                + "  \"zipCode\": \"12345\""
                + "},"
                + "\"phoneNumbers\": [\"555-1234\", \"555-5678\"]"
                + "}";
        
        try {
            // Parse the JSON string into a Map
            Map<String, Object> person = parseJson(jsonString);
            
            // Access and display the parsed data
            System.out.println("Parsed person data:");
            System.out.println("Name: " + person.get("name"));
            System.out.println("Age: " + person.get("age"));
            System.out.println("Email: " + person.get("email"));
            System.out.println("Active: " + person.get("isActive"));
            
            // Access nested objects
            @SuppressWarnings("unchecked")
            Map<String, Object> address = (Map<String, Object>) person.get("address");
            System.out.println("Address: " + address.get("street") + ", " 
                    + address.get("city") + ", " + address.get("zipCode"));
            
            // Access arrays
            @SuppressWarnings("unchecked")
            List<String> phoneNumbers = (List<String>) person.get("phoneNumbers");
            System.out.println("Phone numbers: " + String.join(", ", phoneNumbers));
            
        } catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
        }
        
        System.out.println("\nAfter simple JSON parsing example\n");
    }
    
    /**
     * Example 2: Parse a JSON file with an array of objects
     */
    private static void parseJsonFile(String filename) {
        System.out.println("Example 2: Parse a JSON file with an array of objects");
        
        try {
            // Read the JSON file content
            String jsonContent = new String(Files.readAllBytes(Paths.get(filename)));
            
            // Parse the JSON array
            List<Map<String, Object>> users = parseJsonArray(jsonContent);
            
            System.out.println("Parsed " + users.size() + " users from JSON file");
            
            // Process each user
            for (int i = 0; i < Math.min(3, users.size()); i++) {
                Map<String, Object> user = users.get(i);
                System.out.println("\nUser " + (i + 1) + ":");
                System.out.println("ID: " + user.get("id"));
                System.out.println("Name: " + user.get("name"));
                System.out.println("Email: " + user.get("email"));
                
                // Process roles array
                @SuppressWarnings("unchecked")
                List<String> roles = (List<String>) user.get("roles");
                if (roles != null) {
                    System.out.println("Roles: " + String.join(", ", roles));
                }
            }
            
        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error parsing JSON: " + e.getMessage());
        }
        
        System.out.println("\nAfter JSON file parsing example\n");
    }
    
    /**
     * Example 3: Build a custom JSON parser
     */
    private static void buildCustomJsonParser() {
        System.out.println("Example 3: Build a custom JSON parser");
        
        String jsonString = "{"
                + "\"id\": 12345,"
                + "\"name\": \"Product Name\","
                + "\"price\": 99.99,"
                + "\"tags\": [\"electronics\", \"gadget\", \"new\"],"
                + "\"dimensions\": {"
                + "  \"length\": 10.5,"
                + "  \"width\": 5.5,"
                + "  \"height\": 2.5"
                + "},"
                + "\"inStock\": true"
                + "}";
        
        // Create a simple JSON parser
        SimpleJsonParser parser = new SimpleJsonParser();
        
        try {
            // Parse the JSON string
            Map<String, Object> product = parser.parse(jsonString);
            
            // Display the parsed data
            System.out.println("Parsed product data using custom parser:");
            System.out.println("ID: " + product.get("id"));
            System.out.println("Name: " + product.get("name"));
            System.out.println("Price: $" + product.get("price"));
            System.out.println("In Stock: " + product.get("inStock"));
            
            // Display tags
            @SuppressWarnings("unchecked")
            List<String> tags = (List<String>) product.get("tags");
            System.out.println("Tags: " + String.join(", ", tags));
            
            // Display dimensions
            @SuppressWarnings("unchecked")
            Map<String, Object> dimensions = (Map<String, Object>) product.get("dimensions");
            System.out.println("Dimensions: " + dimensions.get("length") + " x " 
                    + dimensions.get("width") + " x " + dimensions.get("height"));
            
        } catch (Exception e) {
            System.out.println("Error in custom JSON parser: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\nAfter custom JSON parser example\n");
    }
    
    /**
     * Example 4: Map JSON to custom objects
     */
    private static void mapJsonToObjects(String filename) {
        System.out.println("Example 4: Map JSON to custom objects");
        
        try {
            // Read the JSON file content
            String jsonContent = new String(Files.readAllBytes(Paths.get(filename)));
            
            // Parse the JSON array
            List<Map<String, Object>> productMaps = parseJsonArray(jsonContent);
            
            // Map to Product objects
            List<Product> products = new ArrayList<>();
            for (Map<String, Object> map : productMaps) {
                Product product = new Product();
                
                // Map basic properties
                product.setId(((Number) map.get("id")).intValue());
                product.setName((String) map.get("name"));
                product.setPrice(((Number) map.get("price")).doubleValue());
                product.setInStock((Boolean) map.get("inStock"));
                
                // Map tags
                @SuppressWarnings("unchecked")
                List<String> tags = (List<String>) map.get("tags");
                if (tags != null) {
                    product.setTags(tags);
                }
                
                // Map dimensions
                @SuppressWarnings("unchecked")
                Map<String, Object> dimensionsMap = (Map<String, Object>) map.get("dimensions");
                if (dimensionsMap != null) {
                    Dimensions dimensions = new Dimensions();
                    dimensions.setLength(((Number) dimensionsMap.get("length")).doubleValue());
                    dimensions.setWidth(((Number) dimensionsMap.get("width")).doubleValue());
                    dimensions.setHeight(((Number) dimensionsMap.get("height")).doubleValue());
                    product.setDimensions(dimensions);
                }
                
                products.add(product);
            }
            
            System.out.println("Mapped " + products.size() + " products from JSON");
            
            // Display the products
            for (Product product : products) {
                System.out.println("\n" + product);
            }
            
            // Calculate total inventory value
            double totalValue = products.stream()
                    .filter(Product::isInStock)
                    .mapToDouble(p -> p.getPrice())
                    .sum();
            
            System.out.printf("\nTotal inventory value: $%.2f%n", totalValue);
            
        } catch (IOException e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error mapping JSON to objects: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("\nAfter JSON to objects mapping example\n");
    }
    
    /**
     * Parse a JSON string into a Map
     * Note: This is a simplified parser for demonstration purposes
     */
    private static Map<String, Object> parseJson(String json) {
        // Remove whitespace for simplicity
        json = json.trim();
        
        if (!json.startsWith("{") || !json.endsWith("}")) {
            throw new IllegalArgumentException("Invalid JSON object format");
        }
        
        // Remove the outer braces
        json = json.substring(1, json.length() - 1).trim();
        
        Map<String, Object> result = new HashMap<>();
        
        // Simple state machine to parse key-value pairs
        int pos = 0;
        while (pos < json.length()) {
            // Find the key (always a string in JSON)
            int keyStart = json.indexOf("\"", pos);
            if (keyStart == -1) break;
            
            int keyEnd = json.indexOf("\"", keyStart + 1);
            if (keyEnd == -1) throw new IllegalArgumentException("Unterminated key string");
            
            String key = json.substring(keyStart + 1, keyEnd);
            
            // Find the colon separator
            int colon = json.indexOf(":", keyEnd);
            if (colon == -1) throw new IllegalArgumentException("Missing colon after key");
            
            // Find the value
            int valueStart = colon + 1;
            while (valueStart < json.length() && Character.isWhitespace(json.charAt(valueStart))) {
                valueStart++;
            }
            
            if (valueStart >= json.length()) {
                throw new IllegalArgumentException("Missing value after key");
            }
            
            // Determine the type of value and parse accordingly
            char valueStartChar = json.charAt(valueStart);
            Object value;
            int valueEnd;
            
            if (valueStartChar == '{') {
                // Nested object
                int nestedObjectEnd = findMatchingBrace(json, valueStart);
                String nestedJson = json.substring(valueStart, nestedObjectEnd + 1);
                value = parseJson(nestedJson);
                valueEnd = nestedObjectEnd;
            } else if (valueStartChar == '[') {
                // Array
                int arrayEnd = findMatchingBracket(json, valueStart);
                String arrayJson = json.substring(valueStart, arrayEnd + 1);
                value = parseJsonArray(arrayJson);
                valueEnd = arrayEnd;
            } else if (valueStartChar == '\"') {
                // String
                int stringEnd = findStringEnd(json, valueStart);
                value = json.substring(valueStart + 1, stringEnd);
                valueEnd = stringEnd;
            } else if (Character.isDigit(valueStartChar) || valueStartChar == '-') {
                // Number
                valueEnd = findTokenEnd(json, valueStart);
                String numStr = json.substring(valueStart, valueEnd);
                if (numStr.contains(".")) {
                    value = Double.parseDouble(numStr);
                } else {
                    value = Integer.parseInt(numStr);
                }
            } else if (json.startsWith("true", valueStart)) {
                // Boolean true
                value = Boolean.TRUE;
                valueEnd = valueStart + 3;
            } else if (json.startsWith("false", valueStart)) {
                // Boolean false
                value = Boolean.FALSE;
                valueEnd = valueStart + 4;
            } else if (json.startsWith("null", valueStart)) {
                // Null
                value = null;
                valueEnd = valueStart + 3;
            } else {
                throw new IllegalArgumentException("Unknown value type at position " + valueStart);
            }
            
            // Add the key-value pair to the result
            result.put(key, value);
            
            // Move to the next key-value pair
            pos = valueEnd + 1;
            
            // Skip any whitespace and the comma
            while (pos < json.length() && (Character.isWhitespace(json.charAt(pos)) || json.charAt(pos) == ',')) {
                pos++;
            }
        }
        
        return result;
    }
    
    /**
     * Parse a JSON array string into a List
     */
    private static List<Object> parseJsonArray(String json) {
        // Remove whitespace for simplicity
        json = json.trim();
        
        if (!json.startsWith("[") || !json.endsWith("]")) {
            throw new IllegalArgumentException("Invalid JSON array format");
        }
        
        // Remove the outer brackets
        json = json.substring(1, json.length() - 1).trim();
        
        List<Object> result = new ArrayList<>();
        
        // Handle empty array
        if (json.isEmpty()) {
            return result;
        }
        
        // Simple state machine to parse array elements
        int pos = 0;
        while (pos < json.length()) {
            // Skip whitespace
            while (pos < json.length() && Character.isWhitespace(json.charAt(pos))) {
                pos++;
            }
            
            if (pos >= json.length()) break;
            
            // Determine the type of value and parse accordingly
            char valueStartChar = json.charAt(pos);
            Object value;
            int valueEnd;
            
            if (valueStartChar == '{') {
                // Object
                int objectEnd = findMatchingBrace(json, pos);
                String objectJson = json.substring(pos, objectEnd + 1);
                value = parseJson(objectJson);
                valueEnd = objectEnd;
            } else if (valueStartChar == '[') {
                // Nested array
                int arrayEnd = findMatchingBracket(json, pos);
                String arrayJson = json.substring(pos, arrayEnd + 1);
                value = parseJsonArray(arrayJson);
                valueEnd = arrayEnd;
            } else if (valueStartChar == '\"') {
                // String
                int stringEnd = findStringEnd(json, pos);
                value = json.substring(pos + 1, stringEnd);
                valueEnd = stringEnd;
            } else if (Character.isDigit(valueStartChar) || valueStartChar == '-') {
                // Number
                valueEnd = findTokenEnd(json, pos);
                String numStr = json.substring(pos, valueEnd);
                if (numStr.contains(".")) {
                    value = Double.parseDouble(numStr);
                } else {
                    value = Integer.parseInt(numStr);
                }
            } else if (json.startsWith("true", pos)) {
                // Boolean true
                value = Boolean.TRUE;
                valueEnd = pos + 3;
            } else if (json.startsWith("false", pos)) {
                // Boolean false
                value = Boolean.FALSE;
                valueEnd = pos + 4;
            } else if (json.startsWith("null", pos)) {
                // Null
                value = null;
                valueEnd = pos + 3;
            } else {
                throw new IllegalArgumentException("Unknown value type at position " + pos);
            }
            
            // Add the value to the result
            result.add(value);
            
            // Move to the next element
            pos = valueEnd + 1;
            
            // Skip any whitespace and the comma
            while (pos < json.length() && (Character.isWhitespace(json.charAt(pos)) || json.charAt(pos) == ',')) {
                pos++;
            }
        }
        
        return result;
    }
    
    /**
     * Find the position of the matching closing brace
     */
    private static int findMatchingBrace(String json, int openBracePos) {
        int braceCount = 1;
        boolean inString = false;
        
        for (int i = openBracePos + 1; i < json.length(); i++) {
            char c = json.charAt(i);
            
            if (c == '\"' && (i == 0 || json.charAt(i - 1) != '\\')) {
                inString = !inString;
            } else if (!inString) {
                if (c == '{') {
                    braceCount++;
                } else if (c == '}') {
                    braceCount--;
                    if (braceCount == 0) {
                        return i;
                    }
                }
            }
        }
        
        throw new IllegalArgumentException("No matching closing brace found");
    }
    
    /**
     * Find the position of the matching closing bracket
     */
    private static int findMatchingBracket(String json, int openBracketPos) {
        int bracketCount = 1;
        boolean inString = false;
        
        for (int i = openBracketPos + 1; i < json.length(); i++) {
            char c = json.charAt(i);
            
            if (c == '\"' && (i == 0 || json.charAt(i - 1) != '\\')) {
                inString = !inString;
            } else if (!inString) {
                if (c == '[') {
                    bracketCount++;
                } else if (c == ']') {
                    bracketCount--;
                    if (bracketCount == 0) {
                        return i;
                    }
                }
            }
        }
        
        throw new IllegalArgumentException("No matching closing bracket found");
    }
    
    /**
     * Find the end of a string value
     */
    private static int findStringEnd(String json, int startQuotePos) {
        for (int i = startQuotePos + 1; i < json.length(); i++) {
            char c = json.charAt(i);
            
            if (c == '\"' && json.charAt(i - 1) != '\\') {
                return i;
            }
        }
        
        throw new IllegalArgumentException("No closing quote found for string");
    }
    
    /**
     * Find the end of a token (number, boolean, null)
     */
    private static int findTokenEnd(String json, int startPos) {
        for (int i = startPos; i < json.length(); i++) {
            char c = json.charAt(i);
            
            if (c == ',' || c == '}' || c == ']' || Character.isWhitespace(c)) {
                return i;
            }
        }
        
        return json.length();
    }
    
    /**
     * A simple JSON parser class
     */
    static class SimpleJsonParser {
        private String json;
        private int pos;
        
        public Map<String, Object> parse(String jsonString) {
            this.json = jsonString.trim();
            this.pos = 0;
            
            if (!json.startsWith("{") || !json.endsWith("}")) {
                throw new IllegalArgumentException("Invalid JSON object format");
            }
            
            // Skip the opening brace
            pos++;
            
            Map<String, Object> result = parseObject();
            
            return result;
        }
        
        private Map<String, Object> parseObject() {
            Map<String, Object> map = new HashMap<>();
            
            // Skip whitespace
            skipWhitespace();
            
            // Handle empty object
            if (json.charAt(pos) == '}') {
                pos++;
                return map;
            }
            
            while (pos < json.length()) {
                // Skip whitespace
                skipWhitespace();
                
                // Expect a string key
                if (json.charAt(pos) != '\"') {
                    throw new IllegalArgumentException("Expected string key at position " + pos);
                }
                
                // Parse the key
                pos++; // Skip the opening quote
                StringBuilder key = new StringBuilder();
                while (pos < json.length() && json.charAt(pos) != '\"') {
                    key.append(json.charAt(pos++));
                }
                pos++; // Skip the closing quote
                
                // Skip whitespace and expect a colon
                skipWhitespace();
                if (json.charAt(pos) != ':') {
                    throw new IllegalArgumentException("Expected colon at position " + pos);
                }
                pos++; // Skip the colon
                
                // Skip whitespace before the value
                skipWhitespace();
                
                // Parse the value
                Object value = parseValue();
                
                // Add the key-value pair to the map
                map.put(key.toString(), value);
                
                // Skip whitespace
                skipWhitespace();
                
                // Check for end of object or comma
                if (json.charAt(pos) == '}') {
                    pos++;
                    break;
                } else if (json.charAt(pos) == ',') {
                    pos++; // Skip the comma
                } else {
                    throw new IllegalArgumentException("Expected comma or closing brace at position " + pos);
                }
            }
            
            return map;
        }
        
        private List<Object> parseArray() {
            List<Object> list = new ArrayList<>();
            
            // Skip the opening bracket
            pos++;
            
            // Skip whitespace
            skipWhitespace();
            
            // Handle empty array
            if (json.charAt(pos) == ']') {
                pos++;
                return list;
            }
            
            while (pos < json.length()) {
                // Skip whitespace
                skipWhitespace();
                
                // Parse the value
                Object value = parseValue();
                
                // Add the value to the list
                list.add(value);
                
                // Skip whitespace
                skipWhitespace();
                
                // Check for end of array or comma
                if (json.charAt(pos) == ']') {
                    pos++;
                    break;
                } else if (json.charAt(pos) == ',') {
                    pos++; // Skip the comma
                } else {
                    throw new IllegalArgumentException("Expected comma or closing bracket at position " + pos);
                }
            }
            
            return list;
        }
        
        private Object parseValue() {
            skipWhitespace();
            
            char c = json.charAt(pos);
            
            if (c == '{') {
                return parseObject();
            } else if (c == '[') {
                return parseArray();
            } else if (c == '\"') {
                return parseString();
            } else if (c == 't') {
                return parseTrue();
            } else if (c == 'f') {
                return parseFalse();
            } else if (c == 'n') {
                return parseNull();
            } else if (c == '-' || (c >= '0' && c <= '9')) {
                return parseNumber();
            } else {
                throw new IllegalArgumentException("Unexpected character at position " + pos + ": " + c);
            }
        }
        
        private String parseString() {
            // Skip the opening quote
            pos++;
            
            StringBuilder sb = new StringBuilder();
            while (pos < json.length() && json.charAt(pos) != '\"') {
                sb.append(json.charAt(pos++));
            }
            
            // Skip the closing quote
            pos++;
            
            return sb.toString();
        }
        
        private Boolean parseTrue() {
            if (json.startsWith("true", pos)) {
                pos += 4;
                return Boolean.TRUE;
            } else {
                throw new IllegalArgumentException("Expected 'true' at position " + pos);
            }
        }
        
        private Boolean parseFalse() {
            if (json.startsWith("false", pos)) {
                pos += 5;
                return Boolean.FALSE;
            } else {
                throw new IllegalArgumentException("Expected 'false' at position " + pos);
            }
        }
        
        private Object parseNull() {
            if (json.startsWith("null", pos)) {
                pos += 4;
                return null;
            } else {
                throw new IllegalArgumentException("Expected 'null' at position " + pos);
            }
        }
        
        private Number parseNumber() {
            int start = pos;
            
            // Handle negative sign
            if (json.charAt(pos) == '-') {
                pos++;
            }
            
            // Parse digits before decimal point
            while (pos < json.length() && Character.isDigit(json.charAt(pos))) {
                pos++;
            }
            
            // Handle decimal point and digits after it
            boolean isFloat = false;
            if (pos < json.length() && json.charAt(pos) == '.') {
                isFloat = true;
                pos++;
                
                while (pos < json.length() && Character.isDigit(json.charAt(pos))) {
                    pos++;
                }
            }
            
            // Handle exponent
            if (pos < json.length() && (json.charAt(pos) == 'e' || json.charAt(pos) == 'E')) {
                isFloat = true;
                pos++;
                
                if (pos < json.length() && (json.charAt(pos) == '+' || json.charAt(pos) == '-')) {
                    pos++;
                }
                
                while (pos < json.length() && Character.isDigit(json.charAt(pos))) {
                    pos++;
                }
            }
            
            String numStr = json.substring(start, pos);
            
            if (isFloat) {
                return Double.parseDouble(numStr);
            } else {
                return Integer.parseInt(numStr);
            }
        }
        
        private void skipWhitespace() {
            while (pos < json.length() && Character.isWhitespace(json.charAt(pos))) {
                pos++;
            }
        }
    }
    
    /**
     * Product class for mapping JSON data
     */
    static class Product {
        private int id;
        private String name;
        private double price;
        private List<String> tags;
        private Dimensions dimensions;
        private boolean inStock;
        
        public int getId() {
            return id;
        }
        
        public void setId(int id) {
            this.id = id;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public double getPrice() {
            return price;
        }
        
        public void setPrice(double price) {
            this.price = price;
        }
        
        public List<String> getTags() {
            return tags;
        }
        
        public void setTags(List<String> tags) {
            this.tags = tags;
        }
        
        public Dimensions getDimensions() {
            return dimensions;
        }
        
        public void setDimensions(Dimensions dimensions) {
            this.dimensions = dimensions;
        }
        
        public boolean isInStock() {
            return inStock;
        }
        
        public void setInStock(boolean inStock) {
            this.inStock = inStock;
        }
        
        @Override
        public String toString() {
            return "Product{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", price=" + price +
                    ", tags=" + tags +
                    ", dimensions=" + dimensions +
                    ", inStock=" + inStock +
                    '}';
        }
    }
    
    /**
     * Dimensions class for nested JSON data
     */
    static class Dimensions {
        private double length;
        private double width;
        private double height;
        
        public double getLength() {
            return length;
        }
        
        public void setLength(double length) {
            this.length = length;
        }
        
        public double getWidth() {
            return width;
        }
        
        public void setWidth(double width) {
            this.width = width;
        }
        
        public double getHeight() {
            return height;
        }
        
        public void setHeight(double height) {
            this.height = height;
        }
        
        @Override
        public String toString() {
            return length + " x " + width + " x " + height;
        }
    }
}
