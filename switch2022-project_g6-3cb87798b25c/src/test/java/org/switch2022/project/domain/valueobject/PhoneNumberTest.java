package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberTest {

    @Test
    void constructor_NullPhoneNumber_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new PhoneNumber(null));

        assertEquals("Invalid phone number.", exception.getMessage());
    }

    @Test
    void constructor_NegativePhoneNumber_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new PhoneNumber(-123456789));

        assertEquals("Invalid phone number.", exception.getMessage());
    }

    @Test
    void constructor_PhoneNumberWithLessThan9Digits_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new PhoneNumber(123));

        assertEquals("Invalid phone number.", exception.getMessage());
    }

    @Test
    void equals_sameObject_True() {
        PhoneNumber firstPhoneNumber = new PhoneNumber(123456789);
        PhoneNumber secondPhoneNumber = firstPhoneNumber;

        boolean result = firstPhoneNumber.equals(secondPhoneNumber);

        assertTrue(result);
    }

    @Test
    void equals_differentObjectsButSameContent_True() {
        PhoneNumber firstPhoneNumber = new PhoneNumber(123456789);
        PhoneNumber secondPhoneNumber = new PhoneNumber(123456789);

        boolean result = firstPhoneNumber.equals(secondPhoneNumber);

        assertTrue(result);
    }

    @Test
    void equals_differentObjectSameClass_False() {
        PhoneNumber firstPhoneNumber = new PhoneNumber(123456789);
        PhoneNumber secondPhoneNumber = new PhoneNumber(987654321);

        boolean result = firstPhoneNumber.equals(secondPhoneNumber);

        assertFalse(result);
    }

    @Test
    void equals_differentObjectDifferentClass_False() {
        PhoneNumber phoneNumber = new PhoneNumber(123456789);
        String notAPhoneNumber = "not a phone number";

        boolean result = phoneNumber.equals(notAPhoneNumber);

        assertFalse(result);
    }

    @Test
    void equals_compareWithNull_False() {
        PhoneNumber phoneNumber = new PhoneNumber(123456789);
        PhoneNumber nullObject = null;

        boolean result = phoneNumber.equals(nullObject);

        assertFalse(result);
    }

    @Test
    void hashCode_hashCodeCreation_True() {
        PhoneNumber phoneNumber = new PhoneNumber(123456789);

        int result = Objects.hashCode(phoneNumber);
        int secondResult = phoneNumber.hashCode();

        assertEquals(result, secondResult);
    }

}