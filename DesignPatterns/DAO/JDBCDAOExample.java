/**
 * JDBC DAO Example
 * 
 * This example demonstrates a more realistic implementation of the DAO pattern using JDBC.
 * It shows how to implement a DAO that connects to a relational database.
 * 
 * Note: This is a simulation - the JDBC code won't actually run without a database connection.
 * It's meant to illustrate how a real JDBC DAO would be structured.
 */
package design.patterns.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JDBCDAOExample {
    
    public static void main(String[] args) {
        // This is a simulation - in a real application, you would:
        // 1. Set up a database
        // 2. Create the users table
        // 3. Configure the connection properties
        
        System.out.println("JDBC DAO Example (Simulation)");
        System.out.println("Note: This is a simulation to demonstrate the structure of a JDBC DAO.");
        System.out.println("      No actual database operations are performed.\n");
        
        // Create a DAO with database configuration
        ProductDAO productDAO = new ProductDAOImpl(
                "jdbc:mysql://localhost:3306/productdb",
                "username",
                "password"
        );
        
        // Create a product
        Product product1 = new Product(1, "Laptop", "High-performance laptop", 1299.99);
        
        // Simulate CRUD operations
        System.out.println("Creating a new product:");
        productDAO.create(product1);
        
        System.out.println("\nFinding product by ID (1):");
        Optional<Product> foundProduct = productDAO.findById(1);
        foundProduct.ifPresent(System.out::println);
        
        System.out.println("\nUpdating product:");
        product1.setPrice(1199.99);
        productDAO.update(product1);
        
        System.out.println("\nFinding all products:");
        List<Product> allProducts = productDAO.findAll();
        allProducts.forEach(System.out::println);
        
        System.out.println("\nDeleting product:");
        productDAO.delete(1);
        
        System.out.println("\nFinding products by category:");
        List<Product> electronicsProducts = productDAO.findByCategory("Electronics");
        electronicsProducts.forEach(System.out::println);
    }
}

/**
 * Product entity class
 */
class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private String category;
    
    public Product(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = "Electronics"; // Default category for simulation
    }
    
    // Constructor that would be used when retrieving from database
    public Product(int id, String name, String description, double price, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
    }
    
    // Getters and setters
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
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", category='" + category + '\'' +
                '}';
    }
}

/**
 * DAO Interface for Product entity
 */
interface ProductDAO {
    void create(Product product);
    Optional<Product> findById(int id);
    List<Product> findAll();
    void update(Product product);
    void delete(int id);
    
    // Additional operations
    List<Product> findByCategory(String category);
}

/**
 * JDBC implementation of the ProductDAO interface
 */
class ProductDAOImpl implements ProductDAO {
    private String jdbcUrl;
    private String username;
    private String password;
    
    public ProductDAOImpl(String jdbcUrl, String username, String password) {
        this.jdbcUrl = jdbcUrl;
        this.username = username;
        this.password = password;
    }
    
    /**
     * Get a database connection
     * In a real application, you would use a connection pool
     */
    private Connection getConnection() throws SQLException {
        // This would actually connect to the database in a real application
        System.out.println("Connecting to database: " + jdbcUrl);
        
        // Simulation - this would throw SQLException if connection fails
        if (jdbcUrl == null || jdbcUrl.isEmpty()) {
            throw new SQLException("Invalid JDBC URL");
        }
        
        // In a real application:
        // return DriverManager.getConnection(jdbcUrl, username, password);
        
        // For simulation, return null
        return null;
    }
    
