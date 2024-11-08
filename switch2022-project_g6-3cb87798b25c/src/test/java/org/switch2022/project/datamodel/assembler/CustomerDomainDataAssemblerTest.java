package org.switch2022.project.datamodel.assembler;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.datamodel.jpa.customer.CustomerJPA;
import org.switch2022.project.domain.customer.Customer;
import org.switch2022.project.domain.customer.CustomerFactory;
import org.switch2022.project.domain.valueobject.Country;
import org.switch2022.project.domain.valueobject.CustomerName;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberPortugalImplementation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerDomainDataAssemblerTest {

    private static CustomerDomainDataAssembler customerDomainDataAssembler;
    private static Customer customerDouble;
    private static CustomerJPA customerJPA;

    @BeforeAll
    static void setup() {
        CustomerFactory customerFactoryDouble = mock(CustomerFactory.class);
        customerDouble = mock(Customer.class);

        customerDomainDataAssembler = new CustomerDomainDataAssembler(customerFactoryDouble);

        TaxIdentificationNumber taxIdentificationNumber =
                new TaxIdentificationNumberPortugalImplementation("204904315");

        CustomerName customerName = new CustomerName("Matilde");
        Country country = new Country("portugal");

        customerJPA = new CustomerJPA("204904315", "Matilde", "portugal");

        when(customerDouble.getTaxIdentificationNumber()).thenReturn(taxIdentificationNumber);
        when(customerDouble.getCustomerName()).thenReturn(customerName);
        when(customerDouble.getCountry()).thenReturn(country);

        when(customerFactoryDouble.createCustomer(taxIdentificationNumber, customerName, country))
                .thenReturn(customerDouble);

    }

    @AfterAll
    static void tearDown() {
        customerDomainDataAssembler = null;
        customerDouble = null;
        customerJPA = null;
    }

    @Test
    void constructor_NullCustomerFactory_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                new CustomerDomainDataAssembler(null));

        assertEquals("Customer Factory cannot be null.", exception.getMessage());
    }

    @Test
    void toData_ConvertsCustomerToDataCorrectly_True() {
        CustomerJPA result = customerDomainDataAssembler.toData(customerDouble);

        assertEquals(customerJPA, result);
    }

    @Test
    void toDomain_ConvertsCustomerJPAToDataCorrectly_True() {
        Customer result = customerDomainDataAssembler.toDomain(customerJPA);

        assertEquals(customerDouble, result);
    }

    @Test
    void toDomain_NonExistingCountry_ThrowsException() {
        CustomerJPA anotherCustomerJPA = new CustomerJPA
                ("204904315", "Matilde", "non-existing country");
        Exception exception = assertThrows(RuntimeException.class, () ->
                customerDomainDataAssembler.toDomain(anotherCustomerJPA));

        assertEquals("Could not create tax identification number.", exception.getMessage());
    }
}