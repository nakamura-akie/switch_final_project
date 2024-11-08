package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SprintCodeTest {

    @Test
    void constructor_checkIfExceptionIsThrownWhenSprintCodeIsNull_Equal() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SprintCode(null));
        assertEquals("Invalid Sprint Code.", exception.getMessage());
    }

    @Test
    void constructor_checkIfExceptionIsThrownWhenSprintCodeIsEmpty_Equal() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SprintCode(""));
        assertEquals("Invalid Sprint Code.", exception.getMessage());
    }

    @Test
    void constructor_checkIfExceptionIsThrownWhenSprintCodeIsBlank_Equal() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SprintCode("  "));
        assertEquals("Invalid Sprint Code.", exception.getMessage());
    }

    @Test
    void constructor_checkIfExceptionIsThrownWhenUserStoryCodeOnlyHasWhitespace_Equal() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new SprintCode("\t"));
        assertEquals("Invalid Sprint Code.", exception.getMessage());
    }
    @Test
    void testToString_Equal() {
        String expected = "SPT002";
        SprintCode sprintCode = new SprintCode("SPT002");
        String result = sprintCode.toString();
        assertEquals(expected,result);
    }

    @Test
    void equals_sameObject_Equal() {
        SprintCode sprintCode = new SprintCode("SPT001");
        assertEquals(sprintCode,sprintCode);
    }

    @Test
    void equals_differentObjectsButSameContent_Equal() {
        SprintCode sprintCode = new SprintCode("SPT001");
        SprintCode sameSprintCode = new SprintCode("SPT001");
        assertEquals(sprintCode,sameSprintCode);
    }

    @Test
    void equals_differentObjectSameClass_NotEqual() {
        SprintCode sprintCode = new SprintCode("SPT001");
        SprintCode differentSprintCode = new SprintCode("SPT002");
        assertNotEquals(sprintCode,differentSprintCode);
    }

    @Test
    void equals_differentObjectDifferentClass_NotEqual() {
        SprintCode sprintCode = new SprintCode("SPT001");
        String differentObject = "SPT001";

        assertNotEquals(sprintCode,differentObject);
    }

    @Test
    void equals_compareWithNull_NotEqual() {
        SprintCode sprintCode = new SprintCode("SPT001");
        UserStoryCode nullObject = null;
        assertNotEquals(sprintCode,nullObject);
    }

    @Test
    void hashCode_hashCodeCreation_Equals() {
        SprintCode sprintCode = new SprintCode("SPT001");
        int result = sprintCode.hashCode();
        SprintCode sameSprintCode = new SprintCode("SPT001");
        int secondResult = sameSprintCode.hashCode();
        assertEquals(result, secondResult);
    }

    @Test
    void hashCode_hashCodeCreation_NotEquals() {
        SprintCode sprintCode = new SprintCode("SPT001");
        int result = sprintCode.hashCode();
        SprintCode differentSprintCode = new SprintCode("SPT200");
        int secondResult = differentSprintCode.hashCode();
        assertNotEquals(result, secondResult);
    }

}