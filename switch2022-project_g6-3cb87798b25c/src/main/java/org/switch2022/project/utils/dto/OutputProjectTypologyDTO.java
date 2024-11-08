package org.switch2022.project.utils.dto;

import java.util.Objects;

/**
 * The OutputProjectTypologyDTO class represents the data transfer object (DTO) with the project typology information.
 * It contains the project typology value.
 */
public class OutputProjectTypologyDTO {
    public String projectTypologyValue;

    /**
     * Checks if this OutputProjectTypologyDTO is equal to another object.
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
        OutputProjectTypologyDTO that = (OutputProjectTypologyDTO) o;
        return this.projectTypologyValue.equals(that.projectTypologyValue);
    }

    /**
     * Generates the hash code value for the OutputProjectTypologyDTO object.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(projectTypologyValue);
    }
}
