package org.switch2022.project.utils.dto;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.utils.dto.OutputCustomerDTO;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class OutputCustomerDTOTest {
    private static OutputCustomerDTO outputCustomerDTO;

    @BeforeAll
    static void setup() {
        outputCustomerDTO = new OutputCustomerDTO();
        outputCustomerDTO.taxIdentificationNumber = "276002466";
        outputCustomerDTO.customerName = "Manuel";
        outputCustomerDTO.country = "portugal";
    }

    @AfterAll
    static void tearDown() {
        outputCustomerDTO = null;
    }

    @Test
    void equals_SameObject_True() {
        OutputCustomerDTO anotherCustomer = outputCustomerDTO;

        boolean result = outputCustomerDTO.equals(anotherCustomer);

        assertTrue(result);
    }

    @Test
    void equals_ObjectIsNull_False() {
        OutputCustomerDTO nullObject = null;

        boolean result = outputCustomerDTO.equals(nullObject);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfDifferentClass_False() {
        String differentClass = "not an output customer DTO";

        boolean result = outputCustomerDTO.equals(differentClass);

        assertFalse(result);
    }

    @Test
    void equals_ObjectsOfSameClassButDifferentValue_False() {
        OutputCustomerDTO differentOutputCustomerDTO = new OutputCustomerDTO();
        differentOutputCustomerDTO.taxIdentificationNumber = "264904419";
        differentOutputCustomerDTO.customerName = "Juan";
        differentOutputCustomerDTO.country = "spain";

        boolean result = outputCustomerDTO.equals(differentOutputCustomerDTO);

        assertFalse(result);
    }

    @Test
    void equals_ObjectOfSameClassAndSameValue_True() {
        OutputCustomerDTO differentOutputCustomerDTO = new OutputCustomerDTO();
        differentOutputCustomerDTO.taxIdentificationNumber = "276002466";
        differentOutputCustomerDTO.customerName = "Manuel";
        differentOutputCustomerDTO.country = "portugal";

        boolean result = outputCustomerDTO.equals(differentOutputCustomerDTO);

        assertTrue(result);
    }

    @Test
    void equals_DifferentTINButSameNameAndCountry_True() {
        OutputCustomerDTO differentOutputCustomerDTO = new OutputCustomerDTO();
        differentOutputCustomerDTO.taxIdentificationNumber = "251653110";
        differentOutputCustomerDTO.customerName = "Manuel";
        differentOutputCustomerDTO.country = "portugal";

        boolean result = outputCustomerDTO.equals(differentOutputCustomerDTO);

        assertFalse(result);
    }

    @Test
    void equals_DifferentNameButSameTINAndCountry_True() {
        OutputCustomerDTO differentOutputCustomerDTO = new OutputCustomerDTO();
        differentOutputCustomerDTO.taxIdentificationNumber = "276002466";
        differentOutputCustomerDTO.customerName = "Luísa";
        differentOutputCustomerDTO.country = "portugal";

        boolean result = outputCustomerDTO.equals(differentOutputCustomerDTO);

        assertFalse(result);
    }

    @Test
    void equals_DifferentCountryButSameTINAndName_True() {
        OutputCustomerDTO differentOutputCustomerDTO = new OutputCustomerDTO();
        differentOutputCustomerDTO.taxIdentificationNumber = "276002466";
        differentOutputCustomerDTO.customerName = "Manuel";
        differentOutputCustomerDTO.country = "other";

        boolean result = outputCustomerDTO.equals(differentOutputCustomerDTO);

        assertFalse(result);
    }

    @Test
    void equals_DifferentCountryAndTINButSameName_True() {
        OutputCustomerDTO differentOutputCustomerDTO = new OutputCustomerDTO();
        differentOutputCustomerDTO.taxIdentificationNumber = "222222222";
        differentOutputCustomerDTO.customerName = "Manuel";
        differentOutputCustomerDTO.country = "other";

        boolean result = outputCustomerDTO.equals(differentOutputCustomerDTO);

        assertFalse(result);
    }

    @Test
    void equals_DifferentCountryAndNameButSameTIN_True() {
        OutputCustomerDTO differentOutputCustomerDTO = new OutputCustomerDTO();
        differentOutputCustomerDTO.taxIdentificationNumber = "276002466";
        differentOutputCustomerDTO.customerName = "Joana";
        differentOutputCustomerDTO.country = "other";

        boolean result = outputCustomerDTO.equals(differentOutputCustomerDTO);

        assertFalse(result);
    }

    @Test
    void hashCode_True() {
        OutputCustomerDTO outputCustomerDTO = new OutputCustomerDTO();
        outputCustomerDTO.taxIdentificationNumber = "276002466";
        outputCustomerDTO.customerName = "Manuel";
        outputCustomerDTO.country = "portugal";

        int expected = Objects.hashCode(outputCustomerDTO);
        int result = outputCustomerDTO.hashCode();

        assertEquals(expected, result);
    }

}