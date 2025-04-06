package design.patterns.domaindrivendesign.infrastructure.event;

import design.patterns.domaindrivendesign.domain.model.shared.DomainEvent;
import design.patterns.domaindrivendesign.domain.model.shared.DomainEventPublisher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * SimpleEventPublisher
 * 
 * Simple implementation of the DomainEventPublisher interface.
 * In a real application, this would be replaced with a more robust implementation
 * using a message broker like RabbitMQ or Kafka.
 */
public class SimpleEventPublisher implements DomainEventPublisher {
    private final Map<Class<? extends DomainEvent>, List<DomainEventHandler<? extends DomainEvent>>> handlers = new HashMap<>();
    
    @Override
    public void publish(DomainEvent event) {
        System.out.println("Publishing event: " + event);
        
        // Get handlers for this event type
        List<DomainEventHandler<? extends DomainEvent>> eventHandlers = handlers.get(event.getClass());
        
        if (eventHandlers != null) {
            // Notify all handlers
            for (DomainEventHandler handler : eventHandlers) {
                handler.handle(event);
            }
        }
    }
    
    /**
     * Registers a handler for a specific event type
     */
    public <T extends DomainEvent> void registerHandler(Class<T> eventType, DomainEventHandler<T> handler) {
        handlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
    }
    
    /**
     * Interface for event handlers
     */
    public interface DomainEventHandler<T extends DomainEvent> {
        void handle(T event);
    }
}
