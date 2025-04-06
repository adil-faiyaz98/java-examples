/**
 * Event System Example using the Observer Pattern
 * 
 * This example demonstrates a more modern implementation of the Observer pattern
 * using an event system with different event types and type-safe listeners.
 * 
 * It shows how to implement a flexible event system that can handle different
 * types of events and allow listeners to subscribe to specific event types.
 */
package design.patterns.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventSystemExample {
    
    public static void main(String[] args) {
        // Create the event manager
        EventManager eventManager = new EventManager("login", "logout", "message");
        
        // Create listeners
        EmailNotificationListener emailListener = new EmailNotificationListener("admin@example.com");
        LoggingListener loggingListener = new LoggingListener("log.txt");
        SecurityListener securityListener = new SecurityListener();
        
        // Subscribe listeners to different events
        eventManager.subscribe("login", emailListener);
        eventManager.subscribe("login", securityListener);
        eventManager.subscribe("logout", emailListener);
        eventManager.subscribe("message", loggingListener);
        
        // Create a user service that will use the event manager
        UserService userService = new UserService(eventManager);
        
        // Simulate user actions
        userService.login("john_doe", "password123");
        System.out.println();
        
        userService.sendMessage("john_doe", "Hello, world!");
        System.out.println();
        
        userService.logout("john_doe");
        System.out.println();
        
        // Unsubscribe the email listener from login events
        System.out.println("Unsubscribing email listener from login events...");
        eventManager.unsubscribe("login", emailListener);
        
        // Login again to see the difference
        userService.login("john_doe", "password123");
    }
}

/**
 * The base Event class
 */
class Event {
    private final String type;
    private final long timestamp;
    
    public Event(String type) {
        this.type = type;
        this.timestamp = System.currentTimeMillis();
    }
    
    public String getType() {
        return type;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
}

/**
 * Login Event
 */
class LoginEvent extends Event {
    private final String username;
    private final String ipAddress;
    
    public LoginEvent(String username, String ipAddress) {
        super("login");
        this.username = username;
        this.ipAddress = ipAddress;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getIpAddress() {
        return ipAddress;
    }
}

/**
 * Logout Event
 */
class LogoutEvent extends Event {
    private final String username;
    
    public LogoutEvent(String username) {
        super("logout");
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }
}

/**
 * Message Event
 */
class MessageEvent extends Event {
    private final String sender;
    private final String content;
    
    public MessageEvent(String sender, String content) {
        super("message");
        this.sender = sender;
        this.content = content;
    }
    
    public String getSender() {
        return sender;
    }
    
    public String getContent() {
        return content;
    }
}

/**
 * The EventListener interface
 */
interface EventListener {
    void update(Event event);
}

/**
 * The EventManager (Subject)
 */
class EventManager {
    private final Map<String, List<EventListener>> listeners = new HashMap<>();
    
    /**
     * Constructor that initializes event types
     */
    public EventManager(String... operations) {
        for (String operation : operations) {
            listeners.put(operation, new ArrayList<>());
        }
    }
    
    /**
     * Subscribe a listener to an event type
     */
    public void subscribe(String eventType, EventListener listener) {
        List<EventListener> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            eventListeners.add(listener);
        }
    }
    
    /**
     * Unsubscribe a listener from an event type
     */
    public void unsubscribe(String eventType, EventListener listener) {
        List<EventListener> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            eventListeners.remove(listener);
        }
    }
    
    /**
     * Notify all listeners of an event
     */
    public void notify(Event event) {
        List<EventListener> eventListeners = listeners.get(event.getType());
        if (eventListeners != null) {
            for (EventListener listener : eventListeners) {
                listener.update(event);
            }
        }
    }
}

/**
 * Email Notification Listener
 */
class EmailNotificationListener implements EventListener {
    private final String email;
    
    public EmailNotificationListener(String email) {
        this.email = email;
    }
    
    @Override
    public void update(Event event) {
        if (event instanceof LoginEvent) {
            LoginEvent loginEvent = (LoginEvent) event;
            System.out.println("Email sent to " + email + ": User " + loginEvent.getUsername() + 
                    " logged in from IP " + loginEvent.getIpAddress());
        } else if (event instanceof LogoutEvent) {
            LogoutEvent logoutEvent = (LogoutEvent) event;
            System.out.println("Email sent to " + email + ": User " + logoutEvent.getUsername() + 
                    " logged out");
        }
    }
}

/**
 * Logging Listener
 */
class LoggingListener implements EventListener {
    private final String logFile;
    
    public LoggingListener(String logFile) {
        this.logFile = logFile;
    }
    
    @Override
    public void update(Event event) {
        System.out.println("Logged to " + logFile + ": " + event.getType() + " event at " + 
                event.getTimestamp());
        
        if (event instanceof MessageEvent) {
            MessageEvent messageEvent = (MessageEvent) event;
            System.out.println("Logged message from " + messageEvent.getSender() + 
                    ": " + messageEvent.getContent());
        }
    }
}

/**
 * Security Listener
 */
class SecurityListener implements EventListener {
    @Override
    public void update(Event event) {
        if (event instanceof LoginEvent) {
            LoginEvent loginEvent = (LoginEvent) event;
            System.out.println("Security check for login: " + loginEvent.getUsername() + 
                    " from IP " + loginEvent.getIpAddress());
            
            // Simulate security check
            if (loginEvent.getIpAddress().equals("192.168.1.1")) {
                System.out.println("ALERT: Login from suspicious IP address!");
            }
        }
    }
}

/**
 * User Service that uses the event system
 */
class UserService {
    private final EventManager events;
    
    public UserService(EventManager events) {
        this.events = events;
    }
    
    public void login(String username, String password) {
        // Authenticate user (simplified)
        System.out.println("User " + username + " logged in");
        
        // Get IP address (simplified)
        String ipAddress = "127.0.0.1";
        
        // Notify about login event
        events.notify(new LoginEvent(username, ipAddress));
    }
    
    public void logout(String username) {
        System.out.println("User " + username + " logged out");
        
        // Notify about logout event
        events.notify(new LogoutEvent(username));
    }
    
    public void sendMessage(String username, String message) {
        System.out.println("User " + username + " sent message: " + message);
        
        // Notify about message event
        events.notify(new MessageEvent(username, message));
    }
}
