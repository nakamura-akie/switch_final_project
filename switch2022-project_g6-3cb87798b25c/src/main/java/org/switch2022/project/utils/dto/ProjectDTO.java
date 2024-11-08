package org.switch2022.project.utils.dto;

import java.util.Objects;

/**
 * The ProjectDTO class represents the data transfer object (DTO) with the project information.
 * It contains the project code, project name, customer and status.
 */
public class ProjectDTO {

    public String projectCode;
    public String projectName;
    public String projectCustomer;
    public String projectStatus;

    /**
     * Checks whether this ProjectDTO is equal to another object.
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
        ProjectDTO that = (ProjectDTO) o;
        return projectCode.equals(that.projectCode);
    }

    /**
     * Generates a hash code for this ProjectDTO.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(projectCode);
    }

    /**
     * Returns the string representation of the ProjectDTO object.
     *
     * @return the string representation of the object
     */
    @Override
    public String toString() {
        return "ProjectDTO{" +
                "projectCode='" + projectCode + '\'' +
                ", projectName='" + projectName + '\'' +
                ", projectCustomer='" + projectCustomer + '\'' +
                ", projectStatus='" + projectStatus + '\'' +
                '}';
    }
}
