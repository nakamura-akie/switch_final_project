package org.switch2022.project.utils.dto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.dto.OutputSprintDTO;
import org.switch2022.project.utils.dto.UserStoryDTO;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NewSprintBacklogUserStoryDTOTest {
    private static OutputSprintDTO sprintBacklogDTO;

    @BeforeAll
    static void setup() {
        sprintBacklogDTO = new OutputSprintDTO();
        sprintBacklogDTO.sprintCode = "S1";
        sprintBacklogDTO.projectCode = "P1";
        sprintBacklogDTO.sprintBacklog = new LinkedList<>();
    }

    @AfterAll
    static void tearDown() {
        sprintBacklogDTO = null;
    }

    @Test
    void equals_SameObject_True() {
        OutputSprintDTO secondDTO = sprintBacklogDTO;

        boolean result = sprintBacklogDTO.equals(secondDTO);
        assertTrue(result);
    }

    @Test
    void equals_ObjectIsNull_False() {
        UserStoryDTO secondDTO = null;

        boolean result = sprintBacklogDTO.equals(secondDTO);
        assertFalse(result);
    }

    @Test
    void equals_ObjectOfDifferentClass_False() {
        String notDTO = "Not a DTO";

        boolean result = sprintBacklogDTO.equals(notDTO);
        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentProjectCode_False() {
        OutputSprintDTO secondDTO = new OutputSprintDTO();
        secondDTO.projectCode = "Different Code";
        secondDTO.sprintCode = "S1";
        secondDTO.sprintBacklog = new LinkedList<>();

        boolean result = sprintBacklogDTO.equals(secondDTO);
        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentSprintCode_False() {
        OutputSprintDTO secondDTO = new OutputSprintDTO();
        secondDTO.projectCode = "P1";
        secondDTO.sprintCode = "S2";
        secondDTO.sprintBacklog = new LinkedList<>();

        boolean result = sprintBacklogDTO.equals(secondDTO);
        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentList_False() {
        List<String> newList = new LinkedList<>();
        newList.add("US001");

        OutputSprintDTO secondDTO = new OutputSprintDTO();
        secondDTO.projectCode = "P1";
        secondDTO.sprintCode = "S2";
        secondDTO.sprintBacklog = newList;

        boolean result = sprintBacklogDTO.equals(secondDTO);
        assertFalse(result);
    }
    @Test
    void equals_ObjectOfSameClassAndSameValue_True() {
        OutputSprintDTO secondDTO = new OutputSprintDTO();
        secondDTO.projectCode = "P1";
        secondDTO.sprintCode = "S1";
        secondDTO.sprintBacklog = new LinkedList<>();

        boolean result = sprintBacklogDTO.equals(secondDTO);
        assertTrue(result);
    }

    @Test
    void hashCode_SameObject_True() {
        OutputSprintDTO secondDTO = sprintBacklogDTO;

        int firstHash = sprintBacklogDTO.hashCode();
        int secondHash = secondDTO.hashCode();

        assertEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_DifferentObject_False() {
        OutputSprintDTO secondDTO = new OutputSprintDTO();
        secondDTO.projectCode = "P1";
        secondDTO.sprintCode = "S2";
        secondDTO.sprintBacklog = new LinkedList<>();

        int firstHash = sprintBacklogDTO.hashCode();
        int secondHash = secondDTO.hashCode();

        assertNotEquals(firstHash, secondHash);
    }

}