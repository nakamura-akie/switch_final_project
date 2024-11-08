package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.DomainID;
import org.switch2022.project.utils.exception.EmptyConstructorArgumentException;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import java.io.Serializable;
import java.util.Objects;

/**
 * The ProjectCode class represents the project code of a project in the domain model.
 * It is a value object that implements the DomainID and Serializable interfaces.
 */
public class ProjectCode implements DomainID, Serializable {

    private String projectCodeValue;

    /**
     * Instantiates an empty project code.
     */
    public ProjectCode() {}

    /**
     * Instantiates a new project code with the specified value.
     *
     * @param projectCodeValue the value of the new project code
     * @throws NullConstructorArgumentException if the project code is null
     * @throws EmptyConstructorArgumentException if the project code is empty
     */
    public ProjectCode(String projectCodeValue) {
        validateValue(projectCodeValue);
        this.projectCodeValue = projectCodeValue;
    }

    private static void validateValue(String projectCodeValue) {
        if (projectCodeValue == null) {
            throw new NullConstructorArgumentException("Project Code cannot be null");
        }
        if (projectCodeValue.isBlank()) {
            throw new EmptyConstructorArgumentException("Project Code cannot be empty");
        }
    }

    /**
     * Gets the project code value.
     *
     * @return the project code value
     */
    public String getProjectCodeValue() {
        return projectCodeValue;
    }

    /**
     * Returns the string representation of the project code.
     *
     * @return the project code as a string
     */
    @Override
    public String toString() {
        return projectCodeValue;
    }

    /**
     * Checks if this ProjectCode is equal to the given object.
     *
     * @param o the object to compare
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
        ProjectCode that = (ProjectCode) o;
        return Objects.equals(projectCodeValue, that.projectCodeValue);
    }

    /**
     * Generates the hash code value.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(projectCodeValue);
    }
}
