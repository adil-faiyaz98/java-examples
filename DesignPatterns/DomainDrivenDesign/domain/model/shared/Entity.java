package design.patterns.domaindrivendesign.domain.model.shared;

/**
 * Abstract Entity Base Class
 * 
 * Provides common functionality for all entities in the domain model.
 * Demonstrates the OOP principle of abstraction by defining a common interface
 * for all entities and inheritance by allowing specific entities to extend this class.
 */
public abstract class Entity<ID> {
    protected ID id;
    
    protected Entity(ID id) {
        if (id == null) {
            throw new IllegalArgumentException("Entity ID cannot be null");
        }
        this.id = id;
    }
    
    public ID getId() {
        return id;
    }
    
    /**
     * Entities are compared based on their identity, not their attributes
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Entity<?> entity = (Entity<?>) o;
        return id.equals(entity.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
