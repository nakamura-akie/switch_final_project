package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.ValueObject;
import org.switch2022.project.utils.exception.InvalidConstructorArgumentException;

import java.util.Objects;

/**
 * The CostPerHour class represents the cost per hour of a resource in the domain model.
 * It is a value object that implements the ValueObject interface.
 */
public class CostPerHour implements ValueObject {
    private final Double hourlyCost;

    /**
     * Instantiates a new CostPerHour.
     *
     * @param hourlyCost as a double
     */
    public CostPerHour(double hourlyCost) {
        if (hourlyCost < 0) {
            throw new InvalidConstructorArgumentException("Invalid cost per hour.");
        }
        this.hourlyCost = hourlyCost;
    }

    /**
     * Constructs a CostPerHour object from a string value.
     *
     * @param hourlyCost the cost per hour as a string
     */
    public CostPerHour(String hourlyCost) {
        this.hourlyCost = Double.parseDouble(hourlyCost);
    }

    /**
     * Constructs a CostPerHour object from an integer value.
     *
     * @param hourlyCost the cost per hour as an integer
     */
    protected CostPerHour(Integer hourlyCost) {
        this.hourlyCost = Double.valueOf((hourlyCost));
    }

    /**
     * Gets the value of CostPerHour of current Resource.
     *
     * @return cost per hour as a double
     */
    public Double getCostPerHourValue() {
        return hourlyCost;
    }

    /**
     * Checks if this CostPerHour is equal to the given object.
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
        CostPerHour that = (CostPerHour) o;
        return this.hourlyCost.equals(that.hourlyCost);
    }

    /**
     * Generates the hash code value.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(hourlyCost);
    }
}
