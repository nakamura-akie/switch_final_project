package org.switch2022.project.datamodel.jpa.project;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
@Table(name = "projects")
public class ProjectJPA {

    @Id
    private String projectCode;
    private String projectName;
    private String description;
    private String customerID;
    private String businessSectorName;
    private String projectTypologyName;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="projectCode", referencedColumnName="projectCode")
    private ProductBacklogJPA productBacklog;
    private String projectStatus;
    private Integer sprintDuration;
    private Integer numberOfPlannedSprints;
    private Double budget;
    private String startDate;
    private String endDate;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StatusHistoryJPA> statusHistory;

    protected ProjectJPA() {
    }

    /**
     * Constructs a new ProjectJPA object with the specified parameters.
     *
     * @param projectCode           the project code
     * @param projectName           the project name
     * @param description           the project description
     * @param customerID            the customer's tax identification number
     * @param businessSectorName    the business sector name
     * @param projectTypologyName   the project typology name
     * @param productBacklog        the product backlog
     * @param projectStatus         the project status
     * @param statusHistory         the list of status history
     * @param sprintDuration        the sprint duration (optional)
     * @param numberOfPlannedSprints the number of planned sprints (optional)
     * @param budget                the project budget (optional)
     * @param startDate             the project start date (optional)
     * @param endDate               the project end date (optional)
     */
    public ProjectJPA(
            String projectCode,
            String projectName,
            String description,
            String customerID,
            String businessSectorName,
            String projectTypologyName,
            ProductBacklogJPA productBacklog,
            String projectStatus,
            List<StatusHistoryJPA> statusHistory,
            Integer sprintDuration,
            Integer numberOfPlannedSprints,
            Double budget,
            String startDate,
            String endDate
    ) {

        this.projectCode = projectCode;
        this.projectName = projectName;
        this.description = description;
        this.customerID = customerID;
        this.businessSectorName = businessSectorName;
        this.projectTypologyName = projectTypologyName;
        this.productBacklog = productBacklog;
        this.projectStatus = projectStatus;
        this.statusHistory = List.copyOf(statusHistory);
        this.sprintDuration = sprintDuration;
        this.numberOfPlannedSprints = numberOfPlannedSprints;
        this.budget = budget;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Returns the project code.
     *
     * @return the project code
     */
    public String getProjectCodeValue() {
        return projectCode;
    }

    /**
     * Returns the project name.
     *
     * @return the project name
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * Returns the project description.
     *
     * @return the project description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the customer tax number.
     *
     * @return the customer tax number
     */
    public String getCustomerID() {
        return customerID;
    }

    /**
     * Returns the project typology name.
     *
     * @return the project typology name
     */
    public String getProjectTypologyName() {
        return projectTypologyName;
    }

    /**
     * Returns the business sector name.
     *
     * @return the business sector name
     */
    public String getBusinessSectorName() {
        return businessSectorName;
    }

    /**
     * Returns the product backlog associated with the project.
     *
     * @return the product backlog
     */
    public ProductBacklogJPA getProductBacklog() {
        return productBacklog;
    }

    /**
     * Returns the project status.
     *
     * @return the project status
     */
    public String getProjectStatus() {
        return projectStatus;
    }

    /**
     * Returns the list of status history for the project.
     *
     * @return the list of status history
     */
    public List<StatusHistoryJPA> getStatusHistory() {
        return List.copyOf(statusHistory);
    }

    /**
     * Returns the sprint duration of the project.
     *
     * @return an Optional containing the sprint duration if available, or empty otherwise
     */
    public Optional<Integer> getSprintDuration() {
        return Optional.ofNullable(sprintDuration);
    }

    /**
     * Returns the number of planned sprints for the project.
     *
     * @return an Optional containing the number of planned sprints if available, or empty otherwise
     */
    public Optional<Integer> getNumberOfPlannedSprints() {
        return Optional.ofNullable(numberOfPlannedSprints);
    }

    /**
     * Returns the budget of the project.
     *
     * @return an Optional containing the project budget if available, or empty otherwise
     */
    public Optional<Double> getBudget() {
        return Optional.ofNullable(budget);
    }

    /**
     * Returns the start date of the project.
     *
     * @return an Optional containing the project start date if available, or empty otherwise
     */
    public Optional<String> getStartDate() {
        return Optional.ofNullable(startDate);
    }

    /**
     * Returns the end date of the project.
     *
     * @return an Optional containing the project end date if available, or empty otherwise
     */
    public Optional<String> getEndDate() {
        return Optional.ofNullable(endDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProjectJPA that = (ProjectJPA) o;
        return Objects.equals(projectCode, that.projectCode) &&
                Objects.equals(projectName, that.projectName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(customerID, that.customerID) &&
                Objects.equals(businessSectorName, that.businessSectorName) &&
                Objects.equals(projectTypologyName, that.projectTypologyName) &&
                Objects.equals(productBacklog, that.productBacklog) &&
                Objects.equals(projectStatus, that.projectStatus) &&
                Objects.equals(statusHistory, that.statusHistory) &&
                Objects.equals(sprintDuration, that.sprintDuration) &&
                Objects.equals(numberOfPlannedSprints, that.numberOfPlannedSprints) &&
                Objects.equals(budget, that.budget) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

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
                projectStatus,
                statusHistory,
                sprintDuration,
                numberOfPlannedSprints,
                budget,
                startDate,
                endDate
        );
    }
}
