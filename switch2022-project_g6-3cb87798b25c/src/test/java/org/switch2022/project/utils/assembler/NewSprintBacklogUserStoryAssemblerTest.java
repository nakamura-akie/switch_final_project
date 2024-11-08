package org.switch2022.project.utils.assembler;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.sprint.Sprint;
import org.switch2022.project.domain.sprint.SprintBacklog;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.SprintCode;
import org.switch2022.project.domain.valueobject.SprintDuration;
import org.switch2022.project.domain.valueobject.SprintStatus;
import org.switch2022.project.utils.dto.OutputSprintDTO;

import java.time.LocalDate;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NewSprintBacklogUserStoryAssemblerTest {

    private static Sprint sprint;

    @BeforeAll
    static void init() {
        sprint = mock(Sprint.class);

        ProjectCode projectCode = new ProjectCode("P1");
        SprintCode sprintCode = new SprintCode("S1");
        LocalDate startDate = mock(LocalDate.class);
        LocalDate endDate = mock(LocalDate.class);
        SprintDuration sprintDuration = mock(SprintDuration.class);
        SprintStatus sprintStatus = mock(SprintStatus.class);


        when(sprint.getProjectCode()).thenReturn(projectCode);
        when(sprint.getSprintCode()).thenReturn(sprintCode);
        when(sprint.getStartDate()).thenReturn(startDate);
        when(sprint.getEndDate()).thenReturn(endDate);
        when(sprint.getSprintDuration()).thenReturn(sprintDuration);
        when(sprint.getIDsOfUserStoriesInSprintBacklog()).thenReturn(new LinkedList<>());
        when(sprint.getSprintStatus()).thenReturn(sprintStatus);
    }

    @AfterAll
    static void tearDown() {
        sprint = null;
    }

    @Test
    void createOutputProfileDTO_SuccessfulCreation_True() {
        OutputSprintDTO expected = new OutputSprintDTO();
        expected.projectCode = "P1";
        expected.sprintCode = "S1";
        expected.sprintBacklog = new LinkedList<>();

        OutputSprintDTO result = OutputSprintAssembler.generateDTO(sprint);

        assertEquals(expected, result);
    }

}