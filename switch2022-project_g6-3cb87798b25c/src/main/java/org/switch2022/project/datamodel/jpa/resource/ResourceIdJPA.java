package org.switch2022.project.datamodel.jpa.resource;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ResourceIdJPA implements Serializable {
    private String email;
    private String projectCode;
    private String startDate;
    private String endDate;

    /**
     * Instantiates a new ResourceID for a JPA Resource Entity
     *
     * @param email       as a String which cannot be null
     * @param projectCode as a String which cannot be null
     * @param startDate   as a String which cannot be null
     * @param endDate     as a String which cannot be null
     * @throws IllegalArgumentException if at least one of the parameters is null
     */
    public ResourceIdJPA(String email, String projectCode, String startDate, String endDate) {
        if (email == null || projectCode == null || startDate == null || endDate == null) {
            throw new IllegalArgumentException("Information cannot be null.");
        }
        this.email = email;
        this.projectCode = projectCode;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Constructor used by JPA through reflection.
     */
    public ResourceIdJPA() {
    }

    /**
     * Gets email of JPA EntityID
     *
     * @return email as String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of JPA EntityID
     *
     * @param email as a String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets project code of JPA EntityID
     *
     * @return project code as a String
     */
    public String getProjectCode() {
        return projectCode;
    }

    /**
     * Set project code of JPA EntityID
     *
     * @param projectCode as a String
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    /**
     * Gets start date of JPA EntityID
     *
     * @return as a String
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of JPA EntityID
     *
     * @param startDate as a String
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets end date of JPA EntityID
     *
     * @return as a String
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * Sets end date of JPA EntityID
     *
     * @param endDate as a String
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResourceIdJPA that = (ResourceIdJPA) o;
        return Objects.equals(email, that.email)
                && Objects.equals(projectCode, that.projectCode)
                && Objects.equals(startDate, that.startDate)
                && Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, projectCode, startDate, endDate);
    }

    /**
     * Converts this JPA Entity ID into a string with its information
     *
     * @return String with email, project code, start date and end date
     */
    @Override
    public String toString() {
        return "ResourceIdJPA{" +
                "email='" + email + '\'' +
                ", projectCode='" + projectCode + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
