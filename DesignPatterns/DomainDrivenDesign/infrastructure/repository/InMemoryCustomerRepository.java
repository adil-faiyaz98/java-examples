package design.patterns.domaindrivendesign.infrastructure.repository;

import design.patterns.domaindrivendesign.domain.model.customer.Customer;
import design.patterns.domaindrivendesign.domain.model.customer.CustomerId;
import design.patterns.domaindrivendesign.domain.model.customer.Email;
import design.patterns.domaindrivendesign.domain.repository.CustomerRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * InMemoryCustomerRepository
 * 
 * In-memory implementation of the CustomerRepository interface.
 * In a real application, this would be replaced with a database implementation.
 */
public class InMemoryCustomerRepository implements CustomerRepository {
    private final Map<CustomerId, Customer> customers = new HashMap<>();
    
    @Override
    public Optional<Customer> findById(CustomerId id) {
        return Optional.ofNullable(customers.get(id));
    }
    
    @Override
    public Optional<Customer> findByEmail(Email email) {
        return customers.values().stream()
                .filter(customer -> customer.getEmail().equals(email))
                .findFirst();
    }
    
    @Override
    public Customer save(Customer customer) {
        customers.put(customer.getId(), customer);
        return customer;
    }
    
    @Override
    public void remove(CustomerId id) {
        customers.remove(id);
    }
}
