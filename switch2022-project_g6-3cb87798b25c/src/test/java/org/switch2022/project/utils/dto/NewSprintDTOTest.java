package org.switch2022.project.utils.dto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.ProjectCode;
import org.switch2022.project.utils.dto.NewCustomerDTO;
import org.switch2022.project.utils.dto.NewSprintDTO;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class NewSprintDTOTest {

    private static NewSprintDTO newSprintDTO;

    @BeforeAll
    static void setup() {
        newSprintDTO = new NewSprintDTO();
        ProjectCode projectCode = new ProjectCode("P1");
        LocalDate startDate = LocalDate.of(2023,1,1);
        newSprintDTO.projectCode = projectCode;
        newSprintDTO.startDate= startDate;
    }

    @AfterAll
    static void tearDown() {
        newSprintDTO = null;
    }

    @Test
    void equals_SameObject_True() {
        NewSprintDTO sameObject = newSprintDTO;

        boolean result = newSprintDTO.equals(sameObject);

        assertTrue(result);
    }

    @Test
    void equals_ObjectIsNull_False() {
        NewCustomerDTO nullObject = null;

        boolean result = newSprintDTO.equals(nullObject);

        assertFalse(result);
    }


    @Test
    void equals_EqualObject_True() {
        ProjectCode projectCode = new ProjectCode("P1");
        LocalDate startDate = LocalDate.of(2023,1,1);

        NewSprintDTO newSprintDTOTwo = new NewSprintDTO();
        newSprintDTOTwo.projectCode = projectCode;
        newSprintDTOTwo.startDate= startDate;

        boolean result = newSprintDTO.equals(newSprintDTOTwo);

        assertTrue(result);
    }

    @Test
    void equals_DifferentProjectCode_False() {
        ProjectCode projectCode = new ProjectCode("P2");
        LocalDate startDate = LocalDate.of(2023,1,1);

        NewSprintDTO newSprintDTOTwo = new NewSprintDTO();
        newSprintDTOTwo.projectCode = projectCode;
        newSprintDTOTwo.startDate= startDate;

        boolean result = newSprintDTO.equals(newSprintDTOTwo);

        assertFalse(result);
    }


    @Test
    void equals_DifferentStartDate_False() {
        ProjectCode projectCodeTwo = new ProjectCode("P1");
        LocalDate startDateTwo = LocalDate.of(2025,1,1);

        NewSprintDTO newSprintDTOTwo = new NewSprintDTO();
        newSprintDTOTwo.projectCode = projectCodeTwo;
        newSprintDTOTwo.startDate= startDateTwo;

        boolean result = newSprintDTO.equals(newSprintDTOTwo);

        assertFalse(result);
    }

    @Test
    void equals_NullObject_False() {

        boolean result = newSprintDTO == null;

        assertFalse(result);

    }

    @Test
    void hashCode_SameObject_True() {
        NewSprintDTO newSprintDTOTwo = newSprintDTO;

        int firstHash = newSprintDTO.hashCode();
        int secondHash = newSprintDTOTwo.hashCode();

        assertEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_SameProjectCodeAndStartDate_Equals() {
        ProjectCode projectCodeTwo = new ProjectCode("P1");
        LocalDate startDateTwo = LocalDate.of(2023,1,1);

        NewSprintDTO newSprintDTOTwo = new NewSprintDTO();
        newSprintDTOTwo.projectCode = projectCodeTwo;
        newSprintDTOTwo.startDate= startDateTwo;

        int firstHash = newSprintDTO.hashCode();
        int secondHash = newSprintDTOTwo.hashCode();

        assertEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_SameProjectCodeDifferentStartDate_NotEquals() {
        ProjectCode projectCodeTwo = new ProjectCode("P1");
        LocalDate startDateTwo = LocalDate.of(2025,1,1);

        NewSprintDTO newSprintDTOTwo = new NewSprintDTO();
        newSprintDTOTwo.projectCode = projectCodeTwo;
        newSprintDTOTwo.startDate= startDateTwo;

        int firstHash = newSprintDTO.hashCode();
        int secondHash = newSprintDTOTwo.hashCode();

        assertNotEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_DifferentProjectCode_False() {
        ProjectCode projectCodeTwo = new ProjectCode("P2");
        LocalDate startDateTwo = LocalDate.of(2023, 1, 1);

        NewSprintDTO newSprintDTOTwo = new NewSprintDTO();
        newSprintDTOTwo.projectCode = projectCodeTwo;
        newSprintDTOTwo.startDate = startDateTwo;

        int firstHash = newSprintDTO.hashCode();
        int secondHash = newSprintDTOTwo.hashCode();

        assertNotEquals(firstHash, secondHash);
    }

}