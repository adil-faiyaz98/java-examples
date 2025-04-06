package Spring.service;

import Spring.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * UserNotificationService
 * 
 * Service that demonstrates qualifier-based injection.
 * This service can use different notification methods based on qualifiers.
 */
@Service
public class UserNotificationService {
    
    // Primary notification service (will use @Primary bean by default)
    private final NotificationService primaryNotificationService;
    
    // SMS notification service (explicitly qualified)
    private final NotificationService smsNotificationService;
    
    /**
     * Constructor with qualifier-based injection
     * 
     * @param primaryNotificationService The primary notification service (uses @Primary)
     * @param smsNotificationService The SMS notification service (uses @Qualifier)
     */
    @Autowired
    public UserNotificationService(
            NotificationService primaryNotificationService,
            @Qualifier("smsNotificationService") NotificationService smsNotificationService) {
        
        this.primaryNotificationService = primaryNotificationService;
        this.smsNotificationService = smsNotificationService;
        
        System.out.println("UserNotificationService created with:");
        System.out.println("- Primary notification: " + primaryNotificationService.getNotificationType());
        System.out.println("- SMS notification: " + smsNotificationService.getNotificationType());
    }
    
    /**
     * Send a notification using the primary notification service
     */
    public void notifyUser(User user, String message) {
        primaryNotificationService.notifyUser(user, message);
    }
    
    /**
     * Send a notification using the SMS notification service
     */
    public void notifyUserBySms(User user, String message) {
        smsNotificationService.notifyUser(user, message);
    }
    
    /**
     * Send a notification using both notification services
     */
    public void notifyUserByAllMethods(User user, String message) {
        primaryNotificationService.notifyUser(user, message);
        smsNotificationService.notifyUser(user, message);
    }
}
