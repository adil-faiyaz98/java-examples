package com.example.aop.service;

import com.example.aop.annotation.Auditable;
import com.example.aop.annotation.Cacheable;
import com.example.aop.annotation.LogExecutionTime;
import com.example.aop.annotation.RequiresRole;
import com.example.aop.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service for managing products.
 * 
 * This class demonstrates various AOP concepts through annotations and method calls.
 */
@Service
@Auditable("ProductService")
public class ProductService {

    private final Map<Long, Product> products = new HashMap<>();

    public ProductService() {
        // Initialize with some sample data
        products.put(1L, new Product(1L, "Laptop", "High-performance laptop", 1299.99, true));
        products.put(2L, new Product(2L, "Smartphone", "Latest smartphone model", 799.99, true));
        products.put(3L, new Product(3L, "Tablet", "Portable tablet device", 499.99, false));
    }

    /**
     * Get all products.
     * 
     * This method is marked with @LogExecutionTime to log its execution time.
     * It's also marked with @Cacheable to cache its results.
     */
    @LogExecutionTime
    @Cacheable(key = "allProducts")
    public List<Product> getAllProducts() {
        simulateSlowService();
        return new ArrayList<>(products.values());
    }

    /**
     * Get a product by ID.
     * 
     * This method is marked with @LogExecutionTime to log its execution time.
     */
    @LogExecutionTime
    public Product getProductById(Long id) {
        simulateSlowService();
        return products.get(id);
    }

    /**
     * Create a new product.
     * 
     * This method is marked with @Auditable to log audit information.
     * It's also marked with @RequiresRole to enforce role-based access control.
     */
    @Auditable("createProduct")
    @RequiresRole({"ADMIN", "PRODUCT_MANAGER"})
    public Product createProduct(Product product) {
        products.put(product.getId(), product);
        return product;
    }

    /**
     * Update an existing product.
     * 
     * This method is marked with @Auditable to log audit information.
     * It's also marked with @RequiresRole to enforce role-based access control.
     */
    @Auditable("updateProduct")
    @RequiresRole({"ADMIN", "PRODUCT_MANAGER"})
    public Product updateProduct(Product product) {
        if (!products.containsKey(product.getId())) {
            throw new IllegalArgumentException("Product not found with ID: " + product.getId());
        }
        products.put(product.getId(), product);
        return product;
    }

    /**
     * Delete a product.
     * 
     * This method is marked with @Auditable to log audit information.
     * It's also marked with @RequiresRole to enforce role-based access control.
     */
    @Auditable("deleteProduct")
    @RequiresRole({"ADMIN"})
    public void deleteProduct(Long id) {
        if (!products.containsKey(id)) {
            throw new IllegalArgumentException("Product not found with ID: " + id);
        }
        products.remove(id);
    }

    /**
     * Simulate a slow service by sleeping for a random amount of time.
     */
    private void simulateSlowService() {
        try {
            Thread.sleep((long) (Math.random() * 200));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
