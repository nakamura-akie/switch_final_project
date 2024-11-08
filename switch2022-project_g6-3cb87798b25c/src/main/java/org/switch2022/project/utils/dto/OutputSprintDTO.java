package org.switch2022.project.utils.dto;

import java.util.List;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) for representing output sprint information.
 * It containes the sprint project code, sprint code, start date, end date, sprint duration,
 * sprint backlog and sprint status.
 */
public class OutputSprintDTO {

    public String projectCode;
    public String sprintCode;
    public String startDate;
    public String endDate;
    public Integer sprintDuration;
    public List<String> sprintBacklog;
    public String sprintStatus;

    /**
     * Checks whether this OutputSprintDTO is equal to another object.
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
        OutputSprintDTO that = (OutputSprintDTO) o;
        return Objects.equals(projectCode, that.projectCode)
                && Objects.equals(sprintCode, that.sprintCode);
    }

    /**
     * Generates a hash code for this OutputSprintDTO.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects
                .hash(projectCode,
                        sprintCode);
    }

}
