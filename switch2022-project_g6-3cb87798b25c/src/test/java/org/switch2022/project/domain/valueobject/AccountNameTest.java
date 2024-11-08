package org.switch2022.project.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class AccountNameTest {

    @Test
    void constructor_NullName_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new AccountName(null));

        assertEquals("Invalid name.", exception.getMessage());
    }

    @Test
    void constructor_EmptyName_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new AccountName(""));

        assertEquals("Invalid name.", exception.getMessage());
    }

    @Test
    void constructor_NameWithOnlyWhiteSpace_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new AccountName("     "));

        assertEquals("Invalid name.", exception.getMessage());
    }

    @Test
    void equals_sameObject_True() {
        AccountName accountName = new AccountName("Filipe Faria");
        AccountName newAccountName = accountName;

        boolean result = accountName.equals(newAccountName);
        assertTrue(result);
    }

    @Test
    void equals_differentObjectsButSameContent_True() {
        AccountName firstAccountName = new AccountName("Alberto");
        AccountName secondAccountName = new AccountName("Alberto");

        boolean result = firstAccountName.equals(secondAccountName);
        assertTrue(result);

    }

    @Test
    void equals_differentObjectSameClass_False() {
        AccountName firstAccountName = new AccountName("Amélia");
        AccountName secondAccountName = new AccountName("Helena");

        boolean result = firstAccountName.equals(secondAccountName);
        assertFalse(result);
    }

    @Test
    void equals_differentObjectDifferentClass_False() {
        AccountName accountName = new AccountName("António");
        String notAnAccountName = "Not an AccountName";

        boolean result = accountName.equals(notAnAccountName);
        assertFalse(result);

    }

    @Test
    void equals_compareWithNull_False() {
        AccountName accountName = new AccountName("Filipa");
        AccountName nullObject = null;

        boolean result = accountName.equals(nullObject);
        assertFalse(result);

    }

    @Test
    void hashCode_hashCodeCreation_True() {
        AccountName accountName = new AccountName("Bruno");

        int result = Objects.hashCode(accountName);
        int secondResult = accountName.hashCode();

        assertEquals(result, secondResult);
    }

}