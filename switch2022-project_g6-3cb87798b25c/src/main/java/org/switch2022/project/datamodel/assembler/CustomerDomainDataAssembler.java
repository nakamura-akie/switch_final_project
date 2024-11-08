package org.switch2022.project.datamodel.assembler;

import org.springframework.stereotype.Component;
import org.switch2022.project.datamodel.jpa.customer.CustomerJPA;
import org.switch2022.project.domain.customer.Customer;
import org.switch2022.project.domain.customer.CustomerFactory;
import org.switch2022.project.domain.valueobject.Country;
import org.switch2022.project.domain.valueobject.CustomerName;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.ConfigurationFileReader;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;

/**
 * The CustomerDomainDataAssembler class is responsible for converting data between the domain model and the JPA entity
 * model for Customer objects.
 */
@Component
public class CustomerDomainDataAssembler {

    CustomerFactory customerFactory;

    /**
     * Constructs a CustomerDomainDataAssembler with the specified CustomerFactory.
     *
     * @param customerFactory the {@link CustomerFactory} to be used for creating Customers.
     */
    public CustomerDomainDataAssembler(CustomerFactory customerFactory) {
        if (customerFactory == null) {
            throw new IllegalArgumentException("Customer Factory cannot be null.");
        }
        this.customerFactory = customerFactory;
    }

    /**
     * Converts a Customer domain object to its corresponding {@link CustomerJPA} entity.
     *
     * @param customer the Customer domain object to be converted.
     * @return the corresponding JPA entity.
     */
    public CustomerJPA toData(Customer customer) {
        String taxIdentificationNumber = customer.getTaxIdentificationNumber().getTaxIdentificationNumber();
        String customerName = customer.getCustomerName().getCustomerNameValue();
        String country = customer.getCountry().getCountryName();

        return new CustomerJPA(taxIdentificationNumber, customerName, country);
    }

    /**
     * Converts a CustomerJPA entity to its corresponding {@link Customer} domain object.
     *
     * @param customerJpa the JPA entity to be converted.
     * @return the corresponding Account domain object.
     */
    public Customer toDomain(CustomerJPA customerJpa) {
        String countryValue = customerJpa.getCountry();

        try {
            ConfigurationFileReader configList = new ConfigurationFileReader();

            String classNameString = configList.properties.getProperty(countryValue);

            TaxIdentificationNumber taxIdentificationNumber =
                    (TaxIdentificationNumber) Class.forName(classNameString).
                            getDeclaredConstructor(String.class).newInstance(customerJpa.getTaxIdentificationNumber());

            CustomerName customerName = new CustomerName(customerJpa.getCustomerName());

            Country country = new Country(countryValue);

            return customerFactory.createCustomer(taxIdentificationNumber, customerName, country);

        } catch (Exception e) {
            throw new RuntimeException("Could not create tax identification number.");
        }
    }
}
