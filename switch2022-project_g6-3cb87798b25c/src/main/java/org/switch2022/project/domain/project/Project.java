package org.switch2022.project.domain.project;

import org.switch2022.project.ddd.AggregateRoot;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * The Project class represents a project in the domain model.
 * It implements the AggregateRoot interface with the identity of the project being the project code.
 */
public class Project implements AggregateRoot<ProjectCode> {
    private final ProjectCode projectCode;
    private final ProjectName projectName;
    private final Description description;
    private final TaxIdentificationNumber customerID;
    private final BusinessSectorName businessSectorName;
    private final ProjectTypologyName projectTypologyName;
    private final ProductBacklog productBacklog;
    private ProjectStatus projectStatus;
    private StatusHistory statusHistory;
    private SprintDuration sprintDuration;
    private NumberPlannedSprints numberPlannedSprints;
    private Budget budget;
    private TimePeriod period;

    /**
     * Instantiates a new Project.
     *
     * @param projectCode        the project code
     * @param projectName        the project name
     * @param description        the description
     * @param customerID         the customer id
     * @param businessSectorName the business sector name
     * @param projectTypology    the project typology
     * @param productBacklog     the product backlog
     */
    protected Project(ProjectCode projectCode,
                      ProjectName projectName,
                      Description description,
                      TaxIdentificationNumber customerID,
                      BusinessSectorName businessSectorName,
                      ProjectTypologyName projectTypology,
                      ProductBacklog productBacklog,
                      ProjectStatus projectStatus,
                      StatusHistory statusHistory) {

        notNull(projectCode, "Project Code is a required parameter for Project creation");
        notNull(projectName, "Project Name is a required parameter for Project creation");
        notNull(description, "Description is a required parameter for Project creation");
        notNull(customerID, "Customer Tax Identification Number is a required parameter for Project creation");
        notNull(businessSectorName, "Business Sector Name is a required parameter for Project creation");
        notNull(projectTypology, "Project Typology is a required parameter for Project creation");
        notNull(productBacklog, "Product Backlog is a required parameter for Project creation");
        notNull(projectStatus, "Project Status is a required parameter for Project creation");
        notNull(statusHistory, "Status History is a required parameter for Project creation");

        this.projectCode = projectCode;
        this.projectName = projectName;
        this.description = description;
        this.customerID = customerID;
        this.businessSectorName = businessSectorName;
        this.projectTypologyName = projectTypology;
        this.productBacklog = productBacklog;
        this.projectStatus = projectStatus;
        this.statusHistory = statusHistory;
    }

