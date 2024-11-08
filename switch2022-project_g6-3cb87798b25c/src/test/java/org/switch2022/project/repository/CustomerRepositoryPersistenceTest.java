package org.switch2022.project.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.switch2022.project.datamodel.assembler.CustomerDomainDataAssembler;
import org.switch2022.project.datamodel.jpa.customer.CustomerJPA;
import org.switch2022.project.domain.customer.Customer;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;
import org.switch2022.project.repository.jpa.CustomerRepositoryJPA;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomerRepositoryPersistenceTest {

    private static CustomerRepositoryPersistence customerRepositoryPersistence;
    private static CustomerRepositoryJPA jpaRepositoryDouble;
    private static CustomerDomainDataAssembler customerDomainDataAssemblerDouble;
    private static Customer customerOneDouble;
    private static CustomerJPA customerJPAOneDouble;
    private static TaxIdentificationNumber taxIdentificationNumberOneDouble;

    @BeforeAll
    static void setup() {
        jpaRepositoryDouble = mock(CustomerRepositoryJPA.class);
        customerDomainDataAssemblerDouble = mock(CustomerDomainDataAssembler.class);

        customerRepositoryPersistence = new CustomerRepositoryPersistence(jpaRepositoryDouble,
                customerDomainDataAssemblerDouble);

        customerOneDouble = mock(Customer.class);

        customerJPAOneDouble = mock(CustomerJPA.class);

        taxIdentificationNumberOneDouble = mock(TaxIdentificationNumber.class);

        when(customerDomainDataAssemblerDouble.toData(customerOneDouble)).thenReturn(customerJPAOneDouble);
        when(customerDomainDataAssemblerDouble.toDomain(customerJPAOneDouble)).thenReturn(customerOneDouble);

        when(taxIdentificationNumberOneDouble.getTaxIdentificationNumber()).thenReturn("255738706");
    }

    @AfterAll
    static void tearDown() {
        customerRepositoryPersistence = null;
    }

    @Test
    void constructor_NullJPARepository_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new CustomerRepositoryPersistence(null, customerDomainDataAssemblerDouble));

        assertEquals("Customer Repository JPA and Customer Assembler cannot be null.",
                exception.getMessage());
    }

    @Test
    void constructor_NullAssembler_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> new CustomerRepositoryPersistence(jpaRepositoryDouble, null));

        assertEquals("Customer Repository JPA and Customer Assembler cannot be null.",
                exception.getMessage());
    }

    @Test
    void save_CorrectlySavesCustomer_True() {
        when(jpaRepositoryDouble.save(customerJPAOneDouble)).thenReturn(customerJPAOneDouble);

        Customer result = customerRepositoryPersistence.save(customerOneDouble);

        assertEquals(customerOneDouble, result);
    }

    @Test
    void findAll_ReturnsListOfCustomers_True() {
        List<CustomerJPA> listOfCustomerJPA = new ArrayList<>();
        listOfCustomerJPA.add(customerJPAOneDouble);

        when(jpaRepositoryDouble.findAll()).thenReturn(listOfCustomerJPA);

        List<Customer> listOfDomainCustomer = new ArrayList<>();
        listOfDomainCustomer.add(customerOneDouble);

        Iterable<Customer> expected = listOfDomainCustomer;

        Iterable<Customer> result = customerRepositoryPersistence.findAll();

        assertEquals(expected, result);
    }

    @Test
    void findById_CustomerExists_True() {
        when(jpaRepositoryDouble.findById("255738706")).thenReturn(Optional.of(customerJPAOneDouble));

        Optional<Customer> expected = Optional.of(customerOneDouble);

        Optional<Customer> result = customerRepositoryPersistence.findById(taxIdentificationNumberOneDouble);

        assertEquals(expected, result);
    }

    @Test
    void findById_CustomerDoesNotExist_EmptyOptional() {
        TaxIdentificationNumber nonxistingNIF = mock(TaxIdentificationNumber.class);
        Optional<Customer> expected = Optional.empty();

        Optional<Customer> result = customerRepositoryPersistence.findById(nonxistingNIF);

        assertEquals(expected, result);
    }

    @Test
    void existsById_ExistingCustomer_True() {
        when(jpaRepositoryDouble.existsById("255738706")).thenReturn(true);

        boolean result = customerRepositoryPersistence.existsById(taxIdentificationNumberOneDouble);

        assertTrue(result);
    }

    @Test
    void existsById_CustomerDoesNotExist_False() {
        when(jpaRepositoryDouble.existsById("255738706")).thenReturn(false);

        boolean result = customerRepositoryPersistence.existsById(taxIdentificationNumberOneDouble);

        assertFalse(result);
    }

    @Test
    void deleteAll() {
        customerRepositoryPersistence.deleteAll();
        verify(jpaRepositoryDouble).deleteAll();
    }

    @Test
    void deleteById() {
        customerRepositoryPersistence.deleteById(taxIdentificationNumberOneDouble);
        verify(jpaRepositoryDouble).deleteById("255738706");
    }
}