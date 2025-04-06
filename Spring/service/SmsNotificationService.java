package Spring.service;

import Spring.model.User;
import org.springframework.stereotype.Service;

/**
 * SmsNotificationService
 * 
 * Implementation of the NotificationService interface that sends SMS notifications.
 * This is used to demonstrate qualifier-based injection.
 */
@Service
public class SmsNotificationService implements NotificationService {
    
    /**
     * Constructor
     */
    public SmsNotificationService() {
        System.out.println("SmsNotificationService created");
    }
    
    @Override
    public void notifyUser(User user, String message) {
        // In a real application, this would send an SMS
        System.out.println("Sending SMS to " + user.getUsername() + ": " + message);
    }
    
    @Override
    public String getNotificationType() {
        return "sms";
    }
}
