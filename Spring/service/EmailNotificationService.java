package Spring.service;

import Spring.model.User;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * EmailNotificationService
 * 
 * Implementation of the NotificationService interface that sends email notifications.
 * This is used to demonstrate qualifier-based injection.
 * 
 * The @Primary annotation marks this as the default implementation to use
 * when multiple candidates exist for autowiring.
 */
@Service
@Primary
public class EmailNotificationService implements NotificationService {
    
    /**
     * Constructor
     */
    public EmailNotificationService() {
        System.out.println("EmailNotificationService created");
    }
    
    @Override
    public void notifyUser(User user, String message) {
        System.out.println("Sending EMAIL to " + user.getEmail() + ": " + message);
    }
    
    @Override
    public String getNotificationType() {
        return "email";
    }
}
