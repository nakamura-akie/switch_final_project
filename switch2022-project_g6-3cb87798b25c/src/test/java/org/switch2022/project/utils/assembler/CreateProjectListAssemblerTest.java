package org.switch2022.project.utils.assembler;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.ProjectName;
import org.switch2022.project.domain.valueobject.ProjectStatus;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberPortugalImplementation;
import org.switch2022.project.utils.dto.ProjectDTO;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateProjectListAssemblerTest {

    private List<Project> projectList;

    @BeforeEach
    void init() {
        projectList = new ArrayList<>();

        Project firstProject = mock(Project.class);
        this.projectList.add(firstProject);

        ProjectCode projectCode = new ProjectCode("P1");
        ProjectName projectName = new ProjectName("First Project");
        TaxIdentificationNumberPortugalImplementation projectCustomer = new TaxIdentificationNumberPortugalImplementation("245700943");
        ProjectStatus projectStatus = ProjectStatus.PLANNED;

        when(firstProject.identity()).thenReturn(projectCode);
        when(firstProject.getProjectName()).thenReturn(projectName);
        when(firstProject.getCustomerID()).thenReturn(projectCustomer);
        when(firstProject.getProjectStatus()).thenReturn(projectStatus);
    }

    @AfterEach
    void tearDown(){
        projectList = null;
    }

    @Test
    void createProjectList_ReturnsListOfProjects_Equals() {
        ProjectDTO firstProjectDTO = new ProjectDTO();
        firstProjectDTO.projectCode = "P1";
        firstProjectDTO.projectName = "First Project";
        firstProjectDTO.projectCustomer = "245700943";
        firstProjectDTO.projectStatus = "PLANNED";

        List<ProjectDTO> expected = new ArrayList<>();
        expected.add(firstProjectDTO);

        List<ProjectDTO> result = CreateProjectListAssembler.createProjectList(projectList);

        assertEquals(expected, result);
    }

    @Test
    void createProjectList_ReturnsEmptyList_Equals() {
        List<Project> emptyProjectList = new ArrayList<>();
        List<ProjectDTO> expected = new ArrayList<>();

        List<ProjectDTO> result = CreateProjectListAssembler.createProjectList(emptyProjectList);

        assertEquals(expected, result);
    }

}