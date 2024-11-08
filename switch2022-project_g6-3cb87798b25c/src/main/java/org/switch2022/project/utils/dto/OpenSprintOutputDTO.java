package org.switch2022.project.utils.dto;

import java.util.Objects;


/**
 * The OpenSprintDTO class represents a data transfer object that holds information about a sprint.
 * It includes the sprint code, the start date, the end date and the sprint status.
 */
public class OpenSprintOutputDTO {
    public String sprintCode;
    public String startDate;
    public String endDate;
    public String sprintStatus;

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
        OpenSprintOutputDTO that = (OpenSprintOutputDTO) o;
        return Objects.equals(sprintCode, that.sprintCode) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(sprintStatus, that.sprintStatus);
    }

    /**
     * Generates the hash code value for the object.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(sprintCode, startDate, endDate, sprintStatus);
    }
}
