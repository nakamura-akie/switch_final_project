package org.switch2022.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.customer.Customer;
import org.switch2022.project.domain.customer.CustomerFactory;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.ConfigurationFileReader;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;
import org.switch2022.project.utils.dto.CustomerValueObjectDTO;
import org.switch2022.project.utils.assembler.OutputCustomerAssembler;
import org.switch2022.project.utils.dto.OutputCustomerDTO;

import java.util.Optional;

@Service
public class CustomerService {

    private final Repository<Customer, TaxIdentificationNumber> customerRepository;
    private final CustomerFactory customerFactory;

    /**
     * Constructs a new instance of the CustomerService class with the specified parameters.
     *
     * @param customerRepository The repository used for accessing and managing customer data.
     *                           Must not be null.
     * @param customerFactory    The factory used for creating new instances of Customer objects.
     *                           Must not be null.
     * @throws IllegalArgumentException If either customerRepository or customerFactory is null.
     */
    @Autowired
    public CustomerService(Repository<Customer, TaxIdentificationNumber> customerRepository,
                           CustomerFactory customerFactory) {

        if (customerRepository == null) {
            throw new IllegalArgumentException("Customer Repository cannot be null");
        }


        if (customerFactory == null) {
            throw new IllegalArgumentException("Customer Factory cannot be null");
        }

        this.customerRepository = customerRepository;
        this.customerFactory = customerFactory;
    }

    /**
     * Creates a new customer based on the provided information and returns a DTO (Data Transfer Object)
     * representing the created customer.
     *
     * @param customerValueObjectDTO The initial DTO containing the information for the new customer.
     * @return The DTO representing the created customer.
     * @throws IllegalArgumentException If a customer with the same tax identification number already exists.
     */
    public OutputCustomerDTO createCustomer(CustomerValueObjectDTO customerValueObjectDTO) {

        boolean customerExists = customerRepository.existsById(customerValueObjectDTO.taxIdentificationNumber);

        if (customerExists) {
            throw new IllegalArgumentException("Customer already exists");
        }

        Customer newCustomer = customerFactory.createCustomer(customerValueObjectDTO.taxIdentificationNumber,
                customerValueObjectDTO.customerName,
                customerValueObjectDTO.country);
        Customer savedCustomer = customerRepository.save(newCustomer);

        return OutputCustomerAssembler.createOutputCustomerDTO(savedCustomer);
    }

    /**
     * Finds a customer based on the provided tax identification number and country, and returns a DTO
     * (Data Transfer Object) representing the customer if found.
     *
     * @param taxIdentificationNumber The tax identification number of the customer.
     * @param country                 The country associated with the customer.
     * @return                        The DTO representing the found customer.
     * @throws IllegalArgumentException If the customer does not exist.
     * @throws RuntimeException         If an exception occurs during the process of finding the customer.
     */
    public OutputCustomerDTO findCustomer(String taxIdentificationNumber, String country) {
        try {
            ConfigurationFileReader configList = new ConfigurationFileReader();

            String classNameString = configList.properties.getProperty(country);

            TaxIdentificationNumber taxIdentificationNumberValueObject =
                    (TaxIdentificationNumber) Class.forName(classNameString).
                            getDeclaredConstructor(String.class).newInstance(taxIdentificationNumber);

            Optional<Customer> customer = customerRepository.findById(taxIdentificationNumberValueObject);

            if (customer.isEmpty()) {
                throw new IllegalArgumentException("Customer not found");
            }
            Customer existingCustomer = customer.get();
            return OutputCustomerAssembler.createOutputCustomerDTO(existingCustomer);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
