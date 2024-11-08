package org.switch2022.project.domain.valueobject.taxidentificationnumber;

import org.junit.jupiter.api.*;
import org.switch2022.project.utils.exception.InvalidConstructorArgumentException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class TaxIdentificationNumberInternationalImplementationTest {

    @Test
    void constructor_checkIfExceptionIsThrownWhenTaxIdentificationNumberIsInvalid_True() {

        Exception exception = assertThrows(InvalidConstructorArgumentException.class, () ->
                new TaxIdentificationNumberInternationalImplementation("123"));
        assertEquals("Invalid International Tax Identification Number", exception.getMessage());

    }

    @Test
    void constructor_checkIfExceptionIsThrownWhenTaxIdentificationNumberHasLetters_True() {

        Exception exception = assertThrows(InvalidConstructorArgumentException.class, () ->
                new TaxIdentificationNumberInternationalImplementation("123abc"));
        assertEquals("Invalid International Tax Identification Number", exception.getMessage());

    }

    @Test
    void constructor_checkIfExceptionIsThrownWhenTaxIdentificationNumberHasLettersButCorrectLength_True() {

        Exception exception = assertThrows(InvalidConstructorArgumentException.class, () ->
                new TaxIdentificationNumberInternationalImplementation("123abc133"));
        assertEquals("Invalid International Tax Identification Number", exception.getMessage());

    }

    @Test
    void taxIdentificationNumberValidation_ValidTaxIdentificationNumber_True() {
        TaxIdentificationNumberInternationalImplementation internationalTIN =
                new TaxIdentificationNumberInternationalImplementation("296186279");


        boolean result = internationalTIN.taxIdentificationNumberValidation("123456789");

        assertTrue(result);

    }

    @Test
    void taxIdentificationNumberValidation_TaxIdentificationWithInsuficientDigits_False() {
        TaxIdentificationNumberInternationalImplementation internationalTIN =
                new TaxIdentificationNumberInternationalImplementation("296186279");


        boolean result = internationalTIN.taxIdentificationNumberValidation("123");

        assertFalse(result);
    }

    @Test
    void equals_sameObject_True() {
        TaxIdentificationNumberInternationalImplementation TIN =
                new TaxIdentificationNumberInternationalImplementation("240800206");

        TaxIdentificationNumberInternationalImplementation sameTIN = TIN;

        boolean result = TIN.equals(sameTIN);

        assertTrue(result);
    }

    @Test
    void equals_differentObjectsButSameContent_True() {
        TaxIdentificationNumberInternationalImplementation TIN =
                new TaxIdentificationNumberInternationalImplementation("287233898");

        TaxIdentificationNumberInternationalImplementation TINWithSameContent =
                new TaxIdentificationNumberInternationalImplementation("287233898");

        boolean result = TIN.equals(TINWithSameContent);

        assertTrue(result);
    }

    @Test
    void equals_differentObjectSameClass_False() {
        TaxIdentificationNumberInternationalImplementation TIN =
                new TaxIdentificationNumberInternationalImplementation("213677512");

        TaxIdentificationNumberInternationalImplementation differentTIN =
                new TaxIdentificationNumberInternationalImplementation("268117772");

        boolean result = TIN.equals(differentTIN);

        assertFalse(result);
    }

    @Test
    void equals_differentObjectDifferentClass_False() {
        TaxIdentificationNumberInternationalImplementation TIN =
                new TaxIdentificationNumberInternationalImplementation("204839882");

        String differentObject = "Not a TaxIdentificationNumber";

        boolean result = TIN.equals(differentObject);

        assertFalse(result);
    }

    @Test
    void equals_compareWithNull_False() {
        TaxIdentificationNumberInternationalImplementation TIN =
                new TaxIdentificationNumberInternationalImplementation("258345810");

        TaxIdentificationNumberInternationalImplementation nullObject = null;

        boolean result = TIN.equals(nullObject);

        assertFalse(result);
    }

    @Test
    void hashCode_hashCodeCreation_True() {
        TaxIdentificationNumberInternationalImplementation firstTIN =
                new TaxIdentificationNumberInternationalImplementation("262597438");

        int result = Objects.hashCode(firstTIN);
        int secondResult = firstTIN.hashCode();

        assertEquals(result, secondResult);
    }
}