package org.switch2022.project.utils.dto;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.domain.valueobject.SprintCode;
import org.switch2022.project.domain.valueobject.SprintDuration;
import org.switch2022.project.domain.valueobject.SprintID;
import org.switch2022.project.utils.dto.CreatedSprintDTO;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class CreatedSprintDTOTest {
    private static CreatedSprintDTO createdSprintDTO;

    @BeforeAll
    static void setup() {
        createdSprintDTO = new CreatedSprintDTO();
        ProjectCode projectCode = new ProjectCode("P1");
        SprintCode sprintCode = new SprintCode("S1");
        SprintID sprintID = new SprintID(projectCode, sprintCode);
        SprintDuration sprintDuration = new SprintDuration(4);
        LocalDate startDate = LocalDate.of(2024, 1, 1);

        createdSprintDTO.sprintID = sprintID;
        createdSprintDTO.sprintDuration = sprintDuration;
        createdSprintDTO.startDate = startDate;
    }

    @AfterAll
    static void tearDown() {
        createdSprintDTO = null;
    }

    @Test
    void equals_SameObject_True() {
        CreatedSprintDTO sameObject = createdSprintDTO;

        boolean result = createdSprintDTO.equals(sameObject);

        assertTrue(result);
    }

    @Test
    void equals_ObjectIsNull_False() {
        CreatedSprintDTO nullObject = null;

        boolean result = createdSprintDTO.equals(nullObject);

        assertFalse(result);
    }

    @Test
    void equals_EqualObject_True() {
        SprintID sprintID = new SprintID(new ProjectCode("P1"), new SprintCode("S1"));
        SprintDuration sprintDuration = new SprintDuration(4);
        LocalDate startDate = LocalDate.of(2024, 1, 1);

        CreatedSprintDTO createdSprintDTOTwo = new CreatedSprintDTO();
        createdSprintDTOTwo.sprintID = sprintID;
        createdSprintDTOTwo.sprintDuration = sprintDuration;
        createdSprintDTOTwo.startDate = startDate;

        boolean result = createdSprintDTO.equals(createdSprintDTOTwo);

        assertTrue(result);
    }


    @Test
    void equals_DifferentProjectCode_False() {
        SprintID sprintID = new SprintID(new ProjectCode("P2"), new SprintCode("S1"));
        SprintDuration sprintDuration = new SprintDuration(4);
        LocalDate startDate = LocalDate.of(2024, 1, 1);

        CreatedSprintDTO createdSprintDTOTwo = new CreatedSprintDTO();
        createdSprintDTOTwo.sprintID = sprintID;
        createdSprintDTOTwo.sprintDuration = sprintDuration;
        createdSprintDTOTwo.startDate = startDate;

        boolean result = createdSprintDTO.equals(createdSprintDTOTwo);

        assertFalse(result);
    }

    @Test
    void equals_DifferentSprintCode_False() {
        SprintID sprintID = new SprintID(new ProjectCode("P1"), new SprintCode("S2"));
        SprintDuration sprintDuration = new SprintDuration(4);
        LocalDate startDate = LocalDate.of(2024, 1, 1);

        CreatedSprintDTO createdSprintDTOTwo = new CreatedSprintDTO();
        createdSprintDTOTwo.sprintID = sprintID;
        createdSprintDTOTwo.sprintDuration = sprintDuration;
        createdSprintDTOTwo.startDate = startDate;

        boolean result = createdSprintDTO.equals(createdSprintDTOTwo);

        assertFalse(result);
    }

    @Test
    void equals_DifferentSprintDuration_True() {
        SprintID sprintID = new SprintID(new ProjectCode("P1"), new SprintCode("S1"));
        SprintDuration sprintDuration = new SprintDuration(2);
        LocalDate startDate = LocalDate.of(2024, 1, 1);

        CreatedSprintDTO createdSprintDTOTwo = new CreatedSprintDTO();
        createdSprintDTOTwo.sprintID = sprintID;
        createdSprintDTOTwo.sprintDuration = sprintDuration;
        createdSprintDTOTwo.startDate = startDate;

        boolean result = createdSprintDTO.equals(createdSprintDTOTwo);

        assertTrue(result);
    }

    @Test
    void equals_DifferentStartDate_True() {
        SprintID sprintID = new SprintID(new ProjectCode("P1"), new SprintCode("S1"));
        SprintDuration sprintDuration = new SprintDuration(4);
        LocalDate startDate = LocalDate.of(2025, 1, 1);

        CreatedSprintDTO createdSprintDTOTwo = new CreatedSprintDTO();
        createdSprintDTOTwo.sprintID = sprintID;
        createdSprintDTOTwo.sprintDuration = sprintDuration;
        createdSprintDTOTwo.startDate = startDate;

        boolean result = createdSprintDTO.equals(createdSprintDTOTwo);

        assertTrue(result);
    }

    @Test
    void hashCode_SameObject_True() {
        CreatedSprintDTO createdSprintDTOTwo = createdSprintDTO;

        int firstHash = createdSprintDTOTwo.hashCode();
        int secondHash = createdSprintDTO.hashCode();

        assertEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_SameInformation_Equals() {
        ProjectCode projectCodeTwo = new ProjectCode("P1");
        SprintCode sprintCodeTwo = new SprintCode("S1");
        SprintID sprintIDTwo = new SprintID(projectCodeTwo, sprintCodeTwo);
        SprintDuration sprintDurationTwo = new SprintDuration(4);
        LocalDate startDateTwo = LocalDate.of(2024,1,1);

        CreatedSprintDTO createdSprintDTOTwo = new CreatedSprintDTO();
        createdSprintDTOTwo.sprintID = sprintIDTwo;
        createdSprintDTOTwo.startDate = startDateTwo;
        createdSprintDTOTwo.sprintDuration = sprintDurationTwo;

        int firstHash = createdSprintDTO.hashCode();
        int secondHash = createdSprintDTOTwo.hashCode();

        assertEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_DifferentProjectCode_NotEquals() {
        ProjectCode projectCodeTwo = new ProjectCode("P3");
        SprintCode sprintCodeTwo = new SprintCode("S1");
        SprintID sprintIDTwo = new SprintID(projectCodeTwo, sprintCodeTwo);
        SprintDuration sprintDurationTwo = new SprintDuration(4);
        LocalDate startDateTwo = LocalDate.of(2023,1,1);

        CreatedSprintDTO createdSprintDTOTwo = new CreatedSprintDTO();
        createdSprintDTOTwo.sprintID = sprintIDTwo;
        createdSprintDTOTwo.startDate = startDateTwo;
        createdSprintDTOTwo.sprintDuration = sprintDurationTwo;

        int firstHash = createdSprintDTO.hashCode();
        int secondHash = createdSprintDTOTwo.hashCode();

        assertNotEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_DifferentSprintCode_NotEquals() {
        ProjectCode projectCodeTwo = new ProjectCode("P1");
        SprintCode sprintCodeTwo = new SprintCode("S2");
        SprintID sprintIDTwo = new SprintID(projectCodeTwo, sprintCodeTwo);
        SprintDuration sprintDurationTwo = new SprintDuration(4);
        LocalDate startDateTwo = LocalDate.of(2023,1,1);

        CreatedSprintDTO createdSprintDTOTwo = new CreatedSprintDTO();
        createdSprintDTOTwo.sprintID = sprintIDTwo;
        createdSprintDTOTwo.startDate = startDateTwo;
        createdSprintDTOTwo.sprintDuration = sprintDurationTwo;

        int firstHash = createdSprintDTO.hashCode();
        int secondHash = createdSprintDTOTwo.hashCode();

        assertNotEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_DifferentSprintDuration_Equals() {
        ProjectCode projectCodeTwo = new ProjectCode("P1");
        SprintCode sprintCodeTwo = new SprintCode("S1");
        SprintID sprintIDTwo = new SprintID(projectCodeTwo, sprintCodeTwo);
        SprintDuration sprintDurationTwo = new SprintDuration(1);
        LocalDate startDateTwo = LocalDate.of(2024,1,1);

        CreatedSprintDTO createdSprintDTOTwo = new CreatedSprintDTO();
        createdSprintDTOTwo.sprintID = sprintIDTwo;
        createdSprintDTOTwo.startDate = startDateTwo;
        createdSprintDTOTwo.sprintDuration = sprintDurationTwo;

        int firstHash = createdSprintDTO.hashCode();
        int secondHash = createdSprintDTOTwo.hashCode();

        assertEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_DifferentStartDate_Equals() {
        ProjectCode projectCodeTwo = new ProjectCode("P1");
        SprintCode sprintCodeTwo = new SprintCode("S1");
        SprintID sprintIDTwo = new SprintID(projectCodeTwo, sprintCodeTwo);
        SprintDuration sprintDurationTwo = new SprintDuration(4);
        LocalDate startDateTwo = LocalDate.of(2025,1,1);

        CreatedSprintDTO createdSprintDTOTwo = new CreatedSprintDTO();
        createdSprintDTOTwo.sprintID = sprintIDTwo;
        createdSprintDTOTwo.startDate = startDateTwo;
        createdSprintDTOTwo.sprintDuration = sprintDurationTwo;

        int firstHash = createdSprintDTO.hashCode();
        int secondHash = createdSprintDTOTwo.hashCode();

        assertEquals(firstHash, secondHash);
    }

}