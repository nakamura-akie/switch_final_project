package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.DomainID;
import org.switch2022.project.utils.exception.EmptyConstructorArgumentException;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import java.util.Objects;

/**
 * The BusinessSectorName class represents the business sector name of a project in the domain model.
 * It is a value object that implements the DomainID interfaces.
 */
public class BusinessSectorName implements DomainID {

    private final String businessSectorNameValue;

    /**
     * Constructs a business sector name object.
     *
     * @param businessSectorNameValue the name of the business sector
     * @throws NullConstructorArgumentException  if the business sector name is null
     * @throws EmptyConstructorArgumentException if the business sector name is empty or blank
     */
    public BusinessSectorName(String businessSectorNameValue) {
        validateValue(businessSectorNameValue);
        this.businessSectorNameValue = businessSectorNameValue;
    }

    private static void validateValue(String businessSectorNameValue) {
        if (businessSectorNameValue == null) {
            throw new NullConstructorArgumentException("Business Sector cannot be null");
        }
        if (businessSectorNameValue.isBlank()) {
            throw new EmptyConstructorArgumentException("Business Sector cannot be empty");
        }
    }

    /**
     * Returns the string value representing the business sector name.
     *
     * @return the business sector name value
     */
    public String getBusinessSectorNameValue() {
        return businessSectorNameValue;
    }

    /**
     * Checks if this BusinessSectorName is equal to the given object.
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
        BusinessSectorName that = (BusinessSectorName) o;
        return Objects.equals(businessSectorNameValue, that.businessSectorNameValue);
    }

    /**
     * Generates the hash code value.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(businessSectorNameValue);
    }
}
