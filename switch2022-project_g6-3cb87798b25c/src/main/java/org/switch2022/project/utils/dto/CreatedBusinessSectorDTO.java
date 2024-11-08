package org.switch2022.project.utils.dto;

import java.util.Objects;

/**
 * The CreatedBusinessSectorDTO class represents a data transfer object that holds information about a business sector.
 * It includes the business sector name.
 */
public class CreatedBusinessSectorDTO {

    public String businessSectorNameValue;

    /**
     * Checks whether the current object is equal to another object.
     *
     * @param o The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CreatedBusinessSectorDTO that = (CreatedBusinessSectorDTO) o;
        return this.businessSectorNameValue.equals(that.businessSectorNameValue);
    }

    /**
     * Generates the hash code value for the object.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(businessSectorNameValue);
    }
}
