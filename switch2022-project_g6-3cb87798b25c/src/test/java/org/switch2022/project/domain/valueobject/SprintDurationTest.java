package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.exception.InvalidConstructorArgumentException;

import java.util.InvalidPropertiesFormatException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class SprintDurationTest {

    @Test
    void constructor_WithValidDurationValue_DoesNotThrowException() {
        Integer sprintDurationValue = 3;

        assertDoesNotThrow(() -> new SprintDuration(sprintDurationValue));
    }

    @Test
    void constructor_WithNegativeDurationValue_ThrowsException() {
        Integer sprintDurationValue = -1;

        assertThrows(InvalidConstructorArgumentException.class, () -> new SprintDuration(sprintDurationValue));
    }
    @Test
    void constructor_SprintDurationEqualsZero_ThrowsException() {
        Integer sprintDurationValue = 0;

        assertThrows(InvalidConstructorArgumentException.class, () -> new SprintDuration(sprintDurationValue));
    }

    @Test
    void getSprintDurationValue_ReturnsCorrectValue() {
        Integer sprintDurationValue = 3;

        SprintDuration sprintDuration = new SprintDuration(sprintDurationValue);

        assertEquals(sprintDurationValue, sprintDuration.getSprintDurationValue());
    }

    @Test
    void equals_WithSameObject_ReturnsTrue() {
        Integer sprintDurationValue = 3;

        SprintDuration sprintDuration = new SprintDuration(sprintDurationValue);

        assertTrue(sprintDuration.equals(sprintDuration));
    }

    @Test
    void equals_WithEqualObjects_ReturnsTrue() {
        Integer sprintDurationValue1 = 3;

        SprintDuration sprintDuration1 = new SprintDuration(sprintDurationValue1);

        Integer sprintDurationValue2 = 3;

        SprintDuration sprintDuration2 = new SprintDuration(sprintDurationValue2);

        assertTrue(sprintDuration1.equals(sprintDuration2));
        assertTrue(sprintDuration2.equals(sprintDuration1));
    }

    @Test
    void equals_WithDifferentObjects_ReturnsFalse() {
        Integer sprintDurationValue1 = 3;

        SprintDuration sprintDuration1 = new SprintDuration(sprintDurationValue1);

        Integer sprintDurationValue2 = 4;

        SprintDuration sprintDuration2 = new SprintDuration(sprintDurationValue2);

        assertFalse(sprintDuration1.equals(sprintDuration2));
        assertFalse(sprintDuration2.equals(sprintDuration1));
    }

    @Test
    void equals_WithNull_ReturnsFalse() {
        Integer sprintDurationValue = 3;

        SprintDuration sprintDuration = new SprintDuration(sprintDurationValue);

        assertFalse(sprintDuration.equals(null));
    }

    @Test
    void equals_WithDifferentObjectType_ReturnsFalse() {
        Integer sprintDurationValue = 3;

        SprintDuration sprintDuration = new SprintDuration(sprintDurationValue);

        assertFalse(sprintDuration.equals("sprintDuration"));
    }

    @Test
    void hashCode_EqualObjects_Equals() {
        SprintDuration sprintDuration = new SprintDuration(3);

        int expected = Objects.hashCode(sprintDuration);
        int result = sprintDuration.hashCode();

        assertEquals(expected, result);
    }

    @Test
    void hashCode_DifferentObjects_NotEquals() {
        SprintDuration sprintDuration = new SprintDuration(3);
        SprintDuration sprintDurationTwo = new SprintDuration(4);

        int hashCodeOne = sprintDuration.hashCode();
        int hashCodeTwo = sprintDurationTwo.hashCode();

        assertNotEquals(hashCodeOne, hashCodeTwo);
    }

}