package Spring.service;

import Spring.model.User;

/**
 * NotificationService interface
 * 
 * Defines the contract for sending notifications to users.
 * This is used to demonstrate qualifier-based injection.
 */
public interface NotificationService {
    
    /**
     * Send a notification to a user
     * 
     * @param user The user to notify
     * @param message The message to send
     */
    void notifyUser(User user, String message);
    
    /**
     * Get the notification type
     * 
     * @return The type of notification (e.g., "email", "sms")
     */
    String getNotificationType();
}
