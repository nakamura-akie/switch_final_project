package org.switch2022.project.utils.dto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.dto.NewCustomerDTO;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class NewCustomerDTOTest {

    private static NewCustomerDTO newCustomerDTO;

    @BeforeAll
    static void setup() {
        newCustomerDTO = new NewCustomerDTO();
        newCustomerDTO.taxIdentificationNumber = "215532287";
        newCustomerDTO.customerName = "Ulisses";
        newCustomerDTO.country = "portugal";
    }

    @AfterAll
    static void tearDown() {
        newCustomerDTO = null;
    }

    @Test
    void equals_SameObject_True() {
        NewCustomerDTO sameCustomerDTO = newCustomerDTO;

        boolean result = newCustomerDTO.equals(sameCustomerDTO);

        assertTrue(result);
    }

    @Test
    void equals_ObjectIsNull_False() {
        NewCustomerDTO nullObject = null;

        boolean result = newCustomerDTO.equals(nullObject);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfDifferentClass_False() {
        String differentClass = "not a new Customer DTO.";

        boolean result = newCustomerDTO.equals(differentClass);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentValue_False() {
        NewCustomerDTO anotherCustomerDTO = new NewCustomerDTO();
        anotherCustomerDTO.taxIdentificationNumber = "213997118";
        anotherCustomerDTO.customerName = "Filipe";
        anotherCustomerDTO.country = "spain";

        boolean result = newCustomerDTO.equals(anotherCustomerDTO);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfSameClassAndSameValue_True() {
        NewCustomerDTO anothernewCustomerDTO = new NewCustomerDTO();
        anothernewCustomerDTO.taxIdentificationNumber = "215532287";
        anothernewCustomerDTO.customerName = "Ulisses";
        anothernewCustomerDTO.country = "portugal";

        boolean result = newCustomerDTO.equals(anothernewCustomerDTO);

        assertTrue(result);
    }

    @Test
    void equals_SameNIFAndNameDifferentCountry_False() {
        NewCustomerDTO anotherNewCustomerDTO = new NewCustomerDTO();
        anotherNewCustomerDTO.taxIdentificationNumber = "215532287";
        anotherNewCustomerDTO.customerName = "Ulisses";
        anotherNewCustomerDTO.country = "spain";

        boolean result = newCustomerDTO.equals(anotherNewCustomerDTO);

        assertFalse(result);
    }

    @Test
    void equals_SameNIFAndCountryDifferentName_False() {
        NewCustomerDTO anotherNewCustomerDTO = new NewCustomerDTO();
        anotherNewCustomerDTO.taxIdentificationNumber = "215532287";
        anotherNewCustomerDTO.customerName = "Roberto";
        anotherNewCustomerDTO.country = "portugal";

        boolean result = newCustomerDTO.equals(anotherNewCustomerDTO);

        assertFalse(result);
    }

    @Test
    void equals_SameNIFAndDifferentNameAndCountry_False() {
        NewCustomerDTO anotherNewCustomerDTO = new NewCustomerDTO();
        anotherNewCustomerDTO.taxIdentificationNumber = "215532287";
        anotherNewCustomerDTO.customerName = "Mariana";
        anotherNewCustomerDTO.country = "spain";

        boolean result = newCustomerDTO.equals(anotherNewCustomerDTO);

        assertFalse(result);
    }

    @Test
    void equals_DifferentNIFSameNameAndCountry_False() {
        NewCustomerDTO anotherNewCustomerDTO = new NewCustomerDTO();
        anotherNewCustomerDTO.taxIdentificationNumber = "123456789";
        anotherNewCustomerDTO.customerName = "Ulisses";
        anotherNewCustomerDTO.country = "portugal";

        boolean result = newCustomerDTO.equals(anotherNewCustomerDTO);

        assertFalse(result);
    }

    @Test
    void equals_DifferentNIFAndCountrySameName_False() {
        NewCustomerDTO anotherNewCustomerDTO = new NewCustomerDTO();
        anotherNewCustomerDTO.taxIdentificationNumber = "123456789";
        anotherNewCustomerDTO.customerName = "Ulisses";
        anotherNewCustomerDTO.country = "other";

        boolean result = newCustomerDTO.equals(anotherNewCustomerDTO);

        assertFalse(result);
    }

    @Test
    void hashCode_True() {
        int expected = Objects.hashCode(newCustomerDTO);
        int result = newCustomerDTO.hashCode();

        assertEquals(expected, result);
    }
}