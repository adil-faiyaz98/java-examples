package design.patterns.domaindrivendesign.domain.model.customer;

import design.patterns.domaindrivendesign.domain.model.shared.ValueObject;

import java.util.regex.Pattern;

/**
 * Email Value Object
 *
 * Represents an email address. This is a value object because it's immutable
 * and has no identity of its own.
 *
 * Demonstrates OOP principles:
 * - Immutability: all fields are final
 * - Implementation of an interface: implements ValueObject
 * - Encapsulation: private fields with controlled access
 * - Validation: ensures email format is valid
 */
public final class Email implements ValueObject {
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    private final String address;

    public Email(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Email address cannot be empty");
        }

        if (!EMAIL_PATTERN.matcher(address).matches()) {
            throw new IllegalArgumentException("Invalid email address format: " + address);
        }

        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email = (Email) o;
        return address.equals(email.address);
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }

    @Override
    public String toString() {
        return address;
    }
}
