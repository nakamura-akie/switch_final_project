package org.switch2022.project.datamodel.assembler;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.switch2022.project.datamodel.*;
import org.switch2022.project.domain.sprint.Sprint;
import org.switch2022.project.domain.sprint.SprintFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SprintDomainDataAssemblerTest {
    private static SprintDomainDataAssembler assembler;
    private static SprintJPA sprintJPA;
    private static Sprint mockedSprint;

    @BeforeAll
    static void init() {
        SprintFactory sprintFactory = mock(SprintFactory.class);
        assembler = new SprintDomainDataAssembler(sprintFactory);

        mockedSprint = mock(Sprint.class, Mockito.RETURNS_DEEP_STUBS);
        SprintIdJPA sprintIdJPA = new SprintIdJPA("P1", "S1");

        sprintJPA = new SprintJPA(
                sprintIdJPA,
                "2023-06-25",
                3,
                "2023-06-26",
                new SprintBacklogJPA(sprintIdJPA, "{US01}"),
                "PLANNED"
        );

        when(mockedSprint.getProjectCode().getProjectCodeValue())
                .thenReturn(sprintJPA.getSprintIdJPA().getProjectCodeValue());
        when(mockedSprint.getSprintCode().getSprintCodeValue())
                .thenReturn(sprintJPA.getSprintIdJPA().getSprintCode());

        when(mockedSprint.getStartDate().toString()).thenReturn(sprintJPA.getStartDate());
        when(mockedSprint.getSprintDuration().getSprintDurationValue()).thenReturn(sprintJPA.getSprintDuration());

        when(mockedSprint.getEndDate().toString()).thenReturn(sprintJPA.getEndDate());

        when(mockedSprint.getIDsOfUserStoriesInSprintBacklog().stream()
                .map(any())
                .map(any())
                .collect(any()))
                .thenReturn(sprintJPA.getSprintBacklogJPA().getUserStoryCodeList());

        when(mockedSprint.getSprintStatus().name()).thenReturn(sprintJPA.getSprintStatus());

        when(sprintFactory.createSprint(
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
        )).thenReturn(mockedSprint);
    }

    @Test
    void toData_ConvertsSprintToSprintJPA() {
        SprintJPA convertedSprintJPA = assembler.toData(mockedSprint);
        assertNotNull(convertedSprintJPA);
        assertEquals(sprintJPA, convertedSprintJPA);
    }

    @Test
    void toDomain_ConvertsSprintJPAToSprint() {
        Sprint convertedSprint = assembler.toDomain(sprintJPA);
        assertNotNull(convertedSprint);
        assertEquals(mockedSprint, convertedSprint);
    }
}