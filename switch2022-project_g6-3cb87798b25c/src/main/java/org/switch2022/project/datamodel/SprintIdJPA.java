package org.switch2022.project.datamodel;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SprintIdJPA implements Serializable {

    private String sprintCode;
    private String projectCode;

    /**
     * Constructs a new SprintIdJPA object with the specified project code and sprint code.
     *
     * @param projectCode the project code
     * @param sprintCode the sprint code
     */
    public SprintIdJPA(String projectCode, String sprintCode) {
        this.projectCode = projectCode;
        this.sprintCode = sprintCode;
    }

    /**
     * Constructs a new SprintIdJPA object with default values.
     * This no-argument constructor is necessary so that JPA is able to
     * create an instance of SprintIdJPA through reflection.
     */
    public SprintIdJPA() {
    }

    /**
     * Returns the project code value.
     *
     * @return the project code value
     */
    public String getProjectCodeValue() {
        return projectCode;
    }

    /**
     * Returns the sprint code.
     *
     * @return the sprint code
     */
    public String getSprintCode() {
        return sprintCode;
    }

    /**
     * Sets the sprint code.
     *
     * @param sprintCode the new sprint code
     */
    public void setSprintCode(String sprintCode) {
        this.sprintCode = sprintCode;
    }

    /**
     * Sets the project code.
     *
     * @param projectCode the new project code
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SprintIdJPA sprintIdJPA = (SprintIdJPA) o;
        return Objects.equals(projectCode, sprintIdJPA.projectCode) &&
                Objects.equals(sprintCode, sprintIdJPA.sprintCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectCode, sprintCode);
    }
}
