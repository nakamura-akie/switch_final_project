package org.switch2022.project.utils.assembler;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.projecttypology.ProjectTypology;
import org.switch2022.project.domain.valueobject.ProjectTypologyName;
import org.switch2022.project.utils.assembler.OutputProjectTypologyAssembler;
import org.switch2022.project.utils.dto.OutputProjectTypologyDTO;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class OutputProjectTypologyAssemblerTest {
    private static ProjectTypology projectTypology;
    private static ProjectTypologyName projectTypologyName;

    @BeforeAll
    static void init() {
        projectTypologyName = new ProjectTypologyName("Time and Materials");
        projectTypology = mock(ProjectTypology.class);

        when(projectTypology.identity()).thenReturn(projectTypologyName);
    }

    @AfterAll
    static void tearDown() {
        projectTypologyName = null;
        projectTypology = null;
    }

    @Test
    void generateProjectTypologyDTO_SuccessfulCreation() {
        OutputProjectTypologyDTO outputProjectTypologyDTO = new OutputProjectTypologyDTO();
        outputProjectTypologyDTO.projectTypologyValue = projectTypologyName.getProjectTypologyNameValue();

        OutputProjectTypologyDTO result = OutputProjectTypologyAssembler.generateProjectTypologyDTO(projectTypology);

        assertEquals(outputProjectTypologyDTO, result);
    }
}