package org.switch2022.project.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.switch2022.project.datamodel.jpa.customer.CustomerJPA;

/**
 * Repository interface for managing CustomerJPA entities in the database.
 */
public interface CustomerRepositoryJPA extends CrudRepository<CustomerJPA, String> {
}
