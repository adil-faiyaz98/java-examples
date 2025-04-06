/**
 * Remote Proxy Example
 * 
 * This example demonstrates a Remote Proxy, which represents an object that exists in a different
 * address space (like a web service or remote server).
 * 
 * In a real application, this would involve actual network communication, but for this example,
 * we'll simulate the remote service.
 */
package design.patterns.proxy;

import java.util.HashMap;
import java.util.Map;

public class RemoteProxyExample {
    
    public static void main(String[] args) {
        // Create a proxy to the "remote" weather service
        WeatherService weatherService = new WeatherServiceProxy();
        
        // Get weather data for different cities
        System.out.println("Weather in New York: " + weatherService.getTemperature("New York"));
        System.out.println("Weather in London: " + weatherService.getTemperature("London"));
        System.out.println("Weather in Tokyo: " + weatherService.getTemperature("Tokyo"));
        
        // Get weather for New York again (should be faster due to caching)
        System.out.println("\nWeather in New York again: " + weatherService.getTemperature("New York"));
        
        // Try to update the temperature (only admins can do this)
        System.out.println("\nTrying to update temperature as regular user:");
        weatherService.updateTemperature("London", 25.0);
        
        // Create an admin proxy
        WeatherService adminService = new WeatherServiceAdminProxy("admin", "secret123");
        
        // Admin can update the temperature
        System.out.println("\nTrying to update temperature as admin:");
        adminService.updateTemperature("London", 25.0);
        
        // Check the updated temperature
        System.out.println("\nUpdated weather in London: " + weatherService.getTemperature("London"));
    }
}

/**
 * Subject interface
 */
interface WeatherService {
    double getTemperature(String city);
    void updateTemperature(String city, double temperature);
}

/**
 * Real Subject - simulates a remote weather service
 */
class RemoteWeatherService implements WeatherService {
    private Map<String, Double> temperatures = new HashMap<>();
    
    public RemoteWeatherService() {
        // Initialize with some data
        temperatures.put("New York", 20.5);
        temperatures.put("London", 15.0);
        temperatures.put("Tokyo", 28.3);
        temperatures.put("Paris", 18.7);
        temperatures.put("Sydney", 22.1);
    }
    
    @Override
    public double getTemperature(String city) {
        System.out.println("RemoteWeatherService: Connecting to remote server...");
        
        // Simulate network delay
        simulateNetworkLatency();
        
        System.out.println("RemoteWeatherService: Getting temperature for " + city);
        
        // Return the temperature or a default value if city not found
        return temperatures.getOrDefault(city, 0.0);
    }
    
    @Override
    public void updateTemperature(String city, double temperature) {
        System.out.println("RemoteWeatherService: Connecting to remote server...");
        
        // Simulate network delay
        simulateNetworkLatency();
        
        System.out.println("RemoteWeatherService: Updating temperature for " + city + " to " + temperature);
        temperatures.put(city, temperature);
    }
    
    private void simulateNetworkLatency() {
        try {
            // Simulate a network delay of 1-2 seconds
            Thread.sleep((long) (1000 + Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * Proxy - combines remote proxy (simulating remote access) and caching proxy (caching results)
 */
class WeatherServiceProxy implements WeatherService {
    private RemoteWeatherService service;
    private Map<String, Double> cache = new HashMap<>();
    
    @Override
    public double getTemperature(String city) {
        // Lazy initialization of the service
        if (service == null) {
            System.out.println("WeatherServiceProxy: Creating remote service connection");
            service = new RemoteWeatherService();
        }
        
        // Check if the result is in the cache
        if (cache.containsKey(city)) {
            System.out.println("WeatherServiceProxy: Returning cached temperature for " + city);
            return cache.get(city);
        }
        
        // Get the temperature from the remote service
        double temperature = service.getTemperature(city);
        
        // Cache the result
        System.out.println("WeatherServiceProxy: Caching temperature for " + city);
        cache.put(city, temperature);
        
        return temperature;
    }
    
    @Override
    public void updateTemperature(String city, double temperature) {
        System.out.println("WeatherServiceProxy: Access denied. Regular users cannot update temperatures.");
    }
}

/**
 * Admin Proxy - adds authentication for privileged operations
 */
class WeatherServiceAdminProxy implements WeatherService {
    private RemoteWeatherService service;
    private final String username;
    private final String password;
    
    public WeatherServiceAdminProxy(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    @Override
    public double getTemperature(String city) {
        // Lazy initialization of the service
        if (service == null) {
            System.out.println("WeatherServiceAdminProxy: Creating remote service connection");
            service = new RemoteWeatherService();
        }
        
        return service.getTemperature(city);
    }
    
    @Override
    public void updateTemperature(String city, double temperature) {
        // Authenticate before allowing the update
        if (authenticate()) {
            // Lazy initialization of the service
            if (service == null) {
                System.out.println("WeatherServiceAdminProxy: Creating remote service connection");
                service = new RemoteWeatherService();
            }
            
            service.updateTemperature(city, temperature);
            
            // In a real application, we would also invalidate any cache here
        } else {
            System.out.println("WeatherServiceAdminProxy: Authentication failed. Cannot update temperature.");
        }
    }
    
    private boolean authenticate() {
        System.out.println("WeatherServiceAdminProxy: Authenticating user " + username);
        
        // In a real application, this would check against a database or authentication service
        // For this example, we'll just check if the username is "admin" and the password is "secret123"
        return "admin".equals(username) && "secret123".equals(password);
    }
}
