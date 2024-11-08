package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.ValueObject;

import java.util.Objects;

/**
 * The PhoneNumber class represents the phone number of an account in the domain model.
 * It is a value object that implements the ValueObject interface.
 */
public class PhoneNumber implements ValueObject {
    private final Integer phoneNumberValue;

    /**
     * Constructs a PhoneNumber object.
     *
     * @param phoneNumber the phone number as an Integer
     * @throws IllegalArgumentException if the phone number is invalid
     */
    public PhoneNumber(Integer phoneNumber) {
        if (!isValid(phoneNumber)) {
            throw new IllegalArgumentException("Invalid phone number.");
        }
        this.phoneNumberValue = phoneNumber;
    }

    /**
     * Gets the phone number value.
     *
     * @return the phone number as an Integer
     */
    public Integer getPhoneNumberValue() {
        return phoneNumberValue;
    }

    private static boolean isValid(Integer phoneNumber) {
        final int NUMBER_OF_DIGITS = 9;
        return phoneNumber != null && phoneNumber > 0 && countPhoneNumberDigits(phoneNumber) == NUMBER_OF_DIGITS;
    }

    private static int countPhoneNumberDigits(Integer phoneNumber) {
        return (int) (Math.log10(phoneNumber) + 1);
    }

    /**
     * Checks if this PhoneNumber is equal to the given object.
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
        PhoneNumber that = (PhoneNumber) o;
        return this.phoneNumberValue.equals(that.phoneNumberValue);
    }

    /**
     * Generates the hash code value.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(phoneNumberValue);
    }
}
