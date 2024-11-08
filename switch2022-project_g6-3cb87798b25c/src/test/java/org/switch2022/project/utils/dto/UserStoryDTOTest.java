package org.switch2022.project.utils.dto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.Description;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.UserStoryStatus;
import org.switch2022.project.domain.valueobject.UserStoryCode;
import org.switch2022.project.utils.dto.UserStoryDTO;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class UserStoryDTOTest {

    private static UserStoryDTO userStoryDTO;

    @BeforeAll
    static void setup() {
        userStoryDTO = new UserStoryDTO();
        userStoryDTO.projectCode = new ProjectCode("P1");
        userStoryDTO.userStoryCode = new UserStoryCode( "US001");
        userStoryDTO.description = new Description( "Description");
        userStoryDTO.status = UserStoryStatus.OPEN;
    }

    @AfterAll
    static void tearDown() {
        userStoryDTO = null;
    }

    @Test
    void equals_SameObject_True() {
        UserStoryDTO secondDTO = userStoryDTO;

        boolean result = userStoryDTO.equals(secondDTO);
        assertTrue(result);
    }

    @Test
    void equals_ObjectIsNull_False() {
        UserStoryDTO secondDTO = null;

        boolean result = userStoryDTO.equals(secondDTO);
        assertFalse(result);
    }

    @Test
    void equals_ObjectOfDifferentClass_False() {
        String notDTO = "Not a DTO";

        boolean result = userStoryDTO.equals(notDTO);
        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentProjectCode_False() {
        UserStoryDTO secondDTO = new UserStoryDTO();
        secondDTO.projectCode = new ProjectCode("Different Code");
        secondDTO.userStoryCode = new UserStoryCode("US001");
        secondDTO.description = new Description("Description");
        secondDTO.status = UserStoryStatus.OPEN;

        boolean result = userStoryDTO.equals(secondDTO);
        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentUserStoryCode_False() {
        UserStoryDTO secondDTO = new UserStoryDTO();
        secondDTO.projectCode = new ProjectCode("P1");
        secondDTO.userStoryCode = new UserStoryCode("USOO5");
        secondDTO.description = new Description("Description");
        secondDTO.status = UserStoryStatus.OPEN;

        boolean result = userStoryDTO.equals(secondDTO);
        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentDescription_False() {
        UserStoryDTO secondDTO = new UserStoryDTO();
        secondDTO.projectCode = new ProjectCode("P1");
        secondDTO.userStoryCode = new UserStoryCode("US001");
        secondDTO.description = new Description("Another description");
        secondDTO.status = UserStoryStatus.OPEN;

        boolean result = userStoryDTO.equals(secondDTO);
        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentStatus_False() {
        UserStoryDTO secondDTO = new UserStoryDTO();
        secondDTO.projectCode = new ProjectCode("P1");
        secondDTO.userStoryCode = new UserStoryCode("US001");
        secondDTO.description = new Description("Description");
        secondDTO.status = UserStoryStatus.FINISHED;

        boolean result = userStoryDTO.equals(secondDTO);
        assertFalse(result);
    }

    @Test
    void equals_ObjectOfSameClassAndSameValue_True() {
        UserStoryDTO secondDTO = new UserStoryDTO();
        secondDTO.projectCode = new ProjectCode("P1");
        secondDTO.userStoryCode = new UserStoryCode("US001");
        secondDTO.description = new Description("Description");
        secondDTO.status = UserStoryStatus.OPEN;

        boolean result = userStoryDTO.equals(secondDTO);
        assertTrue(result);
    }

    @Test
    void hashCode_True() {
        int expected = Objects.hashCode(userStoryDTO);
        int result = userStoryDTO.hashCode();

        assertEquals(expected, result);
    }
}