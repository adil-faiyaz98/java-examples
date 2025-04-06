/**
 * Proxy Design Pattern
 * 
 * Intent: Provide a surrogate or placeholder for another object to control access to it.
 * 
 * This example demonstrates three common types of proxies:
 * 1. Protection Proxy - Controls access to the original object based on access rights
 * 2. Virtual Proxy - Delays the creation of expensive objects until they're actually needed
 * 3. Caching Proxy - Caches results of expensive operations for reuse
 */
package design.patterns.proxy;

import java.util.HashMap;
import java.util.Map;

public class ProxyPattern {
    
    public static void main(String[] args) {
        // Example 1: Protection Proxy
        System.out.println("PROTECTION PROXY EXAMPLE");
        System.out.println("------------------------");
        
        // Create the real document
        Document document = new RealDocument("Confidential Report");
        
        // Create proxies with different access levels
        Document adminProxy = new DocumentProtectionProxy(document, "Admin", true);
        Document userProxy = new DocumentProtectionProxy(document, "User", false);
        
        // Admin can both view and edit
        System.out.println("Admin accessing document:");
        adminProxy.view();
        adminProxy.edit("Updated content by Admin");
        
        // User can only view
        System.out.println("\nUser accessing document:");
        userProxy.view();
        userProxy.edit("Attempt to edit by User"); // This will be denied
        
        System.out.println("\n------------------------\n");
        
        // Example 2: Virtual Proxy
        System.out.println("VIRTUAL PROXY EXAMPLE");
        System.out.println("------------------------");
        
        // Create a virtual proxy for a high-resolution image
        Image highResImage = new ImageVirtualProxy("high_resolution_photo.jpg");
        
        // The image is not loaded until it's displayed
        System.out.println("Image created but not loaded yet");
        
        // Now the image will be loaded
        System.out.println("\nDisplaying image for the first time:");
        highResImage.display();
        
        // The image is already loaded, so it's displayed immediately
        System.out.println("\nDisplaying image again:");
        highResImage.display();
        
        System.out.println("\n------------------------\n");
        
        // Example 3: Caching Proxy
        System.out.println("CACHING PROXY EXAMPLE");
        System.out.println("------------------------");
        
        // Create a caching proxy for an expensive data service
        DataService dataService = new DataServiceCachingProxy();
        
        // First request - will be executed and cached
        System.out.println("First request for 'user123':");
        System.out.println(dataService.getData("user123"));
        
        // Second request for the same data - will be retrieved from cache
        System.out.println("\nSecond request for 'user123' (should come from cache):");
        System.out.println(dataService.getData("user123"));
        
        // Request for different data - will be executed and cached
        System.out.println("\nRequest for 'user456':");
        System.out.println(dataService.getData("user456"));
    }
}

/**
 * Example 1: Protection Proxy
 */
interface Document {
    void view();
    void edit(String content);
}

class RealDocument implements Document {
    private String name;
    private String content;
    
    public RealDocument(String name) {
        this.name = name;
        this.content = "Original content of " + name;
        System.out.println("RealDocument: " + name + " created");
    }
    
    @Override
    public void view() {
        System.out.println("RealDocument: Viewing document " + name);
        System.out.println("Content: " + content);
    }
    
    @Override
    public void edit(String newContent) {
        System.out.println("RealDocument: Editing document " + name);
        this.content = newContent;
        System.out.println("Document updated");
    }
}

class DocumentProtectionProxy implements Document {
    private final Document realDocument;
    private final String user;
    private final boolean hasEditPermission;
    
    public DocumentProtectionProxy(Document realDocument, String user, boolean hasEditPermission) {
        this.realDocument = realDocument;
        this.user = user;
        this.hasEditPermission = hasEditPermission;
    }
    
    @Override
    public void view() {
        System.out.println("DocumentProtectionProxy: " + user + " is viewing the document");
        realDocument.view();
    }
    
    @Override
    public void edit(String content) {
        if (hasEditPermission) {
            System.out.println("DocumentProtectionProxy: " + user + " has edit permission");
            realDocument.edit(content);
        } else {
            System.out.println("DocumentProtectionProxy: " + user + " does not have edit permission. Access denied.");
        }
    }
}

/**
 * Example 2: Virtual Proxy
 */
interface Image {
    void display();
}

class RealImage implements Image {
    private final String filename;
    
    public RealImage(String filename) {
        this.filename = filename;
        loadFromDisk();
    }
    
    private void loadFromDisk() {
        System.out.println("RealImage: Loading image " + filename + " from disk");
        // Simulate a time-consuming operation
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void display() {
        System.out.println("RealImage: Displaying image " + filename);
    }
}

class ImageVirtualProxy implements Image {
    private final String filename;
    private RealImage realImage;
    
    public ImageVirtualProxy(String filename) {
        this.filename = filename;
    }
    
    @Override
    public void display() {
        // Create the RealImage only when it's needed
        if (realImage == null) {
            System.out.println("ImageVirtualProxy: Creating RealImage on first display");
            realImage = new RealImage(filename);
        } else {
            System.out.println("ImageVirtualProxy: RealImage already exists");
        }
        
        // Delegate to the real image
        realImage.display();
    }
}

/**
 * Example 3: Caching Proxy
 */
interface DataService {
    String getData(String userId);
}

class RealDataService implements DataService {
    @Override
    public String getData(String userId) {
        System.out.println("RealDataService: Fetching data for user " + userId);
        
        // Simulate an expensive database query
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Return some data
        return "Data for user " + userId + ": " + Math.random();
    }
}

class DataServiceCachingProxy implements DataService {
    private final DataService realService;
    private final Map<String, String> cache;
    
    public DataServiceCachingProxy() {
        this.realService = new RealDataService();
        this.cache = new HashMap<>();
    }
    
    @Override
    public String getData(String userId) {
        // Check if the data is in the cache
        if (cache.containsKey(userId)) {
            System.out.println("DataServiceCachingProxy: Returning cached data for user " + userId);
            return cache.get(userId);
        }
        
        // If not in cache, get from the real service
        String data = realService.getData(userId);
        
        // Store in cache for future use
        System.out.println("DataServiceCachingProxy: Caching data for user " + userId);
        cache.put(userId, data);
        
        return data;
    }
}
