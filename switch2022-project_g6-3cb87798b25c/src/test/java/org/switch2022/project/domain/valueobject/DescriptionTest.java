package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.exception.EmptyConstructorArgumentException;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class DescriptionTest {
    private String userStoryDescription;
    private Description description;

    @BeforeEach
    void init() {

        userStoryDescription = "This is a description.";

        this.description = new Description(userStoryDescription);
    }

    @Test
    void constructor_DescriptionStringIsNull_ThrowException() {
        Exception exception = assertThrows(NullConstructorArgumentException.class, () ->
                new Description(null));
        assertEquals("Description cannot be null", exception.getMessage());
    }

    @Test
    void constructor_DescriptionStringIsEmpty_ThrowException() {
        Exception exception = assertThrows(EmptyConstructorArgumentException.class, () ->
                new Description(""));
        assertEquals("Description cannot be empty", exception.getMessage());
    }

    @Test
    void constructor_DescriptionStringBlank_ThrowException() {
        Exception exception = assertThrows(EmptyConstructorArgumentException.class, () ->
                new Description("\t"));
        assertEquals("Description cannot be empty", exception.getMessage());
    }

    @Test
    void getValue_ReturnsDescription_True() {
        assertEquals(userStoryDescription, description.getDescriptionValue());
    }

    @Test
    void toString_ReturnFalse() {
        assertNotEquals("not", userStoryDescription);
    }

    @Test
    void equals_SameUserStoryDescription_equals() {
        Description firstDescription = new Description(userStoryDescription);
        Description secondDescription = new Description(userStoryDescription);

        assertEquals(firstDescription, secondDescription);
    }

    @Test
    void equals_SameObject_equals() {
        Description firstDescription = new Description(userStoryDescription);
        Description secondDescription = firstDescription;

        assertEquals(firstDescription, secondDescription);
    }

    @Test
    void equals_ObjectIsDifferentClass_equals() {
        Description firstDescription = new Description(userStoryDescription);
        String secondDescription = "This is a description.";

        assertNotEquals(firstDescription, secondDescription);
    }

    @Test
    void equals_ObjectIsNull_equals() {
        Description firstDescription = new Description(userStoryDescription);

        assertNotEquals(null, firstDescription);
    }

    @Test
    void hasCode_True() {
        Description description = new Description(userStoryDescription);

        Integer expected = Objects.hashCode(description);
        Integer result = description.hashCode();

        assertEquals(expected, result);
    }

    @Test
    void toString_String() {
        Description description = new Description("This is a description.");
        String expected = "This is a description.";
        String result = description.getDescriptionValue();

        assertEquals(expected,result);
    }
}