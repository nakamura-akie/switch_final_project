package org.switch2022.project.utils.dto;

import org.switch2022.project.domain.valueobject.SprintDuration;
import org.switch2022.project.domain.valueobject.SprintID;
import org.switch2022.project.domain.valueobject.SprintStatus;

import java.time.LocalDate;
import java.util.Objects;

/**
 * The CreatedSprintDTO class represents a data transfer object that holds information about a sprint.
 * It includes the sprint id, sprint duration, start date, end date and sprint status.
 */
public class CreatedSprintDTO {
    public SprintID sprintID;
    public SprintDuration sprintDuration;
    public LocalDate startDate;
    public LocalDate endDate;
    public SprintStatus sprintStatus;

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

        CreatedSprintDTO that = (CreatedSprintDTO) o;


        if (!Objects.equals(sprintID, that.sprintID)) {
            return false;
        }
        return sprintID.equals(that.sprintID);
    }

    /**
     * Generates the hash code value for the object.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(sprintID);
    }

}
