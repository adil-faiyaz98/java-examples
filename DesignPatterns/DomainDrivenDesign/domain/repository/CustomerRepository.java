package design.patterns.domaindrivendesign.domain.repository;

import design.patterns.domaindrivendesign.domain.model.customer.Customer;
import design.patterns.domaindrivendesign.domain.model.customer.CustomerId;
import design.patterns.domaindrivendesign.domain.model.customer.Email;

import java.util.Optional;

/**
 * CustomerRepository Interface
 * 
 * Repository for the Customer aggregate. Provides methods to find, save, and remove customers.
 */
public interface CustomerRepository {
    /**
     * Finds a customer by ID
     */
    Optional<Customer> findById(CustomerId id);
    
    /**
     * Finds a customer by email
     */
    Optional<Customer> findByEmail(Email email);
    
    /**
     * Saves a customer (creates or updates)
     */
    Customer save(Customer customer);
    
    /**
     * Removes a customer
     */
    void remove(CustomerId id);
}
