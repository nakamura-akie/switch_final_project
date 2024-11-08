package org.switch2022.project.utils.assembler;

import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.Country;
import org.switch2022.project.domain.valueobject.CustomerName;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberInternationalImplementation;
import org.switch2022.project.utils.assembler.CustomerValueObjectAssembler;
import org.switch2022.project.utils.dto.CustomerValueObjectDTO;
import org.switch2022.project.utils.dto.NewCustomerDTO;

import static org.junit.jupiter.api.Assertions.*;

class CustomerValueObjectAssemblerTest {

    @Test
    void createCustomerValueObjectDTO_CorrectlyCreatesCustomerValueObjectDTO(){
        NewCustomerDTO newCustomerDTO = new NewCustomerDTO();
        newCustomerDTO.country = "other";
        newCustomerDTO.taxIdentificationNumber = "123456789";
        newCustomerDTO.customerName = "Joana";

        CustomerValueObjectDTO expected = new CustomerValueObjectDTO();
        expected.country = new Country("other");
        expected.taxIdentificationNumber =
                new TaxIdentificationNumberInternationalImplementation("123456789");
        expected.customerName = new CustomerName("Joana");

        CustomerValueObjectDTO result = CustomerValueObjectAssembler.createCustomerValueObjectDTO(newCustomerDTO);

        assertEquals(expected, result);
    }

    @Test
    void createCustomerValueObjectDTO_InvalidCountry_ThrowsException(){
        NewCustomerDTO newCustomerDTO = new NewCustomerDTO();
        newCustomerDTO.country = "not a country";
        newCustomerDTO.taxIdentificationNumber = "123456789";
        newCustomerDTO.customerName = "Joana";

        String result = assertThrows(Exception.class, () -> {
            CustomerValueObjectAssembler.createCustomerValueObjectDTO(newCustomerDTO);
        }).getMessage();

        //Assert
        assertEquals("Invalid tax identification number or country", result);
    }
}