package org.switch2022.project.utils.dto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.*;
import org.switch2022.project.utils.dto.SprintStatusDTO;

import static org.junit.jupiter.api.Assertions.*;

class SprintStatusDTOTest {

    private static SprintStatusDTO sprintStatusDTO;

    @BeforeAll
    static void setup() {
        sprintStatusDTO = new SprintStatusDTO();
        ProjectCode projectCode = new ProjectCode("P1");
        SprintCode sprintCode = new SprintCode("S1");
        SprintID sprintID = new SprintID(projectCode, sprintCode);

        sprintStatusDTO.sprintID = sprintID;
        sprintStatusDTO.sprintStatus = SprintStatus.PLANNED;
    }

    @AfterAll
    static void tearDown() {
        sprintStatusDTO = null;
    }

    @Test
    void equals_SameObject_True() {
        SprintStatusDTO sameObject = sprintStatusDTO;

        boolean result = sprintStatusDTO.equals(sameObject);

        assertTrue(result);
    }

    @Test
    void equals_ObjectIsNull_False() {
        SprintStatusDTO nullObject = null;

        boolean result = sprintStatusDTO.equals(nullObject);

        assertFalse(result);
    }

    @Test
    void equals_EqualObject_True() {
        SprintID sprintID = new SprintID(new ProjectCode("P1"), new SprintCode("S1"));

        SprintStatusDTO sprintStatusDTOTwo = new SprintStatusDTO();
        sprintStatusDTOTwo.sprintID = sprintID;
        sprintStatusDTOTwo.sprintStatus = SprintStatus.PLANNED;

        boolean result = sprintStatusDTO.equals(sprintStatusDTOTwo);

        assertTrue(result);
    }


    @Test
    void equals_DifferentProjectCode_False() {
        SprintID sprintID = new SprintID(new ProjectCode("P2"), new SprintCode("S1"));

        SprintStatusDTO sprintStatusDTOTwo = new SprintStatusDTO();
        sprintStatusDTOTwo.sprintID = sprintID;
        sprintStatusDTOTwo.sprintStatus = SprintStatus.PLANNED;

        boolean result = sprintStatusDTO.equals(sprintStatusDTOTwo);

        assertFalse(result);
    }

    @Test
    void equals_DifferentSprintCode_False() {
        SprintID sprintID = new SprintID(new ProjectCode("P1"), new SprintCode("S2"));

        SprintStatusDTO sprintStatusDTOTwo = new SprintStatusDTO();
        sprintStatusDTOTwo.sprintID = sprintID;
        sprintStatusDTOTwo.sprintStatus = SprintStatus.PLANNED;

        boolean result = sprintStatusDTO.equals(sprintStatusDTOTwo);

        assertFalse(result);
    }

    @Test
    void hashCode_SameObject_True() {
        SprintStatusDTO sprintStatusDTOTwo = sprintStatusDTO;

        int firstHash = sprintStatusDTOTwo.hashCode();
        int secondHash = sprintStatusDTO.hashCode();

        assertEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_SameInformation_Equals() {
        ProjectCode projectCodeTwo = new ProjectCode("P1");
        SprintCode sprintCodeTwo = new SprintCode("S1");
        SprintID sprintIDTwo = new SprintID(projectCodeTwo, sprintCodeTwo);

        SprintStatusDTO sprintStatusDTOTwo = new SprintStatusDTO();
        sprintStatusDTOTwo.sprintID = sprintIDTwo;
        sprintStatusDTOTwo.sprintStatus = SprintStatus.PLANNED;

        int firstHash = sprintStatusDTO.hashCode();
        int secondHash = sprintStatusDTOTwo.hashCode();

        assertEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_DifferentProjectCode_NotEquals() {
        ProjectCode projectCodeTwo = new ProjectCode("P3");
        SprintCode sprintCodeTwo = new SprintCode("S1");
        SprintID sprintIDTwo = new SprintID(projectCodeTwo, sprintCodeTwo);

        SprintStatusDTO sprintStatusDTOTwo = new SprintStatusDTO();
        sprintStatusDTOTwo.sprintID = sprintIDTwo;
        sprintStatusDTOTwo.sprintStatus = SprintStatus.PLANNED;

        int firstHash = sprintStatusDTO.hashCode();
        int secondHash = sprintStatusDTOTwo.hashCode();

        assertNotEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_DifferentSprintCode_NotEquals() {
        ProjectCode projectCodeTwo = new ProjectCode("P1");
        SprintCode sprintCodeTwo = new SprintCode("S2");
        SprintID sprintIDTwo = new SprintID(projectCodeTwo, sprintCodeTwo);

        SprintStatusDTO sprintStatusDTOTwo = new SprintStatusDTO();
        sprintStatusDTOTwo.sprintID = sprintIDTwo;
        sprintStatusDTOTwo.sprintStatus = SprintStatus.PLANNED;

        int firstHash = sprintStatusDTO.hashCode();
        int secondHash = sprintStatusDTOTwo.hashCode();

        assertNotEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_DifferentSprintStatus_Equals() {
        ProjectCode projectCodeTwo = new ProjectCode("P1");
        SprintCode sprintCodeTwo = new SprintCode("S1");
        SprintID sprintIDTwo = new SprintID(projectCodeTwo, sprintCodeTwo);

        SprintStatusDTO sprintStatusDTOTwo = new SprintStatusDTO();
        sprintStatusDTOTwo.sprintID = sprintIDTwo;
        sprintStatusDTOTwo.sprintStatus = SprintStatus.CLOSED;

        int firstHash = sprintStatusDTO.hashCode();
        int secondHash = sprintStatusDTOTwo.hashCode();

        assertEquals(firstHash, secondHash);
    }

}