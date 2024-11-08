package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.exception.InvalidConstructorArgumentException;

import static org.junit.jupiter.api.Assertions.*;

class NumberPlannedSprintsTest {

    private NumberPlannedSprints numberPlannedSprints;

    @BeforeEach
    void init() {
        int numberOfPlannedSprintsValue = 6;
        numberPlannedSprints = new NumberPlannedSprints(numberOfPlannedSprintsValue);
    }

    @Test
    void constructor_NumberPlannedSprintsValue_ThrowsException() {
        assertDoesNotThrow(() -> new NumberPlannedSprints(0));
    }

    @Test
    void constructor_InvalidNumberPlannedSprints_ThrowsException() {
        Exception exception = assertThrows(InvalidConstructorArgumentException.class,
                () -> new NumberPlannedSprints(-1));

        assertEquals("Number of planned Sprints must be greater than 0", exception.getMessage());
    }

    @Test
    void equals_sameObject_True() {
        assertEquals(numberPlannedSprints, numberPlannedSprints);
    }

    @Test
    void equals_differentObjectsButSameContent_True() {
        NumberPlannedSprints other = new NumberPlannedSprints(6);
        assertEquals(numberPlannedSprints, other);
    }

    @Test
    void equals_differentValues_False() {
        NumberPlannedSprints other = new NumberPlannedSprints(3);
        assertNotEquals(numberPlannedSprints, other);
    }

    @Test
    void equals_differentObjectDifferentClass_False() {
        String differentObject = "P001";
        assertNotEquals(numberPlannedSprints, differentObject);
    }

    @Test
    void equals_compareWithNull_False() {
        assertNotEquals(null, numberPlannedSprints);
    }

    @Test
    void testHashCode_True() {
        NumberPlannedSprints other = new NumberPlannedSprints(6);
        int expected = other.hashCode();
        int result = numberPlannedSprints.hashCode();
        assertEquals(expected, result);
    }

    @Test
    void testHashCode_False() {
        NumberPlannedSprints other = new NumberPlannedSprints(3);
        int expected = other.hashCode();
        int result = numberPlannedSprints.hashCode();
        assertNotEquals(expected, result);
    }

    @Test
    void testGetNumberOfPlannedSprintsValue_ReturnsNumberPlannedSprints() {
        int expectedValue = 10;
        NumberPlannedSprints numberPlannedSprints = new NumberPlannedSprints(expectedValue);
        assertEquals(expectedValue, numberPlannedSprints.getNumberOfPlannedSprintsValue());
    }

}