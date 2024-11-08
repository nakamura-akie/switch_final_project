package org.switch2022.project.utils.mapper;

import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.project.ProjectFactory;
import org.switch2022.project.utils.dto.NewProjectDTO;

/**
 * The ProjectMapper class is responsible for mapping NewProjectDTO objects to Project domain objects.
 */
public final class ProjectMapper {

    /**
     * Private constructor to prevent instantiation of the Project Mapper class.
     */
    private ProjectMapper() {
    }

    /**
     * Creates a Project domain object using the provided ProjectFactory and NewProjectDTO.
     *
     * @param projectFactory the ProjectFactory used to create the Project object
     * @param newProjectDTO  the NewProjectDTO containing the project data
     * @return the created Project domain object
     */
    public static Project createDomainObject(ProjectFactory projectFactory, NewProjectDTO newProjectDTO) {
        return projectFactory.createProject(
                newProjectDTO.getProjectCode(),
                newProjectDTO.getProjectName(),
                newProjectDTO.getDescription(),
                newProjectDTO.getCustomerID(),
                newProjectDTO.getBusinessSectorName(),
                newProjectDTO.getProjectTypologyName(),
                newProjectDTO.getSprintDuration(),
                newProjectDTO.getNumberPlannedSprints(),
                newProjectDTO.getBudget(),
                newProjectDTO.getPeriod()
        );
    }
}
