package org.switch2022.project.utils.assembler;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.utils.dto.ProjectByResourceDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * The ProjectByResourceAssembler class is responsible for converting
 * Project objects into ProjectByResourceDTO objects.
 */
public final class ProjectByResourceAssembler {

    /**
     * Private constructor to prevent instantiation of the ProjectByResourceAssembler class.
     */
    private ProjectByResourceAssembler() {
    }

    /**
     * Creates a list of ProjectByResourceDTO objects from the given list of Project objects.
     *
     * @param projectList the list of Project objects
     * @return the list of ProjectByResourceDTO objects
     */
    public static List<ProjectByResourceDTO> createProjectByResourceList(List<Project> projectList) {
        List<ProjectByResourceDTO> projectByResourceList = new ArrayList<>();

        for (Project projectInResource : projectList) {

            ProjectByResourceDTO userProjectDTO = generateProjectByResourceDTO(projectInResource);
            projectByResourceList.add(userProjectDTO);
            }

        return projectByResourceList;
    }

    /**
     * Generates a ProjectByResourceDTO object from the given Project object.
     *
     * @param project the Project object
     * @return the ProjectByResourceDTO object
     */
    public static ProjectByResourceDTO generateProjectByResourceDTO(Project project) {
        ProjectByResourceDTO projectByResourceDTO = new ProjectByResourceDTO();
        projectByResourceDTO.projectCode = project.getProjectCode().getProjectCodeValue();
        projectByResourceDTO.projectName = project.getProjectName().getProjectNameValue();
        projectByResourceDTO.projectDescription = project.getDescription().getDescriptionValue();

        return projectByResourceDTO;
    }
}


