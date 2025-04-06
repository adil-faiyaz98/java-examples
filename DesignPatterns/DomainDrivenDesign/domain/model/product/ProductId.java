package design.patterns.domaindrivendesign.domain.model.product;

/**
 * ProductId Value Object
 * 
 * Represents the unique identifier for a Product entity.
 * This is a value object because it's immutable and has no identity of its own.
 */
public final class ProductId {
    private final String id;
    
    public ProductId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Product ID cannot be empty");
        }
        this.id = id;
    }
    
    public String getValue() {
        return id;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        ProductId productId = (ProductId) o;
        return id.equals(productId.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    @Override
    public String toString() {
        return id;
    }
}
