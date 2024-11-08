package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.DomainID;
import org.switch2022.project.utils.exception.EmptyConstructorArgumentException;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import java.io.Serializable;
import java.util.Objects;

/**
 * The Email class represents the email of a user account in the domain model.
 * It is a value object that implements the DomainID and Serializable interfaces.
 */
public class Email implements DomainID, Serializable {
    private final String emailValue;

    /**
     * Instantiates a new email.
     *
     * @param email the email value
     * @throws IllegalArgumentException when email is null or blank
     */
    public Email(String email) {
        if (email == null) {
            throw new NullConstructorArgumentException("Invalid Email.");
        }

        if (email.isBlank()) {
            throw new EmptyConstructorArgumentException("Invalid Email.");
        }
        this.emailValue = email;
    }

    /**
     * Gets the email value.
     *
     * @return the email value
     */
    public String getEmailValue() {
        return emailValue;
    }

    /**
     * Checks if this Email is equal to the given object.
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
        Email that = (Email) o;
        return this.emailValue.equals(that.emailValue);
    }

    /**
     * Gets the email value.
     *
     * @return the email value
     */
    @Override
    public String toString() {
        return emailValue;
    }

    /**
     * Generates the hash code value.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(emailValue);
    }
}
