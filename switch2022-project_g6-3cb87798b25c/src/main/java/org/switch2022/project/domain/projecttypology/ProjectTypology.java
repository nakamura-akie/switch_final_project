package org.switch2022.project.domain.projecttypology;

import org.switch2022.project.ddd.AggregateRoot;
import org.switch2022.project.domain.valueobject.ProjectTypologyName;

import java.util.Objects;

/**
 * The ProjectTypology class represents a project typology in the domain model.
 * It implements the AggregateRoot interface with the identity of the project typology being the project typology name.
 */
public class ProjectTypology implements AggregateRoot<ProjectTypologyName> {

    private final ProjectTypologyName projectTypologyName;

    /**
     * Instantiates a new Project typology.
     *
     * @param projectTypologyName the project typology name
     * @throws IllegalArgumentException if the projectTypologyName parameter is null
     */
    protected ProjectTypology(ProjectTypologyName projectTypologyName) {

        if (projectTypologyName == null) {
            throw new IllegalArgumentException("Invalid Project Typology AccountName.");
        }
        this.projectTypologyName = projectTypologyName;
    }

    /**
     * Returns the identity of the Project Typology.
     *
     * @return the project typology name
     */
    @Override
    public ProjectTypologyName identity() {
        return this.projectTypologyName;
    }

    /**
     * Gets the project typology name.
     *
     * @return the project typology name
     */
    public String getProjectTypologyName() {
        return projectTypologyName.getProjectTypologyNameValue();
    }

    /**
     * Checks if this project typology is the same as the given object.
     *
     * @param object the object to compare
     * @return true if the object is the same as this project typology, false otherwise
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof ProjectTypology) {
            ProjectTypology that = (ProjectTypology) object;

            return this.projectTypologyName.equals(that.projectTypologyName);
        }
        return false;
    }

    /**
     * Checks if this project typology is equal to the given object.
     *
     * @param o the object to compare
     * @return true if the object is equal to this project typology, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectTypology projectTypology = (ProjectTypology) o;
        return Objects.equals(projectTypologyName, projectTypology.projectTypologyName);
    }

    /**
     * Generates the hash code value for the project.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(projectTypologyName);
    }

}
