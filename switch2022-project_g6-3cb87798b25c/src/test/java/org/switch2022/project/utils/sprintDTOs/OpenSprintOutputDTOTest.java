package org.switch2022.project.utils.sprintDTOs;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.dto.CreatedSprintDTO;
import org.switch2022.project.utils.dto.OpenSprintOutputDTO;


import static org.junit.jupiter.api.Assertions.*;

class OpenSprintOutputDTOTest {
    private static OpenSprintOutputDTO openSprintOutputDTO;

    @BeforeAll
    static void setup() {
        openSprintOutputDTO = new OpenSprintOutputDTO();
        openSprintOutputDTO.sprintCode = "S1";
        openSprintOutputDTO.sprintStatus = "PLANNED";
        openSprintOutputDTO.startDate = "02-02-2022";
        openSprintOutputDTO.endDate = "02-03-2022";
    }

    @AfterAll
    static void tearDown() {
        openSprintOutputDTO = null;
    }


    @Test
    void equals_SameObject_True() {
        OpenSprintOutputDTO sameObject = openSprintOutputDTO;

        boolean result = openSprintOutputDTO.equals(sameObject);

        assertTrue(result);
    }

    @Test
    void equals_ObjectIsNull_False() {
        CreatedSprintDTO nullObject = null;

        boolean result = openSprintOutputDTO.equals(nullObject);

        assertFalse(result);
    }

    @Test
    void equals_EqualObject_True() {
        OpenSprintOutputDTO openSprintOutputDTOTwo = new OpenSprintOutputDTO();
        openSprintOutputDTOTwo.sprintCode = "S1";
        openSprintOutputDTOTwo.sprintStatus = "PLANNED";
        openSprintOutputDTOTwo.startDate = "02-02-2022";
        openSprintOutputDTOTwo.endDate = "02-03-2022";

        boolean result = openSprintOutputDTO.equals(openSprintOutputDTOTwo);

        assertTrue(result);
    }


    @Test
    void equals_DifferentSprintCode_False() {
        OpenSprintOutputDTO openSprintOutputDTOTwo = new OpenSprintOutputDTO();
        openSprintOutputDTOTwo.sprintCode = "S2";
        openSprintOutputDTOTwo.sprintStatus = "PLANNED";
        openSprintOutputDTOTwo.startDate = "02-02-2022";
        openSprintOutputDTOTwo.endDate = "02-03-2022";

        boolean result = openSprintOutputDTO.equals(openSprintOutputDTOTwo);

        assertFalse(result);
    }

    @Test
    void equals_DifferentSprintStatus_False() {
        OpenSprintOutputDTO openSprintOutputDTOTwo = new OpenSprintOutputDTO();
        openSprintOutputDTOTwo.sprintCode = "S1";
        openSprintOutputDTOTwo.sprintStatus = "CLOSED";
        openSprintOutputDTOTwo.startDate = "02-02-2022";
        openSprintOutputDTOTwo.endDate = "02-03-2022";

        boolean result = openSprintOutputDTO.equals(openSprintOutputDTOTwo);

        assertFalse(result);
    }


    @Test
    void equals_DifferentStartDate_False() {
        OpenSprintOutputDTO openSprintOutputDTOTwo = new OpenSprintOutputDTO();
        openSprintOutputDTOTwo.sprintCode = "S1";
        openSprintOutputDTOTwo.sprintStatus = "PLANNED";
        openSprintOutputDTOTwo.startDate = "01-02-2022";
        openSprintOutputDTOTwo.endDate = "02-03-2022";

        boolean result = openSprintOutputDTO.equals(openSprintOutputDTOTwo);

        assertFalse(result);
    }

    @Test
    void equals_DifferentEndDate_False() {
        OpenSprintOutputDTO openSprintOutputDTOTwo = new OpenSprintOutputDTO();
        openSprintOutputDTOTwo.sprintCode = "S1";
        openSprintOutputDTOTwo.sprintStatus = "PLANNED";
        openSprintOutputDTOTwo.startDate = "01-02-2022";
        openSprintOutputDTOTwo.endDate = "02-04-2022";

        boolean result = openSprintOutputDTO.equals(openSprintOutputDTOTwo);

        assertFalse(result);
    }

    @Test
    void testHashCode_Equal() {
        OpenSprintOutputDTO openSprintOutputDTOTwo = new OpenSprintOutputDTO();
        openSprintOutputDTOTwo.sprintCode = "S1";
        openSprintOutputDTOTwo.sprintStatus = "PLANNED";
        openSprintOutputDTOTwo.startDate = "02-02-2022";
        openSprintOutputDTOTwo.endDate = "02-03-2022";

        int expected = openSprintOutputDTOTwo.hashCode();

        int result = openSprintOutputDTO.hashCode();

        assertEquals(expected,result);
    }

    @Test
    void testHashCode_NotEqual() {
        OpenSprintOutputDTO openSprintOutputDTOTwo = new OpenSprintOutputDTO();
        openSprintOutputDTOTwo.sprintCode = "S2";
        openSprintOutputDTOTwo.sprintStatus = "PLANNED";
        openSprintOutputDTOTwo.startDate = "02-02-2022";
        openSprintOutputDTOTwo.endDate = "02-03-2022";

        int expected = openSprintOutputDTOTwo.hashCode();

        int result = openSprintOutputDTO.hashCode();

        assertNotEquals(expected,result);
    }
}