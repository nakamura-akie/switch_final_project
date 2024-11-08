package org.switch2022.project.domain.customer;

import org.switch2022.project.domain.valueobject.Country;
import org.switch2022.project.domain.valueobject.CustomerName;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;

/**
 * The CustomerFactory interface defines methods for creating Customer objects.
 */
public interface CustomerFactory {

    /**
     * Creates a Customer object with the specified parameters.
     *
     * @param taxIdentificationNumber the tax identification number
     * @param customerName            the customer name
     * @param country                 the customer country
     */
    Customer createCustomer(TaxIdentificationNumber taxIdentificationNumber,
                            CustomerName customerName, Country country);
}
