package org.switch2022.project.repository.interfaces;

import org.switch2022.project.ddd.Repository;
import org.switch2022.project.domain.customer.Customer;
import org.switch2022.project.domain.valueobject.taxidentificationnumber.TaxIdentificationNumber;

/**
 * Repository interface for managing Customer entities.
 */
public interface CustomerRepository extends Repository<Customer, TaxIdentificationNumber> {
}
