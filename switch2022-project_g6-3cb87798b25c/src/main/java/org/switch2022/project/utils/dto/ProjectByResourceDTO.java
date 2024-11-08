package org.switch2022.project.utils.dto;
import java.util.Objects;

/**
 * The ProjectByResource class represents the Data Transfer Object (DTO) with the project information.
 * It contains the project code, project name and project description.
 */
public class ProjectByResourceDTO {
    public String projectCode;
    public String projectName;
    public String projectDescription;

    /**
     * Checks whether this ProjectByResourceDTO is equal to another object.
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
        ProjectByResourceDTO that = (ProjectByResourceDTO) o;
        return this.projectCode.equals(that.projectCode) && this.projectName.equals(that.projectName)
                && this.projectDescription.equals(that.projectDescription);
    }

    /**
     * Generates a hash code for this ProjectByResourceDTO.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(projectCode, projectName, projectDescription);
    }
}
