package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.ValueObject;

import java.util.Objects;

/**
 * The AccountStatus class represents the status of an account in the domain model.
 * It is a value object that implements the ValueObject interface.
 */
public class AccountStatus implements ValueObject {

    private final boolean value;

    /**
     * Constructs an account status object.
     *
     * @param value the account status
     */
    public AccountStatus(boolean value) {
        this.value = value;
    }

    /**
     * Returns the string value representing the account status.
     *
     * @return the account status value ("active" if true, "inactive" if false)
     */
    public String getValue() {
        if (this.value) {
            return "active";
        }
        return "inactive";
    }

    /**
     * Checks if this AccountStatus is equal to the given object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null){
            return false;
        }
        if ((this.getClass() != o.getClass())) {
            return false;
        }
        AccountStatus that = (AccountStatus) o;
        return value == that.value;
    }

    /**
     * Generates the hash code value for the account status.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    /**
     * Checks if the account status is active.
     *
     * @return true if the account status is active, false otherwise
     */
    public boolean isStatusActive() {
        return this.value;
    }
}
