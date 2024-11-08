package org.switch2022.project.repository.jpa;

import org.springframework.data.repository.CrudRepository;
import org.switch2022.project.datamodel.jpa.profile.ProfileJPA;

/**
 * Repository interface for managing ProfileJPA entities in the database.
 */
public interface ProfileRepositoryJPA extends CrudRepository<ProfileJPA, String> {
}
