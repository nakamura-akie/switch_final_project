package org.switch2022.project.utils.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) for representing output resource information.
 * It contains the resource's email, project code, start date, end date, project role, percentage of
 * allocation and cost per hour.
 */
public class OutputResourceDTO implements Serializable {
    private String email;
    private String projectCode;
    private String startDate;
    private String endDate;
    private String projectRole;
    private Integer percentageOfAllocation;
    private Double costPerHour;

    /**
     * Constructs a new OutputResourceDTO object with the provided parameters.
     *
     * @param email                  The email of the resource.
     * @param projectCode            The project code associated with the resource.
     * @param startDate              The start date of the resource's allocation to the project.
     * @param endDate                The end date of the resource's allocation to the project.
     * @param projectRole            The role of the resource in the project.
     * @param percentageOfAllocation The percentage of allocation for the resource in the project.
     * @param costPerHour            The cost per hour for the resource.
     */
    public OutputResourceDTO(
            String email,
            String projectCode,
            String startDate,
            String endDate,
            String projectRole,
            Integer percentageOfAllocation,
            Double costPerHour) {

        this.email = email;
        this.projectCode = projectCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.projectRole = projectRole;
        this.percentageOfAllocation = percentageOfAllocation;
        this.costPerHour = costPerHour;
    }

    /**
     * Returns the email of the resource.
     *
     * @return The email of the resource.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the email of the resource.
     *
     * @param email The email to be set for the resource.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the project code associated with the resource.
     *
     * @return The project code associated with the resource.
     */
    public String getProjectCode() {
        return this.projectCode;
    }

    /**
     * Sets the project code associated with the resource.
     *
     * @param projectCode The project code to be set for the resource.
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    /**
     * Returns the start date of the resource's allocation to the project.
     *
     * @return The start date of the resource's allocation to the project.
     */
    public String getStartDate() {
        return this.startDate;
    }

    /**
     * Sets the start date of the resource's allocation to the project.
     *
     * @param startDate The start date to be set for the resource's allocation to the project.
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns the end date of the resource's allocation to the project.
     *
     * @return The end date of the resource's allocation to the project.
     */
    public String getEndDate() {
        return this.endDate;
    }

    /**
     * Sets the end date of the resource's allocation to the project.
     *
     * @param endDate The end date to be set for the resource's allocation to the project.
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * Returns the role of the resource in the project.
     *
     * @return The role of the resource in the project.
     */
    public String getProjectRole() {
        return this.projectRole;
    }

    /**
     * Sets the role of the resource in the project.
     *
     * @param projectRole The role to be set for the resource in the project.
     */
    public void setProjectRole(String projectRole) {
        this.projectRole = projectRole;
    }

    /**
     * Returns the percentage of allocation for the resource in the project.
     *
     * @return The percentage of allocation for the resource in the project.
     */
    public Integer getPercentageOfAllocation() {
        return this.percentageOfAllocation;
    }

    /**
     * Sets the percentage of allocation for the resource in the project.
     *
     * @param percentageOfAllocation The percentage of allocation to be set for the resource in the project.
     */
    public void setPercentageOfAllocation(Integer percentageOfAllocation) {
        this.percentageOfAllocation = percentageOfAllocation;
    }

    /**
     * Returns the cost per hour for the resource.
     *
     * @return The cost per hour for the resource.
     */
    public Double getCostPerHour() {
        return this.costPerHour;
    }

    /**
     * Sets the cost per hour for the resource.
     *
     * @param costPerHour The cost per hour to be set for the resource.
     */
    public void setCostPerHour(Double costPerHour) {
        this.costPerHour = costPerHour;
    }

    /**
     * Checks whether this OutputResourceDTO is equal to another object.
     *
     * @param o the object to compare with
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
        OutputResourceDTO that = (OutputResourceDTO) o;
        return Objects.equals(email, that.email)
                && Objects.equals(projectCode, that.projectCode)
                && Objects.equals(startDate, that.startDate)
                && Objects.equals(endDate, that.endDate)
                && Objects.equals(projectRole, that.projectRole)
                && Objects.equals(percentageOfAllocation, that.percentageOfAllocation)
                && Objects.equals(costPerHour, that.costPerHour);
    }

    /**
     * Generates a hash code for this OutputResourceDTO.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                email,
                projectCode,
                startDate,
                endDate,
                projectRole,
                percentageOfAllocation,
                costPerHour);
    }
}
