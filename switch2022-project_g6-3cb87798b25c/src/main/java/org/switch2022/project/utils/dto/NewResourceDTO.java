package org.switch2022.project.utils.dto;

import org.switch2022.project.domain.valueobject.*;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) for creating a new resource.
 */
public class NewResourceDTO {
    private Email email;
    private ProjectCode projectCode;
    private LocalDate startDate;
    private LocalDate endDate;
    private ProjectRole projectRole;
    private PercentageOfAllocation percentageOfAllocation;
    private CostPerHour costPerHour;

    /**
     * Constructs a new NewResourceDTO object with the provided parameters.
     *
     * @param email                  The email of the resource.
     * @param projectCode            The project code associated with the resource.
     * @param startDate              The start date of the resource's allocation to the project
     *                               (in the format "yyyy-MM-dd").
     * @param endDate                The end date of the resource's allocation to the project
     *                               (in the format "yyyy-MM-dd").
     * @param projectRole            The role of the resource in the project.
     * @param percentageOfAllocation The percentage of allocation for the resource in the project (as a string).
     * @param costPerHour            The cost per hour for the resource (as a string).
     */
    public NewResourceDTO(String email,
                          String projectCode,
                          String startDate,
                          String endDate,
                          String projectRole,
                          String percentageOfAllocation,
                          String costPerHour) {

        this.email = new Email(email);
        this.projectCode = new ProjectCode(projectCode);
        this.startDate = LocalDate.parse(startDate);
        this.endDate = LocalDate.parse(endDate);
        this.percentageOfAllocation = new PercentageOfAllocation(percentageOfAllocation);
        this.costPerHour = new CostPerHour(costPerHour);
        this.projectRole = ProjectRole.fromString(projectRole);
    }

    /**
     * Returns the email of the resource.
     *
     * @return The email of the resource.
     */
    public Email getEmail() {
        return this.email;
    }

    /**
     * Sets the email of the resource.
     *
     * @param email The email to be set for the resource.
     */
    public void setEmail(Email email) {
        this.email = email;
    }

    /**
     * Returns the project code associated with the resource.
     *
     * @return The project code associated with the resource.
     */
    public ProjectCode getProjectCode() {
        return this.projectCode;
    }

    /**
     * Sets the project code associated with the resource.
     *
     * @param projectCode The project code to be set for the resource.
     */
    public void setProjectCode(ProjectCode projectCode) {
        this.projectCode = projectCode;
    }

    /**
     * Returns the start date of the resource's allocation to the project.
     *
     * @return The start date of the resource's allocation to the project.
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /**
     * Sets the start date of the resource's allocation to the project.
     *
     * @param startDate The start date to be set for the resource's allocation to the project.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns the end date of the resource's allocation to the project.
     *
     * @return The end date of the resource's allocation to the project.
     */
    public LocalDate getEndDate() {
        return this.endDate;
    }

    /**
     * Sets the end date of the resource's allocation to the project.
     *
     * @param endDate The end date to be set for the resource's allocation to the project.
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Returns the role of the resource in the project.
     *
     * @return The role of the resource in the project.
     */
    public ProjectRole getProjectRole() {
        return this.projectRole;
    }

    /**
     * Sets the role of the resource in the project.
     *
     * @param projectRole The role to be set for the resource in the project.
     */
    public void setProjectRole(ProjectRole projectRole) {
        this.projectRole = projectRole;
    }

    /**
     * Returns the percentage of allocation for the resource in the project.
     *
     * @return The percentage of allocation for the resource in the project.
     */
    public PercentageOfAllocation getPercentageOfAllocation() {
        return this.percentageOfAllocation;
    }

    /**
     * Sets the percentage of allocation for the resource in the project.
     *
     * @param percentageOfAllocation The percentage of allocation to be set for the resource in the project.
     */
    public void setPercentageOfAllocation(PercentageOfAllocation percentageOfAllocation) {
        this.percentageOfAllocation = percentageOfAllocation;
    }

    /**
     * Returns the cost per hour for the resource.
     *
     * @return The cost per hour for the resource.
     */
    public CostPerHour getCostPerHour() {
        return this.costPerHour;
    }

    /**
     * Sets the cost per hour for the resource.
     *
     * @param costPerHour The cost per hour to be set for the resource.
     */
    public void setCostPerHour(CostPerHour costPerHour) {
        this.costPerHour = costPerHour;
    }

    /**
     * Checks whether the current object is equal to another object.
     *
     * @param o The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null){
            return  false;
        }
        if (!(o.getClass() == this.getClass())) {
            return false;
        }
        NewResourceDTO that = (NewResourceDTO) o;
        return Objects.equals(email, that.email)
                && Objects.equals(projectCode, that.projectCode)
                && Objects.equals(startDate, that.startDate)
                && Objects.equals(endDate, that.endDate)
                && projectRole == that.projectRole
                && Objects.equals(percentageOfAllocation, that.percentageOfAllocation)
                && Objects.equals(costPerHour, that.costPerHour);
    }

    /**
     * Generates the hash code value for the object.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(email, projectCode, startDate, endDate, projectRole,
                percentageOfAllocation, costPerHour);
    }
}
