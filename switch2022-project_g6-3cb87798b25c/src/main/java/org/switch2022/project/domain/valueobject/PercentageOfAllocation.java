package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.ValueObject;
import org.switch2022.project.utils.exception.InvalidConstructorArgumentException;

import javax.activation.UnsupportedDataTypeException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;


/**
 * The PercentageOfAllocation class represents the percentage of allocation of a
 * resource in a project in the domain model.
 * It is a value object that implements the ValueObject interface.
 */
public class PercentageOfAllocation implements ValueObject {

    private static final Integer MAXIMUM_PERCENTAGE_OF_ALLOCATION = 100;
    private static final Integer MINIMUM_PERCENTAGE_OF_ALLOCATION = 0;
    private final Integer allocationPercentage;

    /**
     * Instantiates a new PercentageOfAllocation.
     *
     * @param allocationPercentage as an Integer between 0 and 100
     * @throws IllegalArgumentException if the parameter is bellow 0 or above 100
     */
    public PercentageOfAllocation(Integer allocationPercentage) {
        if (allocationPercentage < MINIMUM_PERCENTAGE_OF_ALLOCATION
                || allocationPercentage > MAXIMUM_PERCENTAGE_OF_ALLOCATION) {
            throw new InvalidConstructorArgumentException("Invalid percentage of allocation.");
        }
        this.allocationPercentage = allocationPercentage;
    }

    public PercentageOfAllocation(String allocationPercentage) {
        try {
            this.allocationPercentage = Integer.parseInt(allocationPercentage);
        } catch ( RuntimeException e) {
            throw new InvalidConstructorArgumentException("Invalid percentage of allocation.");
        }
    }

    /**
     * Gets percentage of allocation of current Resource as an Integer.
     *
     * @return percentageOfAllocation
     */
    public Integer getPercentageValue() {
        return this.allocationPercentage;
    }

    /**
     * Checks if this PercentageOfAllocation is equal to the given object.
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
        PercentageOfAllocation that = (PercentageOfAllocation) o;
        return this.allocationPercentage.equals(that.allocationPercentage);
    }

    /**
     * Generates the hash code value.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(allocationPercentage);
    }

}
