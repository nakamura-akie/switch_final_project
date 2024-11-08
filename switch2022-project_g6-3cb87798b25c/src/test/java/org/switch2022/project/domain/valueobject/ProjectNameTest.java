package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.exception.EmptyConstructorArgumentException;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ProjectNameTest {

    @Test
    void constructor_ValueIsNull_ThrowException() {
        Exception exception = assertThrows(NullConstructorArgumentException.class, () ->
                new ProjectName(null));
        assertEquals("Project Name cannot be null", exception.getMessage());
    }

    @Test
    void constructor_ValueIsBlank_ThrowException() {
        Exception exception = assertThrows(EmptyConstructorArgumentException.class, () ->
                new ProjectName(""));
        assertEquals("Project Name cannot be empty", exception.getMessage());
    }

    @Test
    void toString_ValueAsString() {
        String projectNameValue = "Project name";

        ProjectName projectName = new ProjectName(projectNameValue);

        String expected = projectNameValue;

        String result = projectName.getProjectNameValue();

        assertEquals(expected, result);
    }

    @Test
    void equals_SameObject_True() {
        ProjectName projectName = new ProjectName("Project name");
        ProjectName sameProjectName = projectName;

        boolean result = projectName.equals(sameProjectName);

        assertTrue(result);
    }

    @Test
    void equals_ObjectIsNull_False() {
        ProjectName projectName = new ProjectName("Project name");
        ProjectName nullObject = null;

        boolean result = projectName.equals(nullObject);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfDifferentClass_False() {
        ProjectName projectName = new ProjectName("Project name");
        String differentObject = "Project name";

        boolean result = projectName.equals(differentObject);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentValue_False() {
        ProjectName projectName = new ProjectName("Project name");
        ProjectName differentProjectName = new ProjectName("Different project name");

        boolean result = projectName.equals(differentProjectName);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfSameClassAndSameValue_True() {
        ProjectName projectName = new ProjectName("Project name");
        ProjectName nameWithSameValue = new ProjectName("Project name");

        boolean result = projectName.equals(nameWithSameValue);

        assertTrue(result);
    }

    @Test
    void hashCode_True() {
        ProjectName projectName = new ProjectName("Project name");

        int expected = Objects.hashCode(projectName);
        int result = projectName.hashCode();

        assertEquals(expected, result);
    }

}

