package design.patterns.domaindrivendesign.domain.model.shared;

/**
 * DomainEventPublisher Interface
 * 
 * Defines the contract for publishing domain events.
 */
public interface DomainEventPublisher {
    /**
     * Publishes a domain event
     */
    void publish(DomainEvent event);
}
