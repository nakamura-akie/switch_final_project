package org.switch2022.project.utils.dto;

import java.util.Objects;

/**
 * The OutputUserStoryDTO represents the Data Transfer Object (DTO) with the output user story information.
 * It contains the user story's project code, user story code, description, status and effort.
 */
public class OutputUserStoryDTO {

    public String projectCode;
    public String userStoryCode;
    public String description;
    public String status;
    public Integer userStoryEffort;

    /**
     * Checks whether this OutputUserStoryDTO is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OutputUserStoryDTO)) {
            return false;
        }
        OutputUserStoryDTO that = (OutputUserStoryDTO) o;
        return projectCode.equals(that.projectCode) &&
                userStoryCode.equals(that.userStoryCode) &&
                description.equals(that.description) &&
                status.equals(that.status) &&
                userStoryEffort.equals(that.userStoryEffort);
    }

    /**
     * Generates a hash code for this OutputUserStoryDTO.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(projectCode, userStoryCode, description, status, userStoryEffort);
    }
}
