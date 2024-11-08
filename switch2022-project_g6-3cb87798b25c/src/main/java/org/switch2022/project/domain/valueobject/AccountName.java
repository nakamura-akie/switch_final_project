package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.ValueObject;

import java.util.Objects;

/**
 * The AccountName class represents the name of an account in the domain model.
 * It is a value object that implements the DomainID interface.
 */
public class AccountName implements ValueObject {
    private final String name;

    /**
     * Constructs an account name object.
     *
     * @param name the account name
     * @throws IllegalArgumentException if the name is null or blank
     */
    public AccountName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Invalid name.");
        }
        this.name = name;
    }

    /**
     * Checks if this AccountName is equal to the given object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AccountName that = (AccountName) o;
        return this.name.equals(that.name);
    }

    /**
     * Returns the string representation of this AccountName.
     *
     * @return the account name as a string
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Generates the hash code value for the account name.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
