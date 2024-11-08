package org.switch2022.project.domain.customer;

import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.valueobject.Country;
import org.switch2022.project.domain.valueobject.CustomerName;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CustomerFactoryImplementationTest {

    @Test
    void createCustomer_CreateCustomerSuccess() {
        CustomerFactoryImplementation factoryImplementation = new CustomerFactoryImplementation();

        TaxIdentificationNumber taxIdentificationNumberDouble = mock(TaxIdentificationNumber.class);
        CustomerName customerNameDouble = mock(CustomerName.class);
        Country countryDouble = mock(Country.class);

        Customer result = factoryImplementation.createCustomer
                (taxIdentificationNumberDouble, customerNameDouble, countryDouble);

        assertEquals(taxIdentificationNumberDouble, result.getTaxIdentificationNumber());
        assertEquals(customerNameDouble, result.getCustomerName());
        assertEquals(countryDouble, result.getCountry());
    }
}