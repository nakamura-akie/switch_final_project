package org.switch2022.project.datamodel.assembler;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.switch2022.project.datamodel.jpa.project.ProductBacklogJPA;
import org.switch2022.project.datamodel.jpa.project.ProjectJPA;
import org.switch2022.project.datamodel.jpa.project.StatusHistoryJPA;
import org.switch2022.project.domain.project.Project;
import org.switch2022.project.domain.project.ProjectFactory;
import org.switch2022.project.domain.valueobject.TimePeriod;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectDomainDataAssemblerTest {

    private static ProjectDomainDataAssembler projectDomainDataAssembler;
    private static Project completeProjectDouble;
    private static Project basicProjectDouble;
    private static ProjectJPA completeProjectJPA;
    private static ProjectJPA basicProjectJPA;

    @BeforeAll
    static void init() {
        ProjectFactory projectFactory = mock(ProjectFactory.class);
        projectDomainDataAssembler = new ProjectDomainDataAssembler(projectFactory);

        completeProjectDouble = mock(Project.class, Mockito.RETURNS_DEEP_STUBS);
        basicProjectDouble = mock(Project.class, Mockito.RETURNS_DEEP_STUBS);
        TimePeriod periodDouble = mock(TimePeriod.class);

        List<StatusHistoryJPA> statusHistoryJPAList = Arrays.asList(
                new StatusHistoryJPA("P001", "PLANNED", "2023-01-01"),
                new StatusHistoryJPA("P001", "INCEPTION", "2023-02-01"),
                new StatusHistoryJPA("P001", "CLOSED", "2023-03-01")
        );

        completeProjectJPA = new ProjectJPA(
                "P001",
                "Project One",
                "This is the description of the first project.",
                "233867724",
                "Technology",
                "Time and Materials",
                new ProductBacklogJPA("P001", "{US001,US002,US003}"),
                "CLOSED",
                statusHistoryJPAList,
                3,
                10,
                1000.0,
                "2023-05-10",
                "2030-05-10"
        );

        basicProjectJPA = new ProjectJPA(
                "P001",
                "Project One",
                "This is the description of the first project.",
                "233867724",
                "Technology",
                "Time and Materials",
                new ProductBacklogJPA("P001", "{US001,US002,US003}"),
                "CLOSED",
                statusHistoryJPAList,
                null,
                null,
                null,
                null,
                null
        );

        when(completeProjectDouble.getProjectCode().getProjectCodeValue()).thenReturn(completeProjectJPA.getProjectCodeValue());
        when(basicProjectDouble.getProjectCode().getProjectCodeValue()).thenReturn(basicProjectJPA.getProjectCodeValue());

        when(completeProjectDouble.getProjectName().getProjectNameValue()).thenReturn(completeProjectJPA.getProjectName());
        when(basicProjectDouble.getProjectName().getProjectNameValue()).thenReturn(basicProjectJPA.getProjectName());

        when(completeProjectDouble.getDescription().getDescriptionValue()).thenReturn(completeProjectJPA.getDescription());
        when(basicProjectDouble.getDescription().getDescriptionValue()).thenReturn(basicProjectJPA.getDescription());

        when(completeProjectDouble.getCustomerID().getTaxIdentificationNumber()).thenReturn(completeProjectJPA.getCustomerID());
        when(basicProjectDouble.getCustomerID().getTaxIdentificationNumber()).thenReturn(basicProjectJPA.getCustomerID());

        when(completeProjectDouble.getBusinessSectorName().getBusinessSectorNameValue()).thenReturn(completeProjectJPA.getBusinessSectorName());
        when(basicProjectDouble.getBusinessSectorName().getBusinessSectorNameValue()).thenReturn(basicProjectJPA.getBusinessSectorName());

        when(completeProjectDouble.getProjectTypologyName().getProjectTypologyNameValue()).thenReturn(completeProjectJPA.getProjectTypologyName());
        when(basicProjectDouble.getProjectTypologyName().getProjectTypologyNameValue()).thenReturn(basicProjectJPA.getProjectTypologyName());

        when(completeProjectDouble.getProductBacklog().getUserStorylist().stream()
                .map(any())
                .map(any())
                .collect(any()))
                .thenReturn(completeProjectJPA.getProductBacklog().getUserStoryCodeList());
        when(basicProjectDouble.getProductBacklog().getUserStorylist().stream()
                .map(any())
                .map(any())
                .collect(any()))
                .thenReturn(basicProjectJPA.getProductBacklog().getUserStoryCodeList());

        when(completeProjectDouble.getProjectStatus().toString()).thenReturn(completeProjectJPA.getProjectStatus());
        when(basicProjectDouble.getProjectStatus().toString()).thenReturn(basicProjectJPA.getProjectStatus());

        when(completeProjectDouble.getStatusHistory().getStatusHistoryMap().entrySet().stream()
                .map(any())
                .collect(any()))
                .thenReturn(statusHistoryJPAList);
        when(basicProjectDouble.getStatusHistory().getStatusHistoryMap().entrySet().stream()
                .map(any())
                .collect(any()))
                .thenReturn(statusHistoryJPAList);

        when(completeProjectDouble.getSprintDuration().map(any()).orElse(any())).thenReturn(completeProjectJPA.getSprintDuration().get());
        when(basicProjectDouble.getSprintDuration().map(any()).orElse(any())).thenReturn(null);

        when(completeProjectDouble.getNumberPlannedSprints().map(any()).orElse(any())).thenReturn(completeProjectJPA.getNumberOfPlannedSprints().get());
        when(basicProjectDouble.getNumberPlannedSprints().map(any()).orElse(any())).thenReturn(null);

        when(completeProjectDouble.getBudget().map(any()).orElse(any())).thenReturn(completeProjectJPA.getBudget().get());
        when(basicProjectDouble.getBudget().map(any()).orElse(any())).thenReturn(null);

        when(completeProjectDouble.getPeriod()).thenReturn(Optional.of(periodDouble));
        when(periodDouble.getStartDate()).thenReturn(LocalDate.parse(completeProjectJPA.getStartDate().get()));
        when(periodDouble.getEndDate()).thenReturn(LocalDate.parse(completeProjectJPA.getEndDate().get()));
        when(basicProjectDouble.getPeriod().map(any()).orElse(any())).thenReturn(null);

        when(projectFactory.createProject(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any()
        )).thenReturn(completeProjectDouble);

        when(projectFactory.createProject(
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                any(),
                isNull(),
                isNull(),
                isNull(),
                isNull()
        )).thenReturn(basicProjectDouble);
    }

    @Test
    void toData_CompleteProject_CompleteProjectJPA() {
        ProjectJPA expected = completeProjectJPA;

        ProjectJPA result = projectDomainDataAssembler.toData(completeProjectDouble);

        assertEquals(expected, result);
    }

    @Test
    void toData_BasicProject_BasicProjectJPA() {
        ProjectJPA expected = basicProjectJPA;

        ProjectJPA result = projectDomainDataAssembler.toData(basicProjectDouble);

        assertEquals(expected, result);
    }

    @Test
    void toDomain_CompleteProjectData_CompleteProject() {
        Project expected = completeProjectDouble;

        Project result = projectDomainDataAssembler.toDomain(completeProjectJPA);

        assertEquals(expected, result);
    }

    @Test
    void toDomain_BasicProjectData_BasicProject() {
        Project expected = basicProjectDouble;

        Project result = projectDomainDataAssembler.toDomain(basicProjectJPA);

        assertEquals(expected, result);
    }
}