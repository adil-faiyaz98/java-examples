package design.patterns.domaindrivendesign.domain.model.customer;

/**
 * Address Value Object
 * 
 * Represents a physical address. This is a value object because it's immutable
 * and has no identity of its own.
 */
public final class Address {
    private final String street;
    private final String city;
    private final String state;
    private final String postalCode;
    private final String country;
    
    public Address(String street, String city, String state, String postalCode, String country) {
        if (street == null || street.trim().isEmpty()) {
            throw new IllegalArgumentException("Street cannot be empty");
        }
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be empty");
        }
        if (state == null || state.trim().isEmpty()) {
            throw new IllegalArgumentException("State cannot be empty");
        }
        if (postalCode == null || postalCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Postal code cannot be empty");
        }
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be empty");
        }
        
        this.street = street;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.country = country;
    }
    
    public String getStreet() {
        return street;
    }
    
    public String getCity() {
        return city;
    }
    
    public String getState() {
        return state;
    }
    
    public String getPostalCode() {
        return postalCode;
    }
    
    public String getCountry() {
        return country;
    }
    
    public String getFullAddress() {
        return street + ", " + city + ", " + state + " " + postalCode + ", " + country;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Address address = (Address) o;
        if (!street.equals(address.street)) return false;
        if (!city.equals(address.city)) return false;
        if (!state.equals(address.state)) return false;
        if (!postalCode.equals(address.postalCode)) return false;
        return country.equals(address.country);
    }
    
    @Override
    public int hashCode() {
        int result = street.hashCode();
        result = 31 * result + city.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + postalCode.hashCode();
        result = 31 * result + country.hashCode();
        return result;
    }
    
    @Override
    public String toString() {
        return getFullAddress();
    }
}
