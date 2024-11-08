package org.switch2022.project.domain.customer;

import org.springframework.stereotype.Service;
import org.switch2022.project.domain.valueobject.Country;
import org.switch2022.project.domain.valueobject.CustomerName;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;

/**
 * The CustomerFactoryImplementation class is an implementation of the CustomerFactory interface.
 */
@Service
public class CustomerFactoryImplementation implements CustomerFactory {

    /**
     * Creates a Customer object with the specified parameters.
     *
     * @param taxIdentificationNumber the tax identification number
     * @param customerName            the customer name
     * @param country                 the customer country
     */
    @Override
    public Customer createCustomer(TaxIdentificationNumber taxIdentificationNumber,
                                   CustomerName customerName, Country country) {
        return new Customer(taxIdentificationNumber, customerName, country);
    }
}
