package org.switch2022.project.utils.dto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.Country;
import org.switch2022.project.domain.valueobject.CustomerName;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberInternationalImplementation;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberPortugalImplementation;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CustomerValueObjectDTOTest {
    private static CustomerValueObjectDTO customerValueObjectDTO;

    @BeforeAll
    static void setup() {
        customerValueObjectDTO = new CustomerValueObjectDTO();
        customerValueObjectDTO.taxIdentificationNumber =
                new TaxIdentificationNumberPortugalImplementation("248842080");
        customerValueObjectDTO.customerName = new CustomerName("Ana");
        customerValueObjectDTO.country = new Country("portugal");
    }

    @AfterAll
    static void tearDown() {
        customerValueObjectDTO = null;
    }


    @Test
    void equals_SameObject_True() {
        CustomerValueObjectDTO sameDTO = customerValueObjectDTO;

        boolean result = customerValueObjectDTO.equals(sameDTO);

        assertTrue(result);
    }

    @Test
    void equals_ObjectIsNull_False() {
        CustomerValueObjectDTO nullObject = null;

        boolean result = customerValueObjectDTO.equals(nullObject);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfDifferentClass_False() {
        String differentClass = "not a DTO";

        boolean result = customerValueObjectDTO.equals(differentClass);

        assertFalse(result);
    }

    @Test
    void equals_DifferentTIN_False() {
        CustomerValueObjectDTO differentDTO = new CustomerValueObjectDTO();
        differentDTO.taxIdentificationNumber =
                new TaxIdentificationNumberInternationalImplementation("123456789");
        differentDTO.country = new Country("portugal");
        differentDTO.customerName = new CustomerName("Ana");

        boolean result = customerValueObjectDTO.equals(differentDTO);

        assertFalse(result);
    }

    @Test
    void equals_DifferentName_False() {
        CustomerValueObjectDTO differentDTO = new CustomerValueObjectDTO();
        differentDTO.taxIdentificationNumber =
                new TaxIdentificationNumberInternationalImplementation("248842080");
        differentDTO.country = new Country("portugal");
        differentDTO.customerName = new CustomerName("John");

        boolean result = customerValueObjectDTO.equals(differentDTO);

        assertFalse(result);
    }

    @Test
    void equals_DifferentCountry_False() {
        CustomerValueObjectDTO differentDTO = new CustomerValueObjectDTO();
        differentDTO.taxIdentificationNumber =
                new TaxIdentificationNumberInternationalImplementation("248842080");
        differentDTO.country = new Country("other");
        differentDTO.customerName = new CustomerName("Ana");

        boolean result = customerValueObjectDTO.equals(differentDTO);

        assertFalse(result);
    }

    @Test
    void equals_DifferentCountryAndTINButSameName_False() {
        CustomerValueObjectDTO differentDTO = new CustomerValueObjectDTO();
        differentDTO.taxIdentificationNumber =
                new TaxIdentificationNumberInternationalImplementation("333444555");
        differentDTO.country = new Country("other");
        differentDTO.customerName = new CustomerName("Ana");

        boolean result = customerValueObjectDTO.equals(differentDTO);

        assertFalse(result);
    }

    @Test
    void equals_DifferentCountryAndNameButSameTIN_False() {
        CustomerValueObjectDTO differentDTO = new CustomerValueObjectDTO();
        differentDTO.taxIdentificationNumber =
                new TaxIdentificationNumberInternationalImplementation("248842080");
        differentDTO.country = new Country("other");
        differentDTO.customerName = new CustomerName("Manuel");

        boolean result = customerValueObjectDTO.equals(differentDTO);

        assertFalse(result);
    }

    @Test
    void equals_DifferentTINAndNameButSameCountry_False() {
        CustomerValueObjectDTO differentDTO = new CustomerValueObjectDTO();
        differentDTO.taxIdentificationNumber =
                new TaxIdentificationNumberInternationalImplementation("201664305");
        differentDTO.country = new Country("portugal");
        differentDTO.customerName = new CustomerName("Manuel");

        boolean result = customerValueObjectDTO.equals(differentDTO);

        assertFalse(result);
    }

    @Test
    void equals_EverythingDifferent_False() {
        CustomerValueObjectDTO differentDTO = new CustomerValueObjectDTO();
        differentDTO.taxIdentificationNumber =
                new TaxIdentificationNumberInternationalImplementation("201664305");
        differentDTO.country = new Country("other");
        differentDTO.customerName = new CustomerName("Manuel");

        boolean result = customerValueObjectDTO.equals(differentDTO);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfSameClassAndSameValue_True() {
        CustomerValueObjectDTO anotherDTO = new CustomerValueObjectDTO();
        anotherDTO.taxIdentificationNumber =
                new TaxIdentificationNumberPortugalImplementation("248842080");
        anotherDTO.customerName = new CustomerName("Ana");
        anotherDTO.country = new Country("portugal");

        boolean result = customerValueObjectDTO.equals(anotherDTO);

        assertTrue(result);
    }

    @Test
    void hashCode_True() {
        int expected = Objects.hashCode(customerValueObjectDTO);
        int result = customerValueObjectDTO.hashCode();

        assertEquals(expected, result);
    }

}