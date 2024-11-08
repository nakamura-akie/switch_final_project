package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.ValueObject;

import java.util.Objects;

/**
 * The SprintCode class represents the code of a sprint in the domain model.
 * It is a value object that implements the DomainID interface.
 */
public class SprintCode implements ValueObject {
    private final String sprintCodeValue;

    /**
     * Instantiates a new sprint code with the specified value.
     *
     * @param sprintCodeValue the value of the new sprint code
     * @throws IllegalArgumentException if the sprint code is null or blank
     */
    public SprintCode(String sprintCodeValue) {
        if (sprintCodeValue == null || sprintCodeValue.isBlank()) {
            throw new IllegalArgumentException("Invalid Sprint Code.");
        }
        this.sprintCodeValue = sprintCodeValue;
    }

    /**
     * Gets the sprint code value.
     *
     * @return the sprint code value
     */
    public String getSprintCodeValue() {
        return sprintCodeValue;
    }

    /**
     * Checks if this SprintCode is equal to the given object.
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
        SprintCode that = (SprintCode) o;
        return this.sprintCodeValue.equals(that.sprintCodeValue);
    }

    /**
     * Gets the sprint code value.
     *
     * @return the sprint code value
     */
    @Override
    public String toString() {
        return sprintCodeValue;
    }

    /**
     * Generates the hash code value.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(sprintCodeValue);
    }
}

