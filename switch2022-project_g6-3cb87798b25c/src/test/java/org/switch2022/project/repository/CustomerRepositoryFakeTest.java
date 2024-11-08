package org.switch2022.project.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.switch2022.project.domain.customer.Customer;
import org.switch2022.project.domain.valueobject.CustomerName;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumberInternationalImplementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CustomerRepositoryFakeTest {

    CustomerRepositoryFake customerRepositoryFake;
    Customer customerOneDouble;
    Customer customerTwoDouble;
    TaxIdentificationNumber taxIdentificationNumberOneDouble;
    TaxIdentificationNumber taxIdentificationNumberTwoDouble;
    CustomerName customerNameOneDouble;
    CustomerName customerNameTwoDouble;

    @BeforeEach
    void init() {
        customerOneDouble = mock(Customer.class);
        customerTwoDouble = mock(Customer.class);

        customerRepositoryFake = new CustomerRepositoryFake();

        taxIdentificationNumberOneDouble = mock(TaxIdentificationNumber.class);
        taxIdentificationNumberTwoDouble = mock(TaxIdentificationNumber.class);

        customerNameOneDouble = mock(CustomerName.class);
        customerNameTwoDouble = mock(CustomerName.class);

        when(customerOneDouble.identity()).thenReturn(taxIdentificationNumberOneDouble);
        when(customerTwoDouble.identity()).thenReturn(taxIdentificationNumberTwoDouble);

    }

    @AfterEach
    void tearDown() {
        customerOneDouble = null;
        customerTwoDouble = null;
        taxIdentificationNumberOneDouble = null;
        taxIdentificationNumberTwoDouble = null;
        customerNameOneDouble = null;
        customerNameTwoDouble = null;
        customerRepositoryFake.deleteAll();
    }

    @Test
    void save_SuccessfullySavesCustomer_True() {
        Customer result = customerRepositoryFake.save(customerOneDouble);

        assertEquals(customerOneDouble, result);
    }

    @Test
    void save_CustomerAlreadyExists_ThrowsException() {
        customerRepositoryFake.save(customerOneDouble);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                customerRepositoryFake.save(customerOneDouble));

        assertEquals("Customer already exists.", exception.getMessage());

    }

    @Test
    void findAll_SuccessfullyRetrievesAllCustomers_True() {
        customerRepositoryFake.save(customerOneDouble);
        customerRepositoryFake.save(customerTwoDouble);

        List<Customer> expectedList = new ArrayList<>();
        expectedList.add(customerOneDouble);
        expectedList.add(customerTwoDouble);

        Iterable<Customer> expected = expectedList;

        Iterable<Customer> result = customerRepositoryFake.findAll();

        assertEquals(expected, result);
    }

    @Test
    void findByID_SuccessfullyReturnsCustomer_True() {
        customerRepositoryFake.save(customerOneDouble);
        customerRepositoryFake.save(customerTwoDouble);

        Optional<Customer> maybeCustomer = customerRepositoryFake.findById(taxIdentificationNumberOneDouble);

        Customer customer = maybeCustomer.get();

        boolean result = customer.equals(customerOneDouble);

        assertTrue(result);
    }

    @Test
    void findByID_EmptyRepository_EmptyOptional() {
        Optional<Customer> emptyOptional = Optional.empty();

        Optional<Customer> maybeCustomer = customerRepositoryFake.findById(taxIdentificationNumberOneDouble);

        boolean result = maybeCustomer.equals(emptyOptional);
        assertTrue(result);
    }

    @Test
    void findByID_CustomerDoesNotExist_True() {
        customerRepositoryFake.save(customerOneDouble);
        customerRepositoryFake.save(customerTwoDouble);

        Optional<Customer> maybeCustomer =
                customerRepositoryFake.findById
                        (new TaxIdentificationNumberInternationalImplementation("555555558"));


        boolean result = maybeCustomer.equals(Optional.empty());

        assertTrue(result);
    }

    @Test
    void existsByID_CustomerExists_True() {
        customerRepositoryFake.save(customerOneDouble);

        boolean result = customerRepositoryFake.existsById(taxIdentificationNumberOneDouble);

        assertTrue(result);
    }

    @Test
    void existsByID_CustomerDoesNotExist_False() {
        customerRepositoryFake.save(customerOneDouble);

        boolean result = customerRepositoryFake.existsById(taxIdentificationNumberTwoDouble);

        assertFalse(result);
    }

    @Test
    void deleteAll_RepositoryIsEmptyAfterDeleteAll_True() {
        customerRepositoryFake.save(customerOneDouble);

        List<Customer> expectedList = new ArrayList<>();
        Iterable<Customer> expected = expectedList;

        customerRepositoryFake.deleteAll();
        Iterable<Customer> result = customerRepositoryFake.findAll();

        assertEquals(expected, result);
    }

    @Test
    void deleteByID_throwsException_True() {
        Exception exception = assertThrows(UnsupportedOperationException.class, () ->
                customerRepositoryFake.deleteById(taxIdentificationNumberOneDouble));

        assertEquals("Customer Repository doesn't support the deleteByID method yet", exception.getMessage());
    }
}