package org.switch2022.project.domain.valueobject;

import org.switch2022.project.ddd.DomainID;

import java.io.Serializable;
import java.util.Objects;

/**
 * The ResourceID class represents the resource id of a resource in the domain model.
 * It is a value object that implements the DomainID and Serializable interfaces.
 */
public class ResourceID implements DomainID, Serializable {
    private final Email resourceEmail;
    private final ProjectCode projectCode;
    private final TimePeriod periodOfAllocation;

    /**
     * Instantiates a new ResourceID.
     *
     * @param resourceEmail      which cannot be null
     * @param projectCode        which cannot be null
     * @param periodOfAllocation which cannot be null
     * @throws IllegalArgumentException if any one of the parameters is null
     */
    public ResourceID(Email resourceEmail, ProjectCode projectCode, TimePeriod periodOfAllocation) {
        if (resourceEmail == null) {
            throw new IllegalArgumentException("Resource Email cannot be null");
        }

        if (projectCode == null) {
            throw new IllegalArgumentException("Project code cannot be null");
        }

        if (periodOfAllocation == null) {
            throw new IllegalArgumentException("Period of allocation cannot be null");
        }
        this.resourceEmail = resourceEmail;
        this.projectCode = projectCode;
        this.periodOfAllocation = periodOfAllocation;
    }

    /**
     * Gets Period of allocation of current Resource.
     *
     * @return PeriodOfAllocation
     */
    public TimePeriod getPeriodOfAllocation() {
        return this.periodOfAllocation;
    }

    /**
     * Gets email of Resource.
     *
     * @return Email
     */
    public Email getResourceEmail() {
        return this.resourceEmail;
    }

    /**
     * Gets project code of Resource.
     *
     * @return ProjectCode
     */
    public ProjectCode getProjectCode() {
        return this.projectCode;
    }

    /**
     * Checks if Resource has a specific email.
     *
     * @param email
     * @return boolean
     */
    public boolean hasEmail(Email email) {
        return this.resourceEmail.equals(email);
    }

    /**
     * Checks if this ResourceID is equal to the given object.
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
        ResourceID that = (ResourceID) o;
        return this.resourceEmail.equals(that.resourceEmail) && this.projectCode.equals(that.projectCode) &&
                this.periodOfAllocation.equals(that.periodOfAllocation);
    }

    /**
     * Generates the hash code value.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(resourceEmail, projectCode, periodOfAllocation);
    }

    /**
     * Converts the Resource ID into a String.
     *
     * @return a string with the email, projectCode, startDate and endDate
     * information
     */
    @Override
    public String toString() {
        return "ResourceID{" +
                "resourceEmail=" + resourceEmail.getEmailValue() +
                ", projectCode=" + projectCode.getProjectCodeValue() +
                ", periodOfAllocation=" + periodOfAllocation.getStartDate().toString() + " - " +
                periodOfAllocation.getEndDate().toString() +
                '}';
    }
}
