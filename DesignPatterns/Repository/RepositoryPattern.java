/**
 * Repository Design Pattern
 * 
 * Intent: Mediate between the domain and data mapping layers using a collection-like interface
 * for accessing domain objects.
 * 
 * This example demonstrates a Repository implementation for a Product entity with in-memory storage.
 * In a real application, the Repository would connect to a database, file system, or external service.
 */
package design.patterns.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class RepositoryPattern {
    
    public static void main(String[] args) {
        // Create a repository
        ProductRepository productRepository = new InMemoryProductRepository();
        
        // Create a service that uses the repository
        ProductService productService = new ProductService(productRepository);
        
        // Add some products
        System.out.println("Adding products...");
        productService.addProduct("Laptop", "High-performance laptop", 1299.99, "Electronics");
        productService.addProduct("Smartphone", "Latest model", 899.99, "Electronics");
        productService.addProduct("Headphones", "Noise-cancelling", 249.99, "Electronics");
        productService.addProduct("Coffee Maker", "Programmable coffee maker", 89.99, "Kitchen");
        productService.addProduct("Blender", "High-speed blender", 79.99, "Kitchen");
        
        // Find all products
        System.out.println("\nAll products:");
        List<Product> allProducts = productService.getAllProducts();
        allProducts.forEach(System.out::println);
        
        // Find a product by ID
        System.out.println("\nFinding product by ID:");
        if (!allProducts.isEmpty()) {
            String firstProductId = allProducts.get(0).getId();
            Optional<Product> foundProduct = productService.getProductById(firstProductId);
            foundProduct.ifPresent(product -> System.out.println("Found: " + product));
        }
        
        // Find products by category
        System.out.println("\nFinding products by category (Electronics):");
        List<Product> electronicsProducts = productService.getProductsByCategory("Electronics");
        electronicsProducts.forEach(System.out::println);
        
        // Update a product
        System.out.println("\nUpdating a product:");
        if (!electronicsProducts.isEmpty()) {
            Product productToUpdate = electronicsProducts.get(0);
            System.out.println("Before update: " + productToUpdate);
            
            productToUpdate.setPrice(productToUpdate.getPrice() * 0.9); // 10% discount
            productService.updateProduct(productToUpdate);
            
            Optional<Product> updatedProduct = productService.getProductById(productToUpdate.getId());
            updatedProduct.ifPresent(product -> System.out.println("After update: " + product));
        }
        
        // Delete a product
        System.out.println("\nDeleting a product:");
        if (!allProducts.isEmpty()) {
            String lastProductId = allProducts.get(allProducts.size() - 1).getId();
            Optional<Product> productToDelete = productService.getProductById(lastProductId);
            
            if (productToDelete.isPresent()) {
                System.out.println("Deleting: " + productToDelete.get());
                productService.removeProduct(lastProductId);
                
                System.out.println("\nVerifying deletion:");
                Optional<Product> deletedProduct = productService.getProductById(lastProductId);
                if (deletedProduct.isPresent()) {
                    System.out.println("Product still exists: " + deletedProduct.get());
                } else {
                    System.out.println("Product successfully deleted");
                }
            }
        }
        
        // Find products by price range
        System.out.println("\nFinding products in price range ($100-$1000):");
        List<Product> productsInRange = productService.getProductsByPriceRange(100, 1000);
        productsInRange.forEach(System.out::println);
    }
}

/**
 * Domain entity representing a Product
 */
class Product {
    private String id;
    private String name;
    private String description;
    private double price;
    private String category;
    
    public Product(String name, String description, double price, String category) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
    
    // Constructor with ID for creating from storage
    public Product(String id, String name, String description, double price, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
    
    // Getters and setters
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public double getPrice() {
        return price;
    }
    
    public void setPrice(double price) {
        this.price = price;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }
}

/**
 * Repository interface for Product entity
 * Defines collection-like methods for accessing domain objects
 */
interface ProductRepository {
    Product save(Product product);
    Optional<Product> findById(String id);
    List<Product> findAll();
    void delete(String id);
    
    // Domain-specific query methods
    List<Product> findByCategory(String category);
    List<Product> findByPriceRange(double minPrice, double maxPrice);
}

/**
 * In-memory implementation of the ProductRepository
 * In a real application, this would be replaced with a database implementation
 */
class InMemoryProductRepository implements ProductRepository {
    private final Map<String, Product> products = new HashMap<>();
    
    @Override
    public Product save(Product product) {
        products.put(product.getId(), product);
        return product;
    }
    
    @Override
    public Optional<Product> findById(String id) {
        return Optional.ofNullable(products.get(id));
    }
    
    @Override
    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }
    
    @Override
    public void delete(String id) {
        products.remove(id);
    }
    
    @Override
    public List<Product> findByCategory(String category) {
        return products.values().stream()
                .filter(product -> product.getCategory().equals(category))
                .collect(Collectors.toList());
    }
    
    @Override
    public List<Product> findByPriceRange(double minPrice, double maxPrice) {
        return products.values().stream()
                .filter(product -> product.getPrice() >= minPrice && product.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }
}

/**
 * Service class that uses the repository to implement business logic
 */
class ProductService {
    private final ProductRepository productRepository;
    
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    public Product addProduct(String name, String description, double price, String category) {
        Product newProduct = new Product(name, description, price, category);
        return productRepository.save(newProduct);
    }
    
    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }
    
    public void removeProduct(String id) {
        productRepository.delete(id);
    }
    
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }
    
    public List<Product> getProductsByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByPriceRange(minPrice, maxPrice);
    }
    
    // Additional business methods could be added here
    public List<Product> getDiscountedProducts(double discountThreshold) {
        return getAllProducts().stream()
                .filter(product -> product.getPrice() > discountThreshold)
                .map(product -> {
                    Product discounted = new Product(
                            product.getId(),
                            product.getName(),
                            product.getDescription(),
                            product.getPrice() * 0.9, // 10% discount
                            product.getCategory()
                    );
                    return discounted;
                })
                .collect(Collectors.toList());
    }
}
