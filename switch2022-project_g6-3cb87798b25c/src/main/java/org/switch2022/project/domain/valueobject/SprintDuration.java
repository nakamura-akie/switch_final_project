package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.ValueObject;
import org.switch2022.project.utils.exception.InvalidConstructorArgumentException;

import java.util.Objects;

/**
 * The SprintDuration class represents the sprint duration of a project in the domain model.
 * It is a value object that implements the ValueObject interface.
 */
public class SprintDuration implements ValueObject {
    private final Integer sprintDurationValue;

    public SprintDuration(Integer sprintDurationValue) {
        validateValue(sprintDurationValue);
        this.sprintDurationValue = sprintDurationValue;
    }

    private static void validateValue(Integer sprintDurationValue) {
        if (sprintDurationValue <= 0) {
            throw new InvalidConstructorArgumentException("Invalid duration.");
        }
    }

    /**
     * Gets the sprint duration value.
     *
     * @return the sprint duration value
     */
    public Integer getSprintDurationValue() {
        return sprintDurationValue;
    }

    /**
     * Checks if this SprintDuration is equal to the given object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        SprintDuration that = (SprintDuration) o;
        return Objects.equals(sprintDurationValue, that.sprintDurationValue);
    }

    /**
     * Generates the hash code value.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(sprintDurationValue);
    }


}
