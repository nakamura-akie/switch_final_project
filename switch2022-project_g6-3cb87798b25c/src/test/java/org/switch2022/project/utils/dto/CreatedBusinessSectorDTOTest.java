package org.switch2022.project.utils.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.dto.CreatedBusinessSectorDTO;

import static org.junit.jupiter.api.Assertions.*;

class CreatedBusinessSectorDTOTest {
    static CreatedBusinessSectorDTO dtoOne;

    @BeforeAll
    static void init() {
        dtoOne = new CreatedBusinessSectorDTO();
        dtoOne.businessSectorNameValue = "sector11";
    }

    @Test
    void testEquals_sameObject_Equals() {
        CreatedBusinessSectorDTO otherDto = dtoOne;
        boolean result = dtoOne.equals(otherDto);
        assertTrue(result);
    }

    @Test
    void equals_ObjectOfDifferentClass_NotEqual() {
        Object testObject = new Object();
        boolean result = dtoOne.equals(testObject);
        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentEmail_NotEqual() {
        CreatedBusinessSectorDTO dtoTwo = new CreatedBusinessSectorDTO();
        dtoTwo.businessSectorNameValue = "sector12";
        boolean result = dtoOne.equals(dtoTwo);
        assertFalse(result);
    }

    @Test
    void equals_ObjectOfSameClassAndSameValue_Equals() {
        CreatedBusinessSectorDTO dtoTwo = new CreatedBusinessSectorDTO();
        dtoTwo.businessSectorNameValue = "sector11";
        boolean result = dtoOne.equals(dtoTwo);
        assertTrue(result);
    }

    @Test
    void testHashCode_Equal() {
        CreatedBusinessSectorDTO dtoTwo = new CreatedBusinessSectorDTO();
        dtoTwo.businessSectorNameValue = "sector11";
        int one = dtoOne.hashCode();
        int two = dtoTwo.hashCode();
        assertEquals(one, two);
    }

    @Test
    void testHashCode_NotEqual() {
        CreatedBusinessSectorDTO dtoTwo = new CreatedBusinessSectorDTO();
        dtoTwo.businessSectorNameValue = "sector12";
        int one = dtoOne.hashCode();
        int two = dtoTwo.hashCode();
        assertNotEquals(one, two);
    }
}