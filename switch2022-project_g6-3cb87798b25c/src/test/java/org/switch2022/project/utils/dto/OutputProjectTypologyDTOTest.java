package org.switch2022.project.utils.dto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.dto.OutputProjectTypologyDTO;

import static org.junit.jupiter.api.Assertions.*;

class OutputProjectTypologyDTOTest {

    private static OutputProjectTypologyDTO outputProjectTypologyDTO;

    @BeforeAll
    static void init() {
        outputProjectTypologyDTO = new OutputProjectTypologyDTO();
        outputProjectTypologyDTO.projectTypologyValue = "Construction";
    }

    @AfterAll
    static void tearDown() {
        outputProjectTypologyDTO = null;
    }

    @Test
    void equals_SameObject_ReturnTrue() {
        OutputProjectTypologyDTO sameProjectTypologyDTO = outputProjectTypologyDTO;

        boolean result = outputProjectTypologyDTO.equals(sameProjectTypologyDTO);

        assertTrue(result);
    }

    @Test
    void equals_ObjectIsNull_ReturnFalse() {
        OutputProjectTypologyDTO nullProjectTypologyDTO = null;

        boolean result = outputProjectTypologyDTO.equals(nullProjectTypologyDTO);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfDifferentClass_ReturnFalse() {
        String string = "string";

        boolean result = outputProjectTypologyDTO.equals(string);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentValue_ReturnFalse() {
        OutputProjectTypologyDTO anotherDTO = new OutputProjectTypologyDTO();
        anotherDTO.projectTypologyValue = "another PT";

        boolean result = outputProjectTypologyDTO.equals(anotherDTO);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfSameClassAndSameValue_ReturnTrue() {
        OutputProjectTypologyDTO secondDTO = new OutputProjectTypologyDTO();
        secondDTO.projectTypologyValue = "Construction";

        boolean result = outputProjectTypologyDTO.equals(secondDTO);
        assertTrue(result);
    }

    @Test
    void hashCode_SameObject_ReturnTrue() {
        OutputProjectTypologyDTO secondDTO = outputProjectTypologyDTO;

        int firstHash = outputProjectTypologyDTO.hashCode();
        int secondHash = secondDTO.hashCode();

        assertEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_DifferentObject_False() {
        OutputProjectTypologyDTO secondDTO = new OutputProjectTypologyDTO();
        secondDTO.projectTypologyValue = "another DTO";

        int firstHash = outputProjectTypologyDTO.hashCode();
        int secondHash = secondDTO.hashCode();

        assertNotEquals(firstHash, secondHash);
    }
}