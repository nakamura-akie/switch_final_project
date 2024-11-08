package org.switch2022.project.utils.dto;

import org.switch2022.project.domain.valueobject.Description;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.UserStoryStatus;
import org.switch2022.project.domain.valueobject.UserStoryCode;

import java.util.Objects;

/**
 * The UserStoryDTO class represents a Data Transfer Object (DTO) with the user story information.
 * It contains the user story's project code, user story code, description and status.
 */
public class UserStoryDTO {
    public ProjectCode projectCode;
    public UserStoryCode userStoryCode;
    public Description description;
    public UserStoryStatus status;

    /**
     * Checks whether this UserStoryDTO is equal to another object.
     *
     * @param o the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        UserStoryDTO that = (UserStoryDTO) o;
        return projectCode.equals(that.projectCode) && userStoryCode.equals(that.userStoryCode) &&
                description.equals(that.description) && status.equals(that.status);
    }

    /**
     * Generates a hash code for this UserStoryDTO.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(projectCode, userStoryCode, description, status);
    }
}