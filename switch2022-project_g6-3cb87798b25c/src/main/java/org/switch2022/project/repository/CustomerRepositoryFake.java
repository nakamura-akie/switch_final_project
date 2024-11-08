package org.switch2022.project.repository;

import org.springframework.context.annotation.Profile;
import org.switch2022.project.domain.customer.Customer;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;
import org.switch2022.project.repository.interfaces.CustomerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * A fake implementation of the {@link CustomerRepository} interface that uses an in-memory list to store
 * {@link Customer} entities.
 * Note: This implementation will be used when the "test" profile is active.
 */
@org.springframework.stereotype.Repository
@Profile("test")
public class CustomerRepositoryFake implements CustomerRepository {

    private static final List<Customer> customerList = new ArrayList<>();

    /**
     * Saves a customer to the repository, if it is not already presen.
     *
     * @param customer the customer to be saved
     * @return the saved customer
     * @throws IllegalArgumentException if the customer already exists in the database
     */
    @Override
    public Customer save(Customer customer) {
        if (customerList.contains(customer)) {
            throw new IllegalArgumentException("Customer already exists.");
        }
        customerList.add(customer);
        return customer;
    }

    /**
     * Retrieves all the customers from the repository.
     *
     * @return an iterable collection of all customers
     */
    @Override
    public Iterable<Customer> findAll() {
        return new ArrayList<>(customerList);
    }

    /**
     * Retrieves a customer with the given tax identification number.
     *
     * @param taxIdentificationNumber the tax identification number to search for
     * @return the optional of the customer when it is found, an empty optional otherwise
     */
    @Override
    public Optional<Customer> findById(TaxIdentificationNumber taxIdentificationNumber) {
        for (Customer customer : customerList) {
            if (customer.identity().equals(taxIdentificationNumber)) {
                return Optional.of(customer);
            }
        }
        return Optional.empty();
    }

    /**
     * Confirms if a customer exists with a given tax identification number
     *
     * @param taxIdentificationNumber the tax identification number to search for
     * @return true when the customer exists
     * Returns false when the customer does not exist
     */
    @Override
    public boolean existsById(TaxIdentificationNumber taxIdentificationNumber) {
        for (Customer customer : customerList) {
            if (customer.identity().equals(taxIdentificationNumber)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Deletes all customers from the repository
     */
    @Override
    public void deleteAll() {
        customerList.clear();
    }

    /**
     * Deletes customers with a given id, in this case a taxIdentificationNumber.
     *
     * @param id the tax identification number to search for
     * @throws UnsupportedOperationException when method is called because it is not implemented yet
     */
    @Override
    public void deleteById(TaxIdentificationNumber id) {
        throw new UnsupportedOperationException("Customer Repository doesn't support the deleteByID method yet");
    }
}
