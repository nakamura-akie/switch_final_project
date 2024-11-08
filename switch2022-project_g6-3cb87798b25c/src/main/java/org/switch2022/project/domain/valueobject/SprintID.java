package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.DomainID;

import java.io.Serializable;
import java.util.Objects;

/**
 * The SprintID class represents the sprint id of a sprint in the domain model.
 * It is a value object that implements the DomainID and Serializable interfaces.
 */
public class SprintID implements Serializable, DomainID {

    private final ProjectCode projectCode;
    private final SprintCode sprintCode;

    /**
     * Instantiates a new SprintID with the specified project code and sprint code.
     *
     * @param projectCode the project code
     * @param sprintCode  the sprint code
     */
    public SprintID(ProjectCode projectCode, SprintCode sprintCode) {
        this.projectCode = projectCode;
        this.sprintCode = sprintCode;
    }

    /**
     * Checks if this SprintID is equal to the given object.
     *
     * @param o the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if ((this.getClass() != o.getClass())) {
            return false;
        }
        SprintID sprintID = (SprintID) o;
        return Objects.equals(projectCode, sprintID.projectCode) && Objects.equals(sprintCode, sprintID.sprintCode);
    }

    /**
     * Generates the hash code value.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(projectCode, sprintCode);
    }

    /**
     * Checks if this SprintID has the same project code as the given ProjectCode object.
     *
     * @param projectCode the project code to compare
     * @return true if the project codes are equal, false otherwise
     */
    public boolean hasProjectCode(ProjectCode projectCode) {
        return this.projectCode.equals(projectCode);
    }

    /**
     * Gets the project code associated with this SprintID.
     *
     * @return the project code
     */
    public ProjectCode getProjectCode() {
        return projectCode;
    }

    /**
     * Gets the sprint code associated with this SprintID.
     *
     * @return the sprint code
     */
    public SprintCode getSprintCode() {
        return sprintCode;
    }

}


