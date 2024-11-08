package org.switch2022.project.utils.dto;

import org.switch2022.project.domain.valueobject.SprintID;
import org.switch2022.project.domain.valueobject.SprintStatus;

import java.util.Objects;

/**
 * The SprintStatusDTO class represents the data transfer object (DTO) with some of the sprint's information.
 * It contains the sprintID and sprint status.
 */
public class SprintStatusDTO {
    public SprintID sprintID;
    public SprintStatus sprintStatus;

    /**
     * Checks whether this SprintStatusDTO is equal to another object.
     *
     * @param o the object to compare with
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
        SprintStatusDTO that = (SprintStatusDTO) o;

        return sprintID.equals(that.sprintID);
    }

    /**
     * Generates a hash code for this SprintStatusDTO.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(sprintID);
    }
}
