package org.switch2022.project.datamodel.assembler;

import org.springframework.stereotype.Component;
import org.switch2022.project.datamodel.jpa.project.ProductBacklogJPA;
import org.switch2022.project.datamodel.jpa.project.ProjectJPA;
import org.switch2022.project.datamodel.jpa.project.StatusHistoryJPA;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.project.ProjectFactory;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberPortugalImplementation;
import org.switch2022.project.utils.UserStoryCodeListToString;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Component
public class ProjectDomainDataAssembler {

    private final ProjectFactory projectFactory;

    /**
     * Constructs a ProjectDomainDataAssembler with the specified BusinessSectorFactory.
     *
     * @param projectFactory the {@link ProjectFactory} to be used for creating Project objects.
     * @throws IllegalArgumentException if the BusinessSectorFactory is null.
     */
    public ProjectDomainDataAssembler(ProjectFactory projectFactory) {
        this.projectFactory = projectFactory;
    }

    /**
     * Converts a Project domain object to its corresponding {@link ProjectJPA} entity,
     * by extracting and mapping its attributes.
     *
     * @param project the Project domain object to be converted.
     * @return the corresponding JPA entity.
     */
    public ProjectJPA toData(Project project) {
        String projectCode = project.getProjectCode().getProjectCodeValue();

        String projectName = project.getProjectName().getProjectNameValue();

        String description = project.getDescription().getDescriptionValue();

        String customerID = project.getCustomerID().getTaxIdentificationNumber();

        String businessSectorName = project.getBusinessSectorName().getBusinessSectorNameValue();

        String projectTypologyName = project.getProjectTypologyName().getProjectTypologyNameValue();

        ProductBacklogJPA productBacklogJPA = new ProductBacklogJPA(
                project.getProjectCode().getProjectCodeValue(),
                project.getProductBacklog().getUserStorylist().stream()
                        .map(UserStoryID::getUserStoryCode)
                        .map(UserStoryCode::getUserStoryCodeValue)
                        .collect(Collectors.joining(",", "{", "}"))
        );

        String projectStatus = project.getProjectStatus().toString();

        List<StatusHistoryJPA> projectStatusHistoryList =
                project.getStatusHistory().getStatusHistoryMap().entrySet().stream()
                        .map(entry -> new StatusHistoryJPA(
                                project.getProjectCode().getProjectCodeValue(),
                                entry.getKey().toString(),
                                entry.getValue().toString())
                        )
                        .collect(Collectors.toList());

        Integer sprintDuration = project.getSprintDuration().map(SprintDuration::getSprintDurationValue).orElse(null);

        Integer numberPlannedSprints =
                project.getNumberPlannedSprints().map(NumberPlannedSprints::getNumberOfPlannedSprintsValue)
                        .orElse(null);

        Double budget = project.getBudget().map(Budget::getBudgetValue).orElse(null);

        String startDate = project.getPeriod().map(period -> period.getStartDate().toString()).orElse(null);

        String endDate = project.getPeriod().map(period -> period.getEndDate().toString()).orElse(null);

        return new ProjectJPA(
                projectCode,
                projectName,
                description,
                customerID,
                businessSectorName,
                projectTypologyName,
                productBacklogJPA,
                projectStatus,
                projectStatusHistoryList,
                sprintDuration,
                numberPlannedSprints,
                budget,
                startDate,
                endDate
        );
    }

    /**
     * Converts a ProjectJPA entity to its corresponding {@link Project} domain object.
     *
     * @param projectJPA the JPA entity to be converted.
     * @return the corresponding Project domain object.
     */
    public Project toDomain(ProjectJPA projectJPA) {
        ProjectCode projectCode = new ProjectCode(projectJPA.getProjectCodeValue());

        ProjectName projectname = new ProjectName(projectJPA.getProjectName());

        Description description = new Description(projectJPA.getDescription());

        TaxIdentificationNumberPortugalImplementation customerID =
                new TaxIdentificationNumberPortugalImplementation(projectJPA.getCustomerID());

        BusinessSectorName businessSectorName = new BusinessSectorName(projectJPA.getBusinessSectorName());

        ProjectTypologyName projectTypologyName = new ProjectTypologyName(projectJPA.getProjectTypologyName());

        LinkedList<UserStoryID> userStoryIDList =
                UserStoryCodeListToString.parseUserStoryCodeList(projectJPA.getProductBacklog().getUserStoryCodeList())
                        .stream()
                        .map(userStoryCode -> new UserStoryID(projectCode, userStoryCode))
                        .collect(Collectors.toCollection(LinkedList::new));

        ProjectStatus projectStatus = ProjectStatus.valueOf(projectJPA.getProjectStatus());

        Map<ProjectStatus, LocalDate> projectStatusHistoryMap = projectJPA.getStatusHistory().stream()
                .collect(Collectors.toMap(
                        statusHistoryJPA -> ProjectStatus.valueOf(statusHistoryJPA.getStatus()),
                        statusHistoryJPA -> LocalDate.parse(statusHistoryJPA.getDate())));

        SprintDuration sprintDuration = projectJPA.getSprintDuration()
                .map(SprintDuration::new)
                .orElse(null);

        NumberPlannedSprints numberPlannedSprints = projectJPA.getNumberOfPlannedSprints()
                .map(NumberPlannedSprints::new)
                .orElse(null);

        Budget budget = projectJPA.getBudget()
                .map(Budget::new)
                .orElse(null);

        TimePeriod period;
        try {
            period = new TimePeriod(
                    projectJPA.getStartDate().map(LocalDate::parse).orElseThrow(),
                    projectJPA.getEndDate().map(LocalDate::parse).orElseThrow());
        } catch (NoSuchElementException e) {
            period = null;
        }

        return this.projectFactory.createProject(
                projectCode,
                projectname,
                description,
                customerID,
                businessSectorName,
                projectTypologyName,
                userStoryIDList,
                projectStatus,
                projectStatusHistoryMap,
                sprintDuration,
                numberPlannedSprints,
                budget,
                period
        );
    }
}
