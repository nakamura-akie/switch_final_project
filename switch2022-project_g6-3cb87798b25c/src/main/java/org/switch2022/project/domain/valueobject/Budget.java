package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.ValueObject;
import org.switch2022.project.utils.exception.InvalidConstructorArgumentException;

import java.util.Objects;

/**
 * The Budget class represents the budget of project in the domain model.
 * It is a value object that implements the ValueObject interface.
 */
public class Budget implements ValueObject {

    private final Double budgetValue;

    /**
     * Constructs a budget object from a string value.
     *
     * @param budgetValue the budget value as a string
     */
    public Budget(String budgetValue) {
        this.budgetValue = Double.parseDouble(budgetValue);
    }

    /**
     * Constructs a budget object from a double value.
     *
     * @param budgetValue the budget value as a double
     * @throws InvalidConstructorArgumentException if the budget value is negative
     */
    public Budget(Double budgetValue) {
        validateValue(budgetValue);
        this.budgetValue = budgetValue;
    }

    private static void validateValue(Double budgetValue) {
        if (budgetValue < 0) {
            throw new InvalidConstructorArgumentException("Budget must be greater than 0");
        }
    }

    /**
     * Returns the budget value.
     *
     * @return the budget value
     */
    public Double getBudgetValue() {
        return budgetValue;
    }

    /**
     * Checks if this Budget is equal to the given object.
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
        Budget that = (Budget) o;
        return this.budgetValue.equals(that.budgetValue);
    }

    /**
     * Generates the hash code value.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(budgetValue);
    }
}
