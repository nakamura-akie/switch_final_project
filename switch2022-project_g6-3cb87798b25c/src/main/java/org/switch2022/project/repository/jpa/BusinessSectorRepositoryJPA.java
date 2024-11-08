package org.switch2022.project.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.switch2022.project.datamodel.BusinessSectorJPA;

/**
 * Repository interface for managing BusinessSectorJPA entities in the database.
 */
public interface BusinessSectorRepositoryJPA extends CrudRepository<BusinessSectorJPA, String> {
}
