package org.switch2022.project.utils.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.dto.OutputProjectDTO;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class OutputProjectDTOTest {

    private static OutputProjectDTO dtoOne;

    @BeforeAll
    static void init() {
        dtoOne = new OutputProjectDTO(
                "P1",
                "TestProject",
                "TestDescription",
                "123456789",
                "TestSector",
                "TestTypology",
                Collections.emptyList(),
                "PLANNED",
                Map.of("PLANNED", "2022-01-01"),
                1,
                2,
                3.5,
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 3, 3)
        );
    }

    @Test
    void testEquals_sameObject_True() {
        OutputProjectDTO otherDto = dtoOne;
        boolean result = dtoOne.equals(otherDto);
        assertTrue(result);
    }

    @Test
    void testEquals_NullObject_False() {
        boolean result = dtoOne.equals(null);
        assertFalse(result);
    }


    @Test
    void equals_ObjectOfDifferentClass_False() {
        String testObject = "DifferentClass";

        assertFalse(dtoOne.equals(testObject));
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentProjectCode_False() {
        OutputProjectDTO dtoTwo = new OutputProjectDTO(
                "P2",
                "TestProject",
                "TestDescription",
                "123456789",
                "TestSector",
                "TestTypology",
                Collections.emptyList(),
                "PLANNED",
                Map.of("PLANNED", "2022-01-01"),
                1,
                2,
                3.5,
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 3, 3)
        );

        boolean result = dtoOne.equals(dtoTwo);
        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentProjectName_False() {
        OutputProjectDTO dtoTwo = new OutputProjectDTO(
                "P1",
                "OtherProject",
                "TestDescription",
                "123456789",
                "TestSector",
                "TestTypology",
                Collections.emptyList(),
                "PLANNED",
                Map.of("PLANNED", "2022-01-01"),
                1,
                2,
                3.5,
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 3, 3)
        );

        boolean result = dtoOne.equals(dtoTwo);
        assertFalse(result);

    }

    @Test
    void equals_ObjectsOfSameClassButDifferentDescription_False() {
        OutputProjectDTO dtoTwo = new OutputProjectDTO(
                "P1",
                "TestProject",
                "OtherDescription",
                "123456789",
                "TestSector",
                "TestTypology",
                Collections.emptyList(),
                "PLANNED",
                Map.of("PLANNED", "2022-01-01"),
                1,
                2,
                3.5,
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 3, 3)
        );

        boolean result = dtoOne.equals(dtoTwo);
        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentTaxIdentificationNumber_False() {
        OutputProjectDTO dtoTwo = new OutputProjectDTO(
                "P1",
                "TestProject",
                "TestDescription",
                "238199576",
                "TestSector",
                "TestTypology",
                Collections.emptyList(),
                "PLANNED",
                Map.of("PLANNED", "2022-01-01"),
                1,
                2,
                3.5,
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 3, 3)
        );

        boolean result = dtoOne.equals(dtoTwo);
        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentBusinessSectorName_False() {
        OutputProjectDTO dtoTwo = new OutputProjectDTO(
                "P1",
                "TestProject",
                "TestDescription",
                "123456789",
                "OtherSector",
                "TestTypology",
                Collections.emptyList(),
                "PLANNED",
                Map.of("PLANNED", "2022-01-01"),
                1,
                2,
                3.5,
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 3, 3)
        );

        boolean result = dtoOne.equals(dtoTwo);
        assertFalse(result);
    }


    @Test
    void equals_ObjectsOfSameClassButDifferentTypology_False() {
        OutputProjectDTO dtoTwo = new OutputProjectDTO(
                "P1",
                "TestProject",
                "TestDescription",
                "123456789",
                "TestSector",
                "OtherTypology",
                Collections.emptyList(),
                "PLANNED",
                Map.of("PLANNED", "2022-01-01"),
                1,
                2,
                3.5,
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 3, 3)
        );

        boolean result = dtoOne.equals(dtoTwo);
        assertFalse(result);
    }

    @Test
    void equals_ObjectOfSameClassAndSameValue_True() {
        OutputProjectDTO dtoTwo = new OutputProjectDTO(
                "P1",
                "TestProject",
                "TestDescription",
                "123456789",
                "TestSector",
                "TestTypology",
                Collections.emptyList(),
                "PLANNED",
                Map.of("PLANNED", "2022-01-01"),
                1,
                2,
                3.5,
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 3, 3)
        );

        boolean result = dtoOne.equals(dtoTwo);
        assertTrue(result);
    }

    @Test
    void testHashCode_Equal() {
        OutputProjectDTO dtoTwo = new OutputProjectDTO(
                "P1",
                "TestProject",
                "TestDescription",
                "123456789",
                "TestSector",
                "TestTypology",
                Collections.emptyList(),
                "PLANNED",
                Map.of("PLANNED", "2022-01-01"),
                1,
                2,
                3.5,
                LocalDate.of(2022, 1, 1),
                LocalDate.of(2022, 3, 3)
        );

        int one = dtoOne.hashCode();
        int two = dtoTwo.hashCode();

        assertEquals(one, two);
    }

    @Test
    void testHashCode_NotEqual() {
        int expected = Objects.hash(dtoOne);

        int result = dtoOne.hashCode();

        assertNotEquals(expected, result);
    }
}
