package org.switch2022.project.domain.businesssector;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.BusinessSectorName;

import static org.junit.jupiter.api.Assertions.*;

class BusinessSectorTest {

    BusinessSector businessSectorTest;
    BusinessSector businessSectorTestTwo;

    BusinessSectorName businessSectorNameTest;
    BusinessSectorName businessSectorNameTestTwo;

    @BeforeEach
    void init() {
        //businessSectorTestTwo e businessSectorNameTestTwo só são usados 1x
        businessSectorNameTest = new BusinessSectorName("Technology");
        businessSectorNameTestTwo = new BusinessSectorName("Finance");

        businessSectorTest = new BusinessSector(businessSectorNameTest);
        businessSectorTestTwo = new BusinessSector(businessSectorNameTestTwo);
    }

    @AfterEach
    void tearDown() {
        businessSectorNameTest = null;
        businessSectorNameTestTwo = null;

        businessSectorTest = null;
        businessSectorTestTwo = null;
    }


    @Test
    void constructor_NullBusinessSectorName_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new BusinessSector(null));

        assertEquals("Invalid Business Sector name.", exception.getMessage());
    }

    @Test
    void identity_SameBusinessSector_Equals() {
        BusinessSectorName expected = businessSectorNameTest;
        BusinessSectorName result = businessSectorTest.identity();

        assertEquals(result, expected);
    }

    @Test
    void getBusinessSectorName_ReturnsBusinessSectorString_Equals() {
        String expected = "Technology";
        String result = businessSectorTest.getBusinessSectorName();

        assertEquals(expected, result);
    }

    @Test
    void identity_DifferentBusinessSectors_False() {
        BusinessSectorName expected = businessSectorNameTestTwo;
        BusinessSectorName result = businessSectorTest.identity();

        assertNotEquals(result, expected);
    }

    @Test
    void sameAs_SameBusinessSectorName_ReturnTrue() {
        boolean result = businessSectorTest.sameAs(businessSectorTest);

        assertTrue(result);
    }

    @Test
    void sameAs_DifferentBusinessSectorName_ReturnFalse() {
        boolean result = businessSectorTest.sameAs(businessSectorTestTwo);

        assertFalse(result);
    }

    @Test
    void sameAs_DifferentObjects_ReturnFalse() {
        String businessSectorString = "Invalid Name";
        boolean result = businessSectorTest.sameAs(businessSectorString);

        assertFalse(result);
    }

    @Test
    void equals_SameObjects_Equals() {
        assertEquals(businessSectorTest, businessSectorTest);
    }

    @Test
    void equals_SameObjectTypeButDifferentNames_NotEquals() {
        assertNotEquals(businessSectorTest, businessSectorTestTwo);
    }

    @Test
    void equals_ObjectsWithSameName_ReturnsTrue() {
        BusinessSector businessSector = new BusinessSector(businessSectorNameTest);
        boolean result = businessSectorTest.equals(businessSector);

        assertTrue(result);
    }

    @Test
    void equals_DifferentObjects_ReturnsFalse() {
        boolean result = businessSectorTest.equals(businessSectorNameTest);
        assertFalse(result);
    }

    @Test
    void equals_ObjectEqualsNull_ReturnFalse() {
        BusinessSector nullBusinessSector = null;

        boolean result = businessSectorTest.equals(nullBusinessSector);
        assertFalse(result);
    }

    @Test
    void hashCode_SameObject_Equals() {
        int hashCodeOne = businessSectorTest.hashCode();
        int hashCodeTwo = businessSectorTest.hashCode();

        assertEquals(hashCodeOne, hashCodeTwo);
    }

    @Test
    void hashCode_DifferentObjects_NotEquals() {
        int hashCodeOne = businessSectorTest.hashCode();
        int hashCodeTwo = businessSectorTestTwo.hashCode();

        assertNotEquals(hashCodeOne, hashCodeTwo);
    }
}