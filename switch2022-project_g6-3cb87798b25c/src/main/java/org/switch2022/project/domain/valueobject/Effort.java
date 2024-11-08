package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.ValueObject;

/**
 * The Effort enum represents the effort of a user story in the domain model.
 * It is a value object that implements the ValueObject interface.
 */
public enum Effort implements ValueObject {
    ONE(1),
    TWO(2),
    THREE(3),
    FIVE(5),
    EIGHT(8),
    THIRTEEN(13),
    TWENTY(20),
    FORTY(40);

    private final int value;

    /**
     * Constructs an Effort enum constant with the specified value.
     *
     * @param value the effort value
     */
    Effort(int value) {
        this.value = value;
    }

    /**
     * Gets the value of the effort.
     *
     * @return the effort value
     */
    public int getValue() {
        return value;
    }

}
