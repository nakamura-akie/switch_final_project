package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.exception.EmptyConstructorArgumentException;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import static org.junit.jupiter.api.Assertions.*;

class UserStoryCodeTest {

    private String userStoryCodeString;
    private UserStoryCode userStoryCode;

    @BeforeEach
    void init() {

         userStoryCodeString = "US001";

         this.userStoryCode = new UserStoryCode(userStoryCodeString);
    }

    @Test
    void constructor_checkIfExceptionIsThrownWhenUserStoryIsNull_True() {
        Exception exception = assertThrows(NullConstructorArgumentException.class, () ->
                new UserStoryCode(null));
        assertEquals("The inserted User Story code is invalid", exception.getMessage());
    }

    @Test
    void constructor_checkIfExceptionIsThrownWhenUserStoryCodeIsEmpty_True() {
        Exception exception = assertThrows(EmptyConstructorArgumentException.class, () ->
                new UserStoryCode(""));
        assertEquals("The inserted User Story code is invalid", exception.getMessage());
    }

    @Test
    void constructor_checkIfExceptionIsThrownWhenUserStoryCodeIsBlank_True() {
        Exception exception = assertThrows(EmptyConstructorArgumentException.class, () ->
                new UserStoryCode("  "));
        assertEquals("The inserted User Story code is invalid", exception.getMessage());
    }

    @Test
    void constructor_checkIfExceptionIsThrownWhenUserStoryCodeOnlyHasWhitespace_True() {
        Exception exception = assertThrows(EmptyConstructorArgumentException.class, () ->
                new UserStoryCode("\t"));
        assertEquals("The inserted User Story code is invalid", exception.getMessage());
    }

    @Test
    void toString_ReturnTrue() {
        assertEquals(userStoryCodeString, userStoryCode.getUserStoryCodeValue());
    }

    @Test
    void toString_ReturnFalse() {
        assertNotEquals("not", userStoryCodeString);
    }

    @Test
    void getCode_ReturnsCode_True() {
        assertEquals(userStoryCodeString, userStoryCode.getUserStoryCodeValue());
    }

    @Test
    void equals_sameObject_True() {
        UserStoryCode userStoryCode = new UserStoryCode("US01");
        UserStoryCode sameUserStoryCode = userStoryCode;

        boolean result = userStoryCode.equals(sameUserStoryCode);

        assertTrue(result);
    }

    @Test
    void equals_differentObjectsButSameContent_True() {
        UserStoryCode userStoryCode = new UserStoryCode("US01");
        UserStoryCode userStoryCodeWithSameContent =  new UserStoryCode("US01");

        boolean result = userStoryCode.equals(userStoryCodeWithSameContent);

        assertTrue(result);
    }

    @Test
    void equals_differentObjectSameClass_False() {
        UserStoryCode userStoryCode = new UserStoryCode("US01");
        UserStoryCode differentUserStoryCode = new UserStoryCode("US02");

        boolean result = userStoryCode.equals(differentUserStoryCode);

        assertFalse(result);
    }

    @Test
    void equals_differentObjectDifferentClass_False() {
        UserStoryCode userStoryCode = new UserStoryCode("US01");
        String differentObject = "US03";

        boolean result = userStoryCode.equals(differentObject);

        assertFalse(result);
    }

    @Test
    void equals_compareWithNull_False() {
        UserStoryCode userStoryCode = new UserStoryCode("US01");
        UserStoryCode nullObject = null;

        boolean result = userStoryCode.equals(nullObject);

        assertFalse(result);
    }

    @Test
    void hashCode_hashCodeCreation_True() {
        UserStoryCode firstUserStoryCode = new UserStoryCode("US01");
        int result = firstUserStoryCode.hashCode();

        UserStoryCode secondUserStoryCode = new UserStoryCode("US01");
        int secondResult = secondUserStoryCode.hashCode();

        assertEquals(result, secondResult);
    }

}