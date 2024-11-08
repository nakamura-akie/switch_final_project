package org.switch2022.project.utils.assembler;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.switch2022.project.domain.sprint.Sprint;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.utils.assembler.CreatedSprintAssembler;
import org.switch2022.project.utils.dto.CreatedSprintDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreatedSprintAssemblerTest {
    private static Sprint sprintDouble;
    private static ProjectCode projectCode;
    private static SprintCode sprintCode;
    private static SprintID sprintID;
    private static LocalDate startDate;
    private static SprintDuration sprintDuration;


    @BeforeAll
    static void setup() {
        sprintDouble = mock(Sprint.class);

        projectCode = new ProjectCode("P1");
        sprintCode = new SprintCode("S1");
        sprintID = new SprintID(projectCode,sprintCode);
        startDate = LocalDate.of(2023, 05, 02);
        sprintDuration = new SprintDuration(3);

        when(sprintDouble.getProjectCode()).thenReturn(projectCode);
        when(sprintDouble.getSprintCode()).thenReturn(sprintCode);
        when(sprintDouble.identity()).thenReturn(sprintID);
        when(sprintDouble.getStartDate()).thenReturn(startDate);
        when(sprintDouble.getSprintDuration()).thenReturn(sprintDuration);
    }

    @AfterAll
    static void tearDown() {
        sprintDouble = null;
        projectCode = null;
        sprintCode = null;
        startDate = null;
        sprintDuration = null;
    }

    @Test
    void createCreatedSprintDTO_SuccessfulCreation_True() {
        CreatedSprintDTO expected = new CreatedSprintDTO();
        expected.sprintID = new SprintID(projectCode, sprintCode);
        expected.startDate = LocalDate.of(2023, 05, 02);
        expected.sprintDuration = new SprintDuration(3);

        CreatedSprintDTO result = CreatedSprintAssembler.generateDTO(sprintDouble);

        assertEquals(expected, result);
    }

    @Test
    void createSprintList_WithSprints_ReturnsSprintDTOList() {
        ProjectCode projectCode = new ProjectCode("P1");
        SprintCode sprintCodeOne = new SprintCode("S1");
        SprintCode sprintCodeTwo = new SprintCode("S2");
        SprintID sprintIDOne = new SprintID(projectCode, sprintCodeOne);
        SprintID sprintIDTwo = new SprintID(projectCode, sprintCodeTwo);
        SprintDuration sprintDuration = new SprintDuration(3);
        LocalDate startDateOne = LocalDate.of(2023, 1, 1);
        LocalDate endDateOne = LocalDate.of(2023, 1, 22);
        LocalDate startDateTwo = LocalDate.of(2023, 2, 1);
        LocalDate endDateTwo = LocalDate.of(2023, 2, 22);
        SprintStatus sprintStatusOne = SprintStatus.PLANNED;
        SprintStatus sprintStatusTwo = SprintStatus.CLOSED;

        Sprint sprintOne = Mockito.mock(Sprint.class);
        Sprint sprintTwo = Mockito.mock(Sprint.class);

        when(sprintOne.getProjectCode()).thenReturn(projectCode);
        when(sprintTwo.getProjectCode()).thenReturn(projectCode);
        when(sprintOne.getSprintCode()).thenReturn(sprintCodeOne);
        when(sprintTwo.getSprintCode()).thenReturn(sprintCodeTwo);
        when(sprintOne.getSprintDuration()).thenReturn(sprintDuration);
        when(sprintTwo.getSprintDuration()).thenReturn(sprintDuration);
        when(sprintOne.getStartDate()).thenReturn(startDateOne);
        when(sprintTwo.getStartDate()).thenReturn(startDateTwo);
        when(sprintOne.getEndDate()).thenReturn(endDateOne);
        when(sprintTwo.getEndDate()).thenReturn(endDateTwo);
        when(sprintOne.getSprintStatus()).thenReturn(sprintStatusOne);
        when(sprintTwo.getSprintStatus()).thenReturn(sprintStatusTwo);

        List<Sprint> sprints = new ArrayList<>();
        sprints.add(sprintOne);
        sprints.add(sprintTwo);

        Iterator<Sprint> sprintIterator = sprints.iterator();

        List<CreatedSprintDTO> sprintDTOList = CreatedSprintAssembler.createSprintList(sprintIterator);

        Assertions.assertEquals(2, sprintDTOList.size());

        CreatedSprintDTO sprintDTO1 = sprintDTOList.get(0);
        Assertions.assertEquals(sprintIDOne, sprintDTO1.sprintID);
        Assertions.assertEquals(sprintDuration, sprintDTO1.sprintDuration);
        Assertions.assertEquals(startDateOne, sprintDTO1.startDate);
        Assertions.assertEquals(endDateOne, sprintDTO1.endDate);
        Assertions.assertEquals(sprintStatusOne, sprintDTO1.sprintStatus);

        CreatedSprintDTO sprintDTO2 = sprintDTOList.get(1);
        Assertions.assertEquals(sprintIDTwo, sprintDTO2.sprintID);
        Assertions.assertEquals(sprintDuration, sprintDTO2.sprintDuration);
        Assertions.assertEquals(startDateTwo, sprintDTO2.startDate);
        Assertions.assertEquals(endDateTwo, sprintDTO2.endDate);
        Assertions.assertEquals(sprintStatusTwo, sprintDTO2.sprintStatus);
    }

    @Test
    void createSprintList_WithEmptyIterator_ReturnsEmptySprintDTOList() {
        Iterator<Sprint> sprintIterator = new ArrayList<Sprint>().iterator();

        List<CreatedSprintDTO> sprintDTOList = CreatedSprintAssembler.createSprintList(sprintIterator);

        Assertions.assertTrue(sprintDTOList.isEmpty());
    }
}