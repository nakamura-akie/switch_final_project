package org.switch2022.project.domain.resource;

import org.switch2022.project.domain.valueobject.Email;
import org.switch2022.project.domain.valueobject.ProjectRole;
import org.switch2022.project.ddd.AggregateRoot;
import org.switch2022.project.domain.valueobject.CostPerHour;
import org.switch2022.project.domain.valueobject.PercentageOfAllocation;
import org.switch2022.project.domain.valueobject.ResourceID;
import org.switch2022.project.domain.valueobject.TimePeriod;

import java.time.LocalDate;
import java.util.Objects;


/**
 * The Resource class represents a resource in the domain model.
 * It implements the AggregateRoot interface with the identity of the resource being the resource id.
 */
public class Resource implements AggregateRoot<ResourceID> {
    private final ResourceID resourceID;
    private final ProjectRole roleInProject;
    private final PercentageOfAllocation percentageOfAllocation;
    private final CostPerHour costPerHour;

    /**
     * Instantiates a new Resource.
     *
     * @param resourceID             the resource id
     * @param roleInProject          the role in project
     * @param percentageOfAllocation the percentage of allocation
     * @param costPerHour            the cost per hour
     */
    protected Resource(ResourceID resourceID, ProjectRole roleInProject,
                       PercentageOfAllocation percentageOfAllocation, CostPerHour costPerHour) {
        this.resourceID = resourceID;
        this.roleInProject = roleInProject;
        this.percentageOfAllocation = percentageOfAllocation;
        this.costPerHour = costPerHour;
    }

    /**
     * Check if resource is associated with an account.
     *
     * @param email the email
     * @return true if resource is associated with account or false otherwise.
     */
    public boolean hasAccount(Email email) {
        return resourceID.hasEmail(email);
    }

    /**
     * Gets percentage of allocation.
     *
     * @return the percentage of allocation
     */
    public PercentageOfAllocation getPercentageOfAllocation() {
        return this.percentageOfAllocation;
    }

    /**
     * Check if period of a new resource is overlapping other resource.
     *
     * @param newResourcePeriodOfAllocation the new resource period of allocation
     * @return true if period is overlapping or false otherwise.
     */
    public boolean isPeriodOverlapping(TimePeriod newResourcePeriodOfAllocation) {
        LocalDate startDate = newResourcePeriodOfAllocation.getStartDate();
        LocalDate endDate = newResourcePeriodOfAllocation.getEndDate();
        TimePeriod periodOfAllocation = this.resourceID.getPeriodOfAllocation();

        return periodOfAllocation.isOverlap(startDate, endDate);
    }

    /**
     * Check if resource is allocated in a project.
     *
     * @param searchDate the date to verify if the resource is allocated.
     * @return true if resource is allocated or false otherwise.
     */
    public boolean isAllocated(LocalDate searchDate) {
        return resourceID.getPeriodOfAllocation().contains(searchDate);
    }

    /**
     * Gets role in project.
     *
     * @return the role in project
     */
    public ProjectRole getRoleInProject() {
        return roleInProject;
    }

    /**
     * Gets cost per hour.
     *
     * @return the cost per hour.
     */
    public CostPerHour getCostPerHour() {
        return costPerHour;
    }


    /**
     * Returns the unique identifier of this entity.
     * @return resource ID
     */
    @Override
    public ResourceID identity() {
        return this.resourceID;
    }

    /**
     * Checks if this resource is the same as the given object.
     *
     * @param object the object to compare
     * @return true if the object is the same as this resource, false otherwise
     */
    @Override
    public boolean sameAs(Object object) {
        if (object instanceof Resource) {
            Resource that = (Resource) object;

            return this.roleInProject.equals(that.roleInProject)
                    && this.percentageOfAllocation.equals(that.percentageOfAllocation)
                    && this.costPerHour.equals(that.costPerHour);
        }
        return false;
    }

    /**
     * Checks if this resource is equal to the given object.
     *
     * @param o the object to compare
     * @return true if the object is equal to this resource, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Resource that = (Resource) o;
        return this.resourceID.equals(that.resourceID);
    }

    /**
     * Generates the hash code value for the resource.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(resourceID);
    }

    /**
     * Verifies if the given date is within the period of allocation of the resource.
     *
     * @param date the date to be verified
     * @return true if the date is within the period of allocation, false otherwise
     */
    public boolean resourceVerificationOfDate(LocalDate date) {
        return resourceID.getPeriodOfAllocation().contains(date);
    }

}
