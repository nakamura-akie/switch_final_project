package org.switch2022.project.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.switch2022.project.domain.projecttypology.ProjectTypology;
import org.switch2022.project.domain.projecttypology.ProjectTypologyFactory;
import org.switch2022.project.domain.valueobject.ProjectTypologyName;
import org.switch2022.project.repository.ProjectTypologyRepositoryFake;
import org.switch2022.project.utils.dto.NewProjectTypologyDTO;
import org.switch2022.project.utils.assembler.OutputProjectTypologyAssembler;
import org.switch2022.project.utils.dto.OutputProjectTypologyDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class ProjectTypologyServiceTest {

    @Autowired
    ProjectTypologyService projectTypologyService;

    @MockBean
    private ProjectTypologyRepositoryFake projectTypologyRepositoryFakeDouble;
    @MockBean
    ProjectTypologyFactory projectTypologyFactoryDouble;

    MockedStatic<OutputProjectTypologyAssembler> createdProjectTypologyAssembler;

    private ProjectTypology projectTypologyTimeAndMaterials;
    private ProjectTypologyName timeAndMaterials;

    @BeforeEach
    void init() {
        this.projectTypologyFactoryDouble = mock(ProjectTypologyFactory.class);
        this.createdProjectTypologyAssembler = mockStatic(OutputProjectTypologyAssembler.class);
        this.timeAndMaterials = new ProjectTypologyName("Time and Materials");
        this.projectTypologyService = new ProjectTypologyService(projectTypologyRepositoryFakeDouble,
                projectTypologyFactoryDouble);


        this.projectTypologyTimeAndMaterials = mock(ProjectTypology.class);

    }

    @AfterEach
    void tearDown() {
        this.projectTypologyFactoryDouble = null;
        this.projectTypologyTimeAndMaterials = null;
        this.timeAndMaterials = null;
        this.projectTypologyRepositoryFakeDouble.deleteAll();
        this.createdProjectTypologyAssembler.close();
        this.projectTypologyService = null;
    }

    @Test
    void constructor_NullProjectTypologyRepository_ThrowsException() {
        String expected = "Project Typology Repository cannot be null";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new ProjectTypologyService(null, projectTypologyFactoryDouble);}).getMessage();

        assertEquals(expected, result);
    }

    @Test
    void constructor_NullProjectTypologyFactory_ThrowsException() {
        String expected = "Project Typology Factory cannot be null";

        String result = assertThrows(IllegalArgumentException.class, () -> {
            new ProjectTypologyService(projectTypologyRepositoryFakeDouble, null);}).getMessage();

        assertEquals(expected, result);
    }


    @Test
    void createProjectTypology() {

        ProjectTypologyName projectTypologyNameOne = new ProjectTypologyName("Construction");

        NewProjectTypologyDTO newProjectTypologyDTO = new NewProjectTypologyDTO();
        newProjectTypologyDTO.projectTypologyName = projectTypologyNameOne;

        ProjectTypology projectTypology = mock(ProjectTypology.class);
        when(projectTypology.identity()).thenReturn(projectTypologyNameOne);

        OutputProjectTypologyDTO outputProjectTypologyDTO = new OutputProjectTypologyDTO();
        outputProjectTypologyDTO.projectTypologyValue = "Construction";

        when(projectTypologyRepositoryFakeDouble.existsById(projectTypologyNameOne)).thenReturn(false);
        when(projectTypologyFactoryDouble.createProjectTypology(projectTypologyNameOne)).thenReturn(projectTypology);
        when(projectTypologyRepositoryFakeDouble.save(projectTypology)).thenReturn(projectTypology);
        createdProjectTypologyAssembler.when(() -> OutputProjectTypologyAssembler.generateProjectTypologyDTO(projectTypology)).thenReturn(outputProjectTypologyDTO);

        OutputProjectTypologyDTO result = projectTypologyService.createProjectTypology(newProjectTypologyDTO);

        assertEquals(outputProjectTypologyDTO, result);

    }

    @Test
    void createProjectTypology_ProjectTypologyAlreadyExists_ReturnsFalse() {



        ProjectTypologyName projectTypologyAlreadyExistent = new ProjectTypologyName("Time And Materials");
        when(projectTypologyRepositoryFakeDouble.existsById(projectTypologyAlreadyExistent)).thenReturn(true);

        NewProjectTypologyDTO newProjectTypologyDTO = new NewProjectTypologyDTO();
        newProjectTypologyDTO.projectTypologyName = projectTypologyAlreadyExistent;

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                projectTypologyService.createProjectTypology(newProjectTypologyDTO));

        assertEquals("Project Typology already exists", exception.getMessage());
    }
}