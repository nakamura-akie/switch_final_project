package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.exception.EmptyConstructorArgumentException;
import org.switch2022.project.utils.exception.NullConstructorArgumentException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void constructor_NullEmail_ThrowsException() {
        Exception exception = assertThrows(NullConstructorArgumentException.class, () ->
                new Email(null));

        assertEquals("Invalid Email.", exception.getMessage());
    }

    @Test
    void constructor_EmptyEmail_ThrowsException() {
        Exception exception = assertThrows(EmptyConstructorArgumentException.class, () ->
                new Email(""));

        assertEquals("Invalid Email.", exception.getMessage());
    }

    @Test
    void constructor_EmailWithOnlyWhiteSpace_ThrowsException() {
        Exception exception = assertThrows(EmptyConstructorArgumentException.class, () ->
                new Email("     "));

        assertEquals("Invalid Email.", exception.getMessage());
    }

    @Test
    void equals_sameObject_True() {
        Email firstEmail = new Email("ana@ana.pt");
        Email secondEmail = firstEmail;

        boolean result = firstEmail.equals(secondEmail);

        assertTrue(result);
    }

    @Test
    void equals_differentObjectsButSameContent_True() {
        Email firstEmail = new Email("ana@ana.pt");
        Email secondEmail = new Email("ana@ana.pt");

        boolean result = firstEmail.equals(secondEmail);

        assertTrue(result);
    }

    @Test
    void equals_differentObjectSameClass_False() {
        Email firstEmail = new Email("ana@ana.pt");
        Email secondEmail = new Email("paula@paula.pt");

        boolean result = firstEmail.equals(secondEmail);

        assertFalse(result);
    }

    @Test
    void equals_differentObjectDifferentClass_False() {
        Email email = new Email("ana@ana.pt");
        String notAnEmail = "not an email";

        boolean result = email.equals(notAnEmail);

        assertFalse(result);
    }

    @Test
    void equals_compareWithNull_False() {
        Email email = new Email("carla@carla.pt");
        Email nullEmail = null;

        boolean result = email.equals(nullEmail);

        assertFalse(result);
    }

    @Test
    void hashCode_hashCodeCreation_True() {
        Email email = new Email("bruno@isep.ipp.pt");

        int result = Objects.hashCode(email);
        int secondResult = email.hashCode();

        assertEquals(result, secondResult);
    }

    @Test
    void testGetEmailValue() {
        Email email = new Email("bruno@isep.ipp.pt");
        String expected = email.getEmailValue();
        String result = email.getEmailValue();
        assertEquals(expected, result);
    }

    @Test
    void toString_Equals() {
        Email email = new Email("bruno@isep.ipp.pt");
        String expected = email.toString();
        String result = email.toString();
        assertEquals(expected, result);
    }
}