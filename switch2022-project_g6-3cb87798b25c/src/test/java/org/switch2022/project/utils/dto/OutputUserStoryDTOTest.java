package org.switch2022.project.utils.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.dto.OutputUserStoryDTO;

import static org.junit.jupiter.api.Assertions.*;

class OutputUserStoryDTOTest {
    private OutputUserStoryDTO firstDTO;
    private OutputUserStoryDTO secondDTO;
    private OutputUserStoryDTO thirdDTO;
    private OutputUserStoryDTO fourthDTO;
    private OutputUserStoryDTO fifthDTO;

    @BeforeEach
    void init() {
        firstDTO = new OutputUserStoryDTO();
        firstDTO.projectCode = "P001";
        firstDTO.userStoryCode = "US001";
        firstDTO.status = "Status One";
        firstDTO.description = "Some description";
        firstDTO.userStoryEffort = 1;

        secondDTO = new OutputUserStoryDTO();
        secondDTO.projectCode = "P001";
        secondDTO.userStoryCode = "US002";
        secondDTO.status = "Status One";
        secondDTO.description = "Some description";
        secondDTO.userStoryEffort = 1;

        thirdDTO = new OutputUserStoryDTO();
        thirdDTO.projectCode = "P001";
        thirdDTO.userStoryCode = "US001";
        thirdDTO.status = "Status One";
        thirdDTO.description = "Some description";
        thirdDTO.userStoryEffort = 1;

        fourthDTO = new OutputUserStoryDTO();
        fourthDTO.projectCode = "P001";
        fourthDTO.userStoryCode = "US001";
        fourthDTO.status = "Status Four";
        fourthDTO.description = "Some description";
        fourthDTO.userStoryEffort = 1;

        fifthDTO = new OutputUserStoryDTO();
        fifthDTO.projectCode = "P001";
        fifthDTO.userStoryCode = "US001";
        fifthDTO.status = "Status One";
        fifthDTO.description = "A different description";
        fifthDTO.userStoryEffort = 1;
    }

    @AfterEach
    void tearDown() {
        firstDTO = null;
        secondDTO = null;
        thirdDTO = null;
        fourthDTO = null;
        fifthDTO = null;
    }

    @Test
    void equals_SameObject_True() {
        OutputUserStoryDTO anotherDTO = firstDTO;

        boolean result = firstDTO.equals(anotherDTO);

        assertTrue(result);
    }

    @Test
    void equals_ObjectsOfDifferentClasses_False() {
        Object someString = "This is not a User Story";

        boolean result = firstDTO.equals(someString);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsWithSameAttributes_True() {
        boolean result = firstDTO.equals(thirdDTO);

        assertTrue(result);
    }

    @Test
    void equals_ObjectsWithDifferentUserStoryCode_False() {
        boolean result = firstDTO.equals(secondDTO);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsWithDifferentStatusName_True() {
        boolean result = firstDTO.equals(fourthDTO);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsWithDifferentDescription_True() {
        boolean result = firstDTO.equals(fifthDTO);

        assertFalse(result);
    }

    @Test
    void hashCode_EqualObjects_Equals() {
        OutputUserStoryDTO anotherDTO = firstDTO;

        int firstHashCode = firstDTO.hashCode();
        int secondHashCode = anotherDTO.hashCode();

        assertEquals(firstHashCode, secondHashCode);
    }

    @Test
    void hashCode_DifferentObjects_NotEquals() {
        int firstHashCode = firstDTO.hashCode();
        int secondHashCode = secondDTO.hashCode();

        assertNotEquals(firstHashCode, secondHashCode);
    }

}