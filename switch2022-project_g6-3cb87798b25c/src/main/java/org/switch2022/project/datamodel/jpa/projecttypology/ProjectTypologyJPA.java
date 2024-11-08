package org.switch2022.project.datamodel.jpa.projecttypology;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "project_typologies")
public class ProjectTypologyJPA {
    @Id
    private String projectTypologyName;

    protected ProjectTypologyJPA() {
    }

    /**
     * Constructs a new ProjectTypologyJPA object with the specified project typology name.
     *
     * @param projectTypologyName the name of the project typology
     */
    public ProjectTypologyJPA(String projectTypologyName) {
        this.projectTypologyName = projectTypologyName;
    }

    /**
     * Returns the name of the project typology.
     *
     * @return the name of the project typology
     */
    public String getProjectTypologyName() {
        return this.projectTypologyName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectTypologyJPA that = (ProjectTypologyJPA) o;
        return this.projectTypologyName.equals(that.projectTypologyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectTypologyName);
    }
}
