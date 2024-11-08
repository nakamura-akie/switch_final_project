package org.switch2022.project.domain.project;

import org.springframework.stereotype.Component;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * The ProjectFactoryImplementation class is an implementation of the ProjectFactory interface.
 */
@Component
public class ProjectFactoryImplementation implements ProjectFactory {

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
    @Override
    public Project createProject(
            ProjectCode projectCode,
            ProjectName projectName,
            Description description,
            TaxIdentificationNumber customerID,
            BusinessSectorName businessSectorName,
            ProjectTypologyName projectTypologyName
    ) {

        ProductBacklog productBacklog = new ProductBacklog(projectCode);
        ProjectStatus projectStatus = ProjectStatus.PLANNED;
        StatusHistory projectStatusHistory = new StatusHistory(projectCode);
        projectStatusHistory.record(projectStatus);

        return new Project(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypologyName,
                productBacklog,
                projectStatus,
                projectStatusHistory
        );
    }

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
    @Override
    public Project createProject(
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
    ) {

        ProductBacklog productBacklog = new ProductBacklog(projectCode);
        ProjectStatus projectStatus = ProjectStatus.PLANNED;
        StatusHistory projectStatusHistory = new StatusHistory(projectCode);
        projectStatusHistory.record(projectStatus);

        Project project = new Project(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypologyName,
                productBacklog,
                projectStatus,
                projectStatusHistory
        );

        project.setSprintDuration(sprintDuration);
        project.setNumberPlannedSprints(numberOfPlannedSprints);
        project.setBudget(budget);
        project.setPeriod(period);

        return project;
    }

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
     * @param statusHistory the project status history
     * @param sprintDuration the sprint duration
     * @param numberOfPlannedSprints the number of planned sprints
     * @param budget the budget
     * @param period the time period
     * @return the created Project instance
     */
    @Override
    public Project createProject(
            ProjectCode projectCode,
            ProjectName projectName,
            Description description,
            TaxIdentificationNumber customerID,
            BusinessSectorName businessSectorName,
            ProjectTypologyName projectTypologyName,
            List<UserStoryID> productBacklog,
            ProjectStatus projectStatus,
            Map<ProjectStatus, LocalDate> statusHistory,
            SprintDuration sprintDuration,
            NumberPlannedSprints numberOfPlannedSprints,
            Budget budget,
            TimePeriod period
    ) {

        Project project = new Project(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypologyName,
                new ProductBacklog(projectCode, productBacklog),
                projectStatus,
                new StatusHistory(projectCode, statusHistory));

        project.setSprintDuration(sprintDuration);
        project.setNumberPlannedSprints(numberOfPlannedSprints);
        project.setBudget(budget);
        project.setPeriod(period);

        return project;
    }
}