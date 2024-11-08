package org.switch2022.project.utils.dto;

import org.switch2022.project.domain.valueobject.ProjectCode;

import java.time.LocalDate;
import java.util.Objects;


/**
 * The NewSprintDTO class represents a data transfer object that holds information about a sprint.
 * It includes the project code and the start date.
 */
public class NewSprintDTO {

    public ProjectCode projectCode;
    public LocalDate startDate;

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

        NewSprintDTO that = (NewSprintDTO) o;

        if (!Objects.equals(projectCode, that.projectCode)) {
            return false;
        }
        return Objects.equals(startDate, that.startDate);
    }

    /**
     * Generates the hash code value for the object.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        int result = projectCode != null ? projectCode.hashCode() : 0;
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        return result;
    }

}
