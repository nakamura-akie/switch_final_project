package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.ValueObject;
import org.switch2022.project.utils.exception.EmptyConstructorArgumentException;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import java.util.Objects;

/**
 * The ProjectName class represents the name of a project in the domain model.
 * It is a value object that implements the ValueObject interface.
 */
public class ProjectName implements ValueObject {

    private final String projectNameValue;

    /**
     * Instantiates a new project name with the specified value.
     *
     * @param projectNameValue the value of the new project name
     * @throws NullConstructorArgumentException if the project name is null
     * @throws EmptyConstructorArgumentException if the project name is empty
     */
    public ProjectName(String projectNameValue) {
        validateValue(projectNameValue);
        this.projectNameValue = projectNameValue;
    }

    private static void validateValue(String projectNameValue) {
        if (projectNameValue == null) {
            throw new NullConstructorArgumentException("Project Name cannot be null");
        }
        if (projectNameValue.isBlank()) {
            throw new EmptyConstructorArgumentException("Project Name cannot be empty");
        }
    }

    /**
     * Gets the project name value.
     *
     * @return the project name value
     */
    public String getProjectNameValue() {
        return projectNameValue;
    }

    /**
     * Checks if this ProjectName is equal to the given object.
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
        ProjectName that = (ProjectName) o;
        return Objects.equals(projectNameValue, that.projectNameValue);
    }

    /**
     * Generates the hash code value for the account status.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(projectNameValue);
    }
}
