package org.switch2022.project.datamodel;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.BusinessSectorName;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class BusinessSectorJPATest {

    private static String businessSectorName;
    private static BusinessSectorJPA businessSectorJPA;

    @BeforeAll
    static void init() {
        businessSectorName = "Technology";
        businessSectorJPA = new BusinessSectorJPA(businessSectorName);
    }

    @AfterAll
    static void tearDown() {
        businessSectorName = null;
        businessSectorJPA = null;
    }

    @Test
    void protectedConstructor_ValidEmptyConstructor_True() {
        BusinessSectorJPA businessSectorJPA = new BusinessSectorJPA();
        assertTrue(businessSectorJPA instanceof BusinessSectorJPA);
    }

    @Test
    void getBusinessSectorName_ValidString_True() {
        String businessSectorNameOne = businessSectorJPA.getBusinessSectorName();
        String businessSectorNameTwo = "Technology";

        boolean result = businessSectorNameOne.equals(businessSectorNameTwo);

        assertTrue(result);
    }

    @Test
    void equals_SameObject_True() {
        BusinessSectorJPA anotherBusinessSector = businessSectorJPA;

        boolean result = businessSectorJPA.equals(anotherBusinessSector);

        assertTrue(result);
    }

    @Test
    void equals_SameObjectDifferentValue_False() {
        BusinessSectorJPA newBusinessSectorJPA = new BusinessSectorJPA("String");

        boolean result = businessSectorJPA.equals(newBusinessSectorJPA);

        assertFalse(result);
    }

    @Test
    void equals_NullObject_False() {
        BusinessSectorJPA nullBusinessSector = null;

        boolean result = businessSectorJPA.equals(nullBusinessSector);

        assertFalse(result);
    }

    @Test
    void equals_DifferentObjects_False() {
        String businessSectorString = "SpongeBob";

        boolean result = businessSectorJPA.equals(businessSectorString);

        assertFalse(result);
    }

    @Test
    void hashCode_SameObject_Equals() {
        BusinessSectorJPA newBusinessSectorJPA = new BusinessSectorJPA("Technology");

        int expected = Objects.hashCode(businessSectorJPA);
        int result = newBusinessSectorJPA.hashCode();

        assertEquals(expected, result);
    }

    @Test
    void hashCode_DifferentObjects_NotEquals() {
        BusinessSectorJPA newBusinessSectorJPA = new BusinessSectorJPA("Something else");

        int expected = Objects.hashCode(businessSectorJPA);
        int result = newBusinessSectorJPA.hashCode();

        assertNotEquals(expected, result);
    }
}