    private static void notNull(Object parameter, String message) {
        if (parameter == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Add user story to product backlog.
     *
     * @param userStoryCode the user story code
     * @return true if account is successfully added to backlog or false otherwise.
     */
    public boolean addUserStoryToProductBacklog(UserStoryID userStoryCode) {
        return this.productBacklog.addUserStory(userStoryCode);
    }

    /**
     * Remove user story to product backlog.
     *
     * @param userStoryID the user story code
     */
    public void removeUserStoryFromProductBacklog(UserStoryID userStoryID) {
        productBacklog.removeUserStory(userStoryID);
    }

    /**
     * Change the project status and save it in the record history.
     *
     * @param status the project status
     */
    public void changeStatus(ProjectStatus status) {
        this.projectStatus = status;
        this.statusHistory.record(status);
    }

    /**
     * Import the project status history.
     * This method is used to import test data.
     *
     * @param statusHistoryMap the project status history
     */
    public void importStatusHistory(Map<ProjectStatus, LocalDate> statusHistoryMap) {
        Map<ProjectStatus, LocalDate> statusHistoryMapCopy = statusHistoryMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        this.statusHistory = new StatusHistory(this.projectCode, statusHistoryMapCopy);
    }

    /**
     * Checks if the received date is before the project start date.
     *
     * @param date the date
     * @return true if the date is before the project start date, or false otherwise.
     */
    public boolean isDateBeforeProjectCreationDate(LocalDate date) {
        LocalDate projectCreationDate = getProjectCreationDate();
        return date.isBefore(projectCreationDate);
    }

    /**
     * Checks if the received date is after the project end date.
     *
     * @param date the date
     * @return true if the date is after the project end date, or false otherwise.
     */
    public boolean isDateAfterProjectEndDate(LocalDate date) {
        if (isPeriodNull()) {
            return false;
        }
        if (this.period.getEndDate() == null) {
            return false;
        }
        return date.isAfter(this.period.getEndDate());
    }

    private boolean isPeriodNull() {
        return this.period == null;
    }

    private LocalDate getProjectCreationDate() {
        return statusHistory
                .getStatusHistoryMap()
                .get(ProjectStatus.PLANNED);
    }

    /**
     * Gets the product backlog user story list.
     *
     * @return the user story id list.
     */
    public List<UserStoryID> getUserStoryPriorityList() {
        return this.productBacklog.getUserStorylist();
    }

    /**
     * Gets project code.
     *
     * @return project code
     */
    public ProjectCode getProjectCode() {
        return projectCode;
    }

    /**
     * Gets project name.
     *
     * @return project name
     */
    public ProjectName getProjectName() {
        return projectName;
    }

    /**
     * Gets project description.
     *
     * @return project description
     */
    public Description getDescription() {
        return description;
    }

    /**
     * Gets customer id.
     *
     * @return customer id
     */
    public TaxIdentificationNumber getCustomerID() {
        return customerID;
    }

    /**
     * Gets project typology name.
     *
     * @return the project typology name
     */
    public ProjectTypologyName getProjectTypologyName() {
        return projectTypologyName;
    }

    /**
     * Gets the business sector name.
     *
     * @return the business sector name
     */
    public BusinessSectorName getBusinessSectorName() {
        return businessSectorName;
    }

    /**
     * Gets the product backlog
     *
     * @return the product backlog
     */
    public ProductBacklog getProductBacklog() {
        return productBacklog;
    }

    /**
     * Gets project status.
     *
     * @return project status
     */
    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    /**
     * Gets the status history.
     *
     * @return the status history
     */
    public StatusHistory getStatusHistory() {
        return statusHistory;
    }

    /**
     * Gets the sprint duration.
     *
     * @return an optional of the sprint duration
     */
    public Optional<SprintDuration> getSprintDuration() {
        return Optional.ofNullable(sprintDuration);
    }

    /**
     * Gets the number of planned sprints.
     *
     * @return an optional of the number of planned sprints
     */
    public Optional<NumberPlannedSprints> getNumberPlannedSprints() {
        return Optional.ofNullable(numberPlannedSprints);
    }

    /**
     * Gets the budget.
     *
     * @return an optional of the budget
     */
    public Optional<Budget> getBudget() {
        return Optional.ofNullable(budget);
    }

    /**
     * Gets the time period.
     *
     * @return an optional of the time period
     */
    public Optional<TimePeriod> getPeriod() {
        return Optional.ofNullable(period);
    }

    /**
     * Sets the sprint duration.
     */
    public void setSprintDuration(SprintDuration duration) {
        this.sprintDuration = duration;
    }

    /**
     * Sets the number of planned sprints.
     */
    public void setNumberPlannedSprints(NumberPlannedSprints numberPlannedSprints) {
        this.numberPlannedSprints = numberPlannedSprints;
    }

    /**
     * Sets the budget.
     */
    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    /**
     * Sets the time period.
     */
    public void setPeriod(TimePeriod period) {
        this.period = period;
    }

    /**
     * Returns the identity of this project, which is the project code.
     *
     * @return the project code
     */
    @Override
    public ProjectCode identity() {
        return this.projectCode;
    }

    /**
     * Checks if this project is the same as the given object.
     *
     * @param object the object to compare
     * @return true if the object is the same as this project, false otherwise
     */
    @Override
    public boolean sameAs(Object object) {
        if (!(object instanceof Project)) {
            return false;
        }

        Project that = (Project) object;

        return Objects.equals(this.projectName, that.projectName) &&
                Objects.equals(this.description, that.description) &&
                Objects.equals(this.customerID, that.customerID) &&
                Objects.equals(this.businessSectorName, that.businessSectorName) &&
                Objects.equals(this.projectTypologyName, that.projectTypologyName) &&
                Objects.equals(this.productBacklog, that.productBacklog) &&
                Objects.equals(this.projectStatus, that.projectStatus) &&
                Objects.equals(this.statusHistory, that.statusHistory) &&
                Objects.equals(this.sprintDuration, that.sprintDuration) &&
                Objects.equals(this.numberPlannedSprints, that.numberPlannedSprints) &&
                Objects.equals(this.budget, that.budget) &&
                Objects.equals(this.period, that.period);
    }

    /**
     * Checks if this project is equal to the given object.
     *
     * @param o the object to compare
     * @return true if the object is equal to this project, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Project project = (Project) o;
        return Objects.equals(projectCode, project.projectCode);
    }

    /**
     * Generates the hash code value for the project.
     *
     * @return the hash code value
     */
    @Override
    public int hashCode() {
        return Objects.hash(projectCode);
    }
}
