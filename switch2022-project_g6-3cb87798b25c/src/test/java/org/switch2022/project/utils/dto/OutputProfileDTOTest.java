package org.switch2022.project.utils.dto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.dto.OutputProfileDTO;

import static org.junit.jupiter.api.Assertions.*;

class OutputProfileDTOTest {

    private static OutputProfileDTO outputProfileDTO;

    @BeforeAll
    static void init() {
        outputProfileDTO = new OutputProfileDTO();
        outputProfileDTO.profileName = "a new profile";
    }

    @AfterAll
    static void tearDown() {
        outputProfileDTO = null;
    }

    @Test
    void equals_SameObject_True() {
        OutputProfileDTO secondDTO = outputProfileDTO;

        boolean result = outputProfileDTO.equals(secondDTO);

        assertTrue(result);
    }

    @Test
    void equals_ObjectIsNull_False() {
        OutputProfileDTO secondDTO = null;

        boolean result = outputProfileDTO.equals(secondDTO);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfDifferentClass_False() {
        String notDTO = "Not a DTO";

        boolean result = outputProfileDTO.equals(notDTO);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentValue_False() {
        OutputProfileDTO secondDTO = new OutputProfileDTO();
        secondDTO.profileName = "another profile";

        boolean result = outputProfileDTO.equals(secondDTO);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfSameClassAndSameValue_True() {
        OutputProfileDTO secondDTO = new OutputProfileDTO();
        secondDTO.profileName = "a new profile";

        boolean result = outputProfileDTO.equals(secondDTO);
        assertTrue(result);
    }

    @Test
    void hashCode_SameObject_True() {
        OutputProfileDTO secondDTO = outputProfileDTO;

        int firstHash = outputProfileDTO.hashCode();
        int secondHash = secondDTO.hashCode();

        assertEquals(firstHash, secondHash);
    }

    @Test
    void hashCode_DifferentObject_False() {
        OutputProfileDTO secondDTO = new OutputProfileDTO();
        secondDTO.profileName = "another profile";

        int firstHash = outputProfileDTO.hashCode();
        int secondHash = secondDTO.hashCode();

        assertNotEquals(firstHash, secondHash);
    }

}