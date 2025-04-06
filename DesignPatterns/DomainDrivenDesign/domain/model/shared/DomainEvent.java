package design.patterns.domaindrivendesign.domain.model.shared;

import java.time.LocalDateTime;

/**
 * DomainEvent Interface
 * 
 * Base interface for all domain events. Domain events represent something that
 * happened in the domain that domain experts care about.
 */
public interface DomainEvent {
    /**
     * Returns the time when the event occurred
     */
    LocalDateTime getOccurredOn();
}
