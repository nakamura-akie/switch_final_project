package org.switch2022.project.domain.project;

import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * The ProjectFactory interface defines methods for creating Project objects.
 */
public interface ProjectFactory {

    /**
     * Creates a new Project instance.
     *
     * @param projectCode the project code
     * @param projectName the project name
     * @param description the description
     * @param customerID the tax identification number
     * @param businessSectorName the business sector name
     * @param projectTypologyName the project typology name
     * @return the created Project instance
     */
    @Deprecated(forRemoval = true)
    Project createProject(
            ProjectCode projectCode,
            ProjectName projectName,
            Description description,
            TaxIdentificationNumber customerID,
            BusinessSectorName businessSectorName,
            ProjectTypologyName projectTypologyName
    );

    /**
     * Creates a new Project instance.
     *
     * @param projectCode the project code
     * @param projectName the project name
     * @param description the description
     * @param customerID the tax identification number
     * @param businessSectorName the business sector name
     * @param projectTypologyName the project typology name
     * @param sprintDuration the sprint duration
     * @param numberOfPlannedSprints the number of planned sprints
     * @param budget the budget
     * @param period the time period
     * @return the created Project instance
     */
    Project createProject(
            ProjectCode projectCode,
            ProjectName projectName,
            Description description,
            TaxIdentificationNumber customerID,
            BusinessSectorName businessSectorName,
            ProjectTypologyName projectTypologyName,
            SprintDuration sprintDuration,
            NumberPlannedSprints numberOfPlannedSprints,
            Budget budget,
            TimePeriod period
    );

    /**
     * Creates a new Project instance.
     *
     * @param projectCode the project code
     * @param projectName the project name
     * @param description the description
     * @param customerID the tax identification number
     * @param businessSectorName the business sector name
     * @param projectTypologyName the project typology name
     * @param productBacklog the product backlog
     * @param projectStatus the project status
     * @param projectStatusHistory the project status history
     * @param sprintDuration the sprint duration
     * @param numberOfPlannedSprints the number of planned sprints
     * @param budget the budget
     * @param period the time period
     * @return the created Project instance
     */
    Project createProject(
            ProjectCode projectCode,
            ProjectName projectName,
            Description description,
            TaxIdentificationNumber customerID,
            BusinessSectorName businessSectorName,
            ProjectTypologyName projectTypologyName,
            List<UserStoryID> productBacklog,
            ProjectStatus projectStatus,
            Map<ProjectStatus, LocalDate> projectStatusHistory,
            SprintDuration sprintDuration,
            NumberPlannedSprints numberOfPlannedSprints,
            Budget budget,
            TimePeriod period
    );
}
