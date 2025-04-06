package design.patterns.domaindrivendesign.domain.model.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Abstract Aggregate Root Base Class
 * 
 * Provides common functionality for all aggregate roots in the domain model.
 * Demonstrates the OOP principles of abstraction and inheritance.
 * Also shows encapsulation by managing domain events internally.
 */
public abstract class AggregateRoot<ID> extends Entity<ID> {
    private List<DomainEvent> domainEvents = new ArrayList<>();
    
    protected AggregateRoot(ID id) {
        super(id);
    }
    
    /**
     * Registers a domain event to be published when the aggregate is saved
     */
    protected void registerEvent(DomainEvent event) {
        domainEvents.add(event);
    }
    
    /**
     * Returns all domain events and clears the list
     */
    public List<DomainEvent> getAndClearDomainEvents() {
        List<DomainEvent> events = Collections.unmodifiableList(domainEvents);
        domainEvents.clear();
        return events;
    }
    
    /**
     * Returns all domain events without clearing the list
     */
    public List<DomainEvent> getDomainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }
}
