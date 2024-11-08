package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.exception.EmptyConstructorArgumentException;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTypologyNameTest {

    @Test
    void constructor_CheckIfExceptionIsThrownWhenNameIsNull() {
        Exception exception = assertThrows(NullConstructorArgumentException.class, () ->
                new ProjectTypologyName(null));

        assertEquals("Project Typology cannot be null", exception.getMessage());
    }

    @Test
    void constructor_CheckIfExceptionIsThrownWhenNameIsBlank() {
        Exception exception = assertThrows(EmptyConstructorArgumentException.class, () ->
                new ProjectTypologyName(""));

        assertEquals("Project Typology cannot be empty", exception.getMessage());
    }

    @Test
    void testToString() {
        String typologyName = "Materials";

        ProjectTypologyName projectTypologyName = new ProjectTypologyName(typologyName);

        String typologyToString = projectTypologyName.getProjectTypologyNameValue();

        boolean result = typologyToString.equals(typologyName);
        assertTrue(result);
    }

    @Test
    void equals_SameObjects_ReturnsTrue() {
        ProjectTypologyName projectTypologyNameOne = new ProjectTypologyName("one");
        ProjectTypologyName anotherProjectTypology = projectTypologyNameOne;

        boolean result = projectTypologyNameOne.equals(anotherProjectTypology);

        assertTrue(result);
    }

    @Test
    void equals_SameTypeOfObjectButDifferentAttributes_ReturnsFalse() {
        ProjectTypologyName projectTypologyNameOne = new ProjectTypologyName("One");
        ProjectTypologyName projectTypologyNameTwo = new ProjectTypologyName("Two");

        boolean result = projectTypologyNameOne.equals(projectTypologyNameTwo);
        assertFalse(result);
    }

    @Test
    void equals_ObjectsWithSameName_ReturnsTrue() {
        ProjectTypologyName projectTypologyNameOne = new ProjectTypologyName("One");
        ProjectTypologyName projectTypologyNameTwo = new ProjectTypologyName("One");
        boolean result = projectTypologyNameOne.equals(projectTypologyNameTwo);

        assertTrue(result);
    }

    @Test
    void equals_DifferentObjects_ReturnsNotEquals() {
        ProjectTypologyName projectTypologyName = new ProjectTypologyName("idk");
        String name = "name";
        boolean result = projectTypologyName.equals(name);
        assertFalse(result);
    }

    @Test
    void equals_CompareObjectWithNull_ReturnFalse() {
        ProjectTypologyName projectTypologyName = new ProjectTypologyName("yes");
        ProjectTypologyName nullProjectTypologyName = null;

        boolean result = projectTypologyName.equals(nullProjectTypologyName);
        assertFalse(result);
    }

    @Test
    void hashCode_EqualObjects_Equals() {
        ProjectTypologyName name = new ProjectTypologyName("One");

        int expected = Objects.hashCode(name);
        int result = name.hashCode();

        assertEquals(expected, result);
    }

    @Test
    void hashCode_DifferentObjects_NotEquals() {
        ProjectTypologyName name = new ProjectTypologyName("One");
        ProjectTypologyName nameTwo = new ProjectTypologyName("Two");

        int hashCodeOne = name.hashCode();
        int hashCodeTwo = nameTwo.hashCode();

        assertNotEquals(hashCodeOne, hashCodeTwo);
    }
}