    /**
     * Close database resources safely
     */
    private void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
            System.out.println("Database resources closed");
        } catch (SQLException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
    
    @Override
    public void create(Product product) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            
            // SQL for insert
            String sql = "INSERT INTO products (id, name, description, price, category) VALUES (?, ?, ?, ?, ?)";
            
            // In a real application:
            // stmt = conn.prepareStatement(sql);
            // stmt.setInt(1, product.getId());
            // stmt.setString(2, product.getName());
            // stmt.setString(3, product.getDescription());
            // stmt.setDouble(4, product.getPrice());
            // stmt.setString(5, product.getCategory());
            // stmt.executeUpdate();
            
            // Simulation
            System.out.println("Executing SQL: " + sql);
            System.out.println("With parameters: [" + product.getId() + ", " + product.getName() + 
                    ", " + product.getDescription() + ", " + product.getPrice() + ", " + product.getCategory() + "]");
            System.out.println("Product created successfully");
            
        } catch (SQLException e) {
            System.err.println("Error creating product: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
    }
    
    @Override
    public Optional<Product> findById(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            
            // SQL for select by id
            String sql = "SELECT * FROM products WHERE id = ?";
            
            // In a real application:
            // stmt = conn.prepareStatement(sql);
            // stmt.setInt(1, id);
            // rs = stmt.executeQuery();
            // if (rs.next()) {
            //     Product product = new Product(
            //         rs.getInt("id"),
            //         rs.getString("name"),
            //         rs.getString("description"),
            //         rs.getDouble("price"),
            //         rs.getString("category")
            //     );
            //     return Optional.of(product);
            // }
            
            // Simulation
            System.out.println("Executing SQL: " + sql);
            System.out.println("With parameter: [" + id + "]");
            
            // For simulation, return a dummy product
            if (id == 1) {
                Product product = new Product(1, "Laptop", "High-performance laptop", 1299.99, "Electronics");
                System.out.println("Product found");
                return Optional.of(product);
            }
            
            System.out.println("Product not found");
            return Optional.empty();
            
        } catch (SQLException e) {
            System.err.println("Error finding product: " + e.getMessage());
            return Optional.empty();
        } finally {
            closeResources(conn, stmt, rs);
        }
    }
    
    @Override
    public List<Product> findAll() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Product> products = new ArrayList<>();
        
        try {
            conn = getConnection();
            
            // SQL for select all
            String sql = "SELECT * FROM products";
            
            // In a real application:
            // stmt = conn.createStatement();
            // rs = stmt.executeQuery(sql);
            // while (rs.next()) {
            //     Product product = new Product(
            //         rs.getInt("id"),
            //         rs.getString("name"),
            //         rs.getString("description"),
            //         rs.getDouble("price"),
            //         rs.getString("category")
            //     );
            //     products.add(product);
            // }
            
            // Simulation
            System.out.println("Executing SQL: " + sql);
            
            // For simulation, return dummy products
            products.add(new Product(1, "Laptop", "High-performance laptop", 1199.99, "Electronics"));
            products.add(new Product(2, "Smartphone", "Latest model", 899.99, "Electronics"));
            products.add(new Product(3, "Headphones", "Noise-cancelling", 249.99, "Electronics"));
            
            System.out.println("Found " + products.size() + " products");
            
            return products;
            
        } catch (SQLException e) {
            System.err.println("Error finding all products: " + e.getMessage());
            return products;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }
    
    @Override
    public void update(Product product) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            
            // SQL for update
            String sql = "UPDATE products SET name = ?, description = ?, price = ?, category = ? WHERE id = ?";
            
            // In a real application:
            // stmt = conn.prepareStatement(sql);
            // stmt.setString(1, product.getName());
            // stmt.setString(2, product.getDescription());
            // stmt.setDouble(3, product.getPrice());
            // stmt.setString(4, product.getCategory());
            // stmt.setInt(5, product.getId());
            // int rowsAffected = stmt.executeUpdate();
            
            // Simulation
            System.out.println("Executing SQL: " + sql);
            System.out.println("With parameters: [" + product.getName() + ", " + product.getDescription() + 
                    ", " + product.getPrice() + ", " + product.getCategory() + ", " + product.getId() + "]");
            System.out.println("Product updated successfully");
            
        } catch (SQLException e) {
            System.err.println("Error updating product: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
    }
    
    @Override
    public void delete(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = getConnection();
            
            // SQL for delete
            String sql = "DELETE FROM products WHERE id = ?";
            
            // In a real application:
            // stmt = conn.prepareStatement(sql);
            // stmt.setInt(1, id);
            // int rowsAffected = stmt.executeUpdate();
            
            // Simulation
            System.out.println("Executing SQL: " + sql);
            System.out.println("With parameter: [" + id + "]");
            System.out.println("Product deleted successfully");
            
        } catch (SQLException e) {
            System.err.println("Error deleting product: " + e.getMessage());
        } finally {
            closeResources(conn, stmt, null);
        }
    }
    
    @Override
    public List<Product> findByCategory(String category) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Product> products = new ArrayList<>();
        
        try {
            conn = getConnection();
            
            // SQL for select by category
            String sql = "SELECT * FROM products WHERE category = ?";
            
            // In a real application:
            // stmt = conn.prepareStatement(sql);
            // stmt.setString(1, category);
            // rs = stmt.executeQuery();
            // while (rs.next()) {
            //     Product product = new Product(
            //         rs.getInt("id"),
            //         rs.getString("name"),
            //         rs.getString("description"),
            //         rs.getDouble("price"),
            //         rs.getString("category")
            //     );
            //     products.add(product);
            // }
            
            // Simulation
            System.out.println("Executing SQL: " + sql);
            System.out.println("With parameter: [" + category + "]");
            
            // For simulation, return dummy products if category is Electronics
            if ("Electronics".equals(category)) {
                products.add(new Product(1, "Laptop", "High-performance laptop", 1199.99, "Electronics"));
                products.add(new Product(2, "Smartphone", "Latest model", 899.99, "Electronics"));
                products.add(new Product(3, "Headphones", "Noise-cancelling", 249.99, "Electronics"));
            }
            
            System.out.println("Found " + products.size() + " products in category: " + category);
            
            return products;
            
        } catch (SQLException e) {
            System.err.println("Error finding products by category: " + e.getMessage());
            return products;
        } finally {
            closeResources(conn, stmt, rs);
        }
    }
}
