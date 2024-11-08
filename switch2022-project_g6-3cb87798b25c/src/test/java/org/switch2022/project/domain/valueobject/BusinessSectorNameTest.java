package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.exception.EmptyConstructorArgumentException;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class BusinessSectorNameTest {

    BusinessSectorName sectorName;

    @BeforeEach
    void init() {
        sectorName = new BusinessSectorName("Technology");
    }

    @AfterEach
    void tearDown() {
        sectorName = null;
    }

    @Test
    void constructor_NullBusinessSectorName_ThrowsException() {
        Exception exception = assertThrows(NullConstructorArgumentException.class, () ->
                new BusinessSectorName(null));

        assertEquals("Business Sector cannot be null", exception.getMessage());
    }

    @Test
    void constructor_BlankBusinessSectorName_ThrowsException() {
        Exception exception = assertThrows(EmptyConstructorArgumentException.class, () ->
                new BusinessSectorName(""));

        assertEquals("Business Sector cannot be empty", exception.getMessage());
    }

    @Test
    void toString_ValidString_Equals() {
        String expected = "Technology";

        String result = sectorName.getBusinessSectorNameValue();

        assertEquals(expected, result);
    }


    @Test
    void equals_SameObject_True() {
        BusinessSectorName otherBusinessSectorName = sectorName;

        boolean result = sectorName.equals(otherBusinessSectorName);

        assertTrue(result);
    }

    @Test
    void equals_SameObjectDifferentValue_False() {
        BusinessSectorName newBusinessSector = new BusinessSectorName("Finance");

        boolean result = sectorName.equals(newBusinessSector);

        assertFalse(result);
    }

    @Test
    void equals_NullObject_False() {
        BusinessSectorName nullBusinessSector = null;

        boolean result = sectorName.equals(nullBusinessSector);

        assertFalse(result);
    }

    @Test
    void equals_DifferentObjects_False() {
        String businessSectorString = "Bart Simpson";

        boolean result = sectorName.equals(businessSectorString);

        assertFalse(result);
    }

    @Test
    void hashCode_SameObject_Equals() {
        BusinessSectorName newBusinessSectorName = new BusinessSectorName("Technology");

        int expected = Objects.hashCode(sectorName);
        int result = newBusinessSectorName.hashCode();

        assertEquals(expected, result);
    }

    @Test
    void hashCode_DifferentObjects_NotEquals() {
        BusinessSectorName DifferentBusinessSectorName = new BusinessSectorName("Finance");

        int expected = Objects.hashCode(sectorName);
        int result = DifferentBusinessSectorName.hashCode();

        assertNotEquals(expected, result);
    }

}