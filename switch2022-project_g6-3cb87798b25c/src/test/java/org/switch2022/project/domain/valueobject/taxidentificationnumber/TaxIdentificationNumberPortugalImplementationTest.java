package org.switch2022.project.domain.valueobject.taxidentificationnumber;

import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.exception.InvalidConstructorArgumentException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class TaxIdentificationNumberPortugalImplementationTest {

    @Test
    void constructor_checkIfExceptionIsThrownWhenTaxIdentificationNumberIsInvalid_True() {

        Exception exception = assertThrows(InvalidConstructorArgumentException.class, () ->
                new TaxIdentificationNumberPortugalImplementation("111111111"));
        assertEquals("Invalid Portuguese Tax Identification Number", exception.getMessage());

    }

    @Test
    void constructor_checkIfExceptionIsThrownWhenTaxIdentificationNumberHasLetters_True() {

        Exception exception = assertThrows(InvalidConstructorArgumentException.class, () ->
                new TaxIdentificationNumberPortugalImplementation("11a111b11"));
        assertEquals("Invalid Portuguese Tax Identification Number", exception.getMessage());

    }

    @Test
    void taxIdentificationNumberValidation_ValidTaxIdentificationNumber_True() {

        String TIN = "296186279";

        TaxIdentificationNumberPortugalImplementation portugalTIN =
                new TaxIdentificationNumberPortugalImplementation(TIN);


        boolean result = portugalTIN.taxIdentificationNumberValidation("249307936");

        assertTrue(result);

    }

    @Test
    void taxIdentificationNumberValidation_TaxIdentificationWithInsuficientDigits_False() {
        String TIN = "123456789";

        TaxIdentificationNumberPortugalImplementation portugalTIN =
                new TaxIdentificationNumberPortugalImplementation(TIN);


        boolean result = portugalTIN.taxIdentificationNumberValidation("123");

        assertFalse(result);

    }

    @Test
    void taxIdentificationNumberValidation_InvalidTaxIdentificationNumber_False() {
        TaxIdentificationNumberPortugalImplementation portugalTIN =
                new TaxIdentificationNumberPortugalImplementation("123456789");


        boolean result = portugalTIN.taxIdentificationNumberValidation("222222222");

        assertFalse(result);
    }

    @Test
    void equals_sameObject_True() {
        TaxIdentificationNumberPortugalImplementation TIN =
                new TaxIdentificationNumberPortugalImplementation("240800206");

        TaxIdentificationNumberPortugalImplementation sameTIN = TIN;

        boolean result = TIN.equals(sameTIN);

        assertTrue(result);
    }

    @Test
    void equals_differentObjectsButSameContent_True() {
        TaxIdentificationNumberPortugalImplementation TIN =
                new TaxIdentificationNumberPortugalImplementation("287233898");

        TaxIdentificationNumberPortugalImplementation TINWithSameContent =
                new TaxIdentificationNumberPortugalImplementation("287233898");

        boolean result = TIN.equals(TINWithSameContent);

        assertTrue(result);
    }

    @Test
    void equals_differentObjectSameClass_False() {
        TaxIdentificationNumberPortugalImplementation TIN =
                new TaxIdentificationNumberPortugalImplementation("213677512");

        TaxIdentificationNumberPortugalImplementation differentTIN =
                new TaxIdentificationNumberPortugalImplementation("268117772");

        boolean result = TIN.equals(differentTIN);

        assertFalse(result);
    }

    @Test
    void equals_differentObjectDifferentClass_False() {
        TaxIdentificationNumberPortugalImplementation TIN =
                new TaxIdentificationNumberPortugalImplementation("204839882");

        String differentObject = "Not a TaxIdentificationNumber";

        boolean result = TIN.equals(differentObject);

        assertFalse(result);
    }

    @Test
    void equals_compareWithNull_False() {
        TaxIdentificationNumberPortugalImplementation TIN =
                new TaxIdentificationNumberPortugalImplementation("258345810");
        TaxIdentificationNumberPortugalImplementation nullObject = null;

        boolean result = TIN.equals(nullObject);

        assertFalse(result);
    }

    @Test
    void hashCode_hashCodeCreation_True() {
        TaxIdentificationNumberPortugalImplementation firstTIN =
                new TaxIdentificationNumberPortugalImplementation("262597438");

        int result = Objects.hashCode(firstTIN);
        int secondResult = firstTIN.hashCode();

        assertEquals(result, secondResult);
    }
}