package org.switch2022.project.datamodel;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.datamodel.jpa.customer.CustomerJPA;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CustomerJPATest {
    private static CustomerJPA customerJPA;
    private static String NIFOne;
    private static String nameOne;
    private static String countryOne;

    @BeforeAll
    static void setup() {
        NIFOne = "232139890";
        nameOne = "Salmão";
        countryOne = "portugal";
        customerJPA = new CustomerJPA(NIFOne, nameOne, countryOne);
    }

    @AfterAll
    static void tearDown() {
        customerJPA = null;
    }

    @Test
    void getTaxIdentificationNumber_ReturnsTaxIdentificationNumber_True() {
        String result = customerJPA.getTaxIdentificationNumber();

        assertEquals(NIFOne, result);
    }

    @Test
    void getCustomerName_ReturnsCustomerNameTrue() {
        String result = customerJPA.getCustomerName();

        assertEquals(nameOne, result);
    }

    @Test
    void getCoutry_ReturnsCountry() {
        String result = customerJPA.getCountry();

        assertEquals(countryOne, result);
    }

    @Test
    void equals_SameObject_True() {
        CustomerJPA sameCustomer = customerJPA;

        boolean result = customerJPA.equals(sameCustomer);

        assertTrue(result);
    }

    @Test
    void equals_ObjectIsNull_False() {
        CustomerJPA nullObject = null;

        boolean result = customerJPA.equals(nullObject);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfDifferentClass_False() {
        String differentClass = "not a customer JPA";

        boolean result = customerJPA.equals(differentClass);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentValue_False() {
        CustomerJPA differentCustomer =
                new CustomerJPA("258624809", "Manuel", "spain");

        boolean result = customerJPA.equals(differentCustomer);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfSameClassAndSameValue_True() {
        CustomerJPA differentCustomer =
                new CustomerJPA(NIFOne, nameOne, countryOne);

        boolean result = customerJPA.equals(differentCustomer);

        assertTrue(result);
    }

    @Test
    void equals_SameNIFAndNameDifferentCountry_False() {
        CustomerJPA differentCustomer =
                new CustomerJPA(NIFOne, nameOne, "other");

        boolean result = customerJPA.equals(differentCustomer);

        assertFalse(result);
    }

    @Test
    void equals_SameNIFAndCountryDifferentName_False() {
        CustomerJPA differentCustomer =
                new CustomerJPA(NIFOne, "João", countryOne);

        boolean result = customerJPA.equals(differentCustomer);

        assertFalse(result);
    }

    @Test
    void equals_SameNIFAndDifferentNameAndCountry_False() {
        CustomerJPA differentCustomer =
                new CustomerJPA(NIFOne, "João", "other");

        boolean result = customerJPA.equals(differentCustomer);

        assertFalse(result);
    }

    @Test
    void equals_DifferentNIFSameNameAndCountry_False() {
        CustomerJPA differentCustomer =
                new CustomerJPA("267018886", nameOne, countryOne);

        boolean result = customerJPA.equals(differentCustomer);

        assertFalse(result);
    }

    @Test
    void equals_DifferentNIFAndCountrySameName_False() {
        CustomerJPA differentCustomer =
                new CustomerJPA("267018886", nameOne, "spain");

        boolean result = customerJPA.equals(differentCustomer);

        assertFalse(result);
    }

    @Test
    void hashCode_True() {
        int expected = Objects.hashCode(customerJPA);
        int result = customerJPA.hashCode();

        assertEquals(expected, result);
    }
}