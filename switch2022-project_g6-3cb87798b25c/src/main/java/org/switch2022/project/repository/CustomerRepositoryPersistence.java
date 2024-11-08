package org.switch2022.project.repository;

import org.springframework.context.annotation.Profile;
import org.switch2022.project.datamodel.assembler.CustomerDomainDataAssembler;
import org.switch2022.project.datamodel.jpa.customer.CustomerJPA;
import org.switch2022.project.domain.customer.Customer;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;
import org.switch2022.project.repository.interfaces.CustomerRepository;
import org.switch2022.project.repository.jpa.CustomerRepositoryJPA;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Persistence implementation of the {@link CustomerRepository} interface.
 * This implementation interacts with the underlying persistence layer using the
 * {@link CustomerRepositoryJPA} interface to store customers.
 * Note: This implementation will be used when the "!test" profile is active.
 */
@org.springframework.stereotype.Repository
@Profile("!test")
public class CustomerRepositoryPersistence implements CustomerRepository {

    CustomerRepositoryJPA customerRepositoryJPA;
    CustomerDomainDataAssembler customerAssembler;

    /**
     * Instantiates a new CustomerRepositoryPersistence
     *
     * @param customerRepositoryJPA the JPA repository
     * @param customerAssembler     the CustomerDomainDataAssembler
     * @throws IllegalArgumentException when customerRepositoryJPA or CustomerDomainDataAssembler are null
     */
    public CustomerRepositoryPersistence(CustomerRepositoryJPA customerRepositoryJPA,
                                         CustomerDomainDataAssembler customerAssembler) {
        if (customerRepositoryJPA == null || customerAssembler == null) {
            throw new IllegalArgumentException("Customer Repository JPA and Customer Assembler cannot be null.");
        }
        this.customerRepositoryJPA = customerRepositoryJPA;
        this.customerAssembler = customerAssembler;
    }

    /**
     * Saves a Customer but firstly converts it to CustomerJPA.
     *
     * @param domainCustomer the customer to be saved
     * @return the saved Customer
     */
    @Override
    public Customer save(Customer domainCustomer) {
        CustomerJPA customerJPA = customerAssembler.toData(domainCustomer);
        CustomerJPA savedCustomer = customerRepositoryJPA.save(customerJPA);
        return customerAssembler.toDomain(savedCustomer);
    }

    /**
     * Retrieves all the customers in the repository.
     *
     * @return an iterable collection of all customers in the repository
     */
    @Override
    public Iterable<Customer> findAll() {
        Iterable<CustomerJPA> listOfCustomerJPA = customerRepositoryJPA.findAll();

        List<Customer> listOfDomainCustomer = new ArrayList<>();

        for (CustomerJPA customerJPA : listOfCustomerJPA) {
            Customer domainCustomer = customerAssembler.toDomain(customerJPA);
            listOfDomainCustomer.add(domainCustomer);
        }
        return listOfDomainCustomer;
    }

    /**
     * Retrieves a customer with the given id, in this case a tax identification number
     *
     * @param taxIdentificationNumber the tax identification number to search for
     * @return the optional of the customer if it is found, an empty optional otherwise
     */
    @Override
    public Optional<Customer> findById(TaxIdentificationNumber taxIdentificationNumber) {
        Optional<CustomerJPA> maybeCustomer = customerRepositoryJPA
                .findById(taxIdentificationNumber.getTaxIdentificationNumber());

        if (maybeCustomer.isPresent()) {
            CustomerJPA customerJPA = maybeCustomer.get();
            Customer domainCustomer = customerAssembler.toDomain(customerJPA);
            return Optional.of(domainCustomer);
        }
        return Optional.empty();
    }

    /**
     * Confirms if a customer exists by its ID, in this case, a tax identification number
     *
     * @param taxIdentificationNumber the tax identification number to search for
     * @return true when the customer exists, false otherwise
     */
    @Override
    public boolean existsById(TaxIdentificationNumber taxIdentificationNumber) {
        return customerRepositoryJPA.existsById(taxIdentificationNumber.getTaxIdentificationNumber());
    }

    /**
     * Deletes all customers from the repository
     */
    @Override
    public void deleteAll() {
        customerRepositoryJPA.deleteAll();
    }

    /**
     * Deletes customer by its ID, in this case a tax identification number
     *
     * @param taxIdentificationNumber the tax identification number to search for
     */
    @Override
    public void deleteById(TaxIdentificationNumber taxIdentificationNumber) {
        customerRepositoryJPA.deleteById(taxIdentificationNumber.getTaxIdentificationNumber());
    }
}
