package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.exception.EmptyConstructorArgumentException;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ProjectCodeTest {

    @Test
    void constructor_checkIfExceptionIsThrownWhenProjectCodeIsNull_True() {
        Exception exception = assertThrows(NullConstructorArgumentException.class, () ->
                new ProjectCode(null));
        assertEquals("Project Code cannot be null", exception.getMessage());
    }

    @Test
    void constructor_checkIfExceptionIsThrownWhenProjectCodeIsEmpty_True() {
        Exception exception = assertThrows(EmptyConstructorArgumentException.class, () ->
                new ProjectCode(""));
        assertEquals("Project Code cannot be empty", exception.getMessage());
    }

    @Test
    void constructor_checkIfExceptionIsThrownWhenProjectCodeOnlyHasWhitespace_True() {
        Exception exception = assertThrows(EmptyConstructorArgumentException.class, () ->
                new ProjectCode("\t"));
        assertEquals("Project Code cannot be empty", exception.getMessage());
    }

    @Test
    void toString_ValueAsString() {
        String projectCodeValue = "P001";

        ProjectCode projectCode = new ProjectCode(projectCodeValue);

        String expected = projectCodeValue;

        String result = projectCode.getProjectCodeValue();

        assertEquals(expected, result);
    }

    @Test
    void equals_sameObject_True() {
        ProjectCode projectCode = new ProjectCode("Pêssego");
        ProjectCode sameProjectCode = projectCode;

        boolean result = projectCode.equals(sameProjectCode);

        assertTrue(result);
    }

    @Test
    void equals_differentObjectsButSameContent_True() {
        ProjectCode projectCode = new ProjectCode("Lavanda");
        ProjectCode projectCodeWithSameContent = new ProjectCode("Lavanda");

        boolean result = projectCode.equals(projectCodeWithSameContent);

        assertTrue(result);
    }

    @Test
    void equals_differentObjectSameClass_False() {
        ProjectCode projectCode = new ProjectCode("Girassol");
        ProjectCode differentProjectCode = new ProjectCode("Amor-Perfeito");

        boolean result = projectCode.equals(differentProjectCode);

        assertFalse(result);
    }

    @Test
    void equals_differentObjectDifferentClass_False() {
        ProjectCode projectCode = new ProjectCode("Papoila");
        String differentObject = "Margarida";

        boolean result = projectCode.equals(differentObject);

        assertFalse(result);
    }

    @Test
    void equals_compareWithNull_False() {
        ProjectCode projectCode = new ProjectCode("Manjerico");
        ProjectCode nullObject = null;

        boolean result = projectCode.equals(nullObject);

        assertFalse(result);
    }

    @Test
    void hashCode_hashCodeCreation_True() {
        ProjectCode firstProjectCode = new ProjectCode("Calêndula");

        int result = Objects.hashCode(firstProjectCode);
        int secondResult = firstProjectCode.hashCode();

        assertEquals(result, secondResult);
    }

}