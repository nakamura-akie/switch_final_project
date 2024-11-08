package org.switch2022.project.utils.assembler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.valueobject.Description;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.ProjectName;
import org.switch2022.project.utils.assembler.ProjectByResourceAssembler;
import org.switch2022.project.utils.dto.ProjectByResourceDTO;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectByResourceAssemblerTest {

    private Project projectOne;
    private  Project projectTwo;
    private ProjectByResourceDTO DTOOne;
    private ProjectByResourceDTO DTOTwo;

    @BeforeEach
    void init() {
        projectOne = mock(Project.class);
        projectTwo = mock(Project.class);

        when(projectOne.getProjectCode()).thenReturn(new ProjectCode("P01"));
        when(projectOne.getDescription()).thenReturn(new Description("Description 1"));
        when(projectOne.getProjectName()).thenReturn(new ProjectName("project name 1"));

        when(projectTwo.getProjectCode()).thenReturn(new ProjectCode("P02"));
        when(projectTwo.getDescription()).thenReturn(new Description("Description 2"));
        when(projectTwo.getProjectName()).thenReturn(new ProjectName("project name 2"));

        DTOOne = new ProjectByResourceDTO();
        DTOOne.projectCode = "P01";
        DTOOne.projectName = "project name 1";
        DTOOne.projectDescription = "Description 1";

        DTOTwo = new ProjectByResourceDTO();
        DTOTwo.projectCode = "P02";
        DTOTwo.projectName = "project name 2";
        DTOTwo.projectDescription = "Description 2";

    }

    @AfterEach
    void tearDown() {
        projectOne = null;
        projectTwo = null;
        DTOOne = null;
        DTOTwo = null;
    }

    @Test
    void testCreateProjectByResourceList_ReturnsCorrectDTOList() {
        List<Project> projectList = new ArrayList<>();
        projectList.add(projectOne);
        projectList.add(projectTwo);

        List<ProjectByResourceDTO> expectedDTOList = new ArrayList<>();
        expectedDTOList.add(DTOOne);
        expectedDTOList.add(DTOTwo);

        List<ProjectByResourceDTO> actualDTOList = ProjectByResourceAssembler.createProjectByResourceList(projectList);

        assertEquals(expectedDTOList, actualDTOList);
    }

    @Test
    void testGenerateProjectByResourceDTO_ReturnsCorrectDTO() {
        ProjectByResourceDTO expectedDTO = DTOOne;
        ProjectByResourceDTO actualDTO = ProjectByResourceAssembler.generateProjectByResourceDTO(projectOne);

        assertEquals(expectedDTO, actualDTO);
    }

}