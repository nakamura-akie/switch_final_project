package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.ValueObject;
import org.switch2022.project.utils.exception.EmptyConstructorArgumentException;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import java.util.Objects;

/**
 * The Description class represents the description of some entities in the domain model.
 * It is a value object that implements the ValueObject interface.
 */
public class Description implements ValueObject {

    private final String descriptionValue;

    /**
     * Instantiates a new description.
     *
     * @param descriptionValue value of the description
     * @throws IllegalArgumentException when description is null or blank
     */
    public Description(String descriptionValue) {
        validateValue(descriptionValue);
        this.descriptionValue = descriptionValue;
    }

    private static void validateValue(String descriptionValue) {
        if (descriptionValue == null) {
            throw new NullConstructorArgumentException("Description cannot be null");
        }
        if (descriptionValue.isBlank()) {
            throw new EmptyConstructorArgumentException("Description cannot be empty");
        }
    }

    /**
     * Gets description value.
     *
     * @return the description value
     */
    public String getDescriptionValue() {
        return descriptionValue;
    }

    /**
     * Checks if this Description is equal to the given object.
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
        Description that = (Description) o;
        return this.descriptionValue.equals(that.descriptionValue);
    }

    /**
     * Generates the hash code value.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(descriptionValue);
    }

}
