package org.switch2022.project.domain.customer;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.Country;
import org.switch2022.project.domain.valueobject.CustomerName;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberPortugalImplementation;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberSpainImplementation;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CustomerTest {

    private static TaxIdentificationNumberPortugalImplementation taxIdentificationNumberDoubleONE;
    private static TaxIdentificationNumberSpainImplementation taxIdentificationNumberDoubleTWO;
    private static CustomerName customerNameDoubleONE;
    private static CustomerName customerNameDoubleTWO;

    private static Country countryDoubleONE;
    private static Country countryDoubleTWO;
    private static Customer customer;

    @BeforeAll
    static void init() {

        taxIdentificationNumberDoubleONE = mock(TaxIdentificationNumberPortugalImplementation.class);
        taxIdentificationNumberDoubleTWO = mock(TaxIdentificationNumberSpainImplementation.class);

        customerNameDoubleONE = mock(CustomerName.class);
        customerNameDoubleTWO = mock(CustomerName.class);

        countryDoubleONE = mock(Country.class);
        countryDoubleTWO = mock(Country.class);

        customer = new Customer(taxIdentificationNumberDoubleONE, customerNameDoubleONE, countryDoubleONE);
    }

    @AfterAll
    static void tearDown() {
        taxIdentificationNumberDoubleONE = null;
        taxIdentificationNumberDoubleTWO = null;

        customerNameDoubleONE = null;
        customerNameDoubleTWO = null;

        countryDoubleONE = null;
        countryDoubleTWO = null;

        customer = null;
    }

    @Test
    void constructor_NullTaxIdentificationNumber_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Customer(null, customerNameDoubleONE, countryDoubleONE));
        assertEquals("Tax Identification Number, Customer Name and Country cannot be null"
                , exception.getMessage());
    }

    @Test
    void constructor_NullCustomerName_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Customer(taxIdentificationNumberDoubleONE, null, countryDoubleONE));
        assertEquals("Tax Identification Number, Customer Name and Country cannot be null",
                exception.getMessage());
    }

    @Test
    void constructor_NullCountry_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Customer(taxIdentificationNumberDoubleONE, customerNameDoubleONE, null));
        assertEquals("Tax Identification Number, Customer Name and Country cannot be null",
                exception.getMessage());
    }

    @Test
    void constructor_NullTaxIdentificationNumberAndCustomerName_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Customer(null, null, countryDoubleONE));
        assertEquals("Tax Identification Number, Customer Name and Country cannot be null",
                exception.getMessage());
    }

    @Test
    void constructor_NullCountryAndName_ThrowException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new Customer(taxIdentificationNumberDoubleONE, null, null));
        assertEquals("Tax Identification Number, Customer Name and Country cannot be null",
                exception.getMessage());
    }

    @Test
    void identity_ReturnsCorrectIdentity_True() {
        TaxIdentificationNumber identityResult = customer.identity();

        boolean result = identityResult.equals(taxIdentificationNumberDoubleONE);

        assertTrue(result);
    }

    @Test
    void sameAs_SameTaxIdentificationNumberNameAndCountry_True() {
        Customer customerTWO = new Customer(taxIdentificationNumberDoubleONE, customerNameDoubleONE, countryDoubleONE);

        boolean result = customer.sameAs(customerTWO);

        assertTrue(result);
    }

    @Test
    void sameAs_DifferentTaxIdentificationNumberButSameNameAndCountry_False() {
        Customer customerTWO = new Customer(taxIdentificationNumberDoubleTWO, customerNameDoubleONE, countryDoubleONE);

        boolean result = customer.sameAs(customerTWO);

        assertFalse(result);
    }

    @Test
    void sameAs_SameTaxIdentificationNumberAndCountryButDifferentName_False() {
        Customer customerTWO = new Customer(taxIdentificationNumberDoubleONE, customerNameDoubleTWO, countryDoubleONE);

        boolean result = customer.sameAs(customerTWO);

        assertFalse(result);
    }

    @Test
    void sameAs_DifferentTaxIdentificationNumberAndCountryButSameName_False() {
        Customer customerTWO = new Customer(taxIdentificationNumberDoubleTWO, customerNameDoubleONE, countryDoubleTWO);

        boolean result = customer.sameAs(customerTWO);

        assertFalse(result);
    }


    @Test
    void sameAs_SameTaxIdentificationNumberAndNameButDifferentCountry_False() {
        Customer customerTWO = new Customer(taxIdentificationNumberDoubleONE, customerNameDoubleONE, countryDoubleTWO);

        boolean result = customer.sameAs(customerTWO);

        assertFalse(result);
    }

    @Test
    void sameAs_DifferentTaxIdentificationNumberAndDifferentNameButSameCountry_False() {
        Customer customerTWO = new Customer(taxIdentificationNumberDoubleTWO, customerNameDoubleTWO, countryDoubleONE);

        boolean result = customer.sameAs(customerTWO);

        assertFalse(result);
    }

    @Test
    void sameAs_EverythingDifferent_False() {
        Customer customerTWO = new Customer(taxIdentificationNumberDoubleTWO, customerNameDoubleTWO, countryDoubleTWO);

        boolean result = customer.sameAs(customerTWO);

        assertFalse(result);
    }

    @Test
    void sameAs_instanceOfDifferentClass_False() {
        String customerTWO = "This is not a customer";

        boolean result = customer.sameAs(customerTWO);

        assertFalse(result);
    }

    @Test
    void equals_SameObject_True() {
        Customer customerTwo = customer;

        boolean result = customer.equals(customerTwo);

        assertTrue(result);

    }

    @Test
    void equals_ObjectIsNull_False() {
        boolean result = customer.equals(null);

        assertFalse(result);

    }

    @Test
    void equals_ObjectOfDifferentClass_False() {
        String differentObject = "differentObject";

        boolean result = customer.equals(differentObject);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentValue_False() {

        Customer customerTwo = new Customer(taxIdentificationNumberDoubleTWO, customerNameDoubleTWO, countryDoubleTWO);

        boolean result = customer.equals(customerTwo);

        assertFalse(result);

    }

    @Test
    void equals_ObjectOfSameClassAndSameValue_True() {
        Customer customerTwo = new Customer(taxIdentificationNumberDoubleONE, customerNameDoubleONE, countryDoubleONE);

        boolean result = customer.equals(customerTwo);

        assertTrue(result);

    }

    @Test
    void hashCode_True() {
        int expected = Objects.hashCode(customer);
        int result = customer.hashCode();

        assertEquals(expected, result);
    }
}