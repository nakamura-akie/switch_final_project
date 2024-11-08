package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.ValueObject;
import org.switch2022.project.utils.exception.InvalidConstructorArgumentException;

import java.util.Objects;

/**
 * The NumberPlannedSprints class represents the number of planned sprints of a project in the domain model.
 * It is a value object that implements the ValueObject interface.
 */
public class NumberPlannedSprints implements ValueObject {

    private final Integer numberOfPlannedSprintsValue;

    /**
     * Constructs a NumberPlannedSprints object.
     *
     * @param numberPlannedSprintsValue the number of planned sprints
     * @throws InvalidConstructorArgumentException if the number of planned sprints is less than 0
     */
    public NumberPlannedSprints(int numberPlannedSprintsValue) {
        validateValue(numberPlannedSprintsValue);
        this.numberOfPlannedSprintsValue = numberPlannedSprintsValue;
    }

    private static void validateValue(int numberPlannedSprintsValue) {
        if (numberPlannedSprintsValue < 0) {
            throw new InvalidConstructorArgumentException("Number of planned Sprints must be greater than 0");
        }
    }

    /**
     * Gets the number value of planned sprints.
     *
     * @return the number value of planned sprints
     */
    public Integer getNumberOfPlannedSprintsValue() {
        return numberOfPlannedSprintsValue;
    }

    /**
     * Checks if this NumberPlannedSprints is equal to the given object.
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
        NumberPlannedSprints that = (NumberPlannedSprints) o;
        return this.numberOfPlannedSprintsValue.equals(that.numberOfPlannedSprintsValue);
    }

    /**
     * Generates the hash code value.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(numberOfPlannedSprintsValue);
    }

}
