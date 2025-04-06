package design.patterns.domaindrivendesign.domain.model.customer;

import design.patterns.domaindrivendesign.domain.model.shared.AggregateRoot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Customer Entity - Aggregate Root
 *
 * Represents a customer in the system. This is an entity with a distinct identity
 * and is the aggregate root for the Customer aggregate.
 *
 * Demonstrates OOP principles:
 * - Inheritance: extends AggregateRoot
 * - Encapsulation: private fields with public methods
 * - Abstraction: implements high-level customer behavior
 */
public class Customer extends AggregateRoot<CustomerId> {
    // id is inherited from AggregateRoot
    private String name;
    private Email email;
    private Address shippingAddress;
    private Address billingAddress;
    private List<PaymentMethod> paymentMethods;

    /**
     * Creates a new customer with the given information
     */
    public Customer(String name, Email email) {
        super(new CustomerId(UUID.randomUUID().toString()));
        this.name = name;
        this.email = email;
        this.paymentMethods = new ArrayList<>();

        // Validate invariants
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }

        // Register customer created event
        registerEvent(new CustomerCreatedEvent(getId(), email, name));
    }

    /**
     * Reconstitutes a customer from persistence
     */
    public Customer(CustomerId id, String name, Email email,
                   Address shippingAddress, Address billingAddress,
                   List<PaymentMethod> paymentMethods) {
        super(id);
        this.name = name;
        this.email = email;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        this.paymentMethods = new ArrayList<>(paymentMethods);
    }

    /**
     * Updates the customer's name
     */
    public void updateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be empty");
        }
        this.name = name;

        // Register customer updated event
        registerEvent(new CustomerUpdatedEvent(getId(), "name", name));
    }

    /**
     * Updates the customer's email
     */
    public void updateEmail(Email email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        this.email = email;

        // Register customer updated event
        registerEvent(new CustomerUpdatedEvent(getId(), "email", email.toString()));
    }

    /**
     * Sets the shipping address
     */
    public void setShippingAddress(Address address) {
        this.shippingAddress = address;

        // Register customer updated event
        registerEvent(new CustomerUpdatedEvent(getId(), "shippingAddress", address.toString()));
    }

    /**
     * Sets the billing address
     */
    public void setBillingAddress(Address address) {
        this.billingAddress = address;

        // Register customer updated event
        registerEvent(new CustomerUpdatedEvent(getId(), "billingAddress", address.toString()));
    }

    /**
     * Adds a payment method
     */
    public void addPaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            throw new IllegalArgumentException("Payment method cannot be null");
        }
        this.paymentMethods.add(paymentMethod);

        // Register payment method added event
        registerEvent(new PaymentMethodAddedEvent(getId(), paymentMethod));
    }

    /**
     * Removes a payment method
     */
    public void removePaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethods.remove(paymentMethod);

        // Register payment method removed event
        registerEvent(new PaymentMethodRemovedEvent(getId(), paymentMethod));
    }

    // getId() is inherited from AggregateRoot

    /**
     * Returns the customer name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the customer email
     */
    public Email getEmail() {
        return email;
    }

    /**
     * Returns the shipping address
     */
    public Address getShippingAddress() {
        return shippingAddress;
    }

    /**
     * Returns the billing address
     */
    public Address getBillingAddress() {
        return billingAddress;
    }

    /**
     * Returns an unmodifiable list of payment methods
     */
    public List<PaymentMethod> getPaymentMethods() {
        return Collections.unmodifiableList(paymentMethods);
    }

    /**
     * Returns a default payment method if available
     */
    public PaymentMethod getDefaultPaymentMethod() {
        if (paymentMethods.isEmpty()) {
            return null;
        }
        return paymentMethods.get(0);
    }

    // equals() and hashCode() are inherited from Entity

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email=" + email +
                '}';
    }
}
