package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.ValueObject;

import java.util.Objects;

/**
 * The CustomerName class represents the name of a customer in the domain model.
 * It is a value object that implements the ValueObject interface.
 */
public class CustomerName implements ValueObject {
    private final String customerNameValue;

    /**
     * Instantiates a Customer Name.
     *
     * @param nameValue the customer name value
     * @throws IllegalArgumentException when customer name is not valid, i. e. is null or blank
     */
    public CustomerName(String nameValue) {
        if (isValid(nameValue)) {
            throw new IllegalArgumentException("Invalid Customer Name.");
        }
        this.customerNameValue = nameValue;
    }

    private static boolean isValid(String nameValue) {
        return nameValue == null || nameValue.isBlank();
    }

    /**
     * Gets the Customer Name value.
     *
     * @return the customer name value
     */
    public String getCustomerNameValue() {
        return customerNameValue;
    }

    /**
     * Checks if this CustomerName is equal to the given object.
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
        CustomerName that = (CustomerName) o;
        return Objects.equals(customerNameValue, that.customerNameValue);
    }

    /**
     * Generates the hash code value.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(customerNameValue);
    }
}