package org.switch2022.project.utils.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Represents an output project DTO (Data Transfer Object) containing project information.
 * It includes the project code, project name, project description, customer ID, business sector name,
 * project typology name, product backlog, project status, project status history, sprint duration,
 * number of planned sprints, budget, start date and end date.
 */
public class OutputProjectDTO {
    private String projectCode;
    private String projectName;
    private String description;
    private String customerID;
    private String businessSectorName;
    private String projectTypologyName;
    private List<String> productBacklog;
    private String status;
    private Map<String, String> statusHistory;
    private Integer sprintDuration;
    private Integer numberPlannedSprints;
    private Double budget;
    private LocalDate startDate;
    private LocalDate endDate;

    /**
     * Instantiates an OutputProjectDTO.
     *
     * @param projectCode          the project code
     * @param projectName          the project name
     * @param description          the description
     * @param customerID           the customerID
     * @param businessSectorName   the business sector name
     * @param projectTypologyName  the project typology name
     * @param productBacklog       the project typology
     * @param status               the project status
     * @param statusHistory        the status history
     * @param sprintDuration       the sprint duration
     * @param numberPlannedSprints the number of planned sprints
     * @param budget               the budget
     * @param startDate            the start date
     * @param endDate              the end date
     */
    public OutputProjectDTO(
            String projectCode,
            String projectName,
            String description,
            String customerID,
            String businessSectorName,
            String projectTypologyName,
            List<String> productBacklog,
            String status,
            Map<String, String> statusHistory,
            Integer sprintDuration,
            Integer numberPlannedSprints,
            Double budget,
            LocalDate startDate,
            LocalDate endDate
    ) {
        this.projectCode = projectCode;
        this.projectName = projectName;
        this.description = description;
        this.customerID = customerID;
        this.businessSectorName = businessSectorName;
        this.projectTypologyName = projectTypologyName;
        this.productBacklog = productBacklog;
        this.status = status;
        this.statusHistory = statusHistory;
        this.sprintDuration = sprintDuration;
        this.numberPlannedSprints = numberPlannedSprints;
        this.budget = budget;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Gets the project code.
     *
     * @return the project code string
     */
    public String getProjectCode() {
        return this.projectCode;
    }

    /**
     * Sets the project code
     *
     * @param projectCode the project code to set
     */
    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    /**
     * Gets the project name.
     *
     * @return the project name string
     */
    public String getProjectName() {
        return this.projectName;
    }

    /**
     * Sets the project name.
     *
     * @param projectName the project name be set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * Gets description.
     *
     * @return the description string
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets the description.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets customerID.
     *
     * @return the customerID string
     */
    public String getCustomerID() {
        return this.customerID;
    }

    /**
     * Sets the customerID.
     *
     * @param customerID the customer ID to set
     */
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    /**
     * Gets the business sector name.
     *
     * @return the business sector name string
     */
    public String getBusinessSectorName() {
        return this.businessSectorName;
    }

    /**
     * Sets the business sector name.
     *
     * @param businessSectorName the business sector name to set
     */
    public void setBusinessSectorName(String businessSectorName) {
        this.businessSectorName = businessSectorName;
    }

    /**
     * Gets the project typology name.
     *
     * @return the project typology string
     */
    public String getProjectTypologyName() {
        return this.projectTypologyName;
    }

    /**
     * Sets the project typology name.
     *
     * @param projectTypologyName the project typology name to set
     */
    public void setProjectTypologyName(String projectTypologyName) {
        this.projectTypologyName = projectTypologyName;
    }

    /**
     * Gets the product backlog.
     *
     * @return the product backlog, in this case, a list of strings
     */
    public List<String> getProductBacklog() {
        return this.productBacklog;
    }

    /**
     * Sets the product backlog.
     *
     * @param productBacklog the product backlog to be set
     */
    public void setProductBacklog(List<String> productBacklog) {
        this.productBacklog = productBacklog;
    }

    /**
     * Gets the status.
     *
     * @return the status string
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * Sets the status.
     *
     * @param status the status to be set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the status history.
     *
     * @return the status history, in this case a map of strings
     */
    public Map<String, String> getStatusHistory() {
        return this.statusHistory;
    }

    /**
     * Sets the status history.
     *
     * @param statusHistory the status history to be set
     */
    public void setStatusHistory(Map<String, String> statusHistory) {
        this.statusHistory = statusHistory;
    }

    /**
     * Gets the sprint duration.
     *
     * @return the sprint duration Integer
     */
    public Integer getSprintDuration() {
        return this.sprintDuration;
    }

    /**
     * Sets the sprint duration.
     *
     * @param sprintDuration the sprint duration Integer to be set
     */
    public void setSprintDuration(Integer sprintDuration) {
        this.sprintDuration = sprintDuration;
    }

    /**
     * Gets the number of planned sprints.
     *
     * @return the number of planned sprints Integer
     */
    public Integer getNumberPlannedSprints() {
        return this.numberPlannedSprints;
    }

    /**
     * Sets the number of planned sprints.
     *
     * @param numberPlannedSprints the number of planned sprints to be set
     */
    public void setNumberPlannedSprints(Integer numberPlannedSprints) {
        this.numberPlannedSprints = numberPlannedSprints;
    }

    /**
     * Gets the budget.
     *
     * @return the budget double
     */
    public Double getBudget() {
        return this.budget;
    }

    /**
     * Sets the budget.
     *
     * @param budget the budget double to be set
     */
    public void setBudget(Double budget) {
        this.budget = budget;
    }

    /**
     * Gets the start date.
     *
     * @return the start date
     */
    public LocalDate getStartDate() {
        return this.startDate;
    }

    /**
     * Sets the start date.
     *
     * @param startDate the start date to be set
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date.
     *
     * @return the end date
     */
    public LocalDate getEndDate() {
        return this.endDate;
    }

    /**
     * Sets the end date.
     *
     * @param endDate the end date to be set
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Checks if this OutputProjectDTO is equal to another object.
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
        OutputProjectDTO that = (OutputProjectDTO) o;
        return Objects.equals(projectCode, that.projectCode) &&
                Objects.equals(projectName, that.projectName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(customerID, that.customerID) &&
                Objects.equals(businessSectorName, that.businessSectorName) &&
                Objects.equals(projectTypologyName, that.projectTypologyName) &&
                Objects.equals(productBacklog, that.productBacklog) &&
                Objects.equals(status, that.status) &&
                Objects.equals(statusHistory, that.statusHistory) &&
                Objects.equals(sprintDuration, that.sprintDuration) &&
                Objects.equals(numberPlannedSprints, that.numberPlannedSprints) &&
                Objects.equals(budget, that.budget) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    /**
     * Generates the hash code value for the OutputProjectDTO object.
     *
     * @return The hash code value.
     */
    @Override
    public int hashCode() {
        return Objects.hash(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypologyName,
                productBacklog,
                status,
                statusHistory,
                sprintDuration,
                numberPlannedSprints,
                budget,
                startDate,
                endDate
        );
    }
}
