package org.switch2022.project.utils.assembler;

import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.ProjectName;
import org.switch2022.project.domain.valueobject.ProjectStatus;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;
import org.switch2022.project.utils.dto.ProjectDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * The CreateProjectListAssembler class is responsible for converting
 * projects into a ProjectDTOs.
 */
public final class CreateProjectListAssembler {

    /**
     * Private constructor to prevent instantiation of the CreateProjectListAssembler class.
     */
    private CreateProjectListAssembler() {}

    /**
     * Creates a list of projectDTOs from the given iterable of projects.
     *
     * @param projectList the iterable collection of projects
     * @return the resulting list of projectDTOs
     */
    public static List<ProjectDTO> createProjectList(Iterable<Project> projectList) {
        List<ProjectDTO> projectDTOList = new ArrayList<>();

        projectList.forEach (project -> {
            ProjectDTO projectDTO = generateDTO(project);
            projectDTOList.add(projectDTO);
        });
        return projectDTOList;
    }

    /**
     * Generates a ProjectDTO from the given Project object.
     *
     * @param project the Project object
     * @return the ProjectDTO object
     */
    private static ProjectDTO generateDTO(Project project) {
        ProjectDTO projectDTO = new ProjectDTO();

        ProjectCode projectCode = project.identity();
        projectDTO.projectCode = projectCode.getProjectCodeValue();

        ProjectName projectName = project.getProjectName();
        projectDTO.projectName = projectName.getProjectNameValue();

        TaxIdentificationNumber customerID = project.getCustomerID();
        projectDTO.projectCustomer = customerID.getTaxIdentificationNumber();

        ProjectStatus projectStatus = project.getProjectStatus();
        projectDTO.projectStatus = projectStatus.name();

        return projectDTO;
    }

}
