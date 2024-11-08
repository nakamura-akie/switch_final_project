package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.DomainID;
import org.switch2022.project.ddd.ValueObject;
import org.switch2022.project.utils.exception.EmptyConstructorArgumentException;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import java.util.Objects;

/**
 * The ProjectTypologyName class represents the name of a project typolofy in the domain model.
 * It is a value object that implements the ValueObject interface.
 */
public class ProjectTypologyName implements DomainID {

    private final String projectTypologyNameValue;

    /**
     * Instantiates a new project typology name with the specified value.
     *
     * @param projectTypologyNameValue the value of the new project typology name
     * @throws NullConstructorArgumentException if the project typology name is null
     * @throws EmptyConstructorArgumentException if the project typology name is empty
     */
    public ProjectTypologyName(String projectTypologyNameValue) {
        validateValue(projectTypologyNameValue);
        this.projectTypologyNameValue = projectTypologyNameValue;
    }

    private static void validateValue(String projectTypologyNameValue) {
        if (projectTypologyNameValue == null) {
            throw new NullConstructorArgumentException("Project Typology cannot be null");
        }
        if (projectTypologyNameValue.isBlank()) {
            throw new EmptyConstructorArgumentException("Project Typology cannot be empty");
        }
    }

    /**
     * Gets the project typology name value.
     *
     * @return the project typology name value
     */
    public String getProjectTypologyNameValue() {
        return projectTypologyNameValue;
    }

    /**
     * Checks if this ProjectTypology is equal to the given object.
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
        ProjectTypologyName that = (ProjectTypologyName) o;
        return projectTypologyNameValue.equals(that.projectTypologyNameValue);
    }

    /**
     * Generates the hash code value.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(projectTypologyNameValue);
    }
}
