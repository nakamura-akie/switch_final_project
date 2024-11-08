package org.switch2022.project.utils.assembler;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.valueobject.TimePeriod;
import org.switch2022.project.utils.dto.OutputProjectDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OutputProjectAssemblerTest {

    private static Project projectDouble;
    private static OutputProjectDTO outputProjectDTO;

    @BeforeAll
    static void init() {
        projectDouble = mock(Project.class, RETURNS_DEEP_STUBS);

        when(projectDouble.getProjectCode().getProjectCodeValue()).thenReturn("P001");
        when(projectDouble.getProjectName().getProjectNameValue()).thenReturn("Project One");
        when(projectDouble.getDescription().getDescriptionValue()).thenReturn("Description");
        when(projectDouble.getCustomerID().getTaxIdentificationNumber()).thenReturn("257601996");
        when(projectDouble.getBusinessSectorName().getBusinessSectorNameValue()).thenReturn("Technology");
        when(projectDouble.getProjectTypologyName().getProjectTypologyNameValue()).thenReturn("Fixed-Cost");
        when(projectDouble.getProductBacklog().getUserStorylist().stream()
                .map(any())
                .map(any())
                .collect(any())).thenReturn(List.of("US001", "US002", "US003"));
        when(projectDouble.getProjectStatus().toString()).thenReturn("PLANNED");
        when(projectDouble.getStatusHistory().getStatusHistoryMap().entrySet().stream()
                .collect(any())).thenReturn(Map.of("PLANNED", "2023-01-01"));
        when(projectDouble.getSprintDuration().map(any()).orElse(null)).thenReturn(2L);
        when(projectDouble.getNumberPlannedSprints().map(any()).orElse(null)).thenReturn(10);
        when(projectDouble.getBudget().map(any()).orElse(null)).thenReturn(1000.0);
        when(projectDouble.getPeriod()).thenReturn(Optional.of(new TimePeriod(LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 10, 1))));
        outputProjectDTO = new OutputProjectDTO(
                "P001",
                "Project One",
                "Description",
                "257601996",
                "Technology",
                "Fixed-Cost",
                List.of("US001", "US002", "US003"),
                "PLANNED",
                Map.of("PLANNED", "2023-01-01"),
                2,
                3,
                1000.0,
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 10, 1)
        );
    }

    @Test
    void generateDTO_CompleteProjectData_CompleteCreatedProjectDTO() {
        OutputProjectDTO expected = outputProjectDTO;

        OutputProjectDTO result = OutputProjectAssembler.generateDTO(projectDouble);

        assertEquals(expected, result);
    }

    @Test
    void generateDTO_BasicProjectData_BasicProjectDTO() {
        OutputProjectDTO expected = outputProjectDTO;
        expected.setSprintDuration(null);
        expected.setNumberPlannedSprints(null);
        expected.setBudget(null);
        expected.setStartDate(null);
        expected.setEndDate(null);

        when(projectDouble.getSprintDuration()).thenReturn(Optional.empty());
        when(projectDouble.getNumberPlannedSprints()).thenReturn(Optional.empty());
        when(projectDouble.getBudget()).thenReturn(Optional.empty());
        when(projectDouble.getPeriod()).thenReturn(Optional.empty());

        OutputProjectDTO result = OutputProjectAssembler.generateDTO(projectDouble);

        assertEquals(expected, result);
    }
}