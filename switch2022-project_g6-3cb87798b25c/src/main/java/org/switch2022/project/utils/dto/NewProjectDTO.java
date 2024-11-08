package org.switch2022.project.utils.dto;

import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberPortugalImplementation;

import java.time.LocalDate;

/**
 * The NewProjectDTO class represents a data transfer object for creating a new project.
 * It holds information about various fields related to the project.
 */
public class NewProjectDTO {
    private ProjectCode projectCode;
    private ProjectName projectName;
    private Description description;
    private TaxIdentificationNumberPortugalImplementation customerID;
    private BusinessSectorName businessSectorName;
    private ProjectTypologyName projectTypologyName;
    private SprintDuration sprintDuration;
    private NumberPlannedSprints numberPlannedSprints;
    private Budget budget;
    private TimePeriod period;

    /**
     * Constructs a new NewProjectDTO object with the specified parameters.
     *
     * @param projectCode         the project code
     * @param projectName         the project name
     * @param description         the project description
     * @param customerID          the customer id
     * @param businessSectorName  the business sector name
     * @param projectTypologyName the project typology name
     * @param sprintDuration      the sprint duration
     * @param numberPlannedSprints the number of planned sprints
     * @param budget              the project budget
     * @param startDate           the start date of the project
     * @param endDate             the end date of the project
     */
    public NewProjectDTO(
            String projectCode,
            String projectName,
            String description,
            String customerID,
            String businessSectorName,
            String projectTypologyName,
            Integer sprintDuration,
            Integer numberPlannedSprints,
            Double budget,
            String startDate,
            String endDate
    ) {
        this.projectCode = new ProjectCode(projectCode);
        this.projectName = new ProjectName(projectName);
        this.description = new Description(description);
        this.customerID = new TaxIdentificationNumberPortugalImplementation(customerID);
        this.businessSectorName = new BusinessSectorName(businessSectorName);
        this.projectTypologyName = new ProjectTypologyName(projectTypologyName);
        this.sprintDuration = sprintDuration != null ?
                new SprintDuration(sprintDuration) :
                null;
        this.numberPlannedSprints = numberPlannedSprints != null ?
                new NumberPlannedSprints(numberPlannedSprints) :
                null;
        this.budget = budget != null ?
                new Budget(budget) :
                null;
        this.period = startDate == null && endDate == null ?
                null :
                endDate == null ?
                        new TimePeriod(
                                LocalDate.parse(startDate)
                        ) :
                        new TimePeriod(
                                startDate != null ? LocalDate.parse(startDate) : null,
                                startDate != null ? LocalDate.parse(endDate) : null
                        );
    }

    /**
     * Retrieves the project code.
     *
     * @return the project code
     */
    public ProjectCode getProjectCode() {
        return this.projectCode;
    }

    /**
     * Sets the project code.
     *
     * @param projectCode the project code
     */
    public void setProjectCode(ProjectCode projectCode) {
        this.projectCode = projectCode;
    }

    /**
     * Retrieves the project name.
     *
     * @return the project name
     */
    public ProjectName getProjectName() {
        return this.projectName;
    }

    /**
     * Sets the project name.
     *
     * @param projectName the project name
     */
    public void setProjectName(ProjectName projectName) {
        this.projectName = projectName;
    }

    /**
     * Retrieves the project description.
     *
     * @return the project description
     */
    public Description getDescription() {
        return this.description;
    }

    /**
     * Sets the project description.
     *
     * @param description the project description
     */
    public void setDescription(Description description) {
        this.description = description;
    }

    /**
     * Retrieves the customer id.
     *
     * @return the customer id
     */
    public TaxIdentificationNumberPortugalImplementation getCustomerID() {
        return this.customerID;
    }

    /**
     * Sets the customer id.
     *
     * @param customerID the customer id
     */
    public void setCustomerID(TaxIdentificationNumberPortugalImplementation customerID) {
        this.customerID = customerID;
    }

    /**
     * Retrieves the business sector name.
     *
     * @return the business sector name
     */
    public BusinessSectorName getBusinessSectorName() {
        return this.businessSectorName;
    }

    /**
     * Sets the business sector name.
     *
     * @param businessSectorName the business sector
     */
    public void setBusinessSectorName(BusinessSectorName businessSectorName) {
        this.businessSectorName = businessSectorName;
    }

    /**
     * Retrieves the project typology name.
     *
     * @return the project typology name
     */
    public ProjectTypologyName getProjectTypologyName() {
        return this.projectTypologyName;
    }

    /**
     * Sets the project typology name.
     *
     * @param projectTypologyName the business sector
     */
    public void setProjectTypologyName(ProjectTypologyName projectTypologyName) {
        this.projectTypologyName = projectTypologyName;
    }

    /**
     * Retrieves the sprint duration.
     *
     * @return the sprint duration
     */
    public SprintDuration getSprintDuration() {
        return this.sprintDuration;
    }

    /**
     * Sets the sprint duration.
     *
     * @param sprintDuration the sprint duration
     */
    public void setSprintDuration(SprintDuration sprintDuration) {
        this.sprintDuration = sprintDuration;
    }

    /**
     * Retrieves the number of planned sprints.
     *
     * @return the number of planned sprints
     */
    public NumberPlannedSprints getNumberPlannedSprints() {
        return this.numberPlannedSprints;
    }

    /**
     * Sets the number of planned sprints.
     *
     * @param numberPlannedSprints the number of planned sprints
     */
    public void setNumberPlannedSprints(NumberPlannedSprints numberPlannedSprints) {
        this.numberPlannedSprints = numberPlannedSprints;
    }

    /**
     * Retrieves the budget.
     *
     * @return the budget
     */
    public Budget getBudget() {
        return this.budget;
    }

    /**
     * Sets the budget.
     *
     * @param budget the budget
     */
    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    /**
     * Retrieves the period.
     *
     * @return the period
     */
    public TimePeriod getPeriod() {
        return this.period;
    }

    /**
     * Sets the period.
     *
     * @param period the period
     */
    public void setPeriod(TimePeriod period) {
        this.period = period;
    }
}

