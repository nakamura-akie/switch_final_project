package org.switch2022.project.domain.valueobject.taxidentificationnumber;

import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.exception.InvalidConstructorArgumentException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class TaxIdentificationNumberSpainImplementationTest {

    @Test
    void constructor_checkIfExceptionIsThrownWhenTaxIdentificationNumberIsTooShort_True() {
        Exception exception = assertThrows(InvalidConstructorArgumentException.class, () ->
                new TaxIdentificationNumberSpainImplementation("123abc"));

        assertEquals("Invalid Spanish Tax Identification Number", exception.getMessage());
    }

    @Test
    void constructor_checkIfExceptionIsThrownWhenTaxIdentificationNumberIsTooLong_True() {
        Exception exception = assertThrows(InvalidConstructorArgumentException.class, () ->
                new TaxIdentificationNumberSpainImplementation("123abc123456"));

        assertEquals("Invalid Spanish Tax Identification Number", exception.getMessage());
    }

    @Test
    void constructor_checkIfExceptionIsThrownWhenTaxIdentificationNumberHasCorrectLengthButIsNotOnlyDigits_True() {
        Exception exception = assertThrows(InvalidConstructorArgumentException.class, () ->
                new TaxIdentificationNumberSpainImplementation("12abc123"));

        assertEquals("Invalid Spanish Tax Identification Number", exception.getMessage());
    }

    @Test
    void constructor_checkIfExceptionIsThrownWhenTaxIdentificationNumberIsOnlyLetters_True() {
        Exception exception = assertThrows(InvalidConstructorArgumentException.class, () ->
                new TaxIdentificationNumberSpainImplementation("aaabbbccc"));

        assertEquals("Invalid Spanish Tax Identification Number", exception.getMessage());
    }

    @Test
    void constructor_checkIfExceptionIsThrownWhenTaxIdentificationNumberIsInvalid_True() {
        Exception exception = assertThrows(InvalidConstructorArgumentException.class, () ->
                new TaxIdentificationNumberSpainImplementation("98020762P"));

        assertEquals("Invalid Spanish Tax Identification Number", exception.getMessage());
    }

    @Test
    void equals_sameObject_True() {
        TaxIdentificationNumberSpainImplementation TIN =
                new TaxIdentificationNumberSpainImplementation("99431691T");

        TaxIdentificationNumberSpainImplementation sameTIN = TIN;

        boolean result = TIN.equals(sameTIN);

        assertTrue(result);
    }

    @Test
    void equals_differentObjectsButSameContent_True() {
        TaxIdentificationNumberSpainImplementation TIN =
                new TaxIdentificationNumberSpainImplementation("X0465186B");

        TaxIdentificationNumberSpainImplementation TINWithSameContent =
                new TaxIdentificationNumberSpainImplementation("X0465186B");

        boolean result = TIN.equals(TINWithSameContent);

        assertTrue(result);
    }

    @Test
    void equals_differentObjectSameClass_False() {
        TaxIdentificationNumberSpainImplementation TIN =
                new TaxIdentificationNumberSpainImplementation("39187525X");

        TaxIdentificationNumberSpainImplementation differentTIN =
                new TaxIdentificationNumberSpainImplementation("72244161B");

        boolean result = TIN.equals(differentTIN);

        assertFalse(result);
    }

    @Test
    void equals_differentObjectDifferentClass_False() {
        TaxIdentificationNumberSpainImplementation TIN =
                new TaxIdentificationNumberSpainImplementation("Y0247010G");

        String differentObject = "Not a TaxIdentificationNumber";

        boolean result = TIN.equals(differentObject);

        assertFalse(result);
    }

    @Test
    void equals_compareWithNull_False() {
        TaxIdentificationNumberSpainImplementation TIN =
                new TaxIdentificationNumberSpainImplementation("29444167G");

        TaxIdentificationNumberInternationalImplementation nullObject = null;

        boolean result = TIN.equals(nullObject);

        assertFalse(result);
    }

    @Test
    void hashCode_hashCodeCreation_True() {
        TaxIdentificationNumberSpainImplementation firstTIN =
                new TaxIdentificationNumberSpainImplementation("Z9657738C");

        int result = Objects.hashCode(firstTIN);
        int secondResult = firstTIN.hashCode();

        assertEquals(result, secondResult);
    }